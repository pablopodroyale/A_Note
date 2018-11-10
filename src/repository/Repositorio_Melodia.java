package repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.xml.crypto.KeySelector.Purpose;

import entidades.Melodia;
import entidades.Nota;
import funciones_helper.Funcion_Helper;
import funciones_helper.Mapper_Ini;
import interfaces.IRepositorios;
import persistencia.ControlLevelError;
import persistencia.NoteReader;
import persistencia.NoteWriter;
import utils_textfiles.IniManager;

public class Repositorio_Melodia implements IRepositorios {

	private String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	// private Path rootPath;
	private static final String CANCIONES = "Canciones";
	private static final String ERROR_NO_EXISTE_CANCION = "Error, la cancion seleccionada no existe";
	private static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	private static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	private static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";
	private static final String EXTENSION_CSV = ".csv";
	private static final String EXTENSION_INI = ".ini";
	private NoteWriter noteWriter;
	private NoteReader noteReader;
	private Path rootPath;
	private LinkedHashMap<String, Melodia> melodias;
	private IniManager iniManager;

	public Repositorio_Melodia() {
		this.iniManager = new IniManager();
		this.melodias = new LinkedHashMap<>();
		this.noteWriter = new NoteWriter();
		this.noteReader = new NoteReader(Nota.getHeader());
		rootPath = Paths.get(ROOT);
	}

	/***
	 * Le paso un arraylist y lo carga con las notas de la cancion buscada
	 */
	@Override
	public void loadNotas(ArrayList<Nota> notas, String nombreMelodia) {
		String pathCsv = ROOT + File.separator + nombreMelodia + File.separator + nombreMelodia + EXTENSION_CSV;
		noteReader.readAll(pathCsv, notas, ControlLevelError.IGNORE_ERRORS);
	}

	/***
	 * notas, nombre melodia
	 */
	public void saveNotas(ArrayList<Nota> notas, String nombreMelodia, boolean append) {
		String pathCSV = ROOT + File.separator + nombreMelodia + File.separator + nombreMelodia + EXTENSION_CSV;
		try {
			if (!Files.exists(rootPath)) {
				Files.createDirectories(Paths.get(ROOT));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		noteWriter.setHeader(Nota.getHeader());
		// NoteWriter noteWriter = new NoteWriter(Nota.getHeader());
		noteWriter.writeAll(pathCSV, notas, append);
	}

	private void updateNombreCsv(String nombreaAnteriorMelodia, String nuevoNombre) {
		String pathAnteriorIni = ROOT + File.separator + nombreaAnteriorMelodia + File.separator
				+ nombreaAnteriorMelodia + EXTENSION_CSV;
		String nuevoPath = ROOT + File.separator + nombreaAnteriorMelodia + File.separator + nuevoNombre
				+ EXTENSION_CSV;
		File dir = new File(pathAnteriorIni);
		File newDir = new File(nuevoPath);
		dir.renameTo(newDir);

	}

	@Override
	public Melodia loadMelodia(String nombreMelodia) {
		Melodia melodia = null;
		String pathIni = ROOT + File.separator + nombreMelodia + File.separator + nombreMelodia + EXTENSION_INI;
		Path path = Paths.get(pathIni);
		if (Files.notExists(path)) {
			throw new IllegalArgumentException(ERROR_NO_EXISTE_CANCION);
		} else {
			iniManager.setPathSource(path.toAbsolutePath().toString());
			iniManager.load();
			melodia = new Melodia(iniManager.getValueof(nombreMelodia));
			// setear el tiempo a la melodiacon item tiempo dentro de la seccion nombre
			// melodia
			melodia.setTempo(iniManager.getValueOf(nombreMelodia, "Tiempo"));
			melodia.setInstrument(iniManager.getValueOf(nombreMelodia, "Instrumento"));
		}
		return melodia;
	}

	@Override
	public void saveMelodia(String nombreMelodia, boolean append) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			String pathIni = ROOT + File.separator + melodia.getNombre() + File.separator + melodia.getNombre()
					+ EXTENSION_INI;
			iniManager.setPathDestiny(pathIni);
			String instrumento = removeInstrumentMask(melodia.getInstrument());
			String tempo = removeTempoMak(melodia.getTempo());
			Path rootPath = Paths.get(pathIni);
			try {
				if (Files.notExists(rootPath.getParent())) {
					Files.createDirectories(rootPath.getParent());
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			this.iniManager.addSection(melodia.getNombre());
			iniManager.setItem(melodia.getNombre(), melodia.getTempoConstString(), tempo);
			iniManager.setItem(melodia.getNombre(), melodia.getInstrumentConst(), instrumento);
			iniManager.save(append);
			// Hasta aca salva la melodia en el ini
			saveNotas(melodia.getNotas(), nombreMelodia, append);
		}

	}

	private String removeTempoMak(String tempo) {
		return tempo.substring(2, tempo.length());
	}

	private String removeInstrumentMask(String instrument) {
		return instrument.substring(3, instrument.length() - 2);
	}

	/***
	 * Modifica la seccion en el ini que corresponda a esa melodia con el nuevo
	 * nombre
	 */
	@Override
	public void updateMelodiaInIni(Melodia melodia, String nombreAnterior, boolean append) {
		String pathAnteriorIni = ROOT + File.separator + nombreAnterior + File.separator + nombreAnterior
				+ EXTENSION_INI;
		// String instrumento = removeInstrumentMask(melodia.getInstrument());
		// String tempo = removeTempoMak(melodia.getTempo());
		// this.iniManager.addSection(melodia.getNombre());
		iniManager.updateSection(nombreAnterior, melodia.getNombre());
		iniManager.setPathDestiny(pathAnteriorIni);

		// melodia.setNombre(nombreMelodia);
		// iniManager.setItem(melodia.getNombre(), melodia.getTempoConstString(),
		// tempo);
		// iniManager.setItem(melodia.getNombre(), melodia.getInstrumentConst(),
		// instrumento);
		// iniManager.save(append);

	}

	/***
	 * Modifica el nombre del archivo ini(la melodia)
	 */

	private void updateNombreIni(String nombreaAnteriorMelodia, String nuevoNombre) {
		String pathAnteriorIni = ROOT + File.separator + nombreaAnteriorMelodia + File.separator
				+ nombreaAnteriorMelodia + EXTENSION_INI;
		String nuevoPath = ROOT + File.separator + nombreaAnteriorMelodia + File.separator + nuevoNombre
				+ EXTENSION_INI;
		File dir = new File(pathAnteriorIni);
		File newDir = new File(nuevoPath);
		dir.renameTo(newDir);
	}

	@Override
	public void updateSection(String nombreAnterior, String nuevoNombre) {
		this.iniManager.updateSection(nombreAnterior, nuevoNombre);

	}

	/***
	 * Modifica las caracteristicas de la melodia cuando tiene modificaciones, me
	 * sirve cuando modifico el nombre
	 */
	@Override
	public void updateMelodiaParams(Melodia melodia) {
		Mapper_Ini.Map(melodia, iniManager);
		// iniManager.save(false);
	}

	/*
	 * public void loadNotas() { load(notas, this.nombre);
	 * 
	 * }
	 */

	/*
	 * public ArrayList<Nota> getNotas(String nombreMelodia) { loadNotas(); return
	 * notas; }
	 */

	/*
	 * @Override public void loadNotas(String nombreMelodia) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	@Override
	public void updateNombreMelodia(String nombreMelodia, String nuevoNombre) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.setNombre(nuevoNombre);
			// Modificar nombre en memoria
			updateNombreEnMapa(nombreMelodia, nuevoNombre);
			// Modificar contenido ini
			updateMelodiaParams(melodia);
			//Modificar la seccion del csv
			updateSection(nombreMelodia, nuevoNombre);
			// Modificar nombre ini
			updateNombreIni(nombreMelodia, nuevoNombre);
			// Modificar nombre csv
			updateNombreCsv(nombreMelodia, nuevoNombre);
			// Modificar nombre carpeta
			Funcion_Helper.updateFolder(nombreMelodia, nuevoNombre);
		}
	}

	@Override
	public boolean containsMelodia(String nombreMelodia) {
		return melodias.containsKey(nombreMelodia);
	}

	@Override
	public void createMelodia(String nombreMelodia) {
		if (nombreMelodia.isEmpty() || nombreMelodia == null) {
			throw new IllegalArgumentException(ERROR_STRING_VACIO);
		} else if (melodias.containsKey(nombreMelodia)) {
			throw new IllegalArgumentException(ERROR_MELODIA_EXISTENTE);
		} else {
			melodias.put(nombreMelodia, new Melodia(nombreMelodia));
		}

	}

	@Override
	public Melodia getMelodia(String nombreMelodia) {
		Melodia melodia = null;
		if (containsMelodia(nombreMelodia)) {
			melodia = melodias.get(nombreMelodia);
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}
		return melodia;
	}

	public void updateNombreEnMapa(String nombreAnterior, String nuevoNombre) {
		if (melodias.containsKey(nombreAnterior)) {
			melodias.put(nuevoNombre, melodias.remove(nombreAnterior));
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}

	@Override
	public void updateNota(String nombreMelodia, String idNota, String nombreNota, String octava, String figura,
			String alteracion) {
		Melodia melodia;

		Funcion_Helper.validarString(nombreMelodia);
		Funcion_Helper.validarString(idNota);
		Funcion_Helper.validarString(nombreNota);
		Funcion_Helper.validarString(octava);
		Funcion_Helper.validarString(figura);
		Funcion_Helper.validarString(alteracion);
		melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			// persisitidor_Csv.load(melodia.getNotas(), nombreMelodia);
			melodia.updateNota(idNota, nombreNota, octava, figura, alteracion);
		}
	}

	@Override
	public void listarCanciones() {
		Melodia melodia;
		Set<String> canciones = melodias.keySet();
		for (int j = 0; j < canciones.size(); j++) {
			System.out.println((j + 1) + ":" + canciones.toArray()[j]);
			melodia = getMelodia(canciones.toArray()[j].toString());
			melodia.toString();
		}

	}

	@Override
	public void removeNotaById(String nombreMelodia, String idNota) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.removeNotaById(idNota);
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}

	/***
	 * Borra la melodia que se encuentra en el disco
	 */
	@Override
	public void deleteMelodiaInFile(String nombreCancion) {
		String sPath = ROOT + File.separator + nombreCancion;
		File pathCancion = new File(sPath);
		Path path = Paths.get(sPath);
		// String[]entries;
		if (Files.exists(path)) {
			deleteDir(pathCancion);

			/*
			 * entries = pathCancion.list(); for(String entry: entries){ File currentFile =
			 * new File(pathCancion.getPath(),entry); currentFile.delete(); }
			 */
			// pathCancion.delete();
			// deleteMelodiaFromMemory(nombreCancion);
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}
	}

	void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				if (!Files.isSymbolicLink(f.toPath())) {
					deleteDir(f);
				}
			}
		}
		file.delete();
	}

	private void deleteMelodiaFromMemory(String nombreCancion) {
		melodias.remove(nombreCancion);
	}

	@Override
	public void listarNotas(String nombreMelodia) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.listarNotas();
		}

	}

	@Override
	public void cargarCanciones() {
		Melodia melodia;
		ArrayList<String> lstCancionesStr = getCanciones();
		ArrayList<Nota> notas = new ArrayList<>();

		for (int i = 0; i < lstCancionesStr.size(); i++) {
			melodia = loadMelodia(lstCancionesStr.get(i));
			loadNotas(notas, melodia.getNombre());
			melodia.loadNotas(notas);
			notas.clear();
			addMelodia(melodia);
		}

	}

	private void addMelodia(Melodia melodia) {
		melodias.put(melodia.getNombre(), melodia);

	}

	/***
	 * Devuelve la lista de las canciones en el disco
	 * 
	 * @return
	 */
	public ArrayList<String> getCanciones() {
		ArrayList<String> canciones = new ArrayList<>();
		File aDirectory = new File(ROOT);
		// get a listing of all files in the directory
		String[] filesInDir = aDirectory.list();
		// sort the list of files (optional)
		Arrays.sort(filesInDir);
		// have everything i need, just print it now
		for (int i = 0; i < filesInDir.length; i++) {
			canciones.add(filesInDir[i]);
		}
		return canciones;
	}

	@Override
	public void saveAll() {
		// Melodias en memoria
		Set<String> melodiasEnMemoria = this.melodias.keySet();
		ArrayList<String> melodiasEnDisco = getCanciones();
		for (String melodia : melodiasEnMemoria) {
			// remover las melodias que se encuentren en disco y en memoria
			if (melodiasEnDisco.contains(melodia)) {
				deleteMelodiaInFile(melodia);
				// deleteMelodiaFromMemory(melodia);

			}
			// Luego salvar
			saveMelodia(melodia, false);
		}
	}

	/*
	 * @Override public void addMelodia(String string) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */

}
