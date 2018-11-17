package entidades;

import java.util.LinkedHashMap;
import funciones_helper.Funcion_Helper;
import interfaces.Vista;
import interfaces.RepoMelodias;
import opciones.Opcion_Create_Melodia;
import opciones.Opcion_Delete_Melodia;
import opciones.Opcion_Delete_NotaMelodia;
import opciones.Opcion_Details_Cancion;
import opciones.Opcion_Details_Canciones;
import opciones.Opcion_Reproducir_Melodia;
import opciones.Opcion;
import opciones.Opcion_AddNota;
import opciones.Opcion_SaveMelodia;
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
	private RepoMelodias repositorio;
	private LinkedHashMap<Integer, Opcion> opciones;
	private Vista vista;
	// private MyPattern pattern;

	public AnoteManager(Vista vista, RepoMelodias repositorio) {
		this.repositorio = repositorio;
		this.opciones = new LinkedHashMap<>();
		cargarOpciones();
		this.vista = vista;
	}

	private void cargarOpciones() {
		opciones.put(1, new Opcion_Create_Melodia(this.repositorio));
		opciones.put(2, new Opcion_Details_Canciones(repositorio));
		opciones.put(3, new Opcion_Details_Cancion(repositorio));
		opciones.put(4, new Opcion_AddNota(repositorio));
		opciones.put(5, new Opcion_Update_NombreMelodia(repositorio));
		opciones.put(6, new Opcion_Update_InstrumentoMelodia(repositorio));
		opciones.put(7, new Opcion_Update_TempoMelodia(repositorio));
		opciones.put(8, new Opcion_Update_NotaMelodia(repositorio));
		opciones.put(9, new Opcion_Delete_NotaMelodia(repositorio));
		opciones.put(10, new Opcion_Delete_Melodia(repositorio));
		opciones.put(11, new Opcion_Reproducir_Melodia(repositorio));
		opciones.put(12, new Opcion_SaveMelodia(repositorio));

	}

	public void createMelody(String nombreMelodia, String instrumento, String tempo) {
		try {
			repositorio.createMelodia(nombreMelodia, instrumento, tempo);
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage());
		}
		
	}

	/*
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
	*/

	/*
	private Melodia getMelodiaFromMemory(String nombreMelodia) {
		return repositorio.getMelodia(nombreMelodia);
	}
	*/

	/***
	 * 
	 * @param nombreMelodia
	 * @param nota
	 */
	/*
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
	*/

	/*
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
	*/

	public void play(String nombreMelodia, PlayerSingleton player) {
		try {
			repositorio.play(nombreMelodia, player);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
	}

	/*
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
	*/

	/*
	
	public void setInstrument(String nombreMelodia, String instrument) {
		try {
			repositorio.updateInstrumento(nombreMelodia, instrument);
			
			melodia = getMelody(nombreMelodia);
			if (melodia != null) {
				melodia.setInstrument(instrument);
			}
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}
	*/

	public void save(String nombreMelodia, boolean append) {
		repositorio.saveMelodia(nombreMelodia, append);
	}

	public void loadMelodia(String nombreMelodia) {
		repositorio.loadMelodia(nombreMelodia);
	}

	public void updateNombreMelodia(String nombreaAnteriorMelodia, String nuevoNombre, boolean append) {
		repositorio.updateNombreMelodia(nombreaAnteriorMelodia, nuevoNombre);

	}

	public void updateInstrumentMelodia(String nombreMelodia, String instrumento) {
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(instrumento);
			repositorio.updateInstrumento(nombreMelodia, instrumento);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public void updateTempoMelodia(String nombreMelodia, String tempo) {
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(tempo);
			repositorio.updateTempo(nombreMelodia, tempo);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public void updateNota(String nombreMelodia, String idNota, String nombreNota, String octava, String figura,
			String alteracion) {
		repositorio.updateNota(nombreMelodia, idNota, nombreNota, octava, figura, alteracion);
	}

	public void start() {
		//cargarCanciones();
		
		try {
			vista.EjecutarOpcion(this, repositorio, opciones);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}

	}

	/*
	private void cargarCanciones() {
		try {
			repositorio.cargarCanciones();
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
	}
	*/

	public void listarCanciones() {
		repositorio.listarCanciones();
	}

	public void deleteMelodia(String nombreCancion) {
		try {
			repositorio.deleteMelodia(nombreCancion);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarInstrumentos() {
		Instrumentos[] lstInstrumentos = Instrumentos.values();
		for (int i = 0; i < lstInstrumentos.length; i++) {
			System.out.println(i + 1 + "." + lstInstrumentos[i]);
		}
	}

	public void listarNotasDeMelodia(String nombreMelodia) {
		repositorio.listarNotas(nombreMelodia);
	}

	public void removeNotaById(String nombreMelodia, String idNota) {
		repositorio.removeNotaById(nombreMelodia, idNota);
	}

	public void detallesMelodia(String nombreMelodia) {
		try {
			repositorio.detallesMelodia(nombreMelodia);
			repositorio.listarNotas(nombreMelodia);
		} catch (RuntimeException re) {
			// TODO: handle exception
		}
	}

	public void addNoteToMelody(String nombreMelodia, String nombreNota, String octava, String figura,
			String alteracion) {
		try {
			repositorio.addNote(nombreMelodia,nombreNota,octava,figura,alteracion);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		
	}
	
}
