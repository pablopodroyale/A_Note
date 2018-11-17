package utils.database.db;

import java.util.Map;

public class DBEnvironment {

	public static final String K_PASSWORD = "password";
	public static final String K_USERNAME = "username";
	public static final String K_DATABASE = "database";
	public static final String K_PORT = "port";
	public static final String K_SERVER = "server";
	public static final String K_DRIVER = "driver";

	private static final String K_ENVIRONMENTNAME = "environmentName";

	private static final String CONN_STRING_TEMPLATE = "jdbc:%s://%s:%s;database=%s;user=%s;password=%s";
	private static final String ERR_INVALID_ATTRIBUTE = "Nombre de atributo invalido";
	private static final String ERR_INVALID_ATTR_VALUE = "Valor invalido para el atributo %s ('%s')";

	private static final String UNDEFINED = "[UNDEFINED]";

	private String environmentName;
	private String driver;
	private String server;
	private String port;
	private String database;
	private String username;
	private String password;

	/**
	 * Constructor "casi" por defecto. Solo necesita setear el nombre del
	 * ambiente (será la clave cuando haya que identificarlo). Todos sus otros
	 * campos serán seteados como "[UNDEFINED]".
	 * 
	 * @param environmentName
	 *            el nombre que tendrá el ambiente.
	 */
	public DBEnvironment(String environmentName) {
		this(environmentName, UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED);
	}

	/**
	 * Constructor completo, con todos los valores.
	 * 
	 * @param environmentName
	 *            Nombre del ambiente. No puede estar vacio, si lo está lanzará
	 *            una excepción.
	 * @param driver
	 *            Driver que se usara en la conexion. No puede estar vacio, si
	 *            lo está lanzará una excepción.
	 * @param server
	 *            Nombre o direccion del servidor de base de datos. No puede
	 *            estar vacio, si lo está lanzará una excepción.
	 * @param port
	 *            Puerto por el que el servidor de base de datos atiende
	 *            requerimientos. Debe ser numerico. No puede estar vacio, si lo
	 *            está lanzará una excepción.
	 * @param database
	 *            Nombre de la base de datos. No puede estar vacio, si lo está
	 *            lanzará una excepción.
	 * @param username
	 *            Nombre del usuario que se usara para conectarse a la base de
	 *            datos. Si no tiene valor se guardara como [UNDEFINED].
	 * @param password
	 *            Password del usuario que se usara para conectarse a la base de
	 *            datos. Si no tiene valor se guardara como [UNDEFINED].
	 */
	public DBEnvironment(String environmentName, String driver, String server, String port, String database,
			String username, String password) {
		setEnvironmentName(environmentName);
		setValues(driver, server, port, database, username, password);
	}

	public DBEnvironment(Map<String, String> params) {
		setEnvironmentName(params.get("name"));
		setValues(params);
	}

	protected void setValues(Map<String, String> params) {
		setDriver(params.get("driver"));
		setServer(params.get("server"));
		setPort(params.get("port"));
		setDatabase(params.get("database"));
		setUsername(params.get("username"));
		setPassword(params.get("password"));
	}

	/**
	 * @return El nombre del ambiente.
	 */
	public String getEnvironmentName() {
		return environmentName;
	}

	/**
	 * @return El nombre del manejador de base de datos.
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * Setea el nombre del manejador de base de datos.
	 * 
	 * @param driver
	 *            el nombre del manejador de base de datos. No puede estar vacío
	 *            ni ser null. En ese caso disparara una
	 *            InvalidArgumentException.
	 */
	public void setDriver(String driver) {
		checkValue(driver, K_DRIVER);
		this.driver = driver;
	}

	/**
	 * @return El nombre o la direccion del servidor de base de datos.
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server
	 *            El nombre o la direccion del servidor de base de datos. No
	 *            puede estar vacío ni ser null. En ese caso disparara una
	 *            InvalidArgumentException.
	 */
	public void setServer(String server) {
		checkValue(server, K_SERVER);
		this.server = server;
	}

	/**
	 * @return El puerto por el que el servidor atiende los requerimientos de
	 *         base de datos.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Setea el puerto por el que el servidor atiende los requerimientos de base
	 * de datos.
	 * 
	 * @param port
	 *            Setea el puerto por el que el servidor atiende los
	 *            requerimientos de base de datos. Recibe un string con el
	 *            numero del puerto por el que el servidor atiende los
	 *            requerimientos de base de datos. Si es un string vacio, null o
	 *            no es un numero valido (mayor que cero) lanzara una
	 *            InvalidArgumentException.
	 */
	public void setPort(String port) {
		if (port.equals(UNDEFINED)) {
			this.port = port;
		} else {
			checkValue(port, K_PORT);
			int aux;
			try {
				aux = Integer.parseInt(port, 10);
			} catch (RuntimeException re) {
				throw new IllegalArgumentException(String.format(ERR_INVALID_ATTR_VALUE, K_PORT, port));
			}
			if (aux < 1) {
				throw new IllegalArgumentException(String.format(ERR_INVALID_ATTR_VALUE, K_PORT, port));
			}
			this.port = Integer.toString(aux, 10);
		}
	}

	/**
	 * @return El nombre de la base de datos.
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Setea el nombre de la base de datos.
	 * 
	 * @param database
	 *            El nombre de la base de datos. No puede estar vacío ni ser
	 *            null. En ese caso disparara una InvalidArgumentException.
	 */
	public void setDatabase(String database) {
		checkValue(database, K_DATABASE);
		this.database = database;
	}

	/**
	 * @return El usuario de la base de datos.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setea el usuario de la base de datos
	 * 
	 * @param username
	 *            El nombre del usuario. Si esta vacío o es null se setea como
	 *            [UNDEFINED].
	 */
	public void setUsername(String username) {
		this.username = (username == null || username.isEmpty()) ? UNDEFINED : username;
	}

	/**
	 * @return La password del usuario de la base de datos.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setea el usuario de la base de datos.
	 * 
	 * @param password
	 *            La password del usuario de la base de datos.
	 */
	public void setPassword(String password) {
		this.password = (password == null || password.isEmpty()) ? UNDEFINED : password;
	}

	/**
	 * @return Devuelve el string de conexion o URL para un ambiente
	 *         determinado.
	 */
	public String getURL() {
		return String.format(CONN_STRING_TEMPLATE, getDriver(), getServer(), getPort(), getDatabase(), getUsername(),
				getPassword());
	}

	/**
	 * Actualiza el valor de un atributo.
	 * 
	 * @param attribute
	 *            nombre del atributo
	 * @param value
	 *            valor para el atributo
	 */
	public void setValueOf(String attribute, String value) {
		switch (attribute) {
		case K_DATABASE:
			setDatabase(value);
			break;
		case K_DRIVER:
			setDriver(value);
			break;
		case K_PASSWORD:
			setPassword(value);
			break;
		case K_PORT:
			setPort(value);
			break;
		case K_SERVER:
			setServer(value);
			break;
		case K_USERNAME:
			setUsername(value);
			break;
		default:
			throw new IllegalArgumentException(String.format(ERR_INVALID_ATTRIBUTE, attribute, value));
		}

	}

	/**
	 * Setea todos los valores del ambiente exceptuando el nombre que debe
	 * cargarse aparte.
	 * 
	 * @param driver
	 * @param server
	 * @param port
	 * @param database
	 * @param username
	 * @param password
	 */
	protected void setValues(String driver, String server, String port, String database, String username,
			String password) {
		setDriver(driver);
		setServer(server);
		setPort(port);
		setDatabase(database);
		setUsername(username);
		setPassword(password);
	}

	/**
	 * @param environmentName
	 */
	private void setEnvironmentName(String environmentName) {
		checkValue(environmentName, K_ENVIRONMENTNAME);
		this.environmentName = environmentName;
	}

	/**
	 * @param fieldValue
	 *            Campo a chequear
	 * @param fieldName
	 *            Nombre del campo a chequear (para el mensaje)
	 * @throws IllegalArgumentException
	 */
	private void checkValue(String fieldValue, String fieldName) {
		if (fieldValue == null || fieldValue.isEmpty()) {
			throw new IllegalArgumentException(String.format(ERR_INVALID_ATTR_VALUE, fieldName, fieldValue));
		}
	}
}