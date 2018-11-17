package utils.database.logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class FileLogger implements ILogger {

	private static final String LOG_EXTENSION = ".log";
	private static final String ERR_WRONG_PATHNAME = "Nombre de ruta erroneo";
	private static final String ERR_WRONG_FILENAME = "Nombre de archivo erroneo";

	private LogLevel logLevel;
	private Logger logger;
	private FileHandler handler;

	public FileLogger(String path, String filename) throws SecurityException, IOException {
		this(path, filename, LogLevel.WARNING);
	}

	public FileLogger(String path, String filename, LogLevel logLevel) throws SecurityException, IOException {
		if (filename == null || filename.trim().equals("")) {
			throw new IllegalArgumentException(ERR_WRONG_FILENAME);
		} else if (!filename.toLowerCase().endsWith(LOG_EXTENSION)) {
			filename += LOG_EXTENSION;
		}
		if (path != null && !path.trim().equals("")) {
			File f = new File(path);
			if (!f.isDirectory()) {
				if (f.exists()) {
					throw new IllegalArgumentException(ERR_WRONG_PATHNAME);
				} else {
					f.mkdir();
				}
			}
		}
		filename = Paths.get(path, filename).toString();
		setLogLevel(logLevel);
		this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		this.handler = new FileHandler(filename, true);
		this.logger.addHandler(handler);
	}

	/**
	 * @return the logLevel
	 */
	public LogLevel getLogLevel() {
		return logLevel;
	}

	/**
	 * @param logLevel
	 *            the logLevel to set
	 */
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * Guarda en el log el mensaje en modo INFO.
	 * 
	 * @param msg
	 *            El mensaje a guardar.
	 */
	@Override
	public void logInfo(String msg) {
		if (logLevel.compareTo(LogLevel.INFO) <= 0) {
			logger.info(msg);
		}
	}

	/**
	 * Guarda en el log el mensaje en modo WARNING.
	 * 
	 * @param msg
	 *            El mensaje a guardar.
	 */
	@Override
	public void logWarning(String msg) {
		if (logLevel.compareTo(LogLevel.WARNING) <= 0) {
			logger.warning(msg);
		}
	}

	/**
	 * Guarda en el log el mensaje en modo SEVERE
	 * 
	 * @param msg
	 *            El mensaje a guardar.
	 */
	@Override
	public void logSevere(String msg) {
		if (logLevel.compareTo(LogLevel.SEVERE) <= 0) {
			logger.severe(msg);
		}
	}

	/**
	 * Remueve el handler y lo cierra.
	 */
	public void release() {
		logger.removeHandler(handler);
		handler.close();
	}

}
