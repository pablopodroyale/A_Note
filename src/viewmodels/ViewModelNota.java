package viewmodels;

import entidades.Figuras_Ritmicas;
import entidades.Instrumentos;
import entidades.Notas;
import funciones_helper.Funcion_Helper;

public class ViewModelNota {
	private static final String ERROR_ALTERACION = "Error, la alteracion debe ser '#' o 'b' o 'n'";
	private static final String ERROR_FIGURA_INVALIDA = "Error, figura invalida";
	private static final String ERROR_RANGO_OCTAVA = "Error, la octava debe estar entre 0 y 9";
	private static final int RANGO_OCTAVA_MIN = 0;
	private static final int RANGO_OCTAVA_MAX = 9;
	private static final String ERROR_NOTA_INVALIDA = "ERROR la nota debe ser C, D, E, F, G, A, o B";
	private int pistaID;
	private String nombreCancion;
	private String nombre;
	private String octava;
	private String figura;
	private String alteracion;
	private int id;

	public ViewModelNota() {

	}

	public ViewModelNota(int id2, String nombre2, String octava2, String figura2, String alteracion2) {
		setValues(id2,nombre2,octava2,figura2,alteracion2);
	}

	private void setValues(int id2, String nombre2, String octava2, String figura2, String alteracion2) {
		setId(id2);
		setNombre(nombre2);
		setOctava(octava2);
		setFigura(figura2);
		setAlteracion(alteracion2);
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		try {
			// validarNombreNota(nombre);
			// Funcion_Helper.validarString(nombre);
			this.nombre = nombre;
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage());
		}
	}

	private void validarNombreNota(String nombreNota) {
		if (Notas.valueOf(nombre.toUpperCase()) == null) {
			throw new IllegalArgumentException(ERROR_NOTA_INVALIDA);
		}

	}

	private void validarOctava(String poctava) {
		int oct = Integer.parseInt(poctava);
		if (!Funcion_Helper.validarRango(RANGO_OCTAVA_MIN, RANGO_OCTAVA_MAX, oct)) {
			throw new IllegalArgumentException(ERROR_RANGO_OCTAVA);
		}

	}

	public String getOctava() {
		return octava;
	}

	public void setOctava(String octava) {
		try {
			// Funcion_Helper.validarString(octava);
			// validarOctava(octava);
			this.octava = octava;
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage());
		}
	}

	public String getFigura() {
		return figura;
	}

	public void setFigura(String figura) {
		try {
			Funcion_Helper.validarString(figura);
			// messureValidator(figura);
			this.figura = figura;
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage());
		}
	}

	/*
	 * private void messureValidator(String figura2) { if
	 * (Figuras_Ritmicas.valueOf(figura2.toUpperCase()) == null) { throw new
	 * IllegalArgumentException(ERROR_FIGURA_INVALIDA); }
	 * 
	 * }
	 */
	
	public String getAlteracion() {
		return alteracion;
	}

	public void setAlteracion(String alteracion) {
		try {
			// Funcion_Helper.validarString(alteracion);
			// salteracionValidator(alteracion);
			this.alteracion = alteracion;
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
	}

	private void alteracionValidator(String alteracion2) {
		if (!alteracion.equalsIgnoreCase("#") && !alteracion.equalsIgnoreCase("b")
				&& !alteracion.equalsIgnoreCase("n")) {
			throw new IllegalArgumentException(ERROR_ALTERACION);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
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


	@Override
	public String toString() {
		return  id + ":" + "Nota: " + nombre.toUpperCase() + ", octava:" + octava + ", figura: " + figura + ", alteracion:"
				+ alteracion;
	}

}
