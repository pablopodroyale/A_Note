package entidades;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import funciones_helper.Mapper;
import interfaces.Vista;
import interfaces.Exportador;
import interfaces.RepoMelodias;
import opciones.OpcionConsola_Componer;
import opciones.OpcionConsola_Delete_Cancion;
import opciones.OpcionConsola_Details_Cancion;
import opciones.OpcionConsola_Details_Canciones;
import opciones.OpcionConsola_Editar_Cancion;
import opciones.OpcionConsola_Reproducir_Cancion;
import opciones.Opcion;
import opciones.OpcionConsola_Update_NombreCancion;
import opciones.OpcionConsola_Update_Tempo;
import repository.RepoCancionesMixto;
import repository.Repositorio_CancionDb;
import viewmodels.ViewModelCancion;

public class AnoteManager {
	private RepoMelodias repoMelodia;
	private LinkedHashMap<Integer, Opcion> opciones;
	private Vista vista;
	private Exportador exportador;

	public AnoteManager(Vista vista, RepoMelodias repositorio, Exportador exportador) {
		this.repoMelodia = repositorio;
		this.opciones = new LinkedHashMap<>();
		cargarOpciones();
		this.vista = vista;
		this.exportador = exportador;
	}

	private void cargarOpciones() {
		opciones.put(1, new OpcionConsola_Componer());
		opciones.put(2, new OpcionConsola_Details_Canciones());
		opciones.put(3, new OpcionConsola_Details_Cancion());
		opciones.put(4, new OpcionConsola_Delete_Cancion());
		opciones.put(5, new OpcionConsola_Reproducir_Cancion());
	}

	public void save(ViewModelCancion cancionVM) {
		repoMelodia.save(cancionVM);
	}

	public void play(String nombreMelodia, PlayerSingleton player) {
		try {
			repoMelodia.play(nombreMelodia, player);
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage());
		}
	}

	public void updateNombreMelodia(String nombreaAnteriorMelodia, String nuevoNombre, boolean append) {
		repoMelodia.updateNombreMelodia(nombreaAnteriorMelodia, nuevoNombre);
	}

	/*
	 * public void updateTempo(String nombreMelodia, String tempo) { try {
	 * Funcion_Helper.validarString(nombreMelodia);
	 * Funcion_Helper.validarString(tempo); repoDb.updateTempo(nombreMelodia,
	 * tempo); } catch (RuntimeException re) { throw new
	 * RuntimeException(re.getMessage()); }
	 * 
	 * }
	 */

	public void exportar(ViewModelCancion cancionVM) {
		Cancion cancion = new Cancion();
		Mapper.MapCancion(cancionVM, cancion);
		this.exportador.exportar(cancion);
	}

	public void start() {
		try {
			vista.EjecutarOpcion(this, repoMelodia, opciones);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public void deleteMelodia(String nombreCancion) {
		try {
			repoMelodia.deleteCancion(nombreCancion);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public void listarNotasDeMelodia(String nombreMelodia) {
		repoMelodia.listarNotas(nombreMelodia);
	}

	/*
	 * public Cancion detallesCancion(String nombreMelodia) { Cancion cancion =
	 * null; try { cancion = repoMelodia.detallesCancion(nombreMelodia);
	 * //repoMelodia.listarNotas(nombreMelodia); } catch (RuntimeException re) {
	 * throw new RuntimeException(re.getMessage()); } return null; }
	 */

	public ArrayList<String> getCanciones() {
		return repoMelodia.getCanciones();
	}

	public Cancion loadCancion(String nombre) {
		return repoMelodia.loadCancion(nombre);
	}
}
