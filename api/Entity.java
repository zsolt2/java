package api;

/**
 * @since 01-03-2021 
 */

public interface Entity {

	public static char delimiter = '\t';

	public String values();

	public String[] getFields();

	public String[] getTableRow();
}
