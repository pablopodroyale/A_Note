package repository;

import entidades.Melodia;

public interface IRepository_Ini {
	
	void saveIni(Melodia melodia, boolean append);

	Melodia load(String nombreMelodia);

	void updateIni(Melodia melodia, String nombreAnterior, boolean append);

	void updateNombreIni(String nombreaAnteriorMelodia, String nuevoNombre);

}
