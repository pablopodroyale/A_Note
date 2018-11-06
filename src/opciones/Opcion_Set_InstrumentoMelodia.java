package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import funciones_helper.Funcion_Helper;
import repository.IMelodia_Repository;
import repository.INota_Repository;

public class Opcion_Set_InstrumentoMelodia extends Opcion  {
	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodía";
	private static final String MENSAJE_NOMBRE_INSTRUMENTO = "Ingrese el nombre del instrumento de la lista, tal como figura en mayúscula";
	
	public Opcion_Set_InstrumentoMelodia(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		manager.listarInstrumentos();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		String instrument = Funcion_Helper.pedirString(MENSAJE_NOMBRE_INSTRUMENTO, input);
		manager.setInstrument(nombreMelodia, instrument);
	}

}
