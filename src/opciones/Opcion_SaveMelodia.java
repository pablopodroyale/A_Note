package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;

public class Opcion_SaveMelodia extends Opcion {

	private static final String MENSAJE = "Ingrese el nombre de la melodia a salvar";

	public Opcion_SaveMelodia(RepoMelodias repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE, input);
		if (Funcion_Helper.existMelody(nombreMelodia)) {
			manager.deleteMelodia(nombreMelodia);
		}
		manager.save(nombreMelodia, false);
	}

}
