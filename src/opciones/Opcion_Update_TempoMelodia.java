package opciones;

import java.util.Scanner;


import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;

public class Opcion_Update_TempoMelodia extends Opcion {
	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodía";
	private static final String MENSAJE_TEMPO = "Ingrese el tiempo de la melodía, mayor a 0";



	public Opcion_Update_TempoMelodia(RepoMelodias repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		String tempo = Funcion_Helper.pedirString(MENSAJE_TEMPO, input);
		manager.updateTempoMelodia(nombreMelodia, tempo);
		
	}

}
