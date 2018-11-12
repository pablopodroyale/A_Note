package utils.database;

import utils.files.IniManager;
import utils.files.Section;
import interfaces.IIniPersistent;

public class IniPersDBEnvironment extends DBEnvironment implements IIniPersistent {
	private static String ERR_INVALID_ENVIRONMENT = "Ambiente invalido:  %s";

	public IniPersDBEnvironment(String environmentName, IniManager iniManager) {
		super(environmentName);
		getEnvironment(environmentName, iniManager);

		// TODO Auto-generated constructor stub
	}

	public IniPersDBEnvironment(String nombre, String driver, String server, String port, String base, String username,
			String pwd) {
		this(nombre);
		setValues(driver, server, port, base, username, pwd);
	}

	public IniPersDBEnvironment(String environmentName) {
		super(environmentName);
		// TODO Auto-generated constructor stub
	}

	public Section getEnvironment(String environmentName, IniManager iniManager) {
		Section section = null;
		if (iniManager.getValueof(environmentName) != null) {
			throw new RuntimeException(ERR_INVALID_ENVIRONMENT);
		}else {
			section = iniManager.getSection(environmentName);
		}
		return section;
	}

	@Override
	public void updateIni(IniManager iniManager) {
		iniManager.addSection(getEnvironmentName());
		iniManager.setItem(getEnvironmentName(), "environment", super.getEnvironmentName());
		iniManager.setItem(getEnvironmentName(), "driver", super.getDriver());
		iniManager.setItem(getEnvironmentName(), "server", super.getServer());
		iniManager.setItem(getEnvironmentName(), "port", super.getPort());
		iniManager.setItem(getEnvironmentName(), "database", super.getDatabase());
		iniManager.setItem(getEnvironmentName(), "username", super.getUsername());
		iniManager.setItem(getEnvironmentName(), "password", super.getPassword());
	}

	@Override
	public void loadFromIni(IniManager iniManager, String sectionName) {
		if (iniManager.getValueof(sectionName) != null) {
			throw new RuntimeException(ERR_INVALID_ENVIRONMENT);
		} else {
			setValues(iniManager.getValueOf(sectionName, "driver"), iniManager.getValueOf(sectionName, "server"),
					iniManager.getValueOf(sectionName, "port"), iniManager.getValueOf(sectionName, "database"),
					iniManager.getValueOf(sectionName, "username"), iniManager.getValueOf(sectionName, "password"));
		}

	}

	public void updateItemIni(String environmentName, String attribute, String value, IniManager iniManager) {
		iniManager.updateItem(environmentName, attribute, value);
		
	}

	@Override
	public String getName() {
		return getName();
	}

}
