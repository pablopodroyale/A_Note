package test;

import java.io.File;
import java.io.IOException;

import entidades.AnoteManager;
import entidades.VistaConsola;
import interfaces.RepoMelodias;
import interfaces.Vista;
import repository.RepoMelodiasMixto;
import repository.Repositorio_MelodiaDb;
import utils.database.db.DBConfig;
import utils.database.db.DBManager;
import utils.database.logger.FileLogger;
import utils.database.logger.ILogger;

public class Test_Anote {

	public static void main(String[] args) {
		Vista vista = new VistaConsola();
		RepoMelodias repoMelodia = new RepoMelodiasMixto();
		AnoteManager anoteManager = new AnoteManager(vista, repoMelodia);
		anoteManager.start();
	}
}
