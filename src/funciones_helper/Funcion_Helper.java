package funciones_helper;

public final class Funcion_Helper {
	static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";

	public static final void validarString(String str) {
		if (str.isEmpty() || str == null) {
			throw new IllegalArgumentException(ERROR_STRING_VACIO);
		}

	}
	
	public static final boolean validarRango(int min, int max, int numero) {
		boolean ok = false;
		if (numero > min && numero < max ) {
			ok = true;
		}
		return ok;
	}
}
