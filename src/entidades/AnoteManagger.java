package entidades;

import java.util.LinkedHashMap;

import funciones_helper.Funcion_Helper;
import repository.CSV_Repository;
import repository.IRepository_CSV;
import repository.IRepository_Ini;
import repository.IniRepository;

public class AnoteManagger {
	private static final int MAX_INSTRUMENT = 127;
	private static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	private static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	private static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";
	// private static final String ROOT_CANCIONES = "Canciones";
	// private static final String ROOT = new File("").getAbsolutePath() +
	// File.separator + ROOT_CANCIONES + File.separator;
	private IRepository_Ini persisitidor_Ini;
	private IRepository_CSV persisitidor_Csv;
	private LinkedHashMap<String, Melodia> melodias;

	public AnoteManagger() {
		this.melodias = new LinkedHashMap<>();
		this.persisitidor_Ini = new IniRepository();
		this.persisitidor_Csv = new CSV_Repository();
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
			Melodia aux = melodias.get(nombreMelodia);
			if (aux == null) {
				throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
			} else {
				return aux;
			}
		}
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
		persisitidor_Ini.saveIni(melodia, append);
		melodia.save(persisitidor_Csv, append);
	}

	// Revisar como hacer el load de la melodia, si aca o en melodia usando el ini
	public void load(String nombreMelodia) {
		Melodia melodia = persisitidor_Ini.load(nombreMelodia);
		melodia.load(persisitidor_Csv);
		melodia.list();
		melodias.put(nombreMelodia, melodia);

	}

	public void updateNombreMelodia(String nombreaAnteriorMelodia, String nuevoNombre, boolean append) {
		Melodia melodia;
		try {
			// Pregunto primero si esta en memoria porque quizas esta creada en esta sesion

			melodia = persisitidor_Ini.load(nombreaAnteriorMelodia);
			if (melodia == null) {
				throw new IllegalArgumentException(ERROR_MELODIA_EXISTENTE);
			} else {
				Funcion_Helper.tryOpen();
				// Cambio el nombre de la melodia en memoria
				// persisitidor_Csv.updateName(nombreaAnteriorMelodia, nuevoNombre);
				// Cambio el nombre en el archivo ini
				// persisitidor_Ini.saveIni(melodia);
				melodia.setNombre(nuevoNombre);
				persisitidor_Ini.updateIni(melodia, nombreaAnteriorMelodia, append);
				persisitidor_Ini.updateNombreIni(nombreaAnteriorMelodia, nuevoNombre);
				persisitidor_Ini.updateSection(nombreaAnteriorMelodia, nuevoNombre);
				// Le cambio el nombre al archivo csv

				// Le cambio el nombre a la carpeta
				Funcion_Helper.updateFolder(nombreaAnteriorMelodia, nuevoNombre);
			}

		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
	}

	public void updateInstrumentMelodia(String nombreMelodia, String instrumento) {
		Melodia melodia;
		try {
			Funcion_Helper.validarString(nombreMelodia);
			Funcion_Helper.validarString(instrumento);
			melodia = persisitidor_Ini.load(nombreMelodia);
			if (melodia != null) {
				melodia.setInstrument(instrumento);
				persisitidor_Ini.update(melodia);
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
			melodia = persisitidor_Ini.load(nombreMelodia);
			if (melodia != null) {
				melodia.setTempo(tempo);
				persisitidor_Ini.update(melodia);
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
			melodia = persisitidor_Ini.load(nombreMelodia);
			if (melodia != null) {
				melodia.loadNotas(persisitidor_Csv);
				//persisitidor_Csv.load(melodia.getNotas(), nombreMelodia);
				melodia.updateNota(idNota, nombreNota, octava, figura, alteracion, persisitidor_Csv);
			}
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}
}
