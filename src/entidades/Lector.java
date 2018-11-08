package entidades;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;

import Menu.Menu;
import interfaces.ILector;
import opciones.Opcion;

import repository.INota_Repository;
import repository.IMelodia_Repository;

public class Lector implements ILector {
	// private static final String ERROR_EL_TIPO_ES_INVALIDO = "Error, el tipo es
	// invalido";
	private static final String ERROR_OPCION_INVALIDA = "Error, opcion invalida";
	private static final String ANOTE = "Anote";
	Opcion opcion;
	Menu menu;

	public Lector(Scanner input) {
		this.opcion = null;
		this.menu = new Menu(ANOTE, input);
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
	public void EjecutarOpcion(AnoteManagger manager, IMelodia_Repository persisitidor_Ini,
			INota_Repository persisitidor_Csv, LinkedHashMap<Integer, Opcion> opciones, Scanner input,
			MyPattern pattern) {
		int pOpcion;
		do {
			pOpcion = menu.choice();
			if (pOpcion != menu.exitValue()) {
				try {
					this.opcion = getOpcion(opciones, pOpcion);
					opcion.ejecutar(manager, input, pattern);
					System.out.println("Presione enter para continuar");
					System.in.read();
				} catch (Exception e) {
					System.out.println(e.getMessage());

				} finally {
					try {
						System.in.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("Gracias, vuelvas prontos!!");
			}

		} while (pOpcion != 0 && pOpcion != menu.exitValue());

	}

}
