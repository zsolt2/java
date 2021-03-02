package api;

/**
 * Ez az osztály a panaszok tárolására szolgál
 * Implementálja az Entity interfészt
 * @since 01-03-2021 
 */

public class Complaint implements Entity{
    private int cid;
    private int pid;
    private String Description = "";

    /**
     * Paraméter nélküli konstruktor
     */

    public Complaint(){}
    /**
     * Konstruktor
     * @param cid a panasz id-je, az adatbázisban az eslődleges kulcs
     * @param pid a termék id-je, az adatbázisban az idegenkulcs
     * @param Description a panasz leírása
     */
    public Complaint(int cid, int pid, String Description) {
        this.cid = cid;
        this.pid = pid;
        this.Description = Description;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "{" +
            " cid='" + getCid() + "'" +
            ", pid='" + getPid() + "'" +
            ", Description='" + getDescription() + "'" +
            "}";
    }
    /**
     * A függvény vissza adja a pansz paramétereit veszzővel elválasztva, ez megkönnyíti az SQL parancsok létrehozását.
     * @return paraméterek értéke veszzővel elválasztva
     */
    public String values(){
        return getCid() + ", " + getPid() + ", '" + getDescription() + "'";
    }
    /**
     * Ez a függvény visszaadja paraméterek neveit egy string tömbben
     * @return paraméterek nevei string tömbben
     */
    public String[] getFields(){
        return new String[]{"cid", "pid", "Description" };
    }
    /**
     * A függvény vissza adja a pansz paramétereit egy string tömbben
     * @return paraméterek értéke string tömbben
     */
    public String[] getTableRow(){
        return new String[]{ String.valueOf(getCid()) ,  String.valueOf(getPid()) , getDescription() };
    }

   
}
