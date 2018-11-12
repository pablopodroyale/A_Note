package repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entidades.Melodia;
import entidades.Nota;
import interfaces.IRepositorios;
import utils.database.DbManager;
import utils.database.Ilogger;

public class Repositorio_MelodiaDb implements IRepositorios {
	private static final String ERROR_NULL = "Error, el logger y el dbManager no deben ser nulos";
	private static final String ERROR_CREACION_TABLA = "Error, no se pudo crear la tabla melodia";
	
	private Ilogger logger;
	private Connection conn;

	/***
	 * Al construir la instancia de esta clase le pasaremos como parámetro (
	 * inyección de dependencia ) un DBManager (que a su vez recibe y usa DBConfig )
	 * y un TextLogger . La primera nos permitirá crear la conexión contra la base
	 * de
	 * 
	 * @param dbManager
	 * @param logger
	 */
	public Repositorio_MelodiaDb(DbManager dbManager, Ilogger logger) {
		if (dbManager == null || logger == null) {
			throw new IllegalArgumentException(ERROR_NULL);
		}
		this.logger = logger;
		try {
			this.conn = dbManager.getNewConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void createMelodia(String nombreMelodia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveMelodia(String nombreMelodia, boolean append) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveNotas(ArrayList<Nota> notas, String nombreMelodia, boolean append) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMelodiaInIni(Melodia melodia, String nombreAnterior, boolean append) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSection(String nombreAnterior, String nuevoNombre) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMelodiaParams(Melodia melodia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadNotas(ArrayList<Nota> notas, String nombreMelodia) {
		// TODO Auto-generated method stub

	}

	@Override
	public Melodia loadMelodia(String nombreMelodia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsMelodia(String nombreMelodia) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Melodia getMelodia(String nombreMelodia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateNota(String nombreMelodia, String idNota, String nombreNota, String octava, String figura,
			String alteracion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNombreMelodia(String nombreMelodia, String nuevoNombre) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeNotaById(String nombreMelodia, String idNota) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMelodiaInFile(String nombreCancion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listarNotas(String nombreMelodia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listarCanciones() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cargarCanciones() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll() {
		// TODO Auto-generated method stub

	}

	private void logSQLERROR(SQLException ex) {
		logger.logSevere(ex.getMessage());
	}

	public void crearTabla()  {
		String consulta = "	CREATE TABLE melodia (nombre VARCHAR(50), nombre VARCHAR(50), instrumento VARCHAR(50), tiempo VARCHAR(30))";
		Statement statement = null;
		try {
			statement = conn. createStatement ();
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new RuntimeException(ERROR_CREACION_TABLA);
		}
		try {
			statement. execute ( consulta );
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new RuntimeException(ERROR_CREACION_TABLA);
		}
	}

}
