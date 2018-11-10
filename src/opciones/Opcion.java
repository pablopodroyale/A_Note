package opciones;

import interfaces.IRepositorios;
import interfaces.IOpcion;


public abstract class Opcion implements IOpcion {
	protected String ingreso;
	protected IRepositorios repositoryMelodia;
	//protected INota_Repository persistencia_Csv;

	public Opcion(IRepositorios repositoryMelodia) {
		this.repositoryMelodia = repositoryMelodia;
		this.ingreso = null;

	}

}
