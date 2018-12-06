package opciones;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;

import interfaces.RepoMelodias;

public class OpcionConsola_Update_NombreCancion extends Opcion {
	private static final String MENSAJE_NOMBRE_ANTERIOR = "Ingrese el nombre de la melodia a modificar";
	private static final String MENSAJE_NOMBRE_NUEVO = "Ingrese el nuevo nombre";

	public OpcionConsola_Update_NombreCancion() {
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		ArrayList<String> canciones = manager.getCanciones();
		Funcion_Helper.listarCanciones(canciones);
		String nombreaAnteriorMelodia = Funcion_Helper.pedirNombreCancion(MENSAJE_NOMBRE_ANTERIOR, input, canciones);
		String nuevoNombre = Funcion_Helper.pedirString(MENSAJE_NOMBRE_NUEVO, input);
		manager.updateNombreMelodia(nombreaAnteriorMelodia, nuevoNombre, false);
	}

}
