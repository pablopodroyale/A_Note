package test;

import java.io.File;
import java.io.IOException;

import entidades.AnoteManager;
import entidades.ExportadorMusicXml;
import entidades.VistaConsola;
import interfaces.Exportador;
import interfaces.RepoMelodias;
import interfaces.Vista;
import repository.RepoCancionesMixto;
import repository.Repositorio_CancionDb;
import utils.database.db.DBConfig;
import utils.database.db.DBManager;
import utils.database.logger.FileLogger;
import utils.database.logger.ILogger;

public class Test_Anote {

	public static void main(String[] args) {
		String DBCONFIG = "dbconfig.ini";
		String ROOT = new File("").getAbsolutePath() + File.separator;
		String file = ROOT + DBCONFIG;
		Exportador exportador = new ExportadorMusicXml();
		AnoteManager anoteManager;
		DBManager dbManager = null;
		Vista vista = new VistaConsola();
		ILogger logger = null;
		try {
			DBConfig dbConfig = new DBConfig(file,file);
			dbManager = new DBManager(dbConfig);
			logger = new FileLogger(ROOT, "loger.log");
			RepoMelodias repoMelodia = new Repositorio_CancionDb(dbManager, logger);
			anoteManager = new AnoteManager(vista, repoMelodia, exportador);
			anoteManager.start();
		} catch (SecurityException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
