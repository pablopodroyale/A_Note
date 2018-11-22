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
			nota = new Nota(notaVM.getNombre(),notaVM.getOctava(),notaVM.getFigura(),notaVM.getAlteracion());
			nota.setId(notaVM.getId());
			notas.add(nota);
		}
	}

}
