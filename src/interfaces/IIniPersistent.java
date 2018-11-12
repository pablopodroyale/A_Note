package interfaces;

import utils.files.IniManager;

public interface IIniPersistent {
	void updateIni(IniManager iniManager);
	void loadFromIni(IniManager iniManager, String sectionName);
	String getName();
}
