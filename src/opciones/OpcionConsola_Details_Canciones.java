package opciones;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;

public class OpcionConsola_Details_Canciones extends Opcion {

	public OpcionConsola_Details_Canciones() {
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		ArrayList<String> canciones = manager.getCanciones();
		Funcion_Helper.listarCanciones(canciones);
	}
}
