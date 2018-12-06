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
		int cont = 1;
		MyPattern pat;
		for (Pista pista : pistas) {
			pat = pista.getPattern();
			pat.add("L" + cont);
			cont+=1;
			pattern.add(pat);
		}
		PlayerSingleton.getInstance();

	}

	public ArrayList<Pista> getPistas() {
		return this.pistas;
	}

	@Override
	public String toString() {
		return "Cancion: "  + nombreCancion +  ", Id=" + Id + ", tempo: " + tempo;
	}

	public void setPistas(ArrayList<Pista> pistas) {
		this.pistas = pistas;
	}

	public void listar() {
		System.out.println(toString());
		for (Pista pista : pistas) {
			pista.listar();
		}
		
	}

}
