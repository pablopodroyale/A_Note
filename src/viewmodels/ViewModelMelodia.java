package viewmodels;

import entidades.Instrumentos;
import funciones_helper.Funcion_Helper;

public class ViewModelMelodia {
	private static final int TEMPO_MAXIMO = Integer.MAX_VALUE;
	private static final int TEMPO_MINIMO = 1;
	private static final int INSTRUMENTO_MIN = 0;
	private static final int INSTRUMENTO_MAX = 127;
	private String nombre;
	private String tempo;
	private String instrument;
	
	public ViewModelMelodia() {
		
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
			//Funcion_Helper.validarRango(INSTRUMENTO_MIN, INSTRUMENTO_MAX, Instrumentos.valueOf(instrument).getValue());
			this.instrument = instrument.toUpperCase();
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage());
		}
	}
}
