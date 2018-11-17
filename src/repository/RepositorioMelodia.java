package repository;

import java.util.LinkedHashMap;
import java.util.Set;

import entidades.Melodia;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;

public abstract class RepositorioMelodia implements RepoMelodias {
	protected static final String ERROR_MELODIA_INEXISTENTE = "Error, la melodia no existe";
	protected static final String ERROR_MELODIA_EXISTENTE = "Error, la melodia ya exite";
	protected static final String CANCIONES = "Canciones";
	protected static final String ERROR_NO_EXISTE_CANCION = "Error, la cancion seleccionada no existe";
	protected static final String ERROR_STRING_VACIO = "Error, el nombre no puede estar vacio";
	private static final String ERROR_LISTA_VACIA = "No hay melodías guardadas";
	protected LinkedHashMap<String, Melodia> melodias;

	public RepositorioMelodia() {
		this.melodias = new LinkedHashMap<>();
	}

	@Override
	public void createMelodia(String nombreMelodia, String instrumento, String tempo) {
		if (melodias.containsKey(nombreMelodia)) {
			throw new IllegalArgumentException(ERROR_MELODIA_EXISTENTE);
		} else {
			melodias.put(nombreMelodia, new Melodia(nombreMelodia, instrumento, tempo));
		}
	}

	public Melodia getMelodia(String nombreMelodia) {
		Melodia melodia = null;
		if (containsMelodia(nombreMelodia)) {
			melodia = melodias.get(nombreMelodia);
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}
		return melodia;
	}

	public boolean containsMelodia(String nombreMelodia) {
		return melodias.containsKey(nombreMelodia);
	}
	
	public void updateNombreEnMapa(String nombreAnterior, String nuevoNombre) {
		if (melodias.containsKey(nombreAnterior)) {
			melodias.put(nuevoNombre, melodias.remove(nombreAnterior));
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}
	
	protected void addMelodia(Melodia melodia) {
		melodias.put(melodia.getNombre(), melodia);

	}
	
	@Override
	public void removeNotaById(String nombreMelodia, String idNota) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.removeNotaById(idNota);
		} else {
			throw new IllegalArgumentException(ERROR_MELODIA_INEXISTENTE);
		}

	}

	@Override
	public void listarNotas(String nombreMelodia) {
		Melodia melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.listarNotas();
		}

	}
	
	@Override
	public void listarCanciones() {
		Melodia melodia;
		Set<String> canciones = melodias.keySet();
		if (canciones.isEmpty()) {
			throw new IllegalArgumentException(ERROR_LISTA_VACIA);
		}
		for (int j = 0; j < canciones.size(); j++) {
			System.out.println((j + 1) + ":" + canciones.toArray()[j]);
			melodia = getMelodia(canciones.toArray()[j].toString());
			melodia.toString();
		}
	}
	
	@Override
	public void updateNota(String nombreMelodia, String idNota, String nombreNota, String octava, String figura,
			String alteracion) {
		Melodia melodia;
		Funcion_Helper.validarString(nombreMelodia);
		Funcion_Helper.validarString(idNota);
		Funcion_Helper.validarString(nombreNota);
		Funcion_Helper.validarString(octava);
		Funcion_Helper.validarString(figura);
		Funcion_Helper.validarString(alteracion);
		melodia = getMelodia(nombreMelodia);
		if (melodia != null) {
			melodia.updateNota(idNota, nombreNota, octava, figura, alteracion);
		}
	}
	
	

}
