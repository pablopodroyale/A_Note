package repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import entidades.Melodia;
import entidades.Nota;
import persistencia.ControlLevelError;
import persistencia.NoteReader;
import persistencia.NoteWriter;

public class CSV_Repository implements IRepository_CSV {
	private NoteWriter noteWriter;
	private NoteReader noteReader;
	private static final String CANCIONES = "Canciones";
	private String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES;
	private static final String EXTENSION_CSV = ".csv";
	private Path rootPath;

	public CSV_Repository() {
		this.noteWriter = new NoteWriter();
		this.noteReader = new NoteReader(Nota.getHeader());
		rootPath = Paths.get(ROOT);
	}

	@Override
	public void load(ArrayList<Nota> notas, String nombreMelodia) {
		String pathCsv = ROOT + File.separator + nombreMelodia + File.separator + nombreMelodia + EXTENSION_CSV;
		noteReader.readAll(pathCsv, notas, ControlLevelError.IGNORE_ERRORS);
	}

	/***
	 * notas, nombre melodia
	 */
	@Override
	public void saveCSV(ArrayList<Nota> notas, String nombreMelodia, boolean append) {
		String pathCSV = ROOT + File.separator + nombreMelodia + File.separator + nombreMelodia + EXTENSION_CSV;
		try {
			if (!Files.exists(rootPath)) {
				Files.createDirectories(Paths.get(ROOT));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		noteWriter.setHeader(Nota.getHeader());
		// NoteWriter noteWriter = new NoteWriter(Nota.getHeader());
		noteWriter.writeAll(pathCSV, notas, append);
	}

	@Override
	public void updateName(String nombreMelodia, String nuevoNombre) {
		String pathAnteriorCSV = ROOT + File.separator + nombreMelodia + File.separator + nombreMelodia + EXTENSION_CSV;
		String nuevoPathCSV = ROOT + File.separator + nombreMelodia + File.separator + nuevoNombre + EXTENSION_CSV;
		Path path = Paths.get(pathAnteriorCSV);
		// Path pathNuevo = Paths.get(nuevoPathCSV);
		if (Files.exists(path)) {
			File dir = new File(pathAnteriorCSV);
			File newDir = new File(nuevoPathCSV);
			dir.renameTo(newDir);
			// Cambiar el nombre de la carpeta padre
			System.out.println(dir.getAbsolutePath().toString());
			dir = new File(dir.getAbsolutePath());
			newDir = new File(newDir.getAbsolutePath());
			dir.renameTo(newDir);
		}

	}

	// noteWriter.writeAll(pathCsv, notas);
	// NoteWriter noteWriter = new NoteWriter(Nota.getHeader());

}
