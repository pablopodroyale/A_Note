package utils.database.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	private static final String ERR_CONFIG_CANNOT_BE_NULL = "Config no puede ser null.";
	private DBConfig config;

	/**
	 * @param config
	 *            Instancia de DBConfig con acceso a las definiciones de la o
	 *            las conexiones disponibles. No puede ser null, en ese caso
	 *            disparara una IllegalArgumentException.
	 */
	public DBManager(DBConfig config) {
		setConfig(config);
	}

	/**
	 * Crea la conexion con la base de datos usando la url declarada en el
	 * config como por defecto y la devuelve. Si la conexion no se produce se
	 * disparara una SQLException.
	 * 
	 * @throws SQLException
	 */
	public Connection getNewConnection() throws SQLException {
 		return getNewConnectionFromURL(config.getURL());
	}

	/**
	 * Crea la conexion con la base de datos usando la url del config cuyo
	 * nombre coincida con el valor recibido como parametro y la devuelve. Si la
	 * conexion no se produce se disparara una SQLException.
	 * 
	 * @param environment
	 *            nombre del ambiente con el que se quiere estbleer la conexion.
	 * @throws SQLException
	 */
	public Connection getNewConnection(String environment) throws SQLException {
		return getNewConnectionFromURL(config.getURL(environment));
	}

	/**
	 * Setea el atributo config.
	 * 
	 * @param config
	 *            Instancia de DBConfig con acceso a las definiciones de la o
	 *            las conexiones disponibles. No puede ser null, en ese caso
	 *            disparara una IllegalArgumentException.
	 */
	private void setConfig(DBConfig config) {
		if (config != null) {
			this.config = config;
		} else {
			throw new IllegalArgumentException(ERR_CONFIG_CANNOT_BE_NULL);
		}
	}

	/**
	 * Crea la conexion con la url recibida por parametro y la devuelve. Si la
	 * conexion no se produce se disparara una SQLException.
	 * 
	 * @param url
	 *            Connection string o URL con los datos necesarios para
	 *            establecer la conexion.
	 * @throws SQLException
	 */
	private Connection getNewConnectionFromURL(String url) throws SQLException {
		Connection conn = null;
		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		conn = DriverManager.getConnection(url);
		return conn;
	}

	public String getEnvironment() {
		return this.config.getDefaultEnvironment();
	}

}
