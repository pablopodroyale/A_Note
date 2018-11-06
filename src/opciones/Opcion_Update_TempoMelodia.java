package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import funciones_helper.Funcion_Helper;
import repository.IMelodia_Repository;
import repository.INota_Repository;

public class Opcion_Update_TempoMelodia extends Opcion {
	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodía";
	private static final String MENSAJE_TEMPO = "Ingrese el tiempo de la melodía, mayor a 0";



	public Opcion_Update_TempoMelodia(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		String tempo = Funcion_Helper.pedirString(MENSAJE_TEMPO, input);
		manager.updateTempoMelodia(nombreMelodia, tempo);
		
	}

}
