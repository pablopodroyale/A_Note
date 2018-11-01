package persistencia;

import entidades.Nota;

public class NoteReader extends CSVReader<Nota> {

	public NoteReader(String header) {
		super(header);
		// TODO Auto-generated constructor stub
	}

	@Override
	Nota fromCsv(String line) {
		Nota note = new Nota(line);
		return note;
	}

}
