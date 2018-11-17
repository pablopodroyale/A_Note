package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import entidades.Instrumentos;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;

public class Opcion_Create_Melodia extends Opcion {

	private static final String MENSAJE = "Ingrese el nombre de la melodia";
	private static final String MENSAJE_NOMBRE_INSTRUMENTO = "Ingrese el nombre del instrumento de la lista, tal como figura en mayúscula";
	private static final String MENSAJE_TEMPO_MELODIA = "Ingrese el tiempo deseado mayor a 0";
	private static final int MAX_INSTRUMENT = Instrumentos.values().length - 1;
	private static final int MIN_INSTRUMENT = 0;

	public Opcion_Create_Melodia(RepoMelodias repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		String nombre = Funcion_Helper.pedirString(MENSAJE, input);
		manager.listarInstrumentos();
		String instrument = Funcion_Helper.pedirString(MENSAJE_NOMBRE_INSTRUMENTO, input);
		Funcion_Helper.validarRango(MIN_INSTRUMENT, MAX_INSTRUMENT, Instrumentos.valueOf(instrument).getValue());
		String tempo = Funcion_Helper.pedirString(MENSAJE_TEMPO_MELODIA, input);
		manager.createMelody(nombre, instrument, tempo);

	}

}
