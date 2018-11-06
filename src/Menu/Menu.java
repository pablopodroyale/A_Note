package Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
	protected String title;
	private ArrayList<String> options;
	private Scanner input;

	public Menu(String title, Scanner scanner) {
		this.title = title;
		this.input = scanner;
		options = new ArrayList<String>();
		cargarOpciones();
	}

	private void cargarOpciones() {
		register("Crear Melod�a");
		register("Cargar melod�a existente");
		register("Listar melod�a");
		register("Agregar nota");
		register("Setear tiempo");
		register("Setear instrumento");
		register("Modificar nombre de la melodia");
		register("Modificar instrumento de una melodia");
		register("Modificar tiempo de una melod�a");
		register("Modificar nota de una melodia");
		register("Eliminar nota de una melodia");
		register("Eliminar melodia");
		register("Reproducir");
		register("Salvar");

	}

	/***
	 * Registra el texto pasado como parametro como una opcion del men�. El numero
	 * de opcion se crea automaticamente.
	 */
	public void register(String caption) {
		options.add(caption);
	}

	/***
	 * Muestra todas las options del menu incluyendo la opcion automatica "Salir"
	 * (que siempre es la �ltima)
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
	 * Devuelve el valor de una opci�n v�lida de men�. (Estar� entre 1 y la cantidad
	 * de options).
	 */
	public int choice() {
		int selected = 0;
		do {
			show();
			selected = input.nextInt();
			input.nextLine();
		} while (selected < 1 || selected > exitValue());
		return selected;
	}

	/***
	 * Devuelve el valor de la opci�n "Salir" generada autom�ticamente. Coincide con
	 * la cantidad de options creadas + 1.
	 */
	public int exitValue() {
		return options.size() + 1;
	}

}