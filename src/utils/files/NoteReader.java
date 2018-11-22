package utils.files;

import entidades.Nota;
import funciones_helper.Contador;

public class NoteReader extends CSVReader<Nota> {
	private Contador contadorNotas;

	public NoteReader(String header) {
		super(header);
		this.contadorNotas = new Contador();
		// TODO Auto-generated constructor stub
	}

	@Override
	Nota fromCsv(String line) {
		Nota note = new Nota(line);
		//contadorNotas.incrementar();
		return note;
	}

	public void resetContador() {
		contadorNotas.actualizar();
	}

}
