package interfaces;

import java.util.LinkedHashMap;
import entidades.AnoteManager;
import opciones.Opcion;
import repository.RepoCancionesMixto;
import repository.Repositorio_CancionDb;

public interface Vista {

	void EjecutarOpcion(AnoteManager manager, RepoMelodias repo,LinkedHashMap<Integer, Opcion> opciones);
}
