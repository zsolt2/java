package api;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Ez az osztály magasabb szintű metódusokat biztosít, amelyelekkel az adatbázist tudjuk kezelni 
 * @since 01-03-2021 
 */

public class Dbmng {
	/**Az adatbázis elérési útja*/
    private String path;
    private Dbmethods dbm;
    private ResultSet rs = null;

    public Dbmng(String path) {
        this.path = path;
        dbm = new Dbmethods();
    }
    
    /**
     * Ezt a függvényt meg kell hívni miután létrehoztuk 
     * @throws ClassNotFoundException
     */
    public void init() throws ClassNotFoundException {
        Dbmethods.registerDriver();
    }

    /**
     * Termék beszúrása a Product táblába
     * @param product beszúrandó termék 
     * @throws SQLException 
     * @throws DbmngException ha ilyen Pid-del rendelkező termék már létezik a táblában 
     */
    public void insertProduct(Product product) throws SQLException, DbmngException {
        if (doesProductExist(product.getPid()) != 0) {
            throw new DbmngException("Ilyen kulcsú rekord már létezik: " + product.getPid());
        }

        dbm.connect(path);
        dbm.insert("Product", product.values());
        dbm.disconnect();
    }

    /**
     * Panasz beszúrása a Complaint táblába
     * @param complaint
     * @throws SQLException
     * @throws DbmngException ha ilyen Cid-del rendelkező panasz már létezik a táblában 
     * @throws DbmngException ha az idegen kulcshoz(pid) nem tartozik termék 
     */ 
    public void insertComplaint(Complaint complaint) throws SQLException, DbmngException {
        if (doesComplaintExist(complaint.getCid()) != 0) {
            throw new DbmngException("Ilyen kulcsú panasz már létezik: " + complaint.getCid());
        }

        if (doesProductExist(complaint.getPid()) == 0) {
            throw new DbmngException("Nincs ilyen kulcsú termék: " + complaint.getPid());
        }

        dbm.connect(path);
        dbm.insert("Complaint", complaint.values());
        dbm.disconnect();
    }

    /**
     * Ezzel a függvénnyel le tudjuk kérdezni, hogy egy panasz hámyszor szerepel a Complaint táblában
     * @param cid a panasz elsődleges kulcsa
     * @return hányszor található meg a panasz a Complaint táblában
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
     * Ezzel a függvénnyel le tudjuk kérdezni, hogy egy termék hámyszor szerepel a Product táblában
     * @param pid - a termék elsődleges kulcsa
     * @return hányszor található meg a termék a Product táblában
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
     * @param pid a termék elsődleges kulcsa
     * @return beolvasott {@code Product} objektum 
     * @throws SQLException
     * @throws DbmngException - ha ilyen kulcsú termék nem létezik
     */
    public Product getProduct(int pid) throws SQLException, DbmngException {

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
     * @param cid a panasz elsődleges kulcsa
     * @return beolvasott {@code Complaint} objektum 
     * @throws SQLException
     * @throws DbmngException ha nem létezik ilyen kulcsú panasz
     */
    public Complaint getComplaint(int cid) throws SQLException, DbmngException {
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
     * Az összes termék lekérdezése a Product táblából
     * @return {@code ArrayList<Product>}, amely a beolvasott termékeket tartalmazza
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
     * @param sql sql select parancs 
     * @return a beolvasott adatok
     * @throws SQLException 
     * @throws DbmngException ha nem select parancsot adunk meg
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
     * @return a táblák nevei
     * @throws SQLException
     */
    public Table getAllTableNames() throws SQLException {
        dbm.connect(path);
        DatabaseMetaData dbMetadata = dbm.getConnection().getMetaData();
        rs = dbMetadata.getTables(null,null, null, new String[]{"TABLE"});

        Table t = createTableFromResultSet(rs, new String[] { "TABLE_NAME" });

        dbm.disconnect();

        return t;
    }

    /**
     * A resultsetet átalakítjuk táblává, az összes beolvasott oszlopot beleteszi a táblába 
     * @param rs result set
     * @return a resultSetgől létrehozott tábla
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
     * Csak a megadott oszlopokat alakítja át, a többi adat elveszik.
     * @param rs result set
     * @param columns felsorolt oszlopok
     * @return a keletkezett tábla
     * @throws SQLException
     */
    private Table createTableFromResultSet(ResultSet rs, String[] columns) throws SQLException {
        int numColumns = columns.length;

        ArrayList<String[]> rows = new ArrayList<String[]>();
        String[] headers = new String[numColumns];

        for (int j = 0; j < numColumns; j++) {
            headers[j] = columns[j];
        }

        while (rs.next()) {
            String[] row = new String[numColumns];
            for (int i = 0; i < numColumns; i++) {
                row[i] = rs.getString(columns[i]);
            }
            rows.add(row);
        }

        return new Table(headers, rows);
    }

    /**
     * Tábla leírása. Lekérdezi a meta adatokat és létrehoz egy táblában vissza adja azokat
     * @param tableName a tábla neve
     * @return a beolvasott adatok 
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
     * @param cid a panasz elsődleges kulcsa
     * @throws SQLException
     * @throws DbmngException  ha nem létezik ilyen kulcsú panasz
     */
    public void deleteFromComplaint(int cid) throws SQLException, DbmngException {
        if( doesComplaintExist(cid) == 0 ){
            throw new DbmngException("Nincs ilyen kulcsú kulcsú panasz: " + cid);
        }
        dbm.connect(path);
        String sql = "delete from complaint where cid=" + cid;
        dbm.commandExec(sql);
        dbm.disconnect();
    }

    /**
     * Termék törlése. Csak olyan termék törölhető, amely létezik, és nem mutat rá idegen kulcs
     * @param pid a termék elsődleges kulcsa
     * @throws SQLException
     * @throws DbmngException  ha nem létezik ilyen kulcsú termék
     * @throws DbmngException  ha a termékre mutat egy másodlagos kulcs
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
