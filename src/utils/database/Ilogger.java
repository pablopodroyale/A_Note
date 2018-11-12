package utils.database;

public interface Ilogger {
	
	public enum LogLevel {
		INFO, WARNING, SEVERE, OFF
	}

	/***
	 * Loguear� el mensaje recibido en modo INFO. Recibe el mensaje a loguear.
	 * 
	 * @param msg
	 */
	void logInfo(String msg);

	/***
	 * Loguear� el mensaje recibido en modo WARNING. Recibe el mensaje a loguear.
	 * 
	 * @param msg
	 */
	void logWarning(String msg);

	/***
	 * Loguear� el mensaje recibido en modo SEVERE. Recibe el mensaje a loguear.
	 * 
	 * @param msg
	 */
	void logSevere(String msg);
}
