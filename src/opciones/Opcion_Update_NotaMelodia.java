package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.IRepositorios;

public class Opcion_Update_NotaMelodia extends Opcion {
	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodia";
	private static final String MENSAJE_NOMBRE_NOTA = "Ingrese el nombre de la nota(DO 'C', Re 'D', Mi 'E', Fa 'F', Sol 'G', Si 'B')";
	private static final String MENSAJE_NUMERO_OCTAVA = "Ingrese el numero de octava del 1 al 9";
	private static final String MENSAJE_NOMBRE_FIGURA = "Ingrese la figura, REDONDA 'w', BLANCA 'h',NEGRA 'q', CORCHEA 'i', SEMICORCHEA 's', FUSA 't', SEMIFUSA 'x'";
	private static final String MENSAJE_TIPO_ALTERACION = "Ingrese la alteracion, Sotenido '#', bemol 'b', becuadro 'n' ";
	private static final String MENSAJE_PEDIR_ID = "Ingrese el id de la nota a modificar";

	public Opcion_Update_NotaMelodia(IRepositorios repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		// Melodia melodia;
		// Listar las notas
		System.out.println("¿Cuál de las siguientes melodías desea modificar?");
		manager.listarCanciones();
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		System.out.println("Seleccione el id de la nota a modificar");
		manager.listarNotasDeMelodia(nombreMelodia);
		String idNota = Funcion_Helper.pedirString(MENSAJE_PEDIR_ID, input);
		String nombreNota = Funcion_Helper.pedirString(MENSAJE_NOMBRE_NOTA, input);
		String octava = Funcion_Helper.pedirString(MENSAJE_NUMERO_OCTAVA, input);
		String figura = Funcion_Helper.pedirString(MENSAJE_NOMBRE_FIGURA, input);
		String alteracion = Funcion_Helper.pedirString(MENSAJE_TIPO_ALTERACION, input);
		manager.updateNota(nombreMelodia, idNota, nombreNota, octava, figura, alteracion);
	}

}
