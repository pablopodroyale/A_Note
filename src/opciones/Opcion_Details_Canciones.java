package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import interfaces.RepoMelodias;


public class Opcion_Details_Canciones extends Opcion {

	public Opcion_Details_Canciones(RepoMelodias repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		manager.listarCanciones();
	}

}
