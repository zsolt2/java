package api;

/**
 * @author Zsolt
 *
 */
public class Complaint implements Entity{
    private int cid;
    private int pid;
    private String Description;

    public Complaint(){}

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

    public String values(){
        return getCid() + ", " + getPid() + ", '" + getDescription() + "'";
    }

    public String[] getFields(){
        return new String[]{"cid", "pid", "Description" };
    }

    public String[] getTableRow(){
        return new String[]{ String.valueOf(getCid()) ,  String.valueOf(getPid()) , getDescription() };
    }

   
}
