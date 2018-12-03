package funciones_helper;
import java.io.File;

import entidades.Pista;
import utils.files.IniManager;

public final class Mapper_Ini {

	private static final String TEMPO = "Tiempo";
	private static final String INSTRUMENTO = "Instrumento";
	private static final String CANCIONES = "Canciones";
	private static final String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	private static final String EXTENSION_INI = ".ini";

	public static void Map(Pista pista, IniManager iniManager) {
		String pathIni = ROOT + File.separator + pista.getNombre() + File.separator + pista.getNombre() + EXTENSION_INI;
		String nombre = pista.getNombre();
		String instrumento = removeInstrumentMask(pista.getInstrumento());
		String tempo = removeTempoMak(pista.getTempo());
		iniManager.clear();
		iniManager.setPathDestiny(pathIni);
		iniManager.addSection(nombre);
		iniManager.setItem(nombre, TEMPO, tempo);
		iniManager.setItem(nombre, INSTRUMENTO, instrumento);

	}
	
	private static String removeTempoMak(String tempo) {
		return tempo.substring(2, tempo.length());
	}

	private static String removeInstrumentMask(String instrument) {
		return instrument.substring(3, instrument.length() - 2);
	}

}
