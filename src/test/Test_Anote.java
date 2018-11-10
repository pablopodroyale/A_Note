package test;

import java.util.Scanner;
import entidades.AnoteManager;
import entidades.Lector;
import interfaces.IRepositorios;
import repository.Repositorio_Melodia;

public class Test_Anote {
	
	public static void main(String[] args) {
		Scanner input =(Scanner) new Scanner(System.in);
		Lector lector = new Lector(input);
		IRepositorios repoMelodia = new Repositorio_Melodia();
		AnoteManager anoteManagger = new AnoteManager(lector, repoMelodia);
		anoteManagger.start();
		
	}
}
