package utils_textfiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BufferedTextReader implements TextReader {
	private static final String ERROR_BUFFER = "Error, el buffer tiene un error";
	private BufferedReader buffer;
	private ArrayList<String> lista;
	private FileReader fileReader;

	public BufferedTextReader(String path) {
		this.lista = new ArrayList<>();
		try {
			this.buffer = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public boolean isReady() {
		boolean ready = false;
		try {
			ready = buffer.ready();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return ready;
	}

	@Override
	public String readLine() {
		String line = null;
		try {
			if (isReady()) {
				line = buffer.readLine();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return line;
	}

	@Override
	public void close() {
		try {
			buffer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
