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
import entidades.Pista;
import entidades.Cancion;
import entidades.Nota;
import entidades.PlayerSingleton;
import funciones_helper.Funcion_Helper;
import funciones_helper.Mapper_Ini;
import interfaces.RepoMelodias;
import utils.files.ControlLevelError;
import utils.files.NoteReader;
import utils.files.NoteWriter;
import viewmodels.ViewModelCancion;
import viewmodels.ViewModelPista;
import viewmodels.ViewModelNota;
import utils.files.IniManager;

public class RepoCancionesMixto implements RepoMelodias {
	protected static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	protected static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";
	protected static final String CANCIONES = "Canciones";
	protected static final String ERROR_NO_EXISTE_CANCION = "Error, la cancion seleccionada no existe";
	protected static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	private static final String ERROR_LISTA_VACIA = "No hay melodías guardadas";
	private String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	// private Path rootPath;
	private static final String EXTENSION_CSV = ".csv";
	private static final String EXTENSION_INI = ".ini";
	private NoteWriter noteWriter;
	private NoteReader noteReader;
	private Path rootPath;
	private IniManager iniManager;
	// protected LinkedHashMap<String, Melodia> melodias;

	public RepoCancionesMixto() {
		this.iniManager = new IniManager();
		this.noteWriter = new NoteWriter();
		this.noteReader = new NoteReader(Nota.getHeader());
		rootPath = Paths.get(ROOT);
		// cargarCanciones();
		// this.melodias = new LinkedHashMap<>();
	}

	@Override
	public void save(ViewModelCancion cancionVM) {
		String pathIni = ROOT + File.separator + cancionVM.getNombreCancion() + File.separator
				+ cancionVM.getNombreCancion() + EXTENSION_INI;
		iniManager.setPathDestiny(pathIni);
		// String instrumento = cancionVM.getInstrument();
		String tempo = cancionVM.getTempo();
		Path rootPath = Paths.get(pathIni);
		try {
			if (Files.notExists(rootPath.getParent())) {
				Files.createDirectories(rootPath.getParent());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		/*
		 * this.iniManager.addSection(melodia.getNombre());
		 * iniManager.setItem(melodia.getNombre(), melodia.getTempoConstString(),
		 * tempo); iniManager.setItem(melodia.getNombre(), melodia.getInstrumentConst(),
		 * instrumento); iniManager.save(false); // Hasta aca salva la melodia en el ini
		 * saveNotas(notas, melodia.getNombre(), false);
		 */

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

	/*
	 * public void saveAll() { // Melodias en memoria Set<String> melodiasEnMemoria
	 * = this.melodias.keySet(); ArrayList<String> melodiasEnDisco =
	 * getCancionesFromDisk(); for (String melodia : melodiasEnMemoria) { // remover
	 * las melodias que se encuentren en disco if
	 * (melodiasEnDisco.contains(melodia)) { deleteMelodia(melodia); } // Luego
	 * salvar saveMelodia(melodia, false); } }
	 */

	@Override
	public void updateNombreMelodia(String nombreMelodia, String nuevoNombre) {
		/*
		 * Pista melodia = loadCancion(nombreMelodia); if (melodia != null) {
		 * melodia.setNombre(nuevoNombre); // Modificar nombre en memoria //
		 * updateNombreEnMapa(nombreMelodia, nuevoNombre); // Modificar contenido ini
		 * updateMelodiaParams(melodia); // Modificar la seccion del csv
		 * updateSection(nombreMelodia, nuevoNombre); // Modificar nombre ini
		 * updateNombreIni(nombreMelodia, nuevoNombre); // Modificar nombre csv
		 * updateNombreCsv(nombreMelodia, nuevoNombre); // Modificar nombre carpeta
		 * Funcion_Helper.updateFolder(nombreMelodia, nuevoNombre);
		 * 
		 * }
		 */
	}

	public void loadNotas(ArrayList<Nota> notas, String nombreMelodia) {
		String pathCsv = ROOT + File.separator + nombreMelodia + File.separator + nombreMelodia + EXTENSION_CSV;
		noteReader.readAll(pathCsv, notas, ControlLevelError.IGNORE_ERRORS);
		// noteReader.resetContador();

	}

	/*
	 * public Pista loadCancion(String nombreMelodia) { ArrayList<Nota> notas = new
	 * ArrayList<>(); Pista melodia = null; String pathIni = ROOT + File.separator +
	 * nombreMelodia + File.separator + nombreMelodia + EXTENSION_INI; Path path =
	 * Paths.get(pathIni); if (Files.notExists(path)) { throw new
	 * IllegalArgumentException(ERROR_NO_EXISTE_CANCION); } else {
	 * iniManager.setPathSource(path.toAbsolutePath().toString());
	 * iniManager.load(); melodia = new Pista(iniManager.getValueof(nombreMelodia));
	 * // setear el tiempo a la melodia con item tiempo dentro de la seccion nombre
	 * // melodia melodia.setTempo(iniManager.getValueOf(nombreMelodia, "Tiempo"));
	 * melodia.setInstrument(iniManager.getValueOf(nombreMelodia, "Instrumento"));
	 * loadNotas(notas, nombreMelodia); melodia.llenarNotas(notas); } return
	 * melodia; }
	 * 
	 * /* public void cargarCanciones() { Melodia melodia; ArrayList<String>
	 * lstCancionesStr = getCancionesFromDisk(); ArrayList<Nota> notas = new
	 * ArrayList<>();
	 * 
	 * for (int i = 0; i < lstCancionesStr.size(); i++) { melodia =
	 * loadMelodia(lstCancionesStr.get(i)); loadNotas(notas, melodia.getNombre());
	 * melodia.llenarNotas(notas); notas.clear(); addMelodia(melodia); } }
	 */

	public void updateTempo(String nombreMelodia, String tempo) {
		/*
		 * Pista melodia = loadCancion(nombreMelodia); if (melodia != null) {
		 * melodia.setTempo(tempo); updateMelodiaParams(melodia); } else { throw new
		 * IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); }
		 */
	}

	public void updateInstrumento(String nombreMelodia, String instrumento) {
		/*
		 * Pista melodia = loadCancion(nombreMelodia); if (melodia != null) {
		 * melodia.setInstrument(instrumento); updateMelodiaParams(melodia); } else {
		 * throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); }
		 */
	}

	/*
	 * @Override public void deleteCancion(String nombreCancion) {
	 * 
	 * String sPath = ROOT + File.separator + nombreCancion; File pathCancion = new
	 * File(sPath); Path path = Paths.get(sPath); // melodias.remove(nombreCancion);
	 * if (Files.exists(path)) { deleteDir(pathCancion); } else { throw new
	 * IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); }
	 * 
	 * }
	 */
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
	/*
	 * public ArrayList<String> getCancionesFromDisk() { ArrayList<String> canciones
	 * = new ArrayList<>(); File aDirectory = new File(ROOT); // get a listing of
	 * all files in the directory String[] filesInDir = aDirectory.list(); // sort
	 * the list of files (optional) Arrays.sort(filesInDir); // have everything i
	 * need, just print it now for (int i = 0; i < filesInDir.length; i++) {
	 * canciones.add(filesInDir[i]); } return canciones; }
	 */
	/***
	 * Modifica las caracteristicas de la melodia cuando tiene modificaciones, me
	 * sirve cuando modifico el nombre
	 */
	public void updateMelodiaParams(Pista melodia) {
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
		/*
		 * Pista melodia = loadCancion(nombreMelodia); if (melodia != null) {
		 * melodia.play(player); } else { throw new
		 * IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); }
		 */
	}

	/*
	 * @Override public void detallesCancion(String nombreMelodia) { /* Pista
	 * melodia = loadCancion(nombreMelodia); if (melodia != null) {
	 * System.out.println(melodia.toString()); System.out.println("Notas:");
	 * melodia.listarNotas(); } else { throw new
	 * IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); }
	 */
	// }

	/*
	 * @Override public void addNote(String nombreMelodia, String nombreNota, String
	 * octava, String figura, String alteracion) { /* Melodia melodia =
	 * getMelodia(nombreMelodia); if (melodia != null) { melodia.setNote(nombreNota,
	 * octava, figura, alteracion); ; }
	 */

	@Override
	public void listarNotas(String nombreMelodia) {
		/*
		 * Pista melodia = loadCancion(nombreMelodia); melodia.listarNotas();
		 */
	}

	@Override
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

	public void listarCanciones() {
		ArrayList<String> melodias = getCanciones();
		for (int i = 0; i < melodias.size(); i++) {
			System.out.println((i + 1) + ":" + melodias.get(i));
		}
	}

	public void exportar() {
		// TODO Auto-generated method stub

	}

	@Override
	public Cancion loadCancion(String nombreMelodia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCancion(String nombreCancion) {
		// TODO Auto-generated method stub

	}

	/*
	 * public Melodia getMelodia(String nombreMelodia) { Melodia melodia = null; if
	 * (containsMelodia(nombreMelodia)) { melodia = melodias.get(nombreMelodia); }
	 * else { throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); }
	 * return melodia; }
	 */

	/*
	 * public boolean containsMelodia(String nombreMelodia) { return
	 * melodias.containsKey(nombreMelodia); }
	 */

	/*
	 * public void updateNombreEnMapa(String nombreAnterior, String nuevoNombre) {
	 * if (melodias.containsKey(nombreAnterior)) { melodias.put(nuevoNombre,
	 * melodias.remove(nombreAnterior)); } else { throw new
	 * IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); }
	 * 
	 * } /* protected void addMelodia(Melodia melodia) {
	 * melodias.put(melodia.getNombre(), melodia);
	 * 
	 * }
	 * 
	 * @Override public void removeNotaById(String nombreMelodia, String idNota) {
	 * Melodia melodia = getMelodia(nombreMelodia); if (melodia != null) {
	 * melodia.removeNotaById(idNota); } else { throw new
	 * IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); }
	 * 
	 * }
	 * 
	 * @Override public void listarNotas(String nombreMelodia) { Melodia melodia =
	 * getMelodia(nombreMelodia); if (melodia != null) { melodia.listarNotas(); }
	 * 
	 * }
	 * 
	 * @Override public ArrayList<String> getCanciones() { /* Melodia melodia;
	 * 
	 * Set<String> canciones = melodias.keySet(); if (canciones.isEmpty()) { throw
	 * new IllegalArgumentException(ERROR_LISTA_VACIA); } for (int j = 0; j <
	 * canciones.size(); j++) { //System.out.println((j + 1) + ":" +
	 * canciones.toArray()[j]); //melodia =
	 * getMelodia(canciones.toArray()[j].toString()); //melodia.toString();
	 * 
	 * }
	 * 
	 * return
	 * 
	 * getCancionesFromDisk();
	 * 
	 * }
	 */
	/*
	 * @Override public void updateNota(String nombreMelodia, String idNota, String
	 * nombreNota, String octava, String figura, String alteracion) { Melodia
	 * melodia; Funcion_Helper.validarString(nombreMelodia);
	 * Funcion_Helper.validarString(idNota);
	 * Funcion_Helper.validarString(nombreNota);
	 * Funcion_Helper.validarString(octava); Funcion_Helper.validarString(figura);
	 * Funcion_Helper.validarString(alteracion); // melodia =
	 * getMelodia(nombreMelodia); if (melodia != null) { melodia.updateNota(idNota,
	 * nombreNota, octava, figura, alteracion); } }
	 */

}
