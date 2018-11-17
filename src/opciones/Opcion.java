package opciones;

import interfaces.RepoMelodias;
import interfaces.IOpcion;


public abstract class Opcion implements IOpcion {
	protected String ingreso;
	protected RepoMelodias repositoryMelodia;
	//protected INota_Repository persistencia_Csv;

	public Opcion(RepoMelodias repositoryMelodia) {
		this.repositoryMelodia = repositoryMelodia;
		this.ingreso = null;
	}

}
