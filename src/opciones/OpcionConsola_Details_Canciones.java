package opciones;

import java.util.Scanner;

import entidades.AnoteManager;

public class OpcionConsola_Details_Canciones extends Opcion {

	public OpcionConsola_Details_Canciones() {
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		manager.listarCanciones();
	}

}
