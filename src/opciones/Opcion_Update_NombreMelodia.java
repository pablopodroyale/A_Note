package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import funciones_helper.Funcion_Helper;
import repository.IMelodia_Repository;
import repository.INota_Repository;

public class Opcion_Update_NombreMelodia extends Opcion {
	private static final String MENSAJE_NOMBRE_ANTERIOR = "Ingrese el nombre de la melodia a modificar";
	private static final String MENSAJE_NOMBRE_NUEVO = "Ingrese el nuevo nombre";

	public Opcion_Update_NombreMelodia(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		System.out.println("Canciones guardadas:");
		manager.listarCanciones();
		String nombreaAnteriorMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_ANTERIOR, input);
		String nuevoNombre = Funcion_Helper.pedirString(MENSAJE_NOMBRE_NUEVO, input);
		manager.updateNombreMelodia(nombreaAnteriorMelodia, nuevoNombre, false);
	}

}
