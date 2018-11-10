package entidades;

import java.util.LinkedHashMap;
import funciones_helper.Funcion_Helper;
import interfaces.ILector;
import interfaces.IRepositorios;
import opciones.Opcion_Create_Melodia;
import opciones.Opcion_Delete_Melodia;
import opciones.Opcion_Delete_NotaMelodia;
import opciones.Opcion_Details_Cancion;
import opciones.Opcion_Details_Canciones;
import opciones.Opcion_Reproducir_Melodia;
import opciones.Opcion;
import opciones.Opcion_Set_TempoMelodia;
import opciones.Opcion_AddNota;
import opciones.Opcion_SaveMelodia;
import opciones.Opcion_Set_InstrumentoMelodia;
import opciones.Opcion_Update_InstrumentoMelodia;
import opciones.Opcion_Update_NombreMelodia;
import opciones.Opcion_Update_NotaMelodia;
import opciones.Opcion_Update_TempoMelodia;

public class AnoteManager {
	private static final int MAX_INSTRUMENT = 127;
	private static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	private static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	private static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";
	// private static final String CANCIONES = "Canciones";
	// private static final String ROOT = new File("").getAbsolutePath() +
	// File.separator + CANCIONES;
	private static final String ERROR_LISTA_VACIA = "No, hay canciones guardadas";
	private IRepositorios repositorioMelodia;
	// private INota_Repository repositorioNotas_Csv;
	private LinkedHashMap<Integer, Opcion> opciones;
	private ILector lector;
	// private MyPattern pattern;

	public AnoteManager(Lector lector, IRepositorios repoMelodia) {
		// this.pattern = new MyPattern();
		this.repositorioMelodia = repoMelodia;
		this.opciones = new LinkedHashMap<>();
		cargarOpciones();
		this.lector = lector;
		cargarCanciones();
		// start();

	}

	private void cargarOpciones() {
		opciones.put(1, new Opcion_Create_Melodia(this.repositorioMelodia));
		// opciones.put(2, new Opcion_Load_Melodia(repositorioMelodia_Ini,
		// repositorioNotas_Csv));
		opciones.put(2, new Opcion_Details_Canciones(repositorioMelodia));
		opciones.put(3, new Opcion_Details_Cancion(repositorioMelodia));
		opciones.put(4, new Opcion_AddNota(repositorioMelodia));
		//opciones.put(5, new Opcion_Set_TempoMelodia(repositorioMelodia));
		//opciones.put(6, new Opcion_Set_InstrumentoMelodia(repositorioMelodia));
		opciones.put(5, new Opcion_Update_NombreMelodia(repositorioMelodia));
		opciones.put(6, new Opcion_Update_InstrumentoMelodia(repositorioMelodia));
		opciones.put(7, new Opcion_Update_TempoMelodia(repositorioMelodia));
		opciones.put(8, new Opcion_Update_NotaMelodia(repositorioMelodia));
		opciones.put(9, new Opcion_Delete_NotaMelodia(repositorioMelodia));
		opciones.put(10, new Opcion_Delete_Melodia(repositorioMelodia));
		opciones.put(11, new Opcion_Reproducir_Melodia(repositorioMelodia));
		opciones.put(12, new Opcion_SaveMelodia(repositorioMelodia));

	}

	public void createMelody(String nombreMelodia) {
		if (nombreMelodia.isEmpty() || nombreMelodia == null) {
			throw new IllegalArgumentException(ERROR_STRING_VACIO);
		} else if (repositorioMelodia.containsMelodia(nombreMelodia)) {
			throw new IllegalArgumentException(ERROR_MELODIA_EXISTENTE);

		} else {
			repositorioMelodia.createMelodia((nombreMelodia));
		}
	}

	public Melodia getMelody(String nombreMelodia) {
		Melodia melodia = null;
		try {
			melodia = getMelodiaFromMemory(nombreMelodia);
			if (melodia == null) {
				throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return melodia;
	}

	private Melodia getMelodiaFromMemory(String nombreMelodia) {
		return repositorioMelodia.getMelodia(nombreMelodia);
	}

	/*
	 * public ArrayList<Nota> getNotas(String nombreMelodia) {
	 * 
	 * 
	 * Melodia melodia = loadMelodyFromIni(nombreMelodia); ArrayList<Nota> notas =
	 * new ArrayList<>(); if (melodia != null) { notas =
	 * melodia.getNotas(repositorioNotas_Csv); } else { throw new
	 * IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); }
	 * 
	 * return repositorioMelodia.getNotas(nombreMelodia); }
	 */

	/***
	 * 
	 * @param nombreMelodia
	 * @param nota
	 */
	public void addNoteToMelody(String nombreMelodia, Nota nota) {
		Melodia melodia = null;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(nota.getNombre());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			melodia = getMelody(nombreMelodia);
			// melodia.createNote(nota);
			melodia.setNote(nota);

		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public void addNoteToMelody(String nombreMelodia, String nombreNota, String octava, String figura,
			String alteracion) {
		Nota nota;
		Melodia melodia;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(nombreNota);
			Funcion_Helper.validarString(figura);
			Funcion_Helper.validarString(octava);
			Funcion_Helper.validarString(alteracion);
			melodia = getMelody(nombreMelodia);
			nota = new Nota(nombreNota, octava, figura, alteracion);
			melodia.setNote(nota);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public void play(String nombreMelodia, PlayerSingleton player) {
		Melodia melodia = null;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			melodia = getMelody(nombreMelodia);
			melodia.play(player);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
	}

	public void setTempo(String nombreMelodia, String tempo) {
		Melodia melodia;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(tempo);
			melodia = getMelody(nombreMelodia);
			if (melodia != null) {
				melodia.setTempo(tempo);
			}
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public void setInstrument(String nombreMelodia, String instrument) {
		Melodia melodia;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(instrument);
			int MIN_INSTRUMENT = 0;
			Funcion_Helper.validarRango(MIN_INSTRUMENT, MAX_INSTRUMENT, Instrumentos.valueOf(instrument).getValue());
			melodia = getMelody(nombreMelodia);
			if (melodia != null) {
				melodia.setInstrument(instrument);
			}
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public void save(String nombreMelodia, boolean append) {
		/*
		 * Melodia melodia = getMelody(nombreMelodia);
		 * repositorioMelodia.saveIni(melodia, append);
		 * melodia.saveNotas(repositorioNotas_Csv, append);
		 */
		repositorioMelodia.saveMelodia(nombreMelodia, append);
	}

	// Revisar como hacer el load de la melodia, si aca o en melodia usando el ini
	public void loadMelodia(String nombreMelodia) {
		repositorioMelodia.loadMelodia(nombreMelodia);
		/*
		 * melodia.loadMelodia(repositorioNotas_Csv); // melodia.list();
		 * melodias.put(nombreMelodia, melodia);
		 */
	}

	public void updateNombreMelodia(String nombreaAnteriorMelodia, String nuevoNombre, boolean append) {
		repositorioMelodia.updateNombreMelodia(nombreaAnteriorMelodia, nuevoNombre);

		/*
		 * Melodia melodia; try { // Pregunto primero si esta en memoria porque quizas
		 * esta creada en esta sesion
		 * 
		 * melodia = getMelody(nombreaAnteriorMelodia); if (melodia == null) { throw new
		 * IllegalArgumentException(ERROR_MELODIA_EXISTENTE); } else {
		 * melodia.setNombre(nuevoNombre); cambiarNombreClave(nombreaAnteriorMelodia,
		 * nuevoNombre); // Funcion_Helper.tryOpen(); // Cambio el nombre de la melodia
		 * en memoria // persisitidor_Csv.updateName(nombreaAnteriorMelodia,
		 * nuevoNombre); // Cambio el nombre en el archivo ini //
		 * persisitidor_Ini.saveIni(melodia); //
		 * repositorioMelodia_Ini.updateIni(melodia, nombreaAnteriorMelodia, append); //
		 * repositorioMelodia_Ini.updateNombreIni(nombreaAnteriorMelodia, nuevoNombre);
		 * // repositorioMelodia_Ini.updateSection(nombreaAnteriorMelodia, nuevoNombre);
		 * // Le cambio el nombre al archivo csv // Le cambio el nombre a la carpeta //
		 * Funcion_Helper.updateFolder(nombreaAnteriorMelodia, nuevoNombre); }
		 * 
		 * } catch (RuntimeException re) { System.out.println(re.getMessage()); }
		 */
	}

	/*
	 * private void cambiarNombreClave(String nombreAnterior, String nuevoNombre) {
	 * repositorioMelodia.updateNombreEnMapa(nombreAnterior, nuevoNombre); //
	 * melodias.put(nuevoNombre, melodias.remove(nombreAnterior));
	 * 
	 * }
	 */

	/*
	 * private Melodia loadMelodyFromIni(String nombreaAnteriorMelodia) { Melodia
	 * melodia; melodia = repositorioMelodia.loadMelodia(nombreaAnteriorMelodia);
	 * return melodia; }
	 */

	public void updateInstrumentMelodia(String nombreMelodia, String instrumento) {
		Melodia melodia;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(instrumento);
			melodia = getMelody(nombreMelodia);
			if (melodia != null) {
				melodia.setInstrument(instrumento);
				repositorioMelodia.updateMelodiaParams(melodia);
			}

		} catch (RuntimeException re) {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}

	public void updateTempoMelodia(String nombreMelodia, String tempo) {
		Melodia melodia;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(tempo);
			melodia = getMelody(nombreMelodia);
			if (melodia != null) {
				melodia.setTempo(tempo);
				repositorioMelodia.updateMelodiaParams(melodia);
			}
		} catch (RuntimeException re) {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}

	public void updateNota(String nombreMelodia, String idNota, String nombreNota, String octava, String figura,
			String alteracion) {
		repositorioMelodia.updateNota(nombreMelodia, idNota, nombreNota, octava, figura, alteracion);

	}

	public void start() {
		// this.lector = new Lector(input);
		cargarCanciones();
		System.out.println("Bienvenido a Anote, el programa con el que podrá componer, reproducir y guardar melodías fácilmente.\n"
				+ "Seleccione una opción para continuar:");
		try {
			lector.EjecutarOpcion(this, repositorioMelodia, opciones);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}

	}

	private void cargarCanciones() {
		try {
			repositorioMelodia.cargarCanciones();
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

		/*
		 * ArrayList<String> lstCancionesStr = getCanciones(); for (int i = 0; i <
		 * lstCancionesStr.size(); i++) { loadMelodia(lstCancionesStr.get(i)); }
		 */
	}

	public void listarCanciones() {
		/*
		 * // int index = 1; System.out.println("Canciones:"); ArrayList<String>
		 * canciones = getCanciones(); if (canciones.size() == 0) { throw new
		 * RuntimeException(ERROR_LISTA_VACIA); }
		 * 
		 * for (int i = 0; i < canciones.size(); i++) { System.out.println(i + 1 + ":" +
		 * canciones.get(i)); // index++; }
		 */
		repositorioMelodia.listarCanciones();

	}

	public void deleteMelodia(String nombreCancion) {
		try {
			repositorioMelodia.deleteMelodiaInFile(nombreCancion);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		/*
		 * String sPath = ROOT + File.separator + nombreCancion; File pathCancion = new
		 * File(sPath); Path path = Paths.get(sPath); if (Files.exists(path)) { try {
		 * pathCancion.delete(); } catch (Exception e) { throw new
		 * IllegalArgumentException(ERROR_MELODIA_INEXISTENTE); } }
		 */

	}

	public void listarInstrumentos() {
		Instrumentos[] lstInstrumentos = Instrumentos.values();
		for (int i = 0; i < lstInstrumentos.length; i++) {
			System.out.println(i + 1 + "." + lstInstrumentos[i]);
		}
	}

	public void listarNotasDeMelodia(String nombreMelodia) {
		repositorioMelodia.listarNotas(nombreMelodia);
		/*
		 * ArrayList<Nota> notas = getNotas(nombreMelodia); if (notas != null) { for
		 * (Nota nota : notas) { System.out.println(nota.toStringConId()); } }
		 */

	}

	public void removeNotaById(String nombreMelodia, String idNota) {
		repositorioMelodia.removeNotaById(nombreMelodia, idNota);
	}

	public void detallesMelodia(String nombreMelodia) {
		Melodia melodia = getMelody(nombreMelodia);
		System.out.println(melodia.toString());
		System.out.println("Notas:");
		melodia.listarNotas();
	}

	public void saveAll() {
		repositorioMelodia.saveAll();		
	}

}
