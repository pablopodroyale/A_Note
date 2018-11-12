package test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import entidades.AnoteManager;
import entidades.Lector;
import interfaces.IRepositorios;
import repository.Repositorio_MelodiaDb;
import repository.Repositorio_MelodiaMixto;
import utils.database.DbConfig;
import utils.database.DbManager;
import utils.database.FileLogger;
import utils.database.Ilogger;

public class Test_Anote {

	//private static final String DBCONFIG = "dbconfig.ini";

	public static void main(String[] args) {
		AnoteManager anoteManager;
		//String ROOT = new File("").getAbsolutePath() + File.separator;
		Scanner input = (Scanner) new Scanner(System.in);
		Lector lector = new Lector(input);
		IRepositorios repoMelodia = new Repositorio_MelodiaMixto();
		anoteManager = new AnoteManager(lector, repoMelodia);
		anoteManager.start();
		/*
		DbConfig dbConfig = new DbConfig(ROOT, DBCONFIG);
		DbManager dbManager = new DbManager(dbConfig);
		Ilogger logger = null;
		try {
			logger = new FileLogger(ROOT, "loger.log");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/***
		 * Al construir la instancia de esta clase le pasaremos como parámetro (
		 * inyección de dependencia ) un DBManager (que a su vez recibe y usa DBConfig )
		 * y un TextLogger . La primera nos permitirá crear la conexión contra la base
		 * de datos; la segunda será usada para obtener su URL de conexión y la última
		 * para registrar/informar los errores y/o lo que necesitemos informar en el
		 * log.
		 */
		/*
		IRepositorios repoDb = new Repositorio_MelodiaDb(dbManager, logger);
		anoteManager = new AnoteManager(lector, repoDb);
		
		*/
	}
}
