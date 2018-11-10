package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.IRepositorios;

public class Opcion_Set_TempoMelodia extends Opcion  {

	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodía";
	private static final String MENSAJE_TEMPO_MELODIA = "Ingrese el tiempo deseado mayor a 0";

	public Opcion_Set_TempoMelodia(IRepositorios repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		String tempo = Funcion_Helper.pedirString(MENSAJE_TEMPO_MELODIA, input);
		manager.setTempo(nombreMelodia, tempo);
		
	}

}
