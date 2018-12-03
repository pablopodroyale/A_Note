package opciones;

import interfaces.IOpcion;



public abstract class Opcion implements IOpcion {
	protected String ingreso;
	//protected RepoMelodiasMixto repoMixto;
	//protected Repositorio_MelodiaDb repoDb;
	//protected INota_Repository persistencia_Csv;

	public Opcion() {
		this.ingreso = null;
	}

}
