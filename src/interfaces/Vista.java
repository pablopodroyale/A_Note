package interfaces;

import java.util.LinkedHashMap;
import entidades.AnoteManager;
import opciones.Opcion;

public interface Vista {

	void EjecutarOpcion(AnoteManager manager, RepoMelodias repositorioMelodia,LinkedHashMap<Integer, Opcion> opciones);
}
