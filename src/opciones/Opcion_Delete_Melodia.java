package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.IRepositorios;

public class Opcion_Delete_Melodia extends Opcion {

	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodia";

	public Opcion_Delete_Melodia(IRepositorios repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		System.out.println("¿Cuál de las siguientes melodías desea eliminar?");
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		manager.deleteMelodia(nombreMelodia);
	}

}
