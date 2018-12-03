package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;

public class OpcionConsola_Delete_Cancion extends Opcion {

	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodia";

	public OpcionConsola_Delete_Cancion() {
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		System.out.println("¿Cuál de las siguientes melodías desea eliminar?");
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		manager.deleteMelodia(nombreMelodia);
	}

}
