package entidades;

import funciones_helper.Funcion_Helper;
import interfaces.ISonable;
import utils.files.CSVCompatible;

public class Nota implements ISonable, CSVCompatible<Nota> {
	private static final String HEADER_NOTA = "Id,Nombre_Nota,Octava,Figura,Alteracion";
	private static final String ERROR_ALTERACION = "Error, la alteracion debe ser '#' o 'b' o 'n'";
	private static final String ERROR_FIGURA_INVALIDA = "Error, figura invalida";
	private static final String ERROR_RANGO_OCTAVA = "Error, la octava debe estar entre 0 y 9";
	private static final int RANGO_OCTAVA_MIN = 0;
	private static final int RANGO_OCTAVA_MAX = 9;
	private static final int CANT_ATRIBUTOS = 5;
	private static final String ERR_CANT_VALORES = "Error, la cantidad de valores recibidos, no coincide con los esperados";
	private int pistaID;
	private String nombreCancion;
	private String nombreNota;
	private String octava;
	private String figura;
	private String alteracion;
	// Definir como manejar el id
	private int id;

	/***
	 * Fomato Nota: [nombre],[octava],[figura],[alteración] Sólo recibe el nombre de
	 * la nota, setea la octava por defecto 6, figura negra, alteracion ""
	 * 
	 * @param line
	 */
	public Nota(String line) {
		// por el if esta recibiendo la linea leida por el reader
		String fields[] = line.split(",");
		// verifyFields(lineOrName);
		if (fields.length < CANT_ATRIBUTOS) {
			throw new IllegalArgumentException(ERR_CANT_VALORES);
		}
		setValues(Integer.parseInt(fields[0]), fields[1], (fields[2]), fields[3], fields[4]);
		// setId(id);
	}

	/***
	 * Fomato Nota: [nombre],[octava],[figura],[alteración] Sólo recibe el nombre de
	 * la nota, setea la octava por defecto 6, figura negra, alteracion "n"
	 * 
	 * @param nombre
	 * @param octava
	 * @param figura
	 * @param alteracion
	 */
	public Nota(int Id, String nombre, String octava, String figura, String alteracion) {
		setValues(Id, nombre, octava, figura, alteracion);
	}
	
	public Nota() {
		
	}

	/*
	 * private void verifyFields(String lineOrName) { String fields[] =
	 * lineOrName.split(","); if (fields[0] != null) { System.out.println("0 ok"); }
	 * if (fields[1] != null) { System.out.println("1 ok"); } if (fields[2] != null)
	 * { System.out.println("2 ok"); } if (fields[3] != null) {
	 * System.out.println("3 ok"); }
	 * 
	 * }
	 */
	public int getPistaID() {
		return pistaID;
	}

	public void setPistaID(int pistaID) {
		this.pistaID = pistaID;
	}

	public String getNombreCancion() {
		return nombreCancion;
	}

	public void setNombreCancion(String nombreCancion) {
		this.nombreCancion = nombreCancion;
	}

	public String getNombreNota() {
		return nombreNota;
	}

	public void setNombreNota(String nombreNota) {
		this.nombreNota = nombreNota;
	}

	private void setValues(int Id, String nombre, String octava, String figura, String alteracion) {
		setId(Id);
		setNombre(nombre);
		setOctava(octava);
		setFigura(figura);
		setAlteracion(alteracion);

	}

	public void setNombre(String nombreNota) {
		try {
			Funcion_Helper.validarString(nombreNota);
			this.nombreNota = nombreNota;
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public String getAlteracion() {
		return alteracion;
	}

	public void setAlteracion(String alteracion) {
		try {
			Funcion_Helper.validarString(alteracion);

			this.alteracion = alteracion;
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	/***
	 * Redonda w , Blanca h , Negra q, Corchea i, Semicorchea s, Fusa t, Semi fusa x
	 * 
	 * @param figura
	 */
	public void setFigura(String figura) {
		try {
			Funcion_Helper.validarString(figura);
			// messureValidator(figura);
			this.figura = figura;
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	public void setOctava(String octava) {
		try {
			Funcion_Helper.validarString(octava);
			validarOctava(octava);
			this.octava = octava;
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
	}

	private void validarOctava(String poctava) {
		int oct = Integer.parseInt(poctava);
		if (!Funcion_Helper.validarRango(RANGO_OCTAVA_MIN, RANGO_OCTAVA_MAX, oct)) {
			throw new IllegalArgumentException(ERROR_RANGO_OCTAVA);
		}

	}

	public String getFigura() {
		return figura;
	}

	public String getNombre() {
		return nombreNota;
	}

	// Validadores

	public String getOctava() {
		return octava;
	}

	private void alteracionValidator(String alteracion) {
		if (!alteracion.equalsIgnoreCase("#") && !alteracion.equalsIgnoreCase("b")
				&& !alteracion.equalsIgnoreCase("n")) {
			throw new IllegalArgumentException(ERROR_ALTERACION);
		}
	}

	private void messureValidator(String figura) {
		Figuras_Ritmicas[] values = Figuras_Ritmicas.values();
		int indice = 0;
		String aux;
		String figuraBuscada = null;
		while (indice < values.length && figuraBuscada == null) {
			aux = values[indice].getNombreJFuge();
			if (aux.equalsIgnoreCase(figura)) {
				figuraBuscada = aux;
			} else {
				indice++;
			}
		}

		if (figuraBuscada == null) {
			throw new IllegalArgumentException(ERROR_FIGURA_INVALIDA);
		}
	}

	@Override
	public String toString() {
		return id + "," + nombreNota + "," + octava + "," + figura + "," + alteracion;
	}

	public String toStringConId() {
		return id + ":" + nombreNota + "," + octava + "," + figura + "," + alteracion;
	}

	public static String getHeader() {
		return HEADER_NOTA;
	}

	@Override
	public String ToCSV() {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO
	@Override
	public void play(PlayerSingleton player) {
		// TODO Auto-generated method stub

	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
