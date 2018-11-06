package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import entidades.Melodia;
import funciones_helper.Funcion_Helper;
import repository.IMelodia_Repository;
import repository.INota_Repository;

public class Opcion_Delete_Melodia extends Opcion {

	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodia";

	public Opcion_Delete_Melodia(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		Melodia melodia;
		// Listar las notas
		System.out.println("¿Cuál de las siguientes melodías desea modificar?");
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		manager.deleteMelodia(nombreMelodia);
	}

}
