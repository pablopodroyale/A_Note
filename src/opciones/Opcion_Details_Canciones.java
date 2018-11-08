package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import repository.IMelodia_Repository;
import repository.INota_Repository;

public class Opcion_Details_Canciones extends Opcion {

	public Opcion_Details_Canciones(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		manager.listarCanciones();
	}

}
