package entidades;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;

import Menu.Menu;
import interfaces.ILector;
import interfaces.IRepositorios;
import opciones.Opcion;



public class Lector implements ILector {
	// private static final String ERROR_EL_TIPO_ES_INVALIDO = "Error, el tipo es
	// invalido";
	private static final String ERROR_OPCION_INVALIDA = "Error, opcion invalida";
	private static final String ANOTE = "Anote";
	private Opcion opcion;
	private Menu menu;
	private Scanner scanner;

	public Lector(Scanner input) {
		this.opcion = null;
		this.menu = new Menu(ANOTE, input);
		this.scanner = input; 
	}

	private Opcion getOpcion(LinkedHashMap<Integer, Opcion> opciones, int pOpcion) throws Exception {
		Opcion opcion;
		opcion = opciones.get(pOpcion);
		if (opcion == null) {
			throw new Exception(ERROR_OPCION_INVALIDA);
		}
		return opcion;
	}

	@Override
	public void EjecutarOpcion(AnoteManager manager, IRepositorios melodiaRepository,LinkedHashMap<Integer, Opcion> opciones) {
		int pOpcion;
		do {
			pOpcion = menu.choice();
			if (pOpcion != menu.exitValue()) {
				try {
					this.opcion = getOpcion(opciones, pOpcion);
					opcion.ejecutar(manager, this.scanner);
					System.out.println("Presione enter para continuar");
					System.in.read();
				} catch (Exception e) {
					System.out.println(e.getMessage());

				} finally {
					try {
						System.in.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				manager.saveAll();
				System.out.println("Gracias, vuelvas prontos!!");
			}

		} while (pOpcion != 0 && pOpcion != menu.exitValue());

	}

}
