package repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import entidades.Melodia;
import entidades.Nota;

public interface IRepository_CSV {
	// ArrayList<Nota> load();
	void saveCSV(ArrayList<Nota> notas, String nombreMelodia, boolean append);

	void load(ArrayList<Nota> notas, String nombreMelodia);

	void updateName(String nombreMelodia, String nuevoNombre);

}
