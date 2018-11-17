package utils.database.db;

import java.util.HashMap;
import java.util.Map;

import utils.database.ini.IniPersistent;
import utils.files.IniManager;

public class IniPersDBEnvironment extends DBEnvironment implements IniPersistent {

	private static final String ERR_INVALID_ENVIRONMENT = "Ambiente invalido: %s";

	/**
	 * Constructor a partir del ambiente "por defecto" indicado en el ini.
	 * 
	 * @param im
	 *            Instancia de un iniManager con acceso al archivo ini.
	 * @param defaultSection
	 *            Nombre del ambiente.
	 */
	public IniPersDBEnvironment(String environmentName, IniManager im) {
		super(environmentName);
		loadFromIni(im, environmentName);
	}

	public IniPersDBEnvironment(String nombre, String driver, String server, String port, String base, String usrname,
			String pwd) {
		super(nombre, driver, server, port, base, usrname, pwd);
	}

	public IniPersDBEnvironment(String environmentName) {
		super(environmentName);
	}

	public IniPersDBEnvironment(Map<String, String> params) {
		super(params);
	}

	/**
	 * Implementa el método updateIni declarado en la interfaz
	 * 
	 * @param iniManager
	 *            Instancia de IniManager
	 */
	@Override
	public void updateIni(IniManager iniManager) {
		String name = getEnvironmentName();
		iniManager.setItem(name, K_DRIVER, getDriver());
		iniManager.setItem(name, K_SERVER, getServer());
		iniManager.setItem(name, K_PORT, getPort());
		iniManager.setItem(name, K_DATABASE, getDatabase());
		iniManager.setItem(name, K_USERNAME, getUsername());
		iniManager.setItem(name, K_PASSWORD, getPassword());
	}

	@Override
	public void loadFromIni(IniManager iniManager, String sectionName) {
		if (iniManager.getValueof(sectionName) != null) {
			final Map<String, String> params = new HashMap<String, String>();
			params.put("driver", iniManager.getValueOf(sectionName, K_DRIVER));
			params.put("server", iniManager.getValueOf(sectionName, K_SERVER));
			params.put("database", iniManager.getValueOf(sectionName, K_DATABASE));
			params.put("port", iniManager.getValueOf(sectionName, K_PORT));
			params.put("username", iniManager.getValueOf(sectionName, K_USERNAME));
			params.put("password", iniManager.getValueOf(sectionName, K_PASSWORD));
			setValues(params);
		} else {
			throw new RuntimeException(String.format(ERR_INVALID_ENVIRONMENT, sectionName));
		}
	}
}
