package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import entidades.MyPattern;
import funciones_helper.Funcion_Helper;
import repository.INota_Repository;
import repository.IMelodia_Repository;

public class Opcion_SaveMelodia extends Opcion {

	private static final String MENSAJE = "Ingrese el nombre de la melodia a salvar";

	public Opcion_SaveMelodia(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE, input);
		if (Funcion_Helper.existMelody(nombreMelodia)) {
			manager.deleteMelodia(nombreMelodia);
		}
		manager.save(nombreMelodia, (MyPattern) pattern, false);
	}

}
