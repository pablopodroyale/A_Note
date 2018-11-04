package funciones_helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Funcion_Helper {
	static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";
	static final String CANCIONES = "Canciones";
	static final String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	private static final String ERROR_ARCHIVO_O_CARPETA_EN_USO = "Error, no se puede renombrar, el directorio esta en uso, cierre los archivos abiertos y vuelva a intentar";

	public static final void validarString(String str) {
		if (str.isEmpty() || str == null) {
			throw new IllegalArgumentException(ERROR_STRING_VACIO);
		}

	}

	public static final boolean validarRango(int min, int max, int numero) {
		boolean ok = false;
		if (numero > min && numero < max) {
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
}
