package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.IRepositorios;

public class Opcion_Details_Cancion extends Opcion {

	private static final String MENSAJE = "Ingrese el nombre de la canción a detallar";

	public Opcion_Details_Cancion(IRepositorios repositoryMelodia) {
		super(repositoryMelodia);
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
