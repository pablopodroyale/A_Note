package opciones;

import java.util.Scanner;


import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;

import interfaces.RepoMelodias;

public class Opcion_Update_NombreMelodia extends Opcion {
	private static final String MENSAJE_NOMBRE_ANTERIOR = "Ingrese el nombre de la melodia a modificar";
	private static final String MENSAJE_NOMBRE_NUEVO = "Ingrese el nuevo nombre";

	public Opcion_Update_NombreMelodia(RepoMelodias repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		System.out.println("Canciones guardadas:");
		manager.listarCanciones();
		String nombreaAnteriorMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_ANTERIOR, input);
		String nuevoNombre = Funcion_Helper.pedirString(MENSAJE_NOMBRE_NUEVO, input);
		manager.updateNombreMelodia(nombreaAnteriorMelodia, nuevoNombre, false);
	}

}
