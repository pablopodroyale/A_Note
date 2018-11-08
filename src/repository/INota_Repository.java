package repository;

import java.util.ArrayList;

import entidades.Nota;

public interface INota_Repository {
	// ArrayList<Nota> load();
	void saveCSV(ArrayList<Nota> notas, String nombreMelodia, boolean append);

	void load(ArrayList<Nota> notas, String nombreMelodia);

	void updateName(String nombreMelodia, String nuevoNombre);

}
