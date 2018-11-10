package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.IRepositorios;

public class Opcion_Create_Melodia extends Opcion {

	private static final String MENSAJE = "Ingrese el nombre de la melodia";

	public Opcion_Create_Melodia(IRepositorios repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		String nombre = Funcion_Helper.pedirString(MENSAJE, input);
		manager.createMelody(nombre);

	}

}
