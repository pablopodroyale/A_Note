package interfaces;

import java.util.LinkedHashMap;
import entidades.AnoteManager;
import opciones.Opcion;

public interface ILector {

	void EjecutarOpcion(AnoteManager manager, IRepositorios repositorioMelodia,LinkedHashMap<Integer, Opcion> opciones);
}
