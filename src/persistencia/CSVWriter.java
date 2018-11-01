package persistencia;

import java.io.IOException;
import java.util.ArrayList;

import tp1.utils.textfiles.BufferedTextWriter;
import tp1.utils.textfiles.TextWriter;

public abstract class CSVWriter<ClassType> {
	private String header;
	//private static final String CSV_EXTENSION = ".csv";

	public CSVWriter(String header) {
		this.header = header;
	}

	public void writeAll(String path, ArrayList<ClassType> lista) {
		BufferedTextWriter bufferWriter = null;
		try {
			bufferWriter = new BufferedTextWriter(path);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		writeHeader(bufferWriter);
		writeitems(bufferWriter, lista);
		bufferWriter.close();
	}

	private void writeHeader(TextWriter tw) {
		BufferedTextWriter bufferWriter = (BufferedTextWriter) tw;
		bufferWriter.writeLine(header);
		bufferWriter.newLine();
	}

	private void writeitems(TextWriter tw, ArrayList<ClassType> lista) {
		BufferedTextWriter bufferWriter = (BufferedTextWriter) tw;
		CSVCompatible<ClassType> csv;
		String line = "";
		for (ClassType classType : lista) {
			line += classType.toString();
			bufferWriter.writeLine(line);
			bufferWriter.newLine();
			line = "";
		}
	}
	public void setHeader(String header) {
		this.header = header;
	}
}
