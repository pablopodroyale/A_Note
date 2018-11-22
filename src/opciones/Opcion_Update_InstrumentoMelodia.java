package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;

public class Opcion_Update_InstrumentoMelodia extends Opcion {

	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodía de la lista";
	private static final String MENSAJE_INSTRUMENTO_MELODIA = "Ingrese el instrumento deseado de la lista";

	public Opcion_Update_InstrumentoMelodia(RepoMelodias repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		Funcion_Helper.listarInstrumentos();
		String instrument = Funcion_Helper.pedirString(MENSAJE_INSTRUMENTO_MELODIA, input);
		manager.updateInstrumentMelodia(nombreMelodia, instrument);

	}

}
