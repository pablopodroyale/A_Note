package opciones;

import java.util.Scanner;

import repository.INota_Repository;
import repository.IMelodia_Repository;

public abstract class Opcion implements IOpcion {
	protected String ingreso;
	protected IMelodia_Repository persistencia_Ini;
	protected INota_Repository persistencia_Csv;

	public Opcion(IMelodia_Repository ini, INota_Repository csv)
     {
         this.persistencia_Ini = ini;
         this.persistencia_Csv = csv;
         this.ingreso = null;
         
     }
	
	
	
}
