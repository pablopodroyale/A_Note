package interfaces;

import java.util.ArrayList;
import entidades.Melodia;
import entidades.Nota;

public interface IRepositorios {

	void createMelodia(String nombreMelodia);

	void saveMelodia(String nombreMelodia, boolean append);

	void saveNotas(ArrayList<Nota> notas, String nombreMelodia, boolean append);

	

	void updateMelodiaInIni(Melodia melodia, String nombreAnterior, boolean append);

	//void updateNombreIni(String nombreaAnteriorMelodia, String nuevoNombre);

	void updateSection(String nombreAnterior, String nuevoNombre);

	void updateMelodiaParams(Melodia melodia);

	void loadNotas(ArrayList<Nota> notas, String nombreMelodia);

	//void loadNotas(String nombreMelodia);
	
	Melodia loadMelodia(String nombreMelodia);

	// ArrayList<Nota> getNotas(String nombreMelodia);

	boolean containsMelodia(String nombreMelodia);

	// void addMelodia(String string);

	Melodia getMelodia(String nombreMelodia);

	//void updateNombreEnMapa(String nombreAnterior, String nuevoNombre);

	void updateNota(String nombreMelodia, String idNota, String nombreNota, String octava, String figura,
			String alteracion);

	//Actualiza el nombre en la melodia y en la clave del hash
	void updateNombreMelodia(String nombreMelodia, String nuevoNombre);

	void removeNotaById(String nombreMelodia, String idNota);

	void deleteMelodiaInFile(String nombreCancion);

	void listarNotas(String nombreMelodia);

	void listarCanciones();

	void cargarCanciones();

	void saveAll();

	// void removeNotaById(String idNota);

}
