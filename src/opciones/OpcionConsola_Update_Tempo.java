package opciones;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.AnoteManager;
import entidades.Cancion;
import entidades.PlayerSingleton;
import funciones_helper.Funcion_Helper;

public class OpcionConsola_Update_Tempo extends Opcion {
	private static final String MENSAJE_NOMBRE_CANCION = "Ingrese el nombre de la melodía";
	private static final String MENSAJE_TEMPO = "Ingrese el tiempo de la melodía, mayor a 0";

	public OpcionConsola_Update_Tempo() {

	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		ArrayList<String> canciones = manager.getCanciones();
		Funcion_Helper.listarCanciones(canciones);
		String nombreCancion = Funcion_Helper.pedirNombreCancion(MENSAJE_NOMBRE_CANCION, input, canciones);
		Cancion cancion = manager.loadCancion(nombreCancion);
		String tempo = Funcion_Helper.pedirTempo(MENSAJE_TEMPO, input);
		cancion.setTempo(tempo);
	}

}
