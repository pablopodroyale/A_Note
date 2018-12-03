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
		// pista.setTempo(pistaVM.getTempo());
		Nota nota;
		for (ViewModelNota notaVM : notascompuestas) {
			nota = new Nota(notaVM.getId(), notaVM.getNombre(), notaVM.getOctava(), notaVM.getFigura(),
					notaVM.getAlteracion());
			nota.setId(notaVM.getId());
			notas.add(nota);
		}
		pista.addNotas(notas);
	}

	public static void MapToVm(Pista pista, ViewModelPista pistaVM, ArrayList<ViewModelNota> notascompuestas) {
		pistaVM.setNombre(pista.getNombre());
		pistaVM.setInstrument(pista.getInstrumento());
		// pistaVM.setTempo(pista.getTempo());
		ViewModelNota notaVM;
		for (Nota nota : pista.getNotas()) {
			notaVM = new ViewModelNota(nota.getId(), nota.getNombre(), nota.getOctava(), nota.getFigura(),
					nota.getAlteracion());
			notaVM.setId(notaVM.getId());
			notascompuestas.add(notaVM);
		}
	}

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
}
