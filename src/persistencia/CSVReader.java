package persistencia;

import java.util.ArrayList;
import java.util.List;

import tp1.utils.textfiles.BufferedTextReader;
import tp1.utils.textfiles.TextReader;

public abstract class CSVReader<ClassType> {
	// private static final String CSV_EXTENSION = ".csv";
	private static final String INVALID_CSV_ERROR_MSG = "Error, datos invalidos";
	private String header;

	public CSVReader(String header) {
		this.header = header;
		// fromCsv(header);

	}

	// abstract String setHeader(String header);

	public void readAll(String path, List<ClassType> lista, ControlLevelError cle) {
		TextReader textReader = new BufferedTextReader(path);
		readHeader(textReader);
		readItems(textReader, lista, cle);
		textReader.close();
	}

	abstract ClassType fromCsv(String lines);

	private void readHeader(TextReader textReader) {
		String header = textReader.readLine();
		if (!this.header.equals(header)) {
			throw new IllegalArgumentException(INVALID_CSV_ERROR_MSG);
		}
	}

	// verificar el level error
	private void readItems(TextReader textReader, List<ClassType> listaItems, ControlLevelError cle) {
		List<ClassType> listaAux = new ArrayList<>();
		// Articulo articulo = null;
		boolean seguir = true;
		while (textReader.isReady() && seguir) {
			String lineClassType = textReader.readLine();
			try {
				listaAux.add((fromCsv(lineClassType)));
			} catch (Exception e) {
				if (cle == ControlLevelError.LOAD_UNTIL_ERROR || cle == ControlLevelError.NO_LOAD_WITH_ERRORS) {
					seguir = false;
				}
				System.out.println(e.getMessage());
			}

		}
		if (cle == ControlLevelError.LOAD_UNTIL_ERROR || cle == ControlLevelError.IGNORE_ERRORS) {
			listaItems.addAll(listaAux);
		} else if (cle == ControlLevelError.NO_LOAD_WITH_ERRORS) {
			listaItems.clear();
		}
	}
}
