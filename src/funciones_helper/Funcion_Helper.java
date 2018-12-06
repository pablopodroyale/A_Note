package funciones_helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

import org.jfugue.pattern.PatternProducer;

import entidades.Figuras_Ritmicas;
import entidades.Instrumentos;
import entidades.Notas;
import viewmodels.ViewModelPista;

public final class Funcion_Helper {
	static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";
	static final String CANCIONES = "Canciones";
	private static final String MASCARA_INSTRUMENT = " I[%s] ";
	private static final String MASCARA_TEMPO = " T%s ";
	static final String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	private static final String ERROR_ARCHIVO_O_CARPETA_EN_USO = "Error, no se puede renombrar, el directorio esta en uso, cierre los archivos abiertos y vuelva a intentar";
	private static final String ERROR_NOTA_INVALIDA = "ERROR la nota debe ser C,D,E,F,G,A, o B";
	private static final String ERROR_YA_EXISTE_PISTA = "Error, ya existe la pista";
	private static final String ERROR_NO_EXISTE_PISTA = "Error, no existe la pista";
	private static final String ERROR_NO_HAY_P = null;
	private static final String ERROR_NO_HAY_PISTAS = "Error, no hay pistas";

	public static final void validarString(String str) {
		if (str.isEmpty() || str == null) {
			throw new IllegalArgumentException(ERROR_STRING_VACIO);
		}

	}

	public static final boolean validarRango(int min, int max, int numero) {
		boolean ok = false;
		if (numero >= min && numero <= max) {
			ok = true;
		}
		return ok;
	}

	public static void updateFolder(String nombreaAnteriorMelodia, String nuevoNombre) {
		String pathAnterior = ROOT + File.separator + nombreaAnteriorMelodia;
		String nuevoPath = ROOT + File.separator + nuevoNombre;
		Path path = Paths.get(pathAnterior);
		// Path pathNuevo = Paths.get(nuevoPathCSV);
		if (Files.exists(path)) {
			File dir = new File(pathAnterior);
			File newDir = new File(nuevoPath);
			dir = new File(dir.getAbsolutePath());
			newDir = new File(newDir.getAbsolutePath());
			if (dir.renameTo(newDir)) {
				dir.renameTo(newDir);
			} else {
				throw new RuntimeException(ERROR_ARCHIVO_O_CARPETA_EN_USO);
			}

		}
	}

	public static void tryOpen() {
		// the file we want to check
		String fileName = ROOT;
		File file = new File(fileName);
		// try to rename the file with the same name
		File sameFileName = new File(fileName);
		if (!file.renameTo(sameFileName)) {
			throw new RuntimeException(ERROR_ARCHIVO_O_CARPETA_EN_USO);
		}
	}

	public static String pedirString(String mensaje, Scanner input) {
		String respuesta;
		do {
			System.out.println(mensaje);
			respuesta = input.nextLine();
		} while (respuesta.isEmpty() || respuesta == null);

		return respuesta;
	}

	public static String pedirNombreCancion(String mensajeNombre, Scanner input, ArrayList<String> canciones2) {
		String respuesta;
		do {
			System.out.println(mensajeNombre);
			respuesta = input.nextLine();
			if (canciones2.contains(respuesta)) {
				System.out.println("Ya existe ese nombre, elija otro");
			}
		} while (respuesta.isEmpty() || respuesta == null || canciones2.contains(respuesta));

		return respuesta;
	}

	public static boolean existMelody(String nombreMelodia) {
		String fileName = ROOT + File.separator + nombreMelodia;
		File file = new File(fileName);
		return file.exists();
	}

	public static void validarNombreNota(String nombre) {
		if (Notas.valueOf(nombre.toUpperCase()) == null) {
			throw new IllegalArgumentException(ERROR_NOTA_INVALIDA);
		}
	}

	public static void listarInstrumentos() {
		Instrumentos[] lstInstrumentos = Instrumentos.values();
		for (int i = 0; i < lstInstrumentos.length; i++) {
			System.out.println(i + 1 + "." + lstInstrumentos[i]);
		}
	}

	public static String pedirInstrumento(String mensajeNombreInstrumento, Scanner input) {
		String respuesta;
		do {
			System.out.println(mensajeNombreInstrumento);
			respuesta = input.nextLine();
		} while (respuesta.isEmpty() || respuesta == null || !validarInstrumento(respuesta.toUpperCase()));

		return respuesta;
	}

	public static boolean validarInstrumento(String respuesta) {
		List<Instrumentos> choices = Arrays.asList(Instrumentos.values());
		boolean existe = choices.contains(Instrumentos.valueOf(respuesta.toUpperCase()));
		return existe;
	}

	public static String pedirTempo(String mensajeTempoMelodia, Scanner input) {
		String respuesta;
		int valor;
		do {
			System.out.println(mensajeTempoMelodia);
			respuesta = input.nextLine();
		} while (respuesta.isEmpty() || respuesta == null || !validarNumero(respuesta));
		return respuesta;
	}

	public static boolean validarNumero(String respuesta) {
		int numero = -1;
		try {
			numero = Integer.parseInt(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return numero != -1;
	}

	public static String pedirNombreNota(String mensajeNombreNota, Scanner input) {
		String respuesta;
		do {
			System.out.println(mensajeNombreNota);
			respuesta = input.nextLine();
		} while (respuesta.isEmpty() || respuesta == null || !validarNota(respuesta));

		return respuesta;
	}

	public static boolean validarNota(String respuesta) {
		List<Notas> choices = Arrays.asList(Notas.values());
		boolean existe = choices.contains(Notas.valueOf(respuesta.toUpperCase()));
		return existe;
	}

	public static String pedirOctava(String mensajeNumeroOctava, Scanner input) {
		String respuesta;
		do {
			System.out.println(mensajeNumeroOctava);
			respuesta = input.nextLine();
		} while (respuesta.isEmpty() || respuesta == null || !validarNumero(respuesta)
				|| !validarRango(0, 9, Integer.parseInt(respuesta)));

		return respuesta;
	}

	public static String pedirFigura(String mensajeNombreFigura, Scanner input) {
		String respuesta;
		do {
			System.out.println(mensajeNombreFigura);
			respuesta = input.nextLine();
		} while (respuesta.isEmpty() || respuesta == null || !validarFigura(respuesta));

		return Figuras_Ritmicas.valueOf(respuesta.toUpperCase()).getNombreJFuge();
	}

	public static boolean validarFigura(String respuesta) {
		/*
		 * boolean valido = false; int indice = 0; Figuras_Ritmicas aux;
		 * Figuras_Ritmicas[] figuras = Figuras_Ritmicas.values(); while (indice <
		 * figuras.length && valido == false) { aux = figuras[indice]; if
		 * (aux.getNombreJFuge().equalsIgnoreCase(respuesta)) { valido = true; } else {
		 * indice++; } }
		 */
		List<Figuras_Ritmicas> choices = Arrays.asList(Figuras_Ritmicas.values());
		boolean existe = choices.contains(Figuras_Ritmicas.valueOf(respuesta.toUpperCase()));
		return existe;
	}

	public static String pedirAlteracion(String mensajeTipoAlteracion, Scanner input) {
		String respuesta;
		do {
			System.out.println(mensajeTipoAlteracion);
			respuesta = input.nextLine();
		} while (respuesta.isEmpty() || respuesta == null || !validarAlteracion(respuesta));

		return respuesta;
	}

	private static boolean validarAlteracion(String respuesta) {
		return respuesta.equalsIgnoreCase("#") || respuesta.equalsIgnoreCase("b") || respuesta.equalsIgnoreCase("n");
	}

	public static int pedirIdNota(String mensajePedirId, Scanner input, ViewModelPista pistaVM) {
		String respuesta;
		int valor = 0;
		do {
			System.out.println(mensajePedirId);
			respuesta = input.nextLine();
		} while (respuesta.isEmpty() || respuesta == null || !validarNumero(respuesta)
				|| !validarRango(1, pistaVM.getSize(), Integer.parseInt(respuesta) - 1));
		valor = Integer.parseInt(respuesta);
		return valor;
	}

	public static String pedirNombreCancionEdicion(String mensajeNombre, Scanner input, ArrayList<String> canciones2) {
		String respuesta;
		do {
			System.out.println(mensajeNombre);
			respuesta = input.nextLine();
			if (!canciones2.contains(respuesta)) {
				System.out.println("Seleccione una canción existente");
			}
		} while (respuesta.isEmpty() || respuesta == null || !canciones2.contains(respuesta));

		return respuesta;
	}

	public static String toJFugueInstrument(String instrument) {
		return String.format(MASCARA_INSTRUMENT, instrument);
	}

	public static String toJfugueTempo(String tempo) {
		return String.format(MASCARA_TEMPO, tempo);
	}

	public static String pedirNuevaPista(Scanner input, String mensajePedirTipoPista,
			ArrayList<ViewModelPista> pistasVM) {
		String respuesta;
		do {
			System.out.println(mensajePedirTipoPista);
			respuesta = input.nextLine();
			if (valueOfPista(respuesta, pistasVM) != null) {
				System.out.println(ERROR_YA_EXISTE_PISTA);
			}
		} while (respuesta.isEmpty() || respuesta == null || valueOfPista(respuesta, pistasVM) != null);
		return respuesta;
	}

	public static ViewModelPista valueOfPista(String respuesta, ArrayList<ViewModelPista> pistasVM) {
		ViewModelPista aux;
		ViewModelPista pista = null;
		int indice = 0;
		while (indice < pistasVM.size() && pista == null) {
			aux = pistasVM.get(indice);
			if (aux.getNombre().equalsIgnoreCase(respuesta)) {
				pista = aux;
			} else {
				indice++;
			}
		}
		return pista;
	}

	public static String pedirPista(Scanner input, String mensajePedirPista, ArrayList<ViewModelPista> pistasVM) {
		String respuesta;
		if (pistasVM.isEmpty()) {
			throw new IllegalArgumentException(ERROR_NO_HAY_PISTAS);
		}
		do {
			System.out.println(mensajePedirPista);
			respuesta = input.nextLine();
			if (valueOfPista(respuesta, pistasVM) == null) {
				System.out.println(ERROR_NO_EXISTE_PISTA);
			}
		} while (respuesta.isEmpty() || respuesta == null || valueOfPista(respuesta, pistasVM) == null);
		return respuesta;
	}

	public static void listarCanciones(ArrayList<String> canciones2) {
		for (String cancion : canciones2) {
			System.out.println(cancion);
		}
	}

	public static String pedirCancion(String mensajeNombreCancion, Scanner input, ArrayList<String> canciones2) {
		String respuesta;
		do {
			System.out.println(mensajeNombreCancion);
			respuesta = input.nextLine();
			if (!canciones2.contains(respuesta)) {
				System.out.println("Seleccione una canción existente");
			}
		} while (respuesta.isEmpty() || respuesta == null || !canciones2.contains(respuesta));

		return respuesta;
	}

}
