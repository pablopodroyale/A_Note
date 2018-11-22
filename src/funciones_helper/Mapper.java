package funciones_helper;

import java.util.ArrayList;

import entidades.Melodia;
import entidades.Nota;
import viewmodels.ViewModelMelodia;
import viewmodels.ViewModelNota;

public class Mapper {

	public static void Map(ViewModelMelodia modelMelodia, ArrayList<ViewModelNota> notascompuestas, Melodia melodia,
			ArrayList<Nota> notas) {
		melodia.setNombre(modelMelodia.getNombre());
		melodia.setInstrument(modelMelodia.getInstrument());
		melodia.setTempo(modelMelodia.getTempo());
		Nota nota;
		for (ViewModelNota notaVM : notascompuestas) {
			nota = new Nota(notaVM.getId(), notaVM.getNombre(), notaVM.getOctava(), notaVM.getFigura(),
					notaVM.getAlteracion());
			nota.setId(notaVM.getId());
			notas.add(nota);
		}
	}

	public static void MapToVm(Melodia melodia, ViewModelMelodia modelMelodia,
			ArrayList<ViewModelNota> notascompuestas) {
		modelMelodia.setNombre(melodia.getNombre());
		modelMelodia.setInstrument(melodia.getInstrument());
		modelMelodia.setTempo(melodia.getTempo());
		ViewModelNota notaVM;
		for (Nota nota : melodia.getNotas()) {
			notaVM = new ViewModelNota(nota.getId(), nota.getNombre(), nota.getOctava(), nota.getFigura(),
					nota.getAlteracion());
			notaVM.setId(notaVM.getId());
			notascompuestas.add(notaVM);
		}
	}

}
