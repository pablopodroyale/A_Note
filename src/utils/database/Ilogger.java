package utils.database;

public interface Ilogger {
	
	public enum LogLevel {
		INFO, WARNING, SEVERE, OFF
	}

	/***
	 * Logueará el mensaje recibido en modo INFO. Recibe el mensaje a loguear.
	 * 
	 * @param msg
	 */
	void logInfo(String msg);

	/***
	 * Logueará el mensaje recibido en modo WARNING. Recibe el mensaje a loguear.
	 * 
	 * @param msg
	 */
	void logWarning(String msg);

	/***
	 * Logueará el mensaje recibido en modo SEVERE. Recibe el mensaje a loguear.
	 * 
	 * @param msg
	 */
	void logSevere(String msg);
}
