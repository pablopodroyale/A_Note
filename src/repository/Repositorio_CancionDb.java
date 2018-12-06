package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import entidades.Pista;
import entidades.Cancion;
import entidades.Nota;
import entidades.PlayerSingleton;
import funciones_helper.Contador;
import funciones_helper.Mapper;
import interfaces.RepoMelodias;
import utils.database.db.DBManager;
import utils.database.logger.ILogger;
import viewmodels.ViewModelCancion;

public class Repositorio_CancionDb implements RepoMelodias {
	private static final String ERROR_NULL = "Error, el logger y el dbManager no deben ser nulos";
	private static final String ERROR_CREACION_TABLA = "Error, no se pudo crear la tabla melodia";
	private static final Object NOMBRE_CANCION = "nombreCancion";
	private static final Object TEMPO = "tempo";
	private ILogger logger;
	private Connection conn;
	// private LinkedHashMap<String, Melodia> melodias;
	private Contador contador;
	// private Melodia melodia;

	/***
	 * Al construir la instancia de esta clase le pasaremos como parámetro (
	 * inyección de dependencia ) un DBManager (que a su vez recibe y usa DBConfig )
	 * y un TextLogger . La primera nos permitirá crear la conexión contra la base
	 * de
	 * 
	 * @param dbManager
	 * @param logger
	 */
	public Repositorio_CancionDb(DBManager dbManager, ILogger logger) {
		// this.melodia = null;
		this.contador = new Contador();
		this.logger = logger;
		try {
			this.conn = dbManager.getNewConnection(dbManager.getEnvironment());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		crearTablas();
	}

	@Override
	public void save(ViewModelCancion cancionVM) {
		Cancion cancion = new Cancion();
		Mapper.MapCancion(cancionVM, cancion);
		deleteCancion(cancion.getNombreCancion());
		String consulta = String.format("INSERT canciones (%s,%s)\r\n" + "VALUES ('%s','%s')", NOMBRE_CANCION, TEMPO,
				cancion.getNombreCancion(), cancion.getTempo());
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
		}
		ArrayList<Pista> pistas = cancion.getPistas();
		for (Pista pista : pistas) {
			savePista(pista);
			saveNotas(pista.getNotas(), cancion.getNombreCancion(), false);
		}
	}

	public void savePista(Pista pista) {
		int IdCancion = 0;
		PreparedStatement statement;
		String consultaID = String.format("SELECT IDCancion FROM canciones WHERE nombreCancion = '%s'",
				pista.getNombreCancion());
		ResultSet rs;
		try {
			rs = conn.prepareStatement(consultaID).executeQuery();
			while (rs.next()) {
				IdCancion = rs.getInt(1);
			}
		} catch (SQLException e1) {
			logSQLERROR(e1);
		}

		String consulta = String.format(
				"INSERT pistas(IdCancion,nombreCancion,nombrePista,instrumento)\r\n" + "VALUES(%s,'%s','%s','%s')",
				IdCancion, pista.getNombreCancion(), pista.getNombre(), pista.getInstrumento());

		try {
			statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
		}
	}

	public void saveNotas(ArrayList<Nota> notas, String nombreCancion, boolean append) {
		int pistaID = 0;
		PreparedStatement statement;
		String consultaID = String.format("SELECT pistaID FROM pistas WHERE nombreCancion = '%s'", nombreCancion);
		ResultSet rs;
		try {
			rs = conn.prepareStatement(consultaID).executeQuery();
			while (rs.next()) {
				pistaID = rs.getInt(1);
			}
		} catch (SQLException e1) {
			logSQLERROR(e1);
		}
		try {
			String consulta = "INSERT notas(PistaID,nombreCancion,nombreNota,octava,figura,alteracion)\r\n"
					+ "VALUES(?,?,?,?,?,?)";
			statement = conn.prepareStatement(consulta);
			conn.setAutoCommit(false);
			for (int i = 0; i < notas.size(); i++) {
				Nota nota = notas.get(i);
				statement.setInt(1, pistaID);
				statement.setString(2, nombreCancion);
				statement.setString(3, nota.getNombre());
				statement.setString(4, nota.getOctava());
				statement.setString(5, nota.getFigura());
				statement.setString(6, nota.getAlteracion());
				statement.addBatch();
			}
			statement.executeBatch();
			conn.commit();

		} catch (SQLException e) {
			logSQLERROR(e);
		}
	}

	@Override
	public void updateNombreMelodia(String nombreAnterior, String nuevoNombre) {
		String consulta = String.format(
				"\r\n" + 
				"ALTER TABLE canciones NOCHECK CONSTRAINT ALL\r\n" + 
				"ALTER TABLE pistas NOCHECK CONSTRAINT ALL\r\n" + 
				"ALTER TABLE notas NOCHECK CONSTRAINT ALL\r\n" + 
				"UPDATE notas \r\n" + 
				"SET nombreCancion = '%s'\r\n" + 
				"WHERE nombreCancion = '%s'\r\n" + 
				"UPDATE pistas\r\n" + 
				"SET nombreCancion = '%s'\r\n" + 
				"WHERE nombreCancion = '%s'\r\n" + 
				"UPDATE canciones\r\n" + 
				"SET nombreCancion = '%s'\r\n" + 
				"WHERE nombreCancion = '%s'\r\n" + 
				"ALTER TABLE canciones WITH CHECK CHECK CONSTRAINT ALL\r\n" + 
				"ALTER TABLE pistas WITH CHECK CHECK CONSTRAINT ALL\r\n" + 
				"ALTER TABLE notas WITH CHECK CHECK CONSTRAINT ALL",
				nuevoNombre, nombreAnterior, nuevoNombre, nombreAnterior,nuevoNombre, nombreAnterior);
		try {
			PreparedStatement statement = conn.prepareStatement(consulta);
			statement.executeUpdate();
		} catch (SQLException e) {
			logSQLERROR(e);
		}
	}

	public void loadNotas(ArrayList<Nota> notas, String nombreCancion, String nombrePista, Pista pista) {
		Nota nota;
		String consulta = String.format("SELECT n.IdNota,n.nombreNota,n.octava,n.figura,n.alteracion\r\n" + 
				"FROM notas n INNER JOIN pistas p \r\n" + 
				"ON n.PistaID = p.PistaID\r\n" + 
				"WHERE p.nombrePista = '%s'  AND n.nombreCancion = '%s'"
				+ "ORDER BY n.IdNota ASC", nombrePista, nombreCancion);
		try {
			ResultSet rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				nota = new Nota(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				notas.add(nota);
			}
			pista.addNotas(notas);
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	@Override
	public Cancion loadCancion(String nombreCancion) {
		Cancion cancion = null;
		String consulta = String.format("SELECT c.IdCancion,c.nombreCancion,c.tempo\r\n" + "FROM canciones c\r\n"
				+ "WHERE c.nombreCancion = '%s'", nombreCancion);
		try {
			ResultSet rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				cancion = new Cancion();
				cancion.setId(rs.getInt(1));
				cancion.setNombreCancion(rs.getString(2));
				cancion.setTempo(rs.getString(3));
		}
			loadPistas(cancion);
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}
		return cancion;
	}

	private void loadPistas(Cancion cancion) {
		Pista pista = null;
		ArrayList<Nota> notas;
		String consulta = String.format(
				"SELECT p.nombrePista,p.instrumento, p.pistaID FROM pistas p\r\n" + "WHERE p.nombreCancion = '%s'",
				cancion.getNombreCancion());
		try {
			ResultSet rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				pista = new Pista(rs.getString(1));
				pista.setInstrumento(rs.getString(2));
				pista.setPistaID(rs.getInt(3));
				cancion.addPista(pista);
				notas = new ArrayList<>();
				loadNotas(notas, cancion.getNombreCancion(), pista.getNombre(), pista);
			}
		} catch (SQLException e) {
			logSQLERROR(e);
		}

	}

	/*
	 * @Override public void updateTempo(String nombreMelodia, String tempo) {
	 * String consulta = String.format("UPDATE melodias\r\n" +
	 * "SET tempo = '%s'\r\n" + "WHERE nombreMelodia = '%s'", tempo, nombreMelodia);
	 * try { PreparedStatement statement = conn.prepareStatement(consulta);
	 * statement.executeUpdate(); } catch (SQLException e) { logSQLERROR(e); } }
	 */

	/*
	 * @Override public void updateInstrumento(String nombreMelodia, String
	 * instrumento) { String consulta = String.format( "UPDATE melodias\r\n" +
	 * "SET instrumento = '%s'\r\n" + "WHERE nombreMelodia = '%s'", instrumento,
	 * nombreMelodia); try { PreparedStatement statement =
	 * conn.prepareStatement(consulta); statement.executeUpdate(); } catch
	 * (SQLException e) { logSQLERROR(e); } }
	 */

	@Override
	public void deleteCancion(String nombreCancion) {
		String consulta = String.format(
				"DELETE notas \r\n" + "WHERE nombreCancion = '%s'\r\n" + "DELETE pistas\r\n"
						+ "WHERE nombreCancion = '%s'\r\n" + "DELETE canciones\r\n" + "WHERE nombreCancion = '%s'",
				nombreCancion, nombreCancion, nombreCancion);
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
		String consulta = "if object_id('canciones', 'U') is null \r\n" + "CREATE TABLE canciones (\r\n"
				+ "	IdCancion int IDENTITY(1,1) NOT NULL,\r\n" + "	nombreCancion nvarchar(250) UNIQUE NOT NULL,\r\n"
				+ "	tempo nvarchar(50) NOT NULL,\r\n" + "    PRIMARY KEY (IdCancion)\r\n" + ");\r\n"
				+ "if object_id('pistas', 'U') is null \r\n" + "CREATE TABLE pistas (\r\n"
				+ "	PistaID int IDENTITY(1,1) NOT NULL,\r\n" + "	IdCancion int NOT NULL,\r\n"
				+ "	nombrePista nvarchar(250) UNIQUE NOT NULL,\r\n" + "	instrumento nvarchar(50) NOT NULL,\r\n"
				+ "	PRIMARY KEY (PistaID),\r\n" + "	FOREIGN KEY(IdCancion) REFERENCES canciones(IdCancion)\r\n"
				+ ");\r\n" + "if object_id('notas', 'U') is null \r\n" + "CREATE TABLE notas (\r\n"
				+ "	IdNota int IDENTITY(1,1) NOT NULL,\r\n" + "	PistaID int NOT NULL,\r\n"
				+ "	nombreNota nvarchar(50) NOT NULL,\r\n" + "    octava nvarchar(50) NOT NULL,\r\n"
				+ "	figura nvarchar(50) NOT NULL,\r\n" + "	alteracion nvarchar(50) NOT NULL,\r\n"
				+ "	PRIMARY KEY (IdNota),\r\n" + "	FOREIGN KEY(PistaID) REFERENCES pistas(PistaID)\r\n" + ");";
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

	@Override
	public void close() throws SQLException {
		if (!conn.isClosed()) {
			conn.close();
		}
	}

	/*
	 * @Override public void updateNota(String nombreMelodia, String idNota, String
	 * nombreNota, String octava, String figura, String alteracion) { String
	 * consulta = String.format( "UPDATE notas \r\n" + "SET nombreNota = %s \r\n" +
	 * "	,octava = %s \r\n" + "	,figura = %s \r\n" + "	,alteracion = %s \r\n" +
	 * "WHERE nombreMelodia = %s ", nombreNota, octava, figura, alteracion,
	 * nombreMelodia); try { PreparedStatement statement =
	 * conn.prepareStatement(consulta); statement.executeUpdate(); } catch
	 * (SQLException e) { logSQLERROR(e); throw new
	 * RuntimeException(e.getMessage()); }
	 * 
	 * }
	 */

	/*
	 * @Override public void removeNotaById(String nombreMelodia, String idNota) {
	 * 
	 * /* String consulta = String.format( "DELETE notas\r\n" +
	 * "WHERE notas.nombreNota = '%s' AND notas.nombreMelodia = '%s'", idNota,
	 * nombreMelodia); try { PreparedStatement statement =
	 * conn.prepareStatement(consulta); statement.executeUpdate(); } catch
	 * (SQLException e) { logSQLERROR(e); throw new
	 * RuntimeException(e.getMessage()); }
	 */
	// }

	@Override
	public void listarNotas(String nombreCancion) {
		ResultSet rs = null;
		String consulta = String.format("SELECT n.Id, n.nombreNota, n.octava, n.figura, n.alteracion \r\n"
				+ "FROM notas n" + " WHERE n.nombreMelodia = '%s' ORDER BY n.Id ASC", nombreCancion);
		try {
			System.out.print("Id,nombre,Octava,alteración   ");
			rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				System.out.println("");
				System.out.print(rs.getString("Id"));
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
	public ArrayList<String> getCanciones() {
		ArrayList<String> canciones = new ArrayList<>();
		ResultSet rs = null;
		String consulta = "SELECT nombreCancion FROM canciones";
		try {
			rs = conn.prepareStatement(consulta).executeQuery();
			while (rs.next()) {
				canciones.add(rs.getString("nombreCancion"));
			}
		} catch (SQLException e) {
			logSQLERROR(e);
			throw new IllegalArgumentException(e.getMessage());
		}
		return canciones;
	}
	@Override
	public void play(String nombreMelodia, PlayerSingleton player) {
		Cancion cancion = loadCancion(nombreMelodia);
		cancion.play(player);

	}
	
}
