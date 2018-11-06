package entidades;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

import funciones_helper.Funcion_Helper;
import interfaces.ILector;
import opciones.Opcion_Create_Melodia;
import opciones.Opcion_Delete_Melodia;
import opciones.Opcion_Delete_NotaMelodia;
import opciones.Opcion_Details_Canciones;
import opciones.Opcion_Load_Melodia;
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
import repository.Nota_Repository;
import repository.INota_Repository;
import repository.IMelodia_Repository;
import repository.Repository_Melodia;

public class AnoteManagger {
	private static final int MAX_INSTRUMENT = 127;
	private static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	private static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	private static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";
	private static final String CANCIONES = "Canciones";
	private static final String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	private IMelodia_Repository repositorioMelodia_Ini;
	private INota_Repository repositorioNotas_Csv;
	private LinkedHashMap<String, Melodia> melodias;
	private LinkedHashMap<Integer, Opcion> opciones;
	private ILector lector;

	public AnoteManagger() {
		this.melodias = new LinkedHashMap<>();
		this.repositorioMelodia_Ini = new Repository_Melodia();
		this.repositorioNotas_Csv = new Nota_Repository();
		this.opciones = new LinkedHashMap<>();
		cargarOpciones();
		this.lector = null;
	}

	private void cargarOpciones() {
		opciones.put(1, new Opcion_Create_Melodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(2, new Opcion_Load_Melodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(3, new Opcion_Details_Canciones(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(4, new Opcion_AddNota(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(5, new Opcion_Set_TempoMelodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(6, new Opcion_Set_InstrumentoMelodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(7, new Opcion_Update_NombreMelodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(8, new Opcion_Update_InstrumentoMelodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(9, new Opcion_Update_TempoMelodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(10, new Opcion_Update_NotaMelodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(11, new Opcion_Delete_NotaMelodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(12, new Opcion_Delete_Melodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(13, new Opcion_Reproducir_Melodia(repositorioMelodia_Ini, repositorioNotas_Csv));
		opciones.put(14, new Opcion_SaveMelodia(repositorioMelodia_Ini, repositorioNotas_Csv));

	}

	public void createMelody(String nombreMelodia) {
		if (nombreMelodia.isEmpty() || nombreMelodia == null) {
			throw new IllegalArgumentException(ERROR_STRING_VACIO);
		} else if (melodias.containsKey(nombreMelodia)) {
			throw new IllegalArgumentException(ERROR_MELODIA_EXISTENTE);

		} else {
			melodias.put(nombreMelodia, new Melodia(nombreMelodia));
		}
	}

	public Melodia getMelody(String nombreMelodia) {
		if (nombreMelodia.isEmpty() || nombreMelodia == null) {
			throw new IllegalArgumentException(ERROR_STRING_VACIO);
		} else {
			Melodia aux = loadMelodyFromIni(nombreMelodia);
			if (aux == null) {
				throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
			} else {
				return aux;
			}
		}
	}

	public ArrayList<Nota> getNotas(String nombreMelodia) {
		Melodia melodia = loadMelodyFromIni(nombreMelodia);
		ArrayList<Nota> notas = new ArrayList<>();
		if (melodia != null) {
			notas = melodia.getNotas(repositorioNotas_Csv);
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}
		return notas;

	}

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

	public void play(String nombreMelodia, MyPattern pattern, PlayerSingleton player) {
		try {
			Funcion_Helper.validarString(nombreMelodia);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
		Melodia melodia = null;
		try {
			melodia = getMelody(nombreMelodia);
			melodia.play(pattern, player);
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

	public void save(String nombreMelodia, MyPattern pattern, boolean append) {
		Melodia melodia = getMelody(nombreMelodia);
		repositorioMelodia_Ini.saveIni(melodia, append);
		melodia.save(repositorioNotas_Csv, append);
	}

	// Revisar como hacer el load de la melodia, si aca o en melodia usando el ini
	public void load(String nombreMelodia) {
		Melodia melodia = repositorioMelodia_Ini.load(nombreMelodia);
		melodia.load(repositorioNotas_Csv);
		melodia.list();
		melodias.put(nombreMelodia, melodia);

	}

	public void updateNombreMelodia(String nombreaAnteriorMelodia, String nuevoNombre, boolean append) {
		Melodia melodia;
		try {
			// Pregunto primero si esta en memoria porque quizas esta creada en esta sesion

			melodia = loadMelodyFromIni(nombreaAnteriorMelodia);
			if (melodia == null) {
				throw new IllegalArgumentException(ERROR_MELODIA_EXISTENTE);
			} else {
				Funcion_Helper.tryOpen();
				// Cambio el nombre de la melodia en memoria
				// persisitidor_Csv.updateName(nombreaAnteriorMelodia, nuevoNombre);
				// Cambio el nombre en el archivo ini
				// persisitidor_Ini.saveIni(melodia);
				melodia.setNombre(nuevoNombre);
				repositorioMelodia_Ini.updateIni(melodia, nombreaAnteriorMelodia, append);
				repositorioMelodia_Ini.updateNombreIni(nombreaAnteriorMelodia, nuevoNombre);
				repositorioMelodia_Ini.updateSection(nombreaAnteriorMelodia, nuevoNombre);
				// Le cambio el nombre al archivo csv

				// Le cambio el nombre a la carpeta
				Funcion_Helper.updateFolder(nombreaAnteriorMelodia, nuevoNombre);
			}

		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
	}

	private Melodia loadMelodyFromIni(String nombreaAnteriorMelodia) {
		Melodia melodia;
		melodia = repositorioMelodia_Ini.load(nombreaAnteriorMelodia);
		return melodia;
	}

	public void updateInstrumentMelodia(String nombreMelodia, String instrumento) {
		Melodia melodia;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(instrumento);
			melodia = loadMelodyFromIni(nombreMelodia);
			if (melodia != null) {
				melodia.setInstrument(instrumento);
				repositorioMelodia_Ini.update(melodia);
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
			melodia = loadMelodyFromIni(nombreMelodia);
			if (melodia != null) {
				melodia.setTempo(tempo);
				repositorioMelodia_Ini.update(melodia);
			}
		} catch (RuntimeException re) {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}

	public void updateNota(String nombreMelodia, String idNota, String nombreNota, String octava, String figura,
			String alteracion) {
		Melodia melodia;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(idNota);
			Funcion_Helper.validarString(nombreNota);
			Funcion_Helper.validarString(octava);
			Funcion_Helper.validarString(figura);
			Funcion_Helper.validarString(alteracion);
			melodia = loadMelodyFromIni(nombreMelodia);
			if (melodia != null) {
				melodia.loadNotas(repositorioNotas_Csv);
				// persisitidor_Csv.load(melodia.getNotas(), nombreMelodia);
				melodia.updateNota(idNota, nombreNota, octava, figura, alteracion, repositorioNotas_Csv);
			}
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public void PedirOpcion(Scanner input, MyPattern pattern) {
		this.lector = new Lector(input);
		try {
			lector.EjecutarOpcion(this, repositorioMelodia_Ini, repositorioNotas_Csv, opciones, input, pattern);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}

	}

	public void listarCanciones() {
		System.out.println("Canciones:");
		// create a file that is really a directory
		File aDirectory = new File(ROOT);
		// get a listing of all files in the directory
		String[] filesInDir = aDirectory.list();
		// sort the list of files (optional)
		Arrays.sort(filesInDir);
		// have everything i need, just print it now
		for (int i = 0; i < filesInDir.length; i++) {
			System.out.println(i + 1 + ":" + filesInDir[i]);
		}
	}

	public void deleteMelodia(String nombreCancion) {
		String sPath = ROOT + File.separator + nombreCancion;
		File pathCancion = new File(sPath);
		Path path = Paths.get(sPath);
		if (Files.exists(path)) {
			try {
				pathCancion.delete();
			} catch (Exception e) {
				throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
			}
		}

	}

	public void listarInstrumentos() {
		Instrumentos[] lstInstrumentos = Instrumentos.values();
		for (int i = 0; i < lstInstrumentos.length; i++) {
			System.out.println(i + 1 + "." + lstInstrumentos[i]);
		}
	}

	public void listarNotasDeMelodia(String nombreMelodia) {
		ArrayList<Nota> notas = getNotas(nombreMelodia);
		if (notas != null) {
			for (Nota nota : notas) {
				System.out.println(nota.toString());
			}
		}
		
	}

	public void removeNotaById(String nombreMelodia, String idNota) {
		Melodia melodia = loadMelodyFromIni(nombreMelodia);
		if (melodia != null) {
			melodia.removeNotaById(idNota, repositorioNotas_Csv);
		}else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}
		
	}

}
