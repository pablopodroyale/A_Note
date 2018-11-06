package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;
import funciones_helper.Funcion_Helper;
import repository.IMelodia_Repository;
import repository.INota_Repository;

public class Opcion_AddNota extends Opcion {

	private static final String MENSAJE_NOMBRE_MELODIA = "Ingrese el nombre de la melodia";
	private static final String MENSAJE_NOMBRE_NOTA = "Ingrese el nombre de la nota";;
	private static final String MENSAJE_NUMERO_OCTAVA = "Ingrese el numero de octava del 1 al 9";;
	private static final String MENSAJE_NOMBRE_FIGURA = "Ingrese la figura, REDONDA 'w', BLANCA 'h',NEGRA 'q', CORCHEA 'i', SEMICORCHEA 's', FUSA 't', SEMIFUSA 'x'";
	private static final String MENSAJE_TIPO_ALTERACION = "Ingrese la alteracion, Sotenido '#', bemol 'b', becuadro 'n' ";

	public Opcion_AddNota(IMelodia_Repository ini, INota_Repository csv) {
		super(ini, csv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern) {
		String nombreMelodia = Funcion_Helper.pedirString(MENSAJE_NOMBRE_MELODIA, input);
		String nombreNota = Funcion_Helper.pedirString(MENSAJE_NOMBRE_NOTA, input);
		String octava = Funcion_Helper.pedirString(MENSAJE_NUMERO_OCTAVA, input);
		String figura = Funcion_Helper.pedirString(MENSAJE_NOMBRE_FIGURA, input);
		String alteracion = Funcion_Helper.pedirString(MENSAJE_TIPO_ALTERACION, input);

		manager.addNoteToMelody(nombreMelodia, nombreNota, octava, figura, alteracion);

	}

}
