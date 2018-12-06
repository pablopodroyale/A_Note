package entidades;

import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import interfaces.Exportador;

public class ExportadorMusicXml implements Exportador {
	private static final String CANCIONES = "Canciones";
	private static final String EXTENSION = ".xml";
	String ROOT = new File("").getAbsolutePath() + File.separator + CANCIONES + File.separator;

	@Override
	public <Cancion> void exportar(Cancion cancion) {
		String path = ROOT + ((entidades.Cancion) cancion).getNombreCancion() + EXTENSION;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(ROOT);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		XMLEncoder encoder = new XMLEncoder(fos);
		encoder.setExceptionListener(new ExceptionListener() {
			public void exceptionThrown(Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		});
		encoder.writeObject(cancion);
		encoder.close();
		try {
			fos.close();
		} catch (IOException e1) {
			throw new IllegalArgumentException(e1.getMessage());
		}
	}
}
