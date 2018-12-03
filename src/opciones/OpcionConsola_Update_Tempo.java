package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;

public class OpcionConsola_Update_Tempo extends Opcion {
	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodía";
	private static final String MENSAJE_TEMPO = "Ingrese el tiempo de la melodía, mayor a 0";

	public OpcionConsola_Update_Tempo() {

	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		String tempo = Funcion_Helper.pedirString(MENSAJE_TEMPO, input);
		//manager.updateTempo(nombreMelodia, tempo);

	}

}
