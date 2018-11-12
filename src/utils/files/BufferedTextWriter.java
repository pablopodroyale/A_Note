package utils.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedTextWriter implements TextWriter {
	private BufferedWriter bufferedWriter;

	public BufferedTextWriter(String path, boolean append) throws IOException {
		File file = new File(path);
		this.bufferedWriter = null;
		if (!file.exists() || file.isFile()) {
			bufferedWriter = new BufferedWriter(new FileWriter(file,append));
		} else {
			throw new FileNotFoundException();
		}
	}

	@Override
	public void writeLine(String line) {
		try {
			bufferedWriter.write(line);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void close() {
		if (bufferedWriter != null) {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void newLine() {
		try {
			bufferedWriter.newLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
