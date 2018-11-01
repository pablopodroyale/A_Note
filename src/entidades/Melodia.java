package entidades;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import edu.ort.csv_utils.ArticuloWriter;
import edu.ort.csv_utils.CSVCCompatible;
import funciones_helper.Funcion_Helper;
import interfaces.sonable;
import persistencia.NoteWriter;
import tp1.utils.textfiles.IniManager;

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
	private static final String TEMPO = "Tiempo";
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
	public void play(PatternSingleton pattern, PlayerSingleton player) {
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

	public void save(String path, PatternSingleton pattern) {
		if (!this.instrument.isEmpty()) {
			pattern.add(instrument);
		}
		if (!this.tempo.isEmpty()) {
			pattern.add(tempo);
		}

		notas.forEach(x -> {
			pattern.add(" " + x.getNombre() + x.getOctava() + x.getFigura() + x.getAlteracion() + " ");
		});
		try {
			pattern.save(new File(path), this.nombre);
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}

	}

	public void save(PatternSingleton pattern) {
		// Crea el directorio
		String path = new File("").getAbsolutePath() + File.separator + CANCIONES + File.separator + this.nombre;
		String pathIni = path + File.separator + this.nombre + EXTENSION_INI;
		String pathCsv = path + File.separator + this.nombre + EXTENSION_CSV;
		try {
			Files.createDirectories(Paths.get(path));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		// Guarda la melodia
		IniManager iniManager = new IniManager(pathIni);
		iniManager.addSection(nombre);
		iniManager.setItem(nombre, TEMPO, this.tempo);
		iniManager.setItem(nombre, INSTRUMENT, this.instrument);
		iniManager.save();
		// Guarda las notas
		NoteWriter noteWriter = new NoteWriter(Nota.getHeader());
		noteWriter.writeAll(pathCsv, notas);

	}

}
