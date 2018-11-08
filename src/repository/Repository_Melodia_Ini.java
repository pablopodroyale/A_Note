package repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import entidades.Melodia;
import funciones_helper.Mapper_Ini;
import utils_textfiles.IniManager;

public class Repository_Melodia_Ini implements IMelodia_Repository {
	private IniManager iniManager;
	private static final String CANCIONES = "Canciones";
	private String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	private static final String EXTENSION_INI = ".ini";
	// private Path rootPath;
	private static final String ERROR_NO_EXISTE_CANCION = "Error, la cancion seleccionada no existe";

	public Repository_Melodia_Ini() {
		this.iniManager = new IniManager();
		// this.rootPath = Paths.get(ROOT);
	}

	@Override
	public Melodia load(String nombreMelodia) {
		Melodia melodia = null;
		String pathIni = ROOT + File.separator + nombreMelodia + File.separator + nombreMelodia + EXTENSION_INI;
		Path path = Paths.get(pathIni);
		if (Files.notExists(path)) {
			throw new IllegalArgumentException(ERROR_NO_EXISTE_CANCION);
		}
		//
		iniManager.setPathSource(path.toAbsolutePath().toString());
		iniManager.load();
		// iniManager.list();
		melodia = new Melodia(iniManager.getValueof(nombreMelodia));
		// setear el tiempo a la melodiacon item tiempo dentro de la seccion nombre
		// melodia
		melodia.setTempo(iniManager.getValueOf(nombreMelodia, "Tiempo"));
		melodia.setInstrument(iniManager.getValueOf(nombreMelodia, "Instrumento"));
		return melodia;
	}

	@Override
	public void saveIni(Melodia melodia, boolean append) {
		String pathIni = ROOT + File.separator + melodia.getNombre() + File.separator + melodia.getNombre()
				+ EXTENSION_INI;
		iniManager.setPathDestiny(pathIni);
		String instrumento = removeInstrumentMask(melodia.getInstrument());
		String tempo = removeTempoMak(melodia.getTempo());
		Path rootPath = Paths.get(pathIni);
		try {
			if (Files.notExists(rootPath.getParent())) {
				Files.createDirectories(rootPath.getParent());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		this.iniManager.addSection(melodia.getNombre());
		iniManager.setItem(melodia.getNombre(), melodia.getTempoConstString(), tempo);
		iniManager.setItem(melodia.getNombre(), melodia.getInstrumentConst(), instrumento);
		iniManager.save(append);
	}

	private String removeTempoMak(String tempo) {
		return tempo.substring(2, tempo.length());
	}

	private String removeInstrumentMask(String instrument) {
		return instrument.substring(3, instrument.length() - 2);
	}

	/***
	 * Verificar que el nombre anterior sea igual al que tenga la melodia Si es
	 * igual actualizo con el nombre que tenia, si no cambio el nombre del archivo y
	 * despues guardo con ese nuevo path
	 */
	@Override
	public void updateIni(Melodia melodia, String nombreAnterior, boolean append) {
		String pathAnteriorIni = ROOT + File.separator + nombreAnterior + File.separator + nombreAnterior
				+ EXTENSION_INI;
		// String instrumento = removeInstrumentMask(melodia.getInstrument());
		// String tempo = removeTempoMak(melodia.getTempo());
		// this.iniManager.addSection(melodia.getNombre());
		iniManager.updateSection(nombreAnterior, melodia.getNombre());
		iniManager.setPathDestiny(pathAnteriorIni);

		// melodia.setNombre(nombreMelodia);
		// iniManager.setItem(melodia.getNombre(), melodia.getTempoConstString(),
		// tempo);
		// iniManager.setItem(melodia.getNombre(), melodia.getInstrumentConst(),
		// instrumento);

		iniManager.save(append);

	}

	@Override
	public void updateNombreIni(String nombreaAnteriorMelodia, String nuevoNombre) {
		String pathAnteriorIni = ROOT + File.separator + nombreaAnteriorMelodia + File.separator
				+ nombreaAnteriorMelodia + EXTENSION_INI;
		String nuevoPath = ROOT + File.separator + nombreaAnteriorMelodia + File.separator + nuevoNombre
				+ EXTENSION_INI;
		File dir = new File(pathAnteriorIni);
		File newDir = new File(nuevoPath);
		dir.renameTo(newDir);
	}

	@Override
	public void updateSection(String nombreAnterior, String nuevoNombre) {
		this.iniManager.updateSection(nombreAnterior, nuevoNombre);

	}

	@Override
	public void update(Melodia melodia) {
		Mapper_Ini.Map(melodia, iniManager);
		iniManager.save(false);
	}

}
