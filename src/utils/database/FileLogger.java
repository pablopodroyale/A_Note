package utils.database;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import funciones_helper.Funcion_Helper;

public class FileLogger implements Ilogger {
	private static final String EXTENSION_LOG = ".log";
	private Logger logger;
	private FileHandler handler;
	private LogLevel loglevel;

	/***
	 * Invoca al otro constructor con el nivel de logging por defecto (WARNING).
	 */
	public FileLogger(String path, String filename) throws SecurityException, IOException {
		this(path, filename, LogLevel.WARNING);

	}

	/***
	 * Crea el archivo de log en la ruta indicada por path y filename , asignando el
	 * nivel de logging indicado por el logLevel recibido. Debe chequear que el
	 * nombre de archivo sea válido (que no esté vacío ni sea null ) y si no tiene
	 * la extensión “.log” debe ponérsela. Si el path no existe y es válido como
	 * directorio, debe crearlo. Si algo le impide seguir deberá lanzar una
	 * IllegalArgumentException .
	 * 
	 * @param path
	 * @param filename
	 * @param logLevel
	 * @throws SecurityException
	 * @throws IOException
	 */
	public FileLogger(String path, String filename, LogLevel logLevel) throws SecurityException, IOException {
		String spath = path + filename;
		try {
			Funcion_Helper.validarString(path);
			Funcion_Helper.validarString(filename);
			validarFile(filename);
			validarPath(path);
			File fPath = new File(spath);
			this.loglevel = logLevel;
			this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
			setLevel();
			this.handler = new FileHandler(path + filename);
			logger.addHandler(handler);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void setLevel() {

		switch (this.loglevel) {
		case OFF:
			logger.setLevel(Level.OFF);
			break;
		case INFO:
			logger.setLevel(Level.INFO);
			break;
		case WARNING:
			logger.setLevel(Level.WARNING);
			break;
		case SEVERE:
			logger.setLevel(Level.SEVERE);
			break;
		default:
			break;
		}

	}

	private void validarPath(String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
	}

	private void validarFile(String filename) {
		if (filename.split(filename).length < 2) {
			filename = filename + EXTENSION_LOG;
		}
	}

	@Override
	public void logInfo(String msg) {
		this.logger.log(Level.INFO, msg);
	}

	@Override
	public void logWarning(String msg) {
		this.logger.log(Level.WARNING, msg);

	}

	@Override
	public void logSevere(String msg) {
		this.logger.log(Level.SEVERE, msg);

	}

	/***
	 * Devuelve el nivel de filtro de logging actual
	 * 
	 * @return
	 */
	public LogLevel getLogLevel() {
		return this.loglevel;
	}

	public void release() {
		this.handler.close();

	}

}
