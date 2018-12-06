package opciones;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;

public class OpcionConsola_Delete_Cancion extends Opcion {

	private static final String MENSAJE_ELIMINAR_CANCION = "¿Cuál de las siguientes melodías desea eliminar?";
	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodia";
	private static final String ERROR_LISTA_VACIA = "No hay canciones guardadas";

	public OpcionConsola_Delete_Cancion() {
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		ArrayList<String> canciones = manager.getCanciones();
		if (canciones.isEmpty()) {
			throw new IllegalArgumentException(ERROR_LISTA_VACIA);
		} else {
			System.out.println(MENSAJE_ELIMINAR_CANCION);
			Funcion_Helper.listarCanciones(canciones);
			String nombreCancion = Funcion_Helper.pedirCancion(MENSAJE_NOMBRE_MELODIA, input, canciones);
			manager.deleteMelodia(nombreCancion);
		}
	}
}
