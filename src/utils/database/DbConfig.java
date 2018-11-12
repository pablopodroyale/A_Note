package utils.database;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import interfaces.IIniPersistent;
import utils.files.IniManager;

public class DbConfig {
	private IniManager iniManager;
	private String defaultEnvironment;
	private static final String ROOT_SECTION = "DBCONFIG";
	private static final String ROOT_SECTION_ITEM = "DBEnvironment";
	private static final String ROOT_SECTION_ENVIRONMENT = "DBEnvironment";
	private static final String ROOT_SERVER = new File("").getAbsolutePath();
	private static final String DEFAULT = "DEFAULT";
	private static final String ERROR_AMBIENTE_INEXISTENTE = "Error, ambiente inexistente";

	public DbConfig() {
		// Verifica que exista el archivo dbconfig.ini en la carpeta root, si no existe
		// crea el archivo dbconfig.ini en la root
		this("", "dbconfig");
		Path path = Paths.get(ROOT_SERVER + "dbconfig.ini");
		if (Files.exists(path)) {
			// que hago aca
		} else if (Files.notExists(path)) {
			File file = new File("dbconfig.ini");
		}

	}

	public DbConfig(String folder, String name) {
		// recibe el folder que dberia ser root server
		// recibe el nombre del archivo que deberia ser dbconfig.ini
		// Verifica que el ini no tenga secciones, si no tiene root_section la crea
		// si ya existe recupera la seccion que sea default
		this.iniManager = new IniManager();
		if (iniManager.getSectionCount() == 0) {
			iniManager.addSection(ROOT_SECTION);
		} else if (iniManager.getValueof(ROOT_SECTION) == null) {
			setDefaultEnvironment(DEFAULT);
		} else {
			setDefaultEnvironment(iniManager.getValueof(DEFAULT));
		}

	}

	private void createDbConfig(String name) {
		Path path = Paths.get(ROOT_SERVER + name);
		if (Files.exists(path)) {

		} else if (Files.notExists(path)) {
			File file = new File(name);
		}

	}

	public void setDefaultEnvironment(String environmentName) {
		createDefaultEnvironment();
	}

	/***
	 * Recibe el nombre del ambiente a modificar, el nombre del atributo a modificar
	 * y el valor a asignar. Usa una instancia de IniPersDBEnvironment para
	 * recuperar un ambiente desde el ini y luego actualizar el campo recibido en
	 * attribute con el nuevo valor.
	 * 
	 * @param environmentName
	 * @param attribute
	 * @param value
	 */
	public void setValueOf(String environmentName, String attribute, String value) {
		IniPersDBEnvironment iniPersDBEnvironment = new IniPersDBEnvironment(environmentName);
		if (iniPersDBEnvironment.getEnvironment(environmentName, iniManager) != null) {
			iniPersDBEnvironment.updateItemIni(environmentName, attribute, value, iniManager);
		} else {
			throw new IllegalArgumentException(ERROR_AMBIENTE_INEXISTENTE);
		}
	}

	/***
	 * Devuelve el nombre del ambiente que se ha definido como por defecto en el
	 * ini. Si no se ha creado ninguno será el ambiente LOCAL, que se crea sin
	 * configurar.
	 * 
	 * @return
	 */
	public String getDefaultEnvironment() {
		return iniManager.getSection(DEFAULT).getName();
	}

	public ArrayList<String> getAllEnvironmentNames() {
		ArrayList<String> environments = iniManager.getSections(DEFAULT);
		return environments;
	}

	public String getURL() {
		IniPersDBEnvironment iniPersDBEnvironment = new IniPersDBEnvironment(DEFAULT, iniManager);
		return iniPersDBEnvironment.getURL();
	}

	/***
	 * Recibe el nombre de un ambiente existente para devolver su string de conexión
	 * o URL. Si no existe un ambiente con el nombre recibido como parámetro se
	 * disparará una RuntimeException .
	 * 
	 * @param environmentName
	 * @return
	 */
	public String getURL(String environmentName) {
		IniPersDBEnvironment iniPersDBEnvironment = new IniPersDBEnvironment(environmentName, iniManager);
		return iniPersDBEnvironment.getURL();
	}

	public boolean hasEnvironment(String environmentName) {
		IniPersDBEnvironment iniPersDBEnvironment = new IniPersDBEnvironment(environmentName, iniManager);
		return iniPersDBEnvironment.getEnvironment(environmentName, iniManager) != null;
	}

	/***
	 * Crea un ambiente a partir de los datos recibidos y lo agrega al ini. Si el
	 * ambiente ya existe, lo reemplaza.
	 * 
	 * @param name
	 * @param driver
	 * @param server
	 * @param port
	 * @param database
	 * @param username
	 * @param password
	 */
	public void createEnvironment(String name, String driver, String server, String port, String database,
			String username, String password) {
		IniPersDBEnvironment iniPersDBEnvironment = new IniPersDBEnvironment(name, iniManager);
		updateIniSection(iniPersDBEnvironment);
	}

	public void save() {
		this.iniManager.save(true);
	}

	/***
	 * Agrega el ambiente como una sección en la estructura de IniManager . Si ya
	 * estaba lo reemplaza.
	 * 
	 * @param dbenv
	 */
	private void updateIniSection(IIniPersistent dbenv) {
		IniPersDBEnvironment iniPersDBEnvironment = (IniPersDBEnvironment) dbenv;
		if (hasEnvironment(iniPersDBEnvironment.getName())) {
			// reemplazo la seccion delini
			iniManager.removeSection(iniPersDBEnvironment.getName());
			iniPersDBEnvironment.setValues(iniPersDBEnvironment.getDriver(), iniPersDBEnvironment.getServer(),
					iniPersDBEnvironment.getPort(), iniPersDBEnvironment.getPort(), iniPersDBEnvironment.getUsername(),
					iniPersDBEnvironment.getPassword());
			iniPersDBEnvironment.updateIni(iniManager);
		} else {
			// la agrego
			iniPersDBEnvironment.setValues(iniPersDBEnvironment.getDriver(), iniPersDBEnvironment.getServer(),
					iniPersDBEnvironment.getPort(), iniPersDBEnvironment.getPort(), iniPersDBEnvironment.getUsername(),
					iniPersDBEnvironment.getPassword());
			iniPersDBEnvironment.updateIni(iniManager);
		}
	}
	
	private void createDefaultEnvironment() {
		iniManager.addSection(defaultEnvironment);
	}

}
