package utils.database.db;

import java.util.ArrayList;
import java.util.Map;

import utils.database.ini.IniPersistent;
import utils.files.IniManager;

public class DBConfig {

	private static final String ROOT_SECTION = "DBCONFIG";
	private static final String ROOT_SECTION_ITEM = "DBEnvironment";
	private static final String LOCAL_ENVIRONMENT = "LOCAL";
	private static final String ERR_DEFAULT_ENVIRONMENT = "Error: ambiente inexistente.";

	private IniManager im;
	private String defaultEnvironment;

	public DBConfig() {
		this("", "dbconfig");
	}

	public DBConfig(String folder, String filename) {
		im = new IniManager(folder, filename);
		im.load();
		try {
			if (im.getSectionCount() == 0 || im.getValueof(ROOT_SECTION) == null) {
				createDefaultEnvironment();
			} else {
				defaultEnvironment = im.getValueOf(ROOT_SECTION, ROOT_SECTION_ITEM);
			}
		} catch (Exception e) {
			
		}
		
	}

	public void setDefaultEnvironment(String environmentName) {
		if ( im.getValueof(environmentName) != null) {
			this.defaultEnvironment = environmentName;
			im.setItem(ROOT_SECTION, ROOT_SECTION_ITEM, environmentName);
		} else {
			throw new RuntimeException(ERR_DEFAULT_ENVIRONMENT);
		}
	}

	/**
	 * Usa una instancia de IniPersDBEnvironment para recuperar un ambiente
	 * desde el ini y actualizar el campo recibido en attribute con el nuevo
	 * valor.
	 * 
	 * @param environmentName
	 *            Nombre del ambiente a modificar.
	 * @param attribute
	 *            Nombre del atributo a modificar.
	 * @param value
	 *            Valor a asignar.
	 */
	public void setValueOf(String environmentName, String attribute, String value) {
		IniPersDBEnvironment env = new IniPersDBEnvironment(environmentName, im);
		env.setValueOf(attribute, value);
		updateIniSection(env);
	}

	/**
	 * @return El nombre del ambiente que se ha definido como ambiente por
	 *         defecto en el ini. Si no se ha creado ninguno sera el ambiente
	 *         LOCAL, que se crea sin configurar.
	 */
	public String getDefaultEnvironment() {
		return defaultEnvironment;
	}

	/**
	 * @return Una lista con los nombres de todos los ambientes existentes. Esta
	 *         lista corresponde a cada sección creada en el ini de
	 *         configuración a partir de un conjunto de ambientes disponibles.
	 *         Cada uno es una seccion dentro del ini.
	 */
	public ArrayList<String> getAllEnvironmentNames() {
		ArrayList<String> envList = new ArrayList<>();
		for (String sectionName : im.getSections()) {
			if (!sectionName.equals(ROOT_SECTION)) {
				envList.add(sectionName);
			}
		}
		return envList;
	}

	/**
	 * @return El string de conexion o URL del ambiente por defecto.
	 */
	public String getURL() {
		return getURL(defaultEnvironment);
	}

	/**
	 * @param environmentName
	 *            Nombre de un ambiente existente
	 * @return El string de conexion o URL del ambiente cuyo nombre se recibio
	 *         como parametro. Si el ambiente no existe se disparara una
	 *         RuntimeException.
	 */
	public String getURL(String environmentName) {
		return new IniPersDBEnvironment("DBCONFIG", im).getURL();
	}

	//TODO
	/**
	 * @param environmentName
	 *            Nombre del ambiente.
	 * @return Verdadero si el ambiente existe como seccion en el ini.
	 */
	public boolean hasEnvironment(String environmentName) {
		return im.getValueof(environmentName) != null;
	}

	/**
	 * Crea un ambiente a partir de los datos recibidos y lo agrega al ini. Si
	 * el ambiente ya existe, lo reemplaza.
	 * 
	 * @param name
	 *            nombre del ambiente
	 * @param driver
	 *            nombre del driver utilizado
	 * @param server
	 *            nombre del servidor de la base de datos
	 * @param port
	 *            puerto de escucha del servidor de base de datos
	 * @param database
	 *            nombre de la base de datos
	 * @param username
	 *            nombre del usuario de la base de datos
	 * @param password
	 *            password del usuario de la base de datos
	 */
	public void createEnvironment(String name, String driver, String server, String port, String database,
			String username, String password) {
		IniPersistent environment = new IniPersDBEnvironment(name, driver, server, port, database, username, password);
		updateIniSection(environment);
	}

	public void createEnvironment(Map<String, String> params) {
		IniPersistent environment = new IniPersDBEnvironment(params);
		updateIniSection(environment);
	}

	/**
	 * Actualiza el archivo de configuracion.
	 */
	public void save() {
		im.save(false);
	}

	/**
	 * Guarda el ambiente como una seccion en la estructura de IniManager. Para
	 * guardarlo debe usarse el metodo save().
	 * 
	 * @param dbenv
	 *            ambiente a guardar
	 */
	private void updateIniSection(IniPersistent dbenv) {
		dbenv.updateIni(im);
	}

	private void createDefaultEnvironment() {
		updateIniSection(new IniPersDBEnvironment(LOCAL_ENVIRONMENT));
		setDefaultEnvironment(LOCAL_ENVIRONMENT);
		save();
	}

}