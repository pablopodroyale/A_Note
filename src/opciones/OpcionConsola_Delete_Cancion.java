package opciones;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;

public class OpcionConsola_Delete_Cancion extends Opcion {

	private static final String MENSAJE_ELIMINAR_CANCION = "¿Cuál de las siguientes melodías desea eliminar?";
	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodia";

	public OpcionConsola_Delete_Cancion() {
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		System.out.println(MENSAJE_ELIMINAR_CANCION);
		ArrayList<String> canciones = manager.getCanciones();
		Funcion_Helper.listarCanciones(canciones);
		String nombreCancion = Funcion_Helper.pedirNombreCancion(MENSAJE_NOMBRE_MELODIA, input, canciones);
		manager.deleteMelodia(nombreCancion);
	}
}
