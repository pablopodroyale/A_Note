package entidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.jfugue.pattern.Pattern;

import funciones_helper.Funcion_Helper;

public class AnoteManagger {
	private static final int MAX_INSTRUMENT = 127;
	private static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	private static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	private static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";

	private LinkedHashMap<String, Melodia> melodias;

	public AnoteManagger() {
		this.melodias = new LinkedHashMap<>();
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

	public void play(String nombreMelodia, PatternSingleton pattern, PlayerSingleton player) {
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

	public void save(String nombreMelodia, PatternSingleton pattern) {
		Melodia melodia = getMelody(nombreMelodia);
		melodia.save(pattern);
	}

}
