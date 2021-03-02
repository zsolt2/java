package api;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Ez az osztály alcsony szintú jdbc függvényeket tartalmaz
 * @since 01-03-2021 
 */

public class Dbmethods {
	private Statement s = null;
	private Connection conn = null;
	private ResultSet rs = null;

	public Connection getConnection() {
		return this.conn;
	}

	public Statement gStatement() {
		return this.s;
	}

	/**
	 * Driver regisztrálása 
	 * @throws ClassNotFoundException - ha az osztály nem található
	 */
	public static void registerDriver() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
	}

	/**
	 * Kapcsolódás az adatbázis kezelő driverhez
	 * @param path - az adatbázis elérési útja
	 * @throws SQLException - ha nem sikerül kapcsolódni az adatbázishoz
	 */
	public void connect(String path) throws SQLException {
		String url = "jdbc:sqlite:" + path;
		conn = DriverManager.getConnection(url);
	}

	/**
	 * Lekapcsolódás az adatbázisról
	 * @throws SQLException - ha hiba keletkezik az adatbázishoz való kapcsolódásnál
	 */

	public void disconnect() throws SQLException {
		conn.close();
	}

	/**
	 * Több elem beszúrása a táblába
	 * @param table - a tábla neve
	 * @param str - beszúrandó értékek felsorolva
	 * @throws SQLException
	 */
	public void insert(String table, String... str) throws SQLException {
		String sqlp = "insert into " + table + " vlaues( ";
		for (String s : str) {
			sqlp += s + ",";
		}
		sqlp = sqlp.substring(0, sqlp.length() - 1);
		sqlp += ");";
		commandExec(sqlp);
	}

	/**
	 * Egy elem beszúrása a táblába
	 * @param table - tábla neve 
	 * @param str - beszúrandó érték
	 * @throws SQLException
	 */
	public void insert(String table, String str) throws SQLException {
		String sqlp = "insert into " + table + " values( " + str + ");";
		commandExec(sqlp);

	}

	/**
	 * SQL parancs végrehajtása
	 * @param SQLcommand - sql parnacs
	 * @throws SQLException
	 */
	public void commandExec(String SQLcommand) throws SQLException {
		s = conn.createStatement();
		s.execute(SQLcommand);
	}

	public Statement createStatement() throws SQLException {
		s = conn.createStatement();
		return s;
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		rs = s.executeQuery(sql);
		return rs;
	}

	public ResultSet createStatementAndExecuteQuery(String sql) throws SQLException {
		createStatement();
		return executeQuery(sql);
	}

}
