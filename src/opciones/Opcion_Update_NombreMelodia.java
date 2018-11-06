package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import repository.IMelodia_Repository;
import repository.INota_Repository;

public class Opcion_Update_NombreMelodia extends Opcion {

	public Opcion_Update_NombreMelodia(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		// TODO Auto-generated method stub
		
	}

}
