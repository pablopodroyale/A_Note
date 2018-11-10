package opciones;

import java.util.Scanner;

import entidades.AnoteManager;
import funciones_helper.Funcion_Helper;
import interfaces.IRepositorios;

public class Opcion_AddNota extends Opcion {

	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodia";
	private static final String MENSAJE_NOMBRE_NOTA = "Ingrese el nombre de la nota(DO 'C', Re 'D', Mi 'E', Fa 'F', Sol 'G', Si 'B')";
	private static final String MENSAJE_NUMERO_OCTAVA = "Ingrese el numero de octava del 1 al 9";;
	private static final String MENSAJE_NOMBRE_FIGURA = "Ingrese la figura, REDONDA 'w', BLANCA 'h',NEGRA 'q', CORCHEA 'i', SEMICORCHEA 's', FUSA 't', SEMIFUSA 'x'";
	private static final String MENSAJE_TIPO_ALTERACION = "Ingrese la alteracion, Sotenido '#', bemol 'b', becuadro 'n' ";

	public Opcion_AddNota(IRepositorios repositorioMelodia) {
		super(repositorioMelodia);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		String nombreNota = Funcion_Helper.pedirString(MENSAJE_NOMBRE_NOTA, input);
		String octava = Funcion_Helper.pedirString(MENSAJE_NUMERO_OCTAVA, input);
		String figura = Funcion_Helper.pedirString(MENSAJE_NOMBRE_FIGURA, input);
		String alteracion = Funcion_Helper.pedirString(MENSAJE_TIPO_ALTERACION, input);

		manager.addNoteToMelody(nombreMelodia, nombreNota, octava, figura, alteracion);

	}

}
