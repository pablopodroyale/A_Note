package entidades;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Scanner;

import Menu.Menu;
import interfaces.Vista;
import interfaces.RepoMelodias;
import opciones.Opcion;

public class VistaConsola implements Vista {
	// private static final String ERROR_EL_TIPO_ES_INVALIDO = "Error, el tipo es
	// invalido";
	private static final String BIENVENIDO = "Bienvenido a Anote, el programa con el que podrá componer, reproducir y guardar melodías fácilmente.\n"
			+ "Seleccione una opción para continuar:";
	private static final String ERROR_OPCION_INVALIDA = "Error, opcion invalida";
	private static final String ANOTE = "Anote";
	private Opcion opcion;
	private Menu menu;
	private Scanner scanner;

	public VistaConsola() {
		this.scanner = new Scanner(System.in);
		this.opcion = null;
		this.menu = new Menu(ANOTE, scanner);
		System.out.println(BIENVENIDO);
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
	public void EjecutarOpcion(AnoteManager manager, RepoMelodias melodiaRepository,
			LinkedHashMap<Integer, Opcion> opciones) {
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
						System.out.println(e.getMessage());
					}
				}
			} else if (pOpcion == menu.exitValue()) {
				melodiaRepository.saveAll();
				try {
					melodiaRepository.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("Gracias, vuelvas prontos!!");
			} else {
				throw new IllegalArgumentException(ERROR_OPCION_INVALIDA);
			}

		} while (pOpcion != 0 && pOpcion != menu.exitValue());

	}

}
