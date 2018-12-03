package entidades;

import java.util.ArrayList;

import viewmodels.ViewModelPista;

public class Cancion {
	private ArrayList<Pista> pistas;
	private String tempo;
	private String nombreCancion;
	private int Id;
	private MyPattern pattern;
	
	public Cancion(String nombre) {
		this.pistas = new ArrayList<>();
		this.nombreCancion = nombre;
		this.pattern = new MyPattern();
	}

	public Cancion() {
		this("");
	}
	

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}


	public void setId(int id) {
		this.Id = id;

	}

	public String getNombreCancion() {
		return nombreCancion;
	}

	public void setNombreCancion(String nombreCancion) {
		this.nombreCancion = nombreCancion;
	}

	public int getId() {
		return Id;
	}

	public void addPista(Pista pista) {
		pistas.add(pista);
	}

	public void removeCancionById(int id) {
		pistas.remove(id);
	}

	public void play(PlayerSingleton player) {
		pattern.clear();
		for (Pista pista : pistas) {
			pattern.add(pista.getPattern());
		}
		PlayerSingleton.getInstance().play(pattern);

	}

	public ArrayList<Pista> getPistas() {
		return this.pistas;
	}

	public void setPistas(ArrayList<Pista> pistas) {
		this.pistas = pistas;
	}

}
