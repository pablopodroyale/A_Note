package interfaces;

import java.util.LinkedHashMap;
import java.util.Scanner;
import entidades.AnoteManagger;
import entidades.MyPattern;
import opciones.Opcion;
import repository.IMelodia_Repository;
import repository.INota_Repository;

public interface ILector {

	void EjecutarOpcion(AnoteManagger manager, IMelodia_Repository persisitidor_Ini, INota_Repository persisitidor_Csv,
			LinkedHashMap<Integer, Opcion> opciones, Scanner input, MyPattern pattern);
}
