package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;
import repository.RepoCancionesMixto;
import repository.Repositorio_CancionDb;

public class OpcionConsola_Details_Cancion extends Opcion {

	private static final String MENSAJE = "Ingrese el nombre de la canción a detallar";

	public OpcionConsola_Details_Cancion() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		System.out.println("Canciones guardadas:");
		manager.listarCanciones();
		System.out.println("---------------------");
		String nombre = Funcion_Helper.pedirString(MENSAJE, input);
		manager.detallesMelodia(nombre);
		System.out.println("---------------------");
	}

}
