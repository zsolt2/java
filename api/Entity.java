package api;

public interface Entity {

	public static char delimiter = '\t';

	public String values();

	public String[] getFields();

	public String[] getTableRow();
}
