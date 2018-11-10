package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import entidades.PlayerSingleton;
import funciones_helper.Funcion_Helper;
import interfaces.IRepositorios;

public class Opcion_Reproducir_Melodia extends Opcion {

	private static final String MENSAJE_NOMBRE_MELODIA = "Seleccione el nombre de la melodía que figura en la lista";

	public Opcion_Reproducir_Melodia(IRepositorios repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		// Melodia melodia;
		// Listar las notas
		System.out.println("¿Cuál de las siguientes melodías desea reproducir?");
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		
		manager.play(nombreMelodia, PlayerSingleton.getInstance());

	}

}
