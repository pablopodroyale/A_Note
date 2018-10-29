package entidades;

import java.util.ArrayList;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import funciones_helper.Funcion_Helper;
import interfaces.sonable;

public class Melodia implements sonable {
	private static final int TEMPO_MAXIMO = Integer.MAX_VALUE;
	private static final int TEMPO_MINIMO = 1;
	private static final String MASCARA_TEMPO = " T%s ";
	private static final String MASCARA_INSTRUMENT = " I[%s] ";
	private static final int INSTRUMENTO_MIN = 0;
	private static final int INSTRUMENTO_MAX = 127;
	private Compas compas;
	private String tempo;
	private String instrument;
	private ArrayList<Nota> notas;

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

	private String Id;

	public Melodia(String id) {
		this.notas = new ArrayList<>();
		this.Id = id;
		this.tempo = "";
		this.instrument = "";
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
			pattern.add(" " + x.getNombre() + x.getOctava() + x.getFigura() + x.getAlteracion() + " " );
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
			Funcion_Helper.validarRango(INSTRUMENTO_MIN, INSTRUMENTO_MAX,Instrumentos.valueOf(instrument).getValue());
			this.instrument = String.format(MASCARA_INSTRUMENT, instrument);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
		
	}

}
