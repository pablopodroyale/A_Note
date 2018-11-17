package repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import entidades.Melodia;
import entidades.Nota;
import entidades.PlayerSingleton;
import funciones_helper.Funcion_Helper;
import funciones_helper.Mapper_Ini;
import utils.files.ControlLevelError;
import utils.files.NoteReader;
import utils.files.NoteWriter;
import utils.files.IniManager;

public class RepoMelodiasMixto extends RepositorioMelodia {

	private String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	// private Path rootPath;
	private static final String EXTENSION_CSV = ".csv";
	private static final String EXTENSION_INI = ".ini";
	private NoteWriter noteWriter;
	private NoteReader noteReader;
	private Path rootPath;
	private IniManager iniManager;

	public RepoMelodiasMixto() {
		this.iniManager = new IniManager();
		this.noteWriter = new NoteWriter();
		this.noteReader = new NoteReader(Nota.getHeader());
		rootPath = Paths.get(ROOT);
		cargarCanciones();
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

	public void saveAll() {
		// Melodias en memoria
		Set<String> melodiasEnMemoria = this.melodias.keySet();
		ArrayList<String> melodiasEnDisco = getCancionesFromDisk();
		for (String melodia : melodiasEnMemoria) {
			// remover las melodias que se encuentren en disco
			if (melodiasEnDisco.contains(melodia)) {
				deleteMelodia(melodia);
			}
			// Luego salvar
			saveMelodia(melodia, false);
		}
	}

	@Override
	public void updateNombreMelodia(String nombreMelodia, String nuevoNombre) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.setNombre(nuevoNombre);
			// Modificar nombre en memoria
			updateNombreEnMapa(nombreMelodia, nuevoNombre);
			// Modificar contenido ini
			updateMelodiaParams(melodia);
			// Modificar la seccion del csv
			updateSection(nombreMelodia, nuevoNombre);
			// Modificar nombre ini
			updateNombreIni(nombreMelodia, nuevoNombre);
			// Modificar nombre csv
			updateNombreCsv(nombreMelodia, nuevoNombre);
			// Modificar nombre carpeta
			Funcion_Helper.updateFolder(nombreMelodia, nuevoNombre);
		}

	}

	public void loadNotas(ArrayList<Nota> notas, String nombreMelodia) {
		String pathCsv = ROOT + File.separator + nombreMelodia + File.separator + nombreMelodia + EXTENSION_CSV;
		noteReader.readAll(pathCsv, notas, ControlLevelError.IGNORE_ERRORS);
		noteReader.resetContador();

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
			// setear el tiempo a la melodia con item tiempo dentro de la seccion nombre
			// melodia
			melodia.setTempo(iniManager.getValueOf(nombreMelodia, "Tiempo"));
			melodia.setInstrument(iniManager.getValueOf(nombreMelodia, "Instrumento"));
		}
		return melodia;
	}

	public void cargarCanciones() {
		Melodia melodia;
		ArrayList<String> lstCancionesStr = getCancionesFromDisk();
		ArrayList<Nota> notas = new ArrayList<>();

		for (int i = 0; i < lstCancionesStr.size(); i++) {
			melodia = loadMelodia(lstCancionesStr.get(i));
			loadNotas(notas, melodia.getNombre());
			melodia.llenarNotas(notas);
			notas.clear();
			addMelodia(melodia);
		}
	}

	@Override
	public void updateTempo(String nombreMelodia, String tempo) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.setTempo(tempo);
			updateMelodiaParams(melodia);
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}

	@Override
	public void updateInstrumento(String nombreMelodia, String instrumento) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.setInstrument(instrumento);
			updateMelodiaParams(melodia);
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}
	}

	@Override
	public void deleteMelodia(String nombreCancion) {
		String sPath = ROOT + File.separator + nombreCancion;
		File pathCancion = new File(sPath);
		Path path = Paths.get(sPath);
		if (containsMelodia(nombreCancion)) {
			melodias.remove(nombreCancion);
			if (Files.exists(path)) {
				deleteDir(pathCancion);
			}
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}

	private void deleteDir(File file) {
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

	private String removeInstrumentMask(String instrument) {
		return instrument.substring(3, instrument.length() - 2);
	}

	private String removeTempoMak(String tempo) {
		return tempo.substring(2, tempo.length());
	}

	/***
	 * Devuelve la lista de las canciones en el disco
	 * 
	 * @return
	 */
	public ArrayList<String> getCancionesFromDisk() {
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

	/***
	 * Modifica las caracteristicas de la melodia cuando tiene modificaciones, me
	 * sirve cuando modifico el nombre
	 */
	public void updateMelodiaParams(Melodia melodia) {
		Mapper_Ini.Map(melodia, iniManager);
	}

	public void updateSection(String nombreAnterior, String nuevoNombre) {
		this.iniManager.updateSection(nombreAnterior, nuevoNombre);
	}

	private void updateNombreIni(String nombreaAnteriorMelodia, String nuevoNombre) {
		String pathAnteriorIni = ROOT + File.separator + nombreaAnteriorMelodia + File.separator
				+ nombreaAnteriorMelodia + EXTENSION_INI;
		String nuevoPath = ROOT + File.separator + nombreaAnteriorMelodia + File.separator + nuevoNombre
				+ EXTENSION_INI;
		File dir = new File(pathAnteriorIni);
		File newDir = new File(nuevoPath);
		dir.renameTo(newDir);
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
	public void close() {

	}

	@Override
	public void play(String nombreMelodia, PlayerSingleton player) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.play(player);
		}else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}

	@Override
	public void detallesMelodia(String nombreMelodia) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.toString();
		}else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}
		
	}

	@Override
	public void addNote(String nombreMelodia,String nombreNota, String octava, String figura,
			String alteracion) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.setNote(nombreNota,octava,figura,alteracion);;
		}
		
	}

}
