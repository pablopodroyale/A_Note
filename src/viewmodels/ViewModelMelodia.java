package viewmodels;

import java.util.ArrayList;

import entidades.Instrumentos;
import entidades.MyPattern;
import entidades.PlayerSingleton;
import funciones_helper.Funcion_Helper;
import interfaces.ISonable;

public class ViewModelMelodia {
	private static final int TEMPO_MAXIMO = Integer.MAX_VALUE;
	private static final int TEMPO_MINIMO = 1;
	private static final int INSTRUMENTO_MIN = 0;
	private static final int INSTRUMENTO_MAX = 127;
	private static final String ERROR_MELODIA_VACIA = null;
	private String nombre;
	private String tempo;
	private String instrument;
	private MyPattern pattern;

	public ViewModelMelodia() {
		this.pattern = new MyPattern();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		try {
			Funcion_Helper.validarRango(TEMPO_MINIMO, TEMPO_MAXIMO, Integer.parseInt(tempo));
			this.tempo = tempo;
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage());
		}
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		try {
			Funcion_Helper.validarInstrumento(instrument);
			// Funcion_Helper.validarRango(INSTRUMENTO_MIN, INSTRUMENTO_MAX,
			// Instrumentos.valueOf(instrument).getValue());
			this.instrument = instrument.toUpperCase();
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage());
		}
	}

	@Override
	public String toString() {
		return "ViewModelMelodia [nombre=" + nombre + ", tempo=" + tempo + ", instrument=" + instrument + "]";
	}

	
	public void play(PlayerSingleton player, ArrayList<ViewModelNota> notas) {
		if (notas.isEmpty()) {
			throw new IllegalArgumentException(ERROR_MELODIA_VACIA);
		} else {
			if (!this.instrument.isEmpty()) {
				this.pattern.add(Funcion_Helper.toJFugueInstrument(instrument));
			}
			if (!this.tempo.isEmpty()) {
				this.pattern.add(Funcion_Helper.toJfugueTempo(tempo));
			}
			notas.forEach(x -> {
				pattern.add(" " + x.getNombre() + x.getOctava() + x.getFigura() + x.getAlteracion() + " ");
			});
			try {
				player.play(pattern);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
