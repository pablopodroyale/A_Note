package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;


public class Opcion_Delete_NotaMelodia extends Opcion  {
	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodia";
	private static final String MENSAJE_PEDIR_ID = "Ingrese el id de la nota a eliminar";
	//private static final String MENSAJE_NOMBRE_NOTA = "Ingrese el nombre de la nota";

	public Opcion_Delete_NotaMelodia(RepoMelodias repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		//Melodia melodia;
		//Listar las notas
		System.out.println("¿Cuál de las siguientes melodías desea modificar?");
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		System.out.println("¿Cuál de las siguientes notas desea eliminar? Ingrese el Id");
		manager.listarNotasDeMelodia(nombreMelodia);
		String idNota = Funcion_Helper.pedirString(MENSAJE_PEDIR_ID, input);
		//String nombreNota = Funcion_Helper.pedirString(MENSAJE_NOMBRE_NOTA, input);
		manager.removeNotaById(nombreMelodia, idNota);
		
	}

}
