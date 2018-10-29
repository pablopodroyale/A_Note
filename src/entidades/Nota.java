package entidades;

import funciones_helper.Funcion_Helper;
import interfaces.sonable;

public class Nota implements sonable {
	private static final String ERROR_ALTERACION = "Error, la alteracion debe ser '#' o 'b'";
	private static final String ERROR_FIGURA_INVALIDA = "Error, figura invalida";
	private static final String ERROR_RANGO_OCTAVA = "Error, la octava debe estar entre 0 y 9";
	private static final int RANGO_OCTAVA_MIN = 0;
	private static final int RANGO_OCTAVA_MAX = 9;
	private String nombre;
	private String octava;
	private String figura;
	private String alteracion;

	// Definir como manejar el id
	private int id;

	/***
	 * Fomato Nota: [nombre],[octava],[figura],[alteración] Sólo recibe el nombre de
	 * la nota, setea la octava por defecto 6, figura negra, alteracion ""
	 * 
	 * @param nombreNota
	 */
	public Nota(String nombreNota) {
		// Validar que el nombre de nota sea correcto
		setNombre(nombreNota);
		this.octava = "6";
		this.figura = Figuras_Ritmicas.NEGRA.getNombreJFuge();
		this.alteracion = "";
	}

	private void setNombre(String nombreNota) {
		try {
			Funcion_Helper.validarString(nombreNota);
			this.nombre = nombreNota;
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
			alteracionValidator(alteracion);
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
			messureValidator(figura);
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
		return nombre;
	}

	// Validadores

	public String getOctava() {
		return octava;
	}

	private void alteracionValidator(String alteracion) {
		if (!alteracion.equalsIgnoreCase("#") && !alteracion.equalsIgnoreCase("b")) {
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
	public void play(PatternSingleton pattern, PlayerSingleton player) {
		// TODO Auto-generated method stub

	}

}
