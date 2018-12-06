package opciones;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;

public class OpcionConsola_Details_Canciones extends Opcion {

	private static final String ERROR_LISTA_VACIA = "No hay canciones compuestas";

	public OpcionConsola_Details_Canciones() {
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		ArrayList<String> canciones = manager.getCanciones();
		if (canciones.isEmpty()) {
			throw new RuntimeException(ERROR_LISTA_VACIA);
		} else {
			Funcion_Helper.listarCanciones(canciones);
		}
	}
}
