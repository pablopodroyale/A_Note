package interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import entidades.Pista;
import entidades.Cancion;
import entidades.Nota;
import entidades.PlayerSingleton;
import viewmodels.ViewModelCancion;

public interface RepoMelodias {

	void save(ViewModelCancion cancionVM);

	void updateNombreMelodia(String nombreMelodia, String nuevoNombre);

	void listarNotas(String nombreMelodia);

	ArrayList<String> getCanciones();

	void deleteCancion(String nombreCancion);

	void close() throws SQLException;

	void play(String nombreCancion, PlayerSingleton player);

	Cancion loadCancion(String nombreCancion);

	//void listarCanciones();

}
