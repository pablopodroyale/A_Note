package utils.database.ini;

import utils.files.IniManager;

public interface IniPersistent {

	/**
	 * Actualiza todos los valores de la instancia en una sección del IniManager
	 * recibido.
	 * 
	 * @param im
	 *            Instancia de IniManager.
	 */
	void updateIni(IniManager iniManager);

	/**
	 * Carga los datos de la seccion requerida desde el IniManager recibido.
	 * 
	 * @param iniManager
	 *            Instancia de IniManager.
	 * @param sectionName
	 *            Nombre de la seccion.
	 */
	void loadFromIni(IniManager iniManager, String sectionName);

}