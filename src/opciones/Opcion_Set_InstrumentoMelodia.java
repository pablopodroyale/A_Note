package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;

public class Opcion_Set_InstrumentoMelodia extends Opcion  {
	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodía";
	private static final String MENSAJE_NOMBRE_INSTRUMENTO = "Ingrese el nombre del instrumento de la lista, tal como figura en mayúscula";
	
	public Opcion_Set_InstrumentoMelodia(RepoMelodias repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		Funcion_Helper.listarInstrumentos();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		String instrument = Funcion_Helper.pedirString(MENSAJE_NOMBRE_INSTRUMENTO, input);
		//manager.setInstrument(nombreMelodia, instrument);
	}

}
