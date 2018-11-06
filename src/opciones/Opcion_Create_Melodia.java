package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import funciones_helper.Funcion_Helper;
import repository.INota_Repository;
import repository.IMelodia_Repository;

public class Opcion_Create_Melodia extends Opcion {

	private static final String MENSAJE = "Ingrese el nombre de la melodia";

	public Opcion_Create_Melodia(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		String nombre = Funcion_Helper.pedirString(MENSAJE, input);
		manager.createMelody(nombre);

	}

}
