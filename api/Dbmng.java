package api;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Dbmng {
    private String path;
    private Dbmethods dbm;
    private ResultSet rs = null;

    {
        try {
            Dbmethods.reg();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Dbmng(String path) {
        this.path = path;
        dbm = new Dbmethods();
    }

    /**
     * Termék beszúrása
     * @param p
     * @throws SQLException
     * @throws DbmngException
     */
    public void insertProduct(Product p) throws SQLException, DbmngException {
        if (doesProductExist(p.getPid()) != 0) {
            throw new DbmngException("Ilyen kulcsú rekord már létezik: " + p.getPid());
        }

        dbm.connect(path);
        dbm.insert("Product", p.values());
        dbm.disconnect();
    }

    /**
     * Panasz beszúrása
     * @param c
     * @throws SQLException
     * @throws DbmngException
     */ 
    public void insertComplaint(Complaint c) throws SQLException, DbmngException {
        if (doesComplaintExist(c.getCid()) != 0) {
            throw new DbmngException("Ilyen kulcsú panasz már létezik: " + c.getCid());
        }

        if (doesProductExist(c.getPid()) == 0) {
            throw new DbmngException("Nincs ilyen kulcsú termék: " + c.getPid());
        }

        dbm.connect(path);
        dbm.insert("Complaint", c.values());
        dbm.disconnect();
    }

    /**
     * Van-e ilyen kulcsú panasz az adatbázisban
     * @param cid
     * @return
     * @throws SQLException
     */ 
    public int doesComplaintExist(int cid) throws SQLException {
        dbm.connect(path);

        String sql = "select count(*) ids from complaint where cid='" + cid + "';";
        rs = dbm.createStatementAndExecuteQuery(sql);

        int pidekSzama = rs.getInt("ids");
        dbm.disconnect();
        return pidekSzama;
    }

    /**
     * Van-e ilyen kulcsú panasz az adatbázisban
     * @param pid
     * @return
     * @throws SQLException
     */
    public int doesProductExist(int pid) throws SQLException {
        dbm.connect(path);

        String sql = "select count(*) ids from product where pid='" + pid + "';";
        rs = dbm.createStatementAndExecuteQuery(sql);

        int pidekSzama = rs.getInt("ids");
        dbm.disconnect();
        return pidekSzama;
    }

    /**
     * Termék lekérdezése az adatbázisból
     * @param pid
     * @return
     * @throws Exception
     */
    public Product getProduct(int pid) throws Exception {

        if (doesProductExist(pid) == 0) {
            throw new DbmngException("Nincs ilyen kulcsú termék: " + pid);
        }

        dbm.connect(path);

        String sql = "select pid, name, price, stock from Product where pid=" + pid;

        rs = dbm.createStatementAndExecuteQuery(sql);

        Product p = new Product();

        while (rs.next()) {
            p.setPid(rs.getInt("pid"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getInt("price"));
            p.setStock(rs.getInt("stock"));
        }

        dbm.disconnect();
        return p;
    }

    /**
     * Panasz lekérdezése az adatbázisból
     * @param cid
     * @return
     * @throws Exception
     */
    public Complaint getComplaint(int cid) throws Exception {
        if (doesComplaintExist(cid) == 0) {
            throw new DbmngException("Ilyen ilyen kulcsú panasz: " + cid);
        }
        dbm.connect(path);
        String sql = "select cid, pid, description from Complaint where cid=" + cid;
        rs = dbm.createStatementAndExecuteQuery(sql);
        Complaint c = new Complaint();
        while (rs.next()) {
            c.setCid(rs.getInt("cid"));
            c.setPid(rs.getInt("pid"));
            c.setDescription(rs.getString("description"));
        }
        dbm.disconnect();
        return c;
    }

    /**
     * Több termék lekérdezése egy tömbben
     * @return
     * @throws SQLException
     */
    public ArrayList<Product> getProductsFromDatabase() throws SQLException {
        ArrayList<Product> products = new ArrayList<Product>();
        dbm.connect(path);

        String sql = "select pid, name, price, stock from Product;";

        rs = dbm.createStatementAndExecuteQuery(sql);

        while (rs.next()) {
            Product p = new Product();
            p.setPid(rs.getInt("pid"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getInt("price"));
            p.setStock(rs.getInt("stock"));
            products.add(p);
        }

        dbm.disconnect();
        return products;
    }

    /**
     * Tetszőleges select parancs futtatása
     * @param sql
     * @return
     * @throws SQLException
     * @throws DbmngException
     */
    public Table selectQuery(String sql) throws SQLException, DbmngException {
        sql.strip();

        if (!sql.substring(0, 6).equalsIgnoreCase("select"))
            throw new DbmngException("Nem select parancs!");

        dbm.connect(path);

        rs = dbm.createStatementAndExecuteQuery(sql);
        Table t = createTableFromResultSet(rs);

        dbm.disconnect();

        return t;
    }

    /**
     * Az adatbázisban található összes tábla nevének lekérdezése
     * @return
     * @throws SQLException
     */
    public Table getAllTableNames() throws SQLException {
        dbm.connect(path);
        rs = dbm.getConnection().getMetaData().getTables(null, null, null, null);

        Table t = createTableFromResultSet(rs, new String[] { "TABLE_NAME" });

        dbm.disconnect();

        return t;
    }

    /**
     * A resultsetet átalakítjuk táblává
     * @param rs
     * @return
     * @throws SQLException
     */
    private Table createTableFromResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int numColumns = rsmd.getColumnCount();

        ArrayList<String[]> rows = new ArrayList<String[]>();
        String[] headers = new String[numColumns];

        for (int j = 0; j < numColumns; j++) {
            headers[j] = rsmd.getColumnName(j + 1);
        }

        while (rs.next()) {
            String[] row = new String[numColumns];
            for (int i = 0; i < numColumns; i++) {
                row[i] = rs.getString(i + 1);
            }
            rows.add(row);
        }

        return new Table(headers, rows);
    }

    /**
     * Tábla létrehozása Result set-ből
     * @param rs
     * @param fields
     * @return
     * @throws SQLException
     */
    private Table createTableFromResultSet(ResultSet rs, String[] fields) throws SQLException {
        int numColumns = fields.length;

        ArrayList<String[]> rows = new ArrayList<String[]>();
        String[] headers = new String[numColumns];

        for (int j = 0; j < numColumns; j++) {
            headers[j] = fields[j];
        }

        while (rs.next()) {
            String[] row = new String[numColumns];
            for (int i = 0; i < numColumns; i++) {
                row[i] = rs.getString(fields[i]);
            }
            rows.add(row);
        }

        return new Table(headers, rows);
    }

    /**
     * Tábla metaadat lekérdezése
     * @param tableName
     * @return
     * @throws SQLException
     */

    public Table tableInfo(String tableName) throws SQLException {
        Table t = null;
        dbm.connect(path);

        String sqlp = "pragma table_info(" + tableName + ");";
        rs = dbm.createStatementAndExecuteQuery(sqlp);
        t = createTableFromResultSet(rs);
        dbm.disconnect();
        return t;
    }
    /**
     * Panasz törlése. Csak olyan panasz törölhető, amely létezik.
     * @param cid
     * @throws SQLException
     */
    public void deleteFromComplaint(int cid) throws SQLException {
        doesComplaintExist(cid);
        dbm.connect(path);
        String sql = "delete from complaint where cid=" + cid;
        dbm.commandExec(sql);
        dbm.disconnect();
    }

    /**
     * Termék törlése. Csak olyan termék törölhető, amely létezik, és nem mutat rá idegen kulcs
     * @param pid
     * @throws SQLException
     * @throws DbmngException
     */
    public void deleteFromProduct(int pid) throws SQLException, DbmngException {
        if( doesProductExist(pid) == 0) {
            throw new DbmngException("Ilyen rekord nincs a táblában");
        }
        Table t = selectQuery("select cid from complaint where pid=" + pid);

        if (!t.isEmpty()) {
            throw new DbmngException("A termékre mutat egy idegenkulcs!");
        }

        dbm.connect(path);
        String sql = "delete from product where pid=" + pid;
        dbm.commandExec(sql);
        dbm.disconnect();
    }

}
