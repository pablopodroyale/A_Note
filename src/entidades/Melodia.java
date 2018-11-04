package entidades;

import java.util.ArrayList;
import funciones_helper.Funcion_Helper;
import interfaces.sonable;
import repository.IRepository_CSV;

public class Melodia implements sonable {

	private static final int TEMPO_MAXIMO = Integer.MAX_VALUE;
	private static final int TEMPO_MINIMO = 1;
	private static final String MASCARA_TEMPO = " T%s ";
	private static final String MASCARA_INSTRUMENT = " I[%s] ";
	private static final int INSTRUMENTO_MIN = 0;
	private static final int INSTRUMENTO_MAX = 127;
	private static final String MASCARAPATH = "%s";
	private static final String EXTENSION_INI = ".ini";
	private static final String EXTENSION_CSV = ".csv";
	private static final String TEMPO_CONST_STRING = "Tiempo";
	private static final String INSTRUMENT = "Instrumento";
	private static final String CANCIONES = "Canciones";

	// private Compas compas;
	private String tempo;
	private String instrument;
	private ArrayList<Nota> notas;
	private String nombre;

	/***
	 * Orden de parametros: nombre, instrument, tempo
	 * 
	 * @param nombreMelodia
	 */
	public Melodia(String nombreMelodia) {
		this.notas = new ArrayList<>();
		this.nombre = nombreMelodia;
		this.tempo = "";
		this.instrument = "";

	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		try {
			Funcion_Helper.validarString(tempo);
			Funcion_Helper.validarRango(TEMPO_MINIMO, TEMPO_MAXIMO, Integer.parseInt(tempo));
			this.tempo = String.format(MASCARA_TEMPO, tempo);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	@Override
	public void play(MyPattern pattern, PlayerSingleton player) {
		if (!this.instrument.isEmpty()) {
			pattern.add(instrument);
		}
		if (!this.tempo.isEmpty()) {
			pattern.add(tempo);
		}

		notas.forEach(x -> {
			pattern.add(" " + x.getNombre() + x.getOctava() + x.getFigura() + x.getAlteracion() + " ");
		});
		player.play(pattern);
		System.exit(0);

	}

	public void setNote(Nota nota) {
		this.notas.add(nota);
	}

	public void setInstrument(String instrument) {
		try {
			Funcion_Helper.validarString(instrument);
			Funcion_Helper.validarRango(INSTRUMENTO_MIN, INSTRUMENTO_MAX, Instrumentos.valueOf(instrument).getValue());
			this.instrument = String.format(MASCARA_INSTRUMENT, instrument);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public void save(IRepository_CSV persisitidor_Csv, boolean append) {
		// Guarda las notas
		persisitidor_Csv.saveCSV(this.notas, this.nombre, append);
	}

	public int getTempoMaximo() {
		return TEMPO_MAXIMO;
	}

	public int getTempoMinimo() {
		return TEMPO_MINIMO;
	}

	public String getMascaraTempo() {
		return MASCARA_TEMPO;
	}

	public String getMascaraInstrument() {
		return MASCARA_INSTRUMENT;
	}

	public int getInstrumentoMin() {
		return INSTRUMENTO_MIN;
	}

	public int getInstrumentoMax() {
		return INSTRUMENTO_MAX;
	}

	public String getMascarapath() {
		return MASCARAPATH;
	}

	public String getExtensionIni() {
		return EXTENSION_INI;
	}

	public String getExtensionCsv() {
		return EXTENSION_CSV;
	}

	public String getInstrumentConst() {
		return INSTRUMENT;
	}

	public String getCancionesConst() {
		return CANCIONES;
	}

	public String getInstrument() {
		return instrument;
	}

	public ArrayList<Nota> getNotas() {
		return notas;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTempoConstString() {
		return TEMPO_CONST_STRING;
	}

	public void load(IRepository_CSV persisitidor_Csv) {
		persisitidor_Csv.load(notas, this.nombre);

	}

	public void list() {
		notas.forEach(x -> System.out.println(x.toString()));
	}

	public void setNombre(String nombreMelodia) {
		Funcion_Helper.validarString(nombreMelodia);
		this.nombre = nombreMelodia;

	}

}
