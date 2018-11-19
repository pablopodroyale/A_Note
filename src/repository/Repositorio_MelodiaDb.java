package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import entidades.Melodia;
import entidades.Nota;
import entidades.PlayerSingleton;
import funciones_helper.Contador;
import interfaces.RepoMelodias;
import utils.database.db.DBManager;
import utils.database.logger.ILogger;;

public class Repositorio_MelodiaDb implements RepoMelodias {
	private static final String ERROR_NULL = "Error, el logger y el dbManager no deben ser nulos";
	private static final String ERROR_CREACION_TABLA = "Error, no se pudo crear la tabla melodia";
	private static final Object NOMBRE = "nombre";
	private static final Object NOMBRE_MELODIA = "nombreMelodia";
	private static final Object INSTRUMENTO = "instrumento";
	private static final Object TEMPO = "tempo";
	private static final Object NOMBRE_NOTA = "nombreNota";
	private static final Object OCTAVA = "octava";
	private static final Object FIGURA = "figura";
	private static final Object ALTERACION = "alteracion";
	private static final Object ID = "Id";
	private ILogger logger;
	private Connection conn;
	//private LinkedHashMap<String, Melodia> melodias;
	private Contador contador;

	/***
	 * Al construir la instancia de esta clase le pasaremos como parámetro (
	 * inyección de dependencia ) un DBManager (que a su vez recibe y usa DBConfig )
	 * y un TextLogger . La primera nos permitirá crear la conexión contra la base
	 * de
	 * 
	 * @param dbManager
	 * @param logger
	 */
	public Repositorio_MelodiaDb(DBManager dbManager, ILogger logger) {
		/*
		 * if (dbManager == null || logger == null) { throw new
		 * IllegalArgumentException(ERROR_NULL); }
		 */
		//this.melodias = new LinkedHashMap<>();
		this.contador = new Contador();
		this.logger = logger;
		try {
			this.conn = dbManager.getNewConnection(dbManager.getEnvironment());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		crearTablas();
	}

	// Seguir salvando las notas
	@Override
	public void saveMelodia(String nombreMelodia, boolean append) {
		/*
		 * if (melodia != null) {
		 * 
		 * String consultaInsert =
		 * String.format("INSERT INTO [dbo].[melodias]([%s][%s][%s])VALUES(?,?;?)",
		 * NOMBRE, INSTRUMENTO, TEMPO); String consultaUpdate = String.format(
		 * "UPDATE [dbo].[melodias m]\r\n" + "   SET %s = %s\r\n" + ",%s = %s\r\n" +
		 * ",%s = %s\r\n" + " WHERE m.nombreMelodia = %s", NOMBRE_MELODIA,
		 * melodia.getNombre(), TEMPO, melodia.getInstrument(), INSTRUMENTO,
		 * melodia.getInstrument(), nombreMelodia); String insertOrUpdate =
		 * String.format(
		 * "IF NOT EXISTS (SELECT nombreMelodia FROM melodias WHERE (m.nombreMelodia = %s))\r\n"
		 * + "BEGIN %s" + "ELSE\r\n" + "BEGIN\r\n" + "%s END", nombreMelodia,
		 * consultaInsert, consultaUpdate);
		 * 
		 * PreparedStatement statement = null; try { statement =
		 * conn.prepareStatement(insertOrUpdate); statement.setString(1,
		 * melodia.getNombre()); statement.setString(2, melodia.getInstrument());
		 * statement.setString(3, melodia.getTempo()); statement.executeUpdate();
		 * melodia.llenarNotas(notas); saveNotas(notas, nombreMelodia, false); } catch
		 * (SQLException e) { logSQLERROR(e); throw new
		 * IllegalArgumentException(ERROR_MELODIA_EXISTENTE); } }
		 */

	}

	/*
	 * public void saveNotas(ArrayList<Nota> notas, String nombreMelodia, boolean
	 * append) { PreparedStatement statement = null; try {
	 * deleteNotasFromMelodiaInDb(nombreMelodia); statement =
	 * conn.prepareStatement(String.format(
	 * "INSERT INTO notas (%s, %s, %s, %s,%s) VALUES (?, ?, ?, ?,?) WHERE notas.nombreMelodia = %s "
	 * , NOMBRE_MELODIA, NOMBRE_NOTA, OCTAVA, FIGURA, ALTERACION, nombreMelodia));
	 * conn.setAutoCommit(false); for (int i = 0; i < notas.size(); i++) { Nota nota
	 * = notas.get(i); statement.setString(1, nota.getNombre());
	 * statement.setString(2, nota.getOctava()); statement.setString(3,
	 * nota.getFigura()); statement.setString(4, nota.getAlteracion());
	 * statement.addBatch(); } statement.executeBatch(); conn.commit();
	 * 
	 * } catch (SQLException e) { logSQLERROR(e); } }
	 */

	@Override
	public void updateNombreMelodia(String nombreMelodia, String nuevoNombre) {
		String consulta = String.format(
				"UPDATE melodias \r\n" + "SET nombreMelodia = '%s'\r\n" + "WHERE nombreMelodia = '%s'\r\n"
						+ "UPDATE notas \r\n" + "SET nombreMelodia = '%s'\r\n" + "WHERE nombreMelodia = '%s'",
				nuevoNombre, nombreMelodia, nuevoNombre, nombreMelodia);
		try {
			PreparedStatement statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
		}
	}

	/*
	public void loadNotas(ArrayList<Nota> notas, String nombreMelodia) {
		String consulta = String.format("SELECT [%s],[%s],[%s],[%s] FROM [dbo].[notas] WHERE nombreMelodia = %s",
				NOMBRE_MELODIA, OCTAVA, FIGURA, ALTERACION, nombreMelodia);
		try {
			ResultSet rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				notas.add(new Nota(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}

	}
	*/

	/*
	@Override
	public Melodia loadMelodia(String nombreMelodia) {
		Melodia melodia = null;
		ArrayList<Nota> notas = new ArrayList<>();
		String consulta = String.format(
				"SELECT [nombreMelodia],[instrumento],[tempo] FROM [dbo].[melodias] WHERE nombreMelodia = %s",
				nombreMelodia);
		try {
			ResultSet rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				melodia = new Melodia(rs.getString(1));
				melodia.setInstrument(rs.getString(2));
				melodia.setTempo(rs.getString(3));
				loadNotas(notas, nombreMelodia);
				melodia.llenarNotas(notas);
			}
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}
		return melodia;
	}
	*/

	@Override
	public void updateTempo(String nombreMelodia, String tempo) {
		String consulta = String.format("UPDATE melodias\r\n" + "SET tempo = '%s'\r\n" + "WHERE nombreMelodia = '%s'",
				tempo, nombreMelodia);
		try {
			PreparedStatement statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
		}
	}

	@Override
	public void updateInstrumento(String nombreMelodia, String instrumento) {
		String consulta = String.format(
				"UPDATE melodias\r\n" + "SET instrumento = '%s'\r\n" + "WHERE nombreMelodia = '%s'", instrumento,
				nombreMelodia);
		try {
			PreparedStatement statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
		}
	}

	@Override
	public void deleteMelodia(String nombreCancion) {
		String consulta = String.format("DELETE melodias \r\n" + "WHERE nombreMelodia = '%s'\r\n" + "DELETE notas\r\n"
				+ "WHERE nombreMelodia = '%s'", nombreCancion, nombreCancion);
		try {
			PreparedStatement statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
		}
	}

	private void logSQLERROR(SQLException e) {
		logger.logSevere(e.getSQLState());
		logger.logSevere(e.getMessage());
	}

	public void crearTablas() {
		String consulta = String.format("if object_id('melodias', 'U') is null \r\n" + 
				"CREATE TABLE melodias (\r\n" + 
				"	nombreMelodia nvarchar(50) NOT NULL,\r\n" + 
				"    instrumento nvarchar(50) NOT NULL,\r\n" + 
				"    tempo nvarchar(50) NOT NULL,\r\n" + 
				"    PRIMARY KEY (nombreMelodia)\r\n" + 
				");\r\n" + 
				"if object_id('notas', 'U') is null \r\n" + 
				"CREATE TABLE notas (\r\n" + 
				"	Id int NOT NULL,\r\n" + 
				"	nombreMelodia nvarchar(50) NOT NULL,\r\n" + 
				"	nombreNota nvarchar(50) NOT NULL,\r\n" + 
				"    octava nvarchar(50) NOT NULL,\r\n" + 
				"	figura nvarchar(50) NOT NULL,\r\n" + 
				"	alteracion nvarchar(50) NOT NULL,\r\n" + 
				"	PRIMARY KEY (Id, nombreMelodia)\r\n" + 
				");", NOMBRE,
				INSTRUMENTO, TEMPO);
		Statement statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new RuntimeException(ERROR_CREACION_TABLA);
		}
		try {
			statement.execute(consulta);
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new RuntimeException(ERROR_CREACION_TABLA);
		}
	}

	private ArrayList<String> getCancionesFromDb() {
		ArrayList<String> canciones = new ArrayList<>();
		String consulta = String.format("SELECT [%s] FROM [dbo].[melodias]", NOMBRE_MELODIA);
		try {
			ResultSet rs = conn.prepareStatement(consulta).executeQuery();
			if (rs.next()) {
				while (rs.next()) {
					canciones.add(rs.getString(1));
				}
			}

		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}
		return canciones;
	}

	public void closeConnection() throws SQLException {

	}

	@Override
	public void close() throws SQLException {
		if (!conn.isClosed()) {
			conn.close();
		}
	}

	@Override
	public void createMelodia(String nombreMelodia, String instrumento, String tempo) {
		PreparedStatement statement = null;
		String consulta = String.format("INSERT INTO [dbo].[melodias](%s,%s,%s)"
				+ "  VALUES(?,?,?)", NOMBRE_MELODIA, INSTRUMENTO, TEMPO);
		try {
			statement = conn.prepareStatement(consulta);
			statement.setString(1, nombreMelodia);
			statement.setString(2, instrumento);
			statement.setString(3, tempo);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	@Override
	public void updateNota(String nombreMelodia, String idNota, String nombreNota, String octava, String figura,
			String alteracion) {
		String consulta = String.format(
				"UPDATE notas \r\n" + "SET nombreNota = %s \r\n" + "	,octava = %s \r\n" + "	,figura = %s \r\n"
						+ "	,alteracion = %s \r\n" + "WHERE nombreMelodia = %s ",
				nombreNota, octava, figura, alteracion, nombreMelodia);
		try {
			PreparedStatement statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public void removeNotaById(String nombreMelodia, String idNota) {
		String consulta = String.format(
				"DELETE notas\r\n" + "WHERE notas.nombreNota = '%s' AND notas.nombreMelodia = '%s'", idNota,
				nombreMelodia);
		try {
			PreparedStatement statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void listarNotas(String nombreMelodia) {
		ResultSet rs = null;
		String consulta = String.format("SELECT n.Id, n.nombreNota, n.octava, n.figura, n.alteracion \r\n" + "FROM notas n"
				+ " WHERE n.nombreMelodia = '%s'", nombreMelodia);
		try {
			System.out.print("Id,nombre,Octava,alteración   ");
			rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				System.out.println("");
				System.out.print(rs.getString("Id")) ;
				System.out.print(rs.getString("nombreNota"));
				System.out.print(rs.getString("octava"));
				System.out.print(rs.getString("figura"));
				System.out.print(rs.getString("alteracion"));
				
				
			}
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public void listarCanciones() {
		ResultSet rs = null;
		String consulta = "SELECT nombreMelodia FROM melodias";
		try {
			rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("nombreMelodia"));
			}
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	@Override
	public void detallesMelodia(String nombreMelodia) {
		ResultSet rs = null;
		String consulta = String.format("SELECT m.nombreMelodia, m.instrumento , m.tempo\r\n" + "FROM melodias m"
				+ " WHERE m.nombreMelodia = '%s'", nombreMelodia);
		try {
			rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("nombreMelodia"));
				System.out.println(rs.getString("instrumento"));
				System.out.println(rs.getString("tempo"));
			}
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	@Override
	public void play(String nombreMelodia, PlayerSingleton player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNote(String nombreMelodia, String nombreNota, String octava, String figura, String alteracion) {
		String consulta = String.format(
				"IF (SELECT nombreMelodia FROM melodias WHERE nombreMelodia = '%s') IS NOT NULL\r\n" + 
				"BEGIN\r\n" + 
				"INSERT INTO notas (nombreMelodia,\r\n" + 
				"			nombreNota,\r\n" + 
				"			octava,\r\n" + 
				"			figura, \r\n" + 
				"			alteracion)\r\n" + 
				"		VALUES('%s','%s','%s','%s','%s');\r\n" + 
				"END", nombreMelodia, nombreNota,octava, figura, alteracion);
		try {
			PreparedStatement statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
			contador.incrementar();
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new RuntimeException(e.getMessage());
		}

	}

}
