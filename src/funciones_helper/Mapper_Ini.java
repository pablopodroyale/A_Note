package funciones_helper;
import java.io.File;

import entidades.Melodia;
import utils_textfiles.IniManager;

public final class Mapper_Ini {

	private static final String TEMPO = "Tiempo";
	private static final String INSTRUMENTO = "Instrumento";
	private static final String CANCIONES = "Canciones";
	private static final String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	private static final String EXTENSION_INI = ".ini";

	public static void Map(Melodia melodia, IniManager iniManager) {
		String pathIni = ROOT + File.separator + melodia.getNombre() + File.separator + melodia.getNombre() + EXTENSION_INI;
		String nombre = melodia.getNombre();
		String instrumento = removeInstrumentMask(melodia.getInstrument());
		String tempo = removeTempoMak(melodia.getTempo());
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
