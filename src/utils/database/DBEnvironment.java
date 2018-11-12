package utils.database;

import java.lang.reflect.Field;

public class DBEnvironment {
	public static final String K_PASSWORD = "password";
	public static final String K_USERNAME = "username";
	public static final String K_DATABASE = "database";
	public static final String K_PORT = "port";
	public static final String K_SERVER = "server";
	public static final String K_DRIVER = "driver";
	private static final String K_ENVIRONMENTNAME = "environmentName";
	private static final String CONN_STRING_TEMPLATE = "jdbc:%s://%s:%s;database=%s;user=%s;password=%s";
	private static final String ERR_INVALID_ATRIBUTE = "Nombre de atributo invalido";
	private static final String ERR_INVALID_ATTR_VALUE = "Valor invalido para el atributo %s (%s)";
	private static final String UNDEFINED = "[UNDEFINED]";
	private static final String ERROR_ARGUMENTO_VACIO = "Error, datos invalidos";
	private String environmentName;
	private String driver;
	private String server;
	private String port;
	private String database;
	private String username;
	private String password;

	public DBEnvironment(String environmentName) {
		setEnvironmentName(environmentName);
		this.driver = UNDEFINED;
		this.server = UNDEFINED;
		this.port = UNDEFINED;
		this.database = UNDEFINED;
		this.username = UNDEFINED;
		this.password = UNDEFINED;
	}

	public DBEnvironment(String environmentName, String driver, String server, String port, String database,
			String username, String password) {
		setEnvironmentName(environmentName);
		setDriver(driver);
		setServer(server);
		setPort(port);
		setDatabase(database);
		setUsername(username);
		setPassword(password);
	}

	private void setPassword(String password) {
		if (password.isEmpty() || password == null) {
			this.password = UNDEFINED;
		} else {
			this.password = password;
		}

	}

	public static String getConnStringTemplate() {
		return CONN_STRING_TEMPLATE;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public String getDriver() {
		return driver;
	}

	public String getServer() {
		return server;
	}

	public String getPort() {
		return port;
	}

	public String getDatabase() {
		return database;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	private void setUsername(String username) {
		if (username.isEmpty() || username == null) {
			this.username = UNDEFINED;
		} else {
			this.username = username;
		}

	}

	private void setDatabase(String database) {
		if (database.isEmpty() || database == null) {
			throw new IllegalArgumentException(ERR_INVALID_ATRIBUTE);
		} else {
			this.database = database;
		}

	}

	private void setPort(String port) {
		int portToInt = -1;
		if (port.isEmpty() || port == null) {
			throw new IllegalArgumentException(ERR_INVALID_ATRIBUTE);
		} else {
			try {
				portToInt = Integer.parseInt(port);
			} catch (IllegalArgumentException e) {
				System.out.println(ERR_INVALID_ATRIBUTE);
			} finally {
				if (portToInt != -1) {
					this.port = port;
				}
			}

		}

	}

	private void setServer(String server) {
		if (server.isEmpty() || server == null) {
			throw new IllegalArgumentException(ERR_INVALID_ATRIBUTE);
		} else {
			this.server = server;
		}

	}

	private void setDriver(String driver) {
		if (driver.isEmpty() || driver == null) {
			throw new IllegalArgumentException(ERR_INVALID_ATRIBUTE);
		} else {
			this.driver = driver;
		}

	}

	private void setEnvironmentName(String environmentName) {
		if (environmentName.isEmpty() || environmentName == null) {
			throw new IllegalArgumentException(ERR_INVALID_ATRIBUTE);
		} else {
			this.environmentName = environmentName;
		}
	}

	public String getURL() {
		return String.format(CONN_STRING_TEMPLATE, driver, server, port, database, username, password);
	}

	/**
	 * Setea el atributo cuyo nombre se recibió en el primer parámetro con el valor
	 * dado en el segundo
	 * 
	 * @param attribute
	 * @param value
	 */
	public void setValueOf(String attribute, String value) {
		Field field = null;
		checkValue(attribute, value);
		try {
			field = DBEnvironment.class.getDeclaredField(attribute);
		} catch (NoSuchFieldException e) {
			System.out.println(e.getMessage());
		} catch (SecurityException e) {
			System.out.println(e.getMessage());
		}
		field.setAccessible(true);
		try {
			field.set(this, value);
			// System.out.println(field.get(this));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println(e.getMessage());
		}
	}

	protected void setValues(String driver, String server, String port, String database, String username,
			String password) {
		setDriver(driver);
		setServer(server);
		setPort(port);
		setDatabase(database);
		setUsername(username);
		setPassword(password);
	}

	private void checkValue(String fieldValue, String fieldName) {
		if (fieldValue.isEmpty() || fieldValue == null || fieldName.isEmpty() || fieldName == null) {
			throw new IllegalArgumentException(ERROR_ARGUMENTO_VACIO);
		}
	}

	protected Field[] getFields() {
		Field[] fields = DBEnvironment.class.getDeclaredFields();
		return fields;

	}
}
