package test;

import entidades.AnoteManagger;
import entidades.Nota;
import entidades.PatternSingleton;
import entidades.PlayerSingleton;

public class Test_Melodia {

	public static void main(String[] args) {
		Nota nota;
		PatternSingleton pattern = PatternSingleton.getInstance();
		PlayerSingleton player = PlayerSingleton.getInstance();
		AnoteManagger anoteManagger = new AnoteManagger();
		anoteManagger.createMelody("nombreMelodia");
		// Pattern 1
		// Nota 1
		nota = new Nota("C");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 2
		nota = new Nota("D");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 3
		nota = new Nota("E");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 4
		nota = new Nota("C");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 5
		nota = new Nota("C");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 6
		nota = new Nota("D");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 7
		nota = new Nota("E");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 8
		nota = new Nota("C");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		//
		// Nota 9
		nota = new Nota("E");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 10
		nota = new Nota("F");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 11
		nota = new Nota("G");
		nota.setFigura("h");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 12
		nota = new Nota("E");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 13
		nota = new Nota("F");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 14
		nota = new Nota("G");
		nota.setFigura("h");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);

		// Nota 15
		nota = new Nota("G");
		nota.setFigura("i");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 16
		nota = new Nota("A");
		nota.setFigura("i");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 17
		nota = new Nota("G");
		nota.setFigura("i");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 18
		nota = new Nota("F");
		nota.setFigura("i");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 19
		nota = new Nota("E");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 20
		nota = new Nota("C");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);

		// Nota 21
		nota = new Nota("G");
		nota.setFigura("i");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 22
		nota = new Nota("A");
		nota.setFigura("i");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 23
		nota = new Nota("G");
		nota.setFigura("i");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 24
		nota = new Nota("F");
		nota.setFigura("i");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 25
		nota = new Nota("E");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 26
		nota = new Nota("C");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);

		// Nota 27
		nota = new Nota("C");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 28
		nota = new Nota("G");
		nota.setFigura("q");
		nota.setOctava("4");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 29
		nota = new Nota("C");
		nota.setFigura("h");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);

		// Nota 30
		nota = new Nota("C");
		nota.setFigura("q");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 31
		nota = new Nota("G");
		nota.setFigura("q");
		nota.setOctava("4");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);
		// Nota 32
		nota = new Nota("C");
		nota.setFigura("h");
		nota.setOctava("5");
		anoteManagger.addNoteToMelody("nombreMelodia", nota);

		anoteManagger.setTempo("nombreMelodia", "100");
		anoteManagger.setInstrument("nombreMelodia", "CONTRABASS");
		anoteManagger.play("nombreMelodia", pattern, player);

	}
}
