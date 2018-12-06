package Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
	private static final String ERROR_OPCION_INVALIDA = "Error, opcion invalida";
	protected String title;
	private ArrayList<String> options;
	private Scanner input;

	public Menu(String title, Scanner scanner) {
		this.title = title;
		this.input = scanner;
		options = new ArrayList<String>();
		// cargarOpciones();
	}

	/***
	 * Registra el texto pasado como parametro como una opcion del menú. El numero
	 * de opcion se crea automaticamente.
	 */
	public void register(String caption) {
		options.add(caption);
	}

	/***
	 * Muestra todas las options del menu incluyendo la opcion automatica "Salir"
	 * (que siempre es la última)
	 */
	public void show() {
		int n = 1;
		System.out.println(title);
		for (String op : options) {
			System.out.println(n + "-" + op);
			n++;
		}
		System.out.println(n + "-Salir");
		System.out.println("Ingrese una opcion entre 1 y " + exitValue() + ":");
	}

	/***
	 * Devuelve el valor de una opción válida de menú. (Estará entre 1 y la cantidad
	 * de options).
	 */
	public int choice() {
		int selected = 0;
		do {
			show();
			try {
				selected = input.nextInt();
				input.nextLine();
			} catch (Exception e) {

			} finally {
				
				if (selected == 0) {
					input.nextLine();
				}
			}
			if (selected < 1 || selected > exitValue()) {
				throw new IllegalArgumentException(ERROR_OPCION_INVALIDA);
			}
		} while (selected < 1 || selected > exitValue());
		return selected;
	}

	/***
	 * Devuelve el valor de la opción "Salir" generada automáticamente. Coincide con
	 * la cantidad de options creadas + 1.
	 */
	public int exitValue() {
		return options.size() + 1;
	}

}
