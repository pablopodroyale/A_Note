package interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import entidades.Melodia;
import entidades.Nota;
import entidades.PlayerSingleton;

public interface RepoMelodias {

	void createMelodia(String nombreMelodia, String instrumento, String tempo);

	void saveMelodia(String nombreMelodia, boolean append);

	// void saveNotas(ArrayList<Nota> notas, String nombreMelodia, boolean append);

	void saveAll();

	void updateNombreMelodia(String nombreMelodia, String nuevoNombre);

	// void loadNotas(ArrayList<Nota> notas, String nombreMelodia);

	//Melodia loadMelodia(String nombreMelodia);

	// void cargarCanciones();

	// boolean containsMelodia(String nombreMelodia);

	// Melodia getMelodia(String nombreMelodia);

	void updateNota(String nombreMelodia, String idNota, String nombreNota, String octava, String figura,
			String alteracion);

	void updateTempo(String nombreMelodia, String tempo);

	void updateInstrumento(String nombreMelodia, String instrumento);

	void removeNotaById(String nombreMelodia, String idNota);

	void listarNotas(String nombreMelodia);

	void listarCanciones();

	void deleteMelodia(String nombreCancion);

	void close() throws SQLException;

	void play(String nombreMelodia, PlayerSingleton player);

	void detallesMelodia(String nombreMelodia);

	void addNote(String nombreMelodia, String nombreNota, String octava, String figura, String alteracion);

	// void updateMelodiaParams(Melodia melodia);
	// void removeNotaById(String idNota);
	// void loadNotas(String nombreMelodia);
	// ArrayList<Nota> getNotas(String nombreMelodia);
	// void addMelodia(String string);
	// void updateNombreEnMapa(String nombreAnterior, String nuevoNombre);
	// void updateMelodiaInIni(Melodia melodia, String nombreAnterior, boolean
	// append);
	// void updateNombreIni(String nombreaAnteriorMelodia, String nuevoNombre);
	// void updateSection(String nombreAnterior, String nuevoNombre);

}
