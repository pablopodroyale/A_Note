package viewmodels;

import java.util.ArrayList;

import entidades.MyPattern;
import entidades.Pista;
import entidades.PlayerSingleton;

public class ViewModelCancion {
	private ArrayList<ViewModelPista> pistas;
	private int IdCancion;
	private String nombreCancion;
	private String tempo;
	private MyPattern pattern;

	public ViewModelCancion(String nombre) {
		this.pistas = new ArrayList<>();
		this.nombreCancion = nombre;
		this.pattern = new MyPattern();

	}

	public ViewModelCancion() {
		this("");
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public void setId(int id) {
		this.IdCancion = id;

	}

	public String getNombreCancion() {
		return nombreCancion;
	}

	public void setNombreCancion(String nombreCancion) {
		this.nombreCancion = nombreCancion;
	}

	public int getId() {
		return IdCancion;
	}

	public void addPista(ViewModelPista pista) {
		pistas.add(pista);
	}

	public boolean hasPistas() {
		return pistas.size() > 0;
	}

	public void play() {
		for (ViewModelPista viewModelPista : pistas) {
			pattern.add(viewModelPista.getPattern());
		}
		PlayerSingleton.getInstance().play(pattern);
	}

	public ArrayList<ViewModelPista> getPistas() {
		return pistas;
	}

	public void deletePistaById(String sPista) {
		int index = 0;
		boolean remove = false;
		ViewModelPista aux;
		while (index < pistas.size() && remove == false) {
			aux = pistas.get(index);
			if (aux.getNombre().equals(sPista)) {
				remove = pistas.remove(aux);
			} else {
				index++;
			}
		}
	}

}
