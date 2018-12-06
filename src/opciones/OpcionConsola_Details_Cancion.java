package opciones;
import java.util.ArrayList;
import java.util.Scanner;
import entidades.AnoteManager;
import entidades.Cancion;
import funciones_helper.Funcion_Helper;

public class OpcionConsola_Details_Cancion extends Opcion {
	private static final String MENSAJE_NOMBRE_CANCION = "Ingrese el nombre de la canción a detallar";
	private static final String ERROR_LISTA_VACIA = "No hay canciones guardadas";

	public OpcionConsola_Details_Cancion() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		ArrayList<String> canciones = manager.getCanciones();
		if (canciones.isEmpty()) {
			throw new IllegalArgumentException(ERROR_LISTA_VACIA);
		}else {
			Funcion_Helper.listarCanciones(canciones);
			String nombreCancion = Funcion_Helper.pedirCancion(MENSAJE_NOMBRE_CANCION, input, canciones);
			Cancion cancion = manager.loadCancion(nombreCancion);
			cancion.listar();
			System.out.println("---------------------");
		}
		
	}

}
