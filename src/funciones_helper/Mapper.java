package funciones_helper;

import java.util.ArrayList;

import entidades.Pista;
import entidades.Cancion;
import entidades.Nota;
import viewmodels.ViewModelPista;
import viewmodels.ViewModelCancion;
import viewmodels.ViewModelNota;

public class Mapper {

	/***
	 * Pasa de viewModelPista a pista
	 * 
	 * @param pistaVM
	 * @param notascompuestas
	 * @param pista
	 * @param notas
	 */
	public static void MapPista(ViewModelPista pistaVM, ArrayList<ViewModelNota> notascompuestas, Pista pista,
			ArrayList<Nota> notas) {
		pista.setNombre(pistaVM.getNombre());
		pista.setInstrumento(pistaVM.getInstrument());
		Nota nota;
		for (ViewModelNota notaVM : notascompuestas) {
			nota = new Nota(notaVM.getId(), notaVM.getNombre(), notaVM.getOctava(), notaVM.getFigura(),
					notaVM.getAlteracion());
			nota.setId(notaVM.getId());
			notas.add(nota);
		}
		pista.addNotas(notas);
	}

	/***
	 * pasa de pista a ViewModel
	 * 
	 * @param pista
	 * @param pistaVM
	 * @param notascompuestas
	 */
	public static void MapToVm(Pista pista, ViewModelPista pistaVM, ArrayList<ViewModelNota> notascompuestas) {
		pistaVM.setNombre(pista.getNombre());
		pistaVM.setInstrument(pista.getInstrumento());
		ViewModelNota notaVM;
		for (Nota nota : pista.getNotas()) {
			notaVM = new ViewModelNota(nota.getId(), nota.getNombre(), nota.getOctava(), nota.getFigura(),
					nota.getAlteracion());
			notaVM.setId(notaVM.getId());
			notascompuestas.add(notaVM);
		}
	}

	/***
	 * Mapea el VM a Cancion
	 * 
	 * @param cancionVM
	 * @param cancion
	 */
	public static void MapCancion(ViewModelCancion cancionVM, Cancion cancion) {
		cancion.setNombreCancion(cancionVM.getNombreCancion());
		cancion.setTempo(cancionVM.getTempo());
		Pista pista;
		ArrayList<ViewModelPista> pistas = cancionVM.getPistas();
		ArrayList<Nota> notas;
		for (ViewModelPista viewModelPista : pistas) {
			pista = new Pista();
			pista.setNombreCancion(cancionVM.getNombreCancion());
			notas = new ArrayList<>();
			MapPista(viewModelPista, viewModelPista.getNotas(), pista, notas);
			cancion.addPista(pista);
		}
	}

	public static void MapToVm(Cancion cancion, ViewModelCancion cancionVM) {
		cancionVM.setNombreCancion(cancion.getNombreCancion());
		cancionVM.setTempo(cancion.getTempo());
		ViewModelPista pistaVM;
		ArrayList<Pista> pistas = cancion.getPistas();
		ArrayList<ViewModelNota> notasVM;
		for (Pista pista : pistas) {
			pistaVM = new ViewModelPista();
			pistaVM.setNombreCancion(cancion.getNombreCancion());
			pistaVM.setTempo(cancion.getTempo());
			notasVM = new ArrayList<>();
			MapToVm(pista, pistaVM);
			cancionVM.addPista(pistaVM);
		}

	}

	private static void MapToVm(Pista pista, ViewModelPista pistaVM) {
		pistaVM.setNombre(pista.getNombre());
		pistaVM.setInstrument(pista.getInstrumento());
		ViewModelNota notaVM;
		for (Nota nota : pista.getNotas()) {
			notaVM = new ViewModelNota(nota.getId(), nota.getNombre(), nota.getOctava(), nota.getFigura(),
					nota.getAlteracion());
			notaVM.setId(notaVM.getId());
			pistaVM.addNota(notaVM);
		}
	}

}
