package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import entidades.Melodia;
import entidades.MyPattern;
import entidades.PlayerSingleton;
import funciones_helper.Funcion_Helper;
import repository.IMelodia_Repository;
import repository.INota_Repository;

public class Opcion_Reproducir_Melodia extends Opcion {

	private static final String MENSAJE_NOMBRE_MELODIA = null;

	public Opcion_Reproducir_Melodia(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		Melodia melodia;
		// Listar las notas
		System.out.println("¿Cuál de las siguientes melodías desea reproducir?");
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		manager.play(nombreMelodia, (MyPattern) pattern, PlayerSingleton.getInstance());

	}

}
