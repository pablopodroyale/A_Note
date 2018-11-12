package utils.files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class IniManager {
	private static final String ERROR_SECCION_INVALIDA = "Error, la seccion no es valida";
	private static final String ERROR_ARGUMENTO_VACIO = "Error, agregue un nombre valido";
	// private static final String ERROR_ARCHIVO_INEXISTENTE = "Error, archivo
	// inexistente";
	private String fileSource;
	private String fileDestinyName;

	private HashMap<String, Section> sections;

	// Consructor para inicializar variables
	public IniManager() {
		this.sections = new LinkedHashMap<String, Section>();
	}

	// Constructor para leer un archivo
	public IniManager(String fileDestiny) {
		this();
		this.fileDestinyName = fileDestiny;
		// createWriter(fileDestiny);
	}

	// Constructor para leer y guardar en otro
	public IniManager(String fileSource, String fileDestiny) {
		this(fileDestiny);
		this.fileSource = fileSource;
		this.fileDestinyName = fileDestiny;
		// setFileName(fileSource);
	}

	/***
	 * Cosntructor con paramettro dummy = 0 para sobrecarga que ya existe
	 * 
	 * @param fileSource
	 * @param dummy
	 */
	public IniManager(String fileSource, int dummy) {
		this();
		this.fileSource = fileSource;
	}

	private TextWriter createWriter(String fileDestiny, boolean append) {
		// File file = new File(fileDestiny);
		TextWriter writer = null;
		try {
			writer = new BufferedTextWriter(fileDestiny, append);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return writer;
	}

	/*
	 * private void setFileName(String fileDestiny) { this.fileDestinyName =
	 * fileDestiny; }
	 */

	private TextReader createReader(String sourceFile) {
		return new BufferedTextReader(sourceFile);
	}

	public String getPath() {
		return this.fileDestinyName;

	}

	public int getSectionCount() {
		return sections.keySet().size();

	}

	public void listLoad() {
		load();
		list();
	}

	public void list() {
		// load();
		sections.forEach((key, value) -> {
			System.out.println(key);
			value.list();
		});
	}

	public void setItem(String section, String key, String value) {
		Section s = sections.get(section);
		if (s != null) {
			s.setItem(key, value);
		} else {
			throw new IllegalArgumentException(ERROR_SECCION_INVALIDA);
		}
	}

	/***
	 * 
	 * @param section
	 * @param value
	 * @return
	 */
	public String getValueOf(String section, String value) {
		Section sectionAux = sections.get(section);
		String val = null;
		if (sectionAux != null) {
			val = sectionAux.getValue(value);
		} else {
			throw new IllegalArgumentException(ERROR_SECCION_INVALIDA);
		}
		return val;

	}

	public Section removeSection(String section) {
		Section sec = null;
		if (section.contains(section)) {
			sec = sections.remove(section);
		} else {
			throw new IllegalArgumentException(ERROR_SECCION_INVALIDA);
		}
		return sec;

	}

	public boolean removeItem(String section, String item) {
		return sections.get(section).remove(item);

	}

	public void save(boolean append) {
		TextWriter writer = createWriter(this.fileDestinyName, append);
		sections.forEach((key, value) -> {
			writer.writeLine(createSection(key));
			writer.newLine();
			value.save(writer, this.fileDestinyName);
		});
		writer.close();
	}

	public String createSection(String key) {
		// TODO Auto-generated method stub
		return "[" + key + "]";
	}

	public void load() {
		TextReader reader = createReader(this.fileSource);
		String linea = " ";
		Section section = null;
		String nombreSeccion;
		while (reader.isReady()) {
			linea = reader.readLine();
			if (isSection(linea)) {
				// section = getSection(linea);
				nombreSeccion = getNombre(linea);
				section = new Section(nombreSeccion);
				sections.put(nombreSeccion, section);

			} else if (isComment(linea)) {
				section.setItem(linea);
			} else if (isItem(linea)) {
				section.setItem(getKey(linea), getValue(linea));
			}
		}
		reader.close();
	}

	private String getValue(String linea) {
		String parts[] = linea.split("=");
		String ret;
		if (parts.length == 1) {
			ret = " ";
		} else {
			ret = parts[1];
		}
		return ret;
	}

	private String getKey(String linea) {
		return linea.split("=")[0];
	}

	private String getNombre(String linea) {
		return linea.substring(1, linea.length() - 1);
	}

	public Section getSection(String name) {
		return sections.get(name);
	}

	/**
	 * Devuelve el nombre de una seccion si existe
	 * 
	 * @param environment
	 * @return
	 */
	public String getValueof(String environment) {
		return getSection(environment).getName();
	}

	/*
	 * private String getSectionName(String name) { return
	 * sections.get(name).getName();
	 * 
	 * }
	 */

	private boolean isSection(String section) {
		String pattern = "\\[\\w[\\w ]*\\]";
		return section.matches(pattern);
	}

	private boolean isItem(String item) {
		String pattern = "[a-z]\\w* *=.*";
		return item.toLowerCase().matches(pattern);

	}

	private boolean isComment(String comment) {
		return comment.startsWith(";");

	}

	public void addSection(String nombre) {
		if (nombre.isEmpty() || nombre == null) {
			throw new IllegalArgumentException(ERROR_ARGUMENTO_VACIO);
		} else {
			Section section = new Section(nombre);
			sections.put(nombre, section);
		}

	}

	public void setPathDestiny(String pathIni) {
		this.fileDestinyName = pathIni;

	}

	public void setPathSource(String sourcePath) {
		this.fileSource = sourcePath;

	}

	public void updateSection(String nombreAnterior, String nuevoNombre) {
		sections.put(nuevoNombre, sections.remove(nombreAnterior));

	}

	public void clear() {
		this.sections.clear();

	}

	public String getSectionName(String default1) {
		return sections.get(default1).getName();
	}

	public void updateItem(String environment, String attribute, String value) {
		Section section = getSection(environment);
		section.updateValue(attribute, value);

	}

	public ArrayList<String> getSections(String default1) {
		ArrayList<String> sections = new ArrayList<>();
		Set<String> sec = this.sections.keySet();
		for (int i = 0; i < sec.size(); i++) {
			if (!sec.toArray()[i].toString().equals(default1)) {
				sections.add(sec.toArray()[i].toString());
			}
		}
		return sections;
	}

}
