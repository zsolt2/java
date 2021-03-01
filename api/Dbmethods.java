package api;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dbmethods {
    private Statement s = null;
    private Connection conn = null;
    private ResultSet rs = null;

    public Connection getConnection(){
        return this.conn;
    }

    public Statement gStatement(){
        return this.s;
    }

    //regisztrálás
    public static void reg () throws ClassNotFoundException{
            Class.forName("org.sqlite.JDBC");
    }

    public void connect(String path) throws SQLException{
            String url = "jdbc:sqlite:"+path;
            conn = DriverManager.getConnection(url);
    }

    public void disconnect() throws SQLException{
            conn.close();
    }

    //Beszúrás több elemet
    public void insert(String table, String ...str) throws SQLException{
        String sqlp = "insert into " + table + " vlaues( ";
        for( String s: str){
            sqlp += s + ",";
        }
        sqlp = sqlp.substring(0, sqlp.length() - 1);
        sqlp += ");";
        commandExec(sqlp);
    }

    //Beszúrás egy elemet
    public void insert(String table, String str) throws SQLException{
        String sqlp = "insert into " + table + " values( " + str + ");";
        commandExec(sqlp);

    }

    public void commandExec( String command) throws SQLException{
        s = conn.createStatement();
        s.execute(command);
    }

    public Statement createStatement() throws SQLException{
        s = conn.createStatement();
        return s;
    }

    public ResultSet executeQuery( String sql) throws SQLException{
        rs = s.executeQuery(sql);
        return rs;
    }

    public ResultSet createStatementAndExecuteQuery( String sql) throws SQLException {
        createStatement();
        return executeQuery(sql);
    }

}