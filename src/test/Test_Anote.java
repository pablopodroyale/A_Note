package test;

import java.util.Scanner;
import Menu.Menu;
import entidades.AnoteManagger;
import entidades.Lector;
import entidades.MyPattern;
import entidades.Nota;

import entidades.PlayerSingleton;

public class Test_Anote {
	static Scanner input =(Scanner) new Scanner(System.in);
	public static void main(String[] args) {
		Nota nota;
		MyPattern pattern = new MyPattern();
		PlayerSingleton player = PlayerSingleton.getInstance();
		AnoteManagger anoteManagger = new AnoteManagger();
		//Lector lector = new Lector();
		//anoteManagger.listarCanciones();
		anoteManagger.deleteMelodia("test");
		anoteManagger.PedirOpcion(input, pattern);
		
		/*
		 * anoteManagger.createMelody("nombreMelodia"); // Pattern 1 // Nota 1
		 * anoteManagger.addNoteToMelody("nombreMelodia", "C", "5", "q", "n"); // Nota 2
		 * anoteManagger.addNoteToMelody("nombreMelodia", "D", "5", "q", "n"); // Nota 3
		 * anoteManagger.addNoteToMelody("nombreMelodia", "E", "5", "q", "n"); // Nota 4
		 * anoteManagger.addNoteToMelody("nombreMelodia", "C", "5", "q", "n"); // Nota 5
		 * anoteManagger.addNoteToMelody("nombreMelodia", "C", "5", "q", "n"); // Nota 6
		 * anoteManagger.addNoteToMelody("nombreMelodia", "D", "5", "q", "n"); // Nota 7
		 * anoteManagger.addNoteToMelody("nombreMelodia", "E", "5", "q", "n"); // Nota 8
		 * anoteManagger.addNoteToMelody("nombreMelodia", "C", "5", "q", "n"); // Nota 9
		 * anoteManagger.addNoteToMelody("nombreMelodia", "E", "5", "q", "n"); // Nota
		 * 10 anoteManagger.addNoteToMelody("nombreMelodia", "F", "5", "q", "n"); //
		 * Nota 11 anoteManagger.addNoteToMelody("nombreMelodia", "G", "5", "h", "n");
		 * // Nota 12 anoteManagger.addNoteToMelody("nombreMelodia", "E", "5", "q",
		 * "n"); // Nota 13 anoteManagger.addNoteToMelody("nombreMelodia", "F", "5",
		 * "q", "n"); // Nota 14 anoteManagger.addNoteToMelody("nombreMelodia", "G",
		 * "5", "h", "n"); // Nota 15 anoteManagger.addNoteToMelody("nombreMelodia",
		 * "G", "5", "i", "n"); // Nota 16
		 * anoteManagger.addNoteToMelody("nombreMelodia", "A", "5", "i", "n"); // Nota
		 * 17 anoteManagger.addNoteToMelody("nombreMelodia", "G", "5", "i", "n"); //
		 * Nota 18 anoteManagger.addNoteToMelody("nombreMelodia", "F", "5", "i", "n");
		 * // Nota 19 anoteManagger.addNoteToMelody("nombreMelodia", "E", "5", "q",
		 * "n"); // Nota 20 anoteManagger.addNoteToMelody("nombreMelodia", "C", "5",
		 * "q", "n"); // Nota 21 anoteManagger.addNoteToMelody("nombreMelodia", "G",
		 * "5", "i", "n"); // Nota 22 anoteManagger.addNoteToMelody("nombreMelodia",
		 * "A", "5", "i", "n"); // Nota 23
		 * anoteManagger.addNoteToMelody("nombreMelodia", "G", "5", "i", "n"); // Nota
		 * 24 anoteManagger.addNoteToMelody("nombreMelodia", "F", "5", "i", "n"); //
		 * Nota 25 anoteManagger.addNoteToMelody("nombreMelodia", "E", "5", "q", "n");
		 * // Nota 26 anoteManagger.addNoteToMelody("nombreMelodia", "C", "5", "q",
		 * "n"); // Nota 27 anoteManagger.addNoteToMelody("nombreMelodia", "C", "5",
		 * "q", "n"); // Nota 28 anoteManagger.addNoteToMelody("nombreMelodia", "G",
		 * "4", "q", "n"); // Nota 29 anoteManagger.addNoteToMelody("nombreMelodia",
		 * "C", "5", "h", "n"); // Nota 30
		 * anoteManagger.addNoteToMelody("nombreMelodia", "C", "5", "q", "n"); // Nota
		 * 31 anoteManagger.addNoteToMelody("nombreMelodia", "G", "4", "q", "n"); //
		 * Nota 32 anoteManagger.addNoteToMelody("nombreMelodia", "C", "5", "h", "n");
		 * 
		 * anoteManagger.setTempo("nombreMelodia", "99");
		 * anoteManagger.setInstrument("nombreMelodia", "CONTRABASS");
		 * anoteManagger.play("nombreMelodia", pattern, player);
		 */
		// anoteManagger.save("nombreMelodia", pattern, false);
		// anoteManagger.load("nombreMelodia");

		// anoteManagger.updateNombreMelodia("nuevoNombre", "nombreMelodia", false);
		// anoteManagger.updateInstrumentMelodia("nombreMelodia","PIANO");
		//anoteManagger.updateTempoMelodia("nombreMelodia", "100");
		//anoteManagger.updateNota("nombreMelodia","1","D","5", "q", "n");
		
	}
}
