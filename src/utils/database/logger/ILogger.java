package utils.database.logger;

public interface ILogger {

	enum LogLevel {
		INFO, WARNING, SEVERE, OFF
	};

	/**
	 * Logueara el mensaje en modo INFO.
	 * 
	 * @param msg
	 *            El mensaje a loguear.
	 */
	void logInfo(String msg);

	/**
	 * Logueara el mensaje en modo WARNING.
	 * 
	 * @param msg
	 *            El mensaje a loguear.
	 */
	void logWarning(String msg);

	/**
	 * Logueara el mensaje en modo SEVERE.
	 * 
	 * @param msg
	 *            El mensaje a loguear.
	 */
	void logSevere(String msg);

	void release();

}