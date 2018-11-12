package utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
	private static final String ERROR_CONFIG_CANNOT_BE_NULL = "Error, config no puede ser null";
	private DbConfig dbConfig;

	public DbManager(DbConfig dbConfig) {
		setDbConfig(dbConfig);
	}

	public void setDbConfig(DbConfig dbConfig) {
		if (dbConfig == null) {
			throw new IllegalArgumentException(ERROR_CONFIG_CANNOT_BE_NULL);
		}
		this.dbConfig = dbConfig;
	}

	/***
	 * Devuelve la conexión contra la base de datos usando la url del ambiente cuyo
	 * nombre coincida con el valor del parámetro. Si la conexión no se produce se
	 * disparará una SQLException .
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getNewConnection() throws SQLException {
		return DriverManager.getConnection(dbConfig.getURL());
	}

	/***
	 * Crea y devuelve la conexión con la url recibida por parámetro. Si la conexión
	 * no se produce se disparará una SQLException . Para crear la conexión se usa:
	 * DriverManager.getConnection(url)
	 * 
	 * @param url
	 * @return
	 * @throws SQLException
	 */
	public Connection getNewConnectionFromURL(String url) throws SQLException {
		return DriverManager.getConnection(url);

	}

}
