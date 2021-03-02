package api;

/**
 * @since 01-03-2021 
 */

public interface Entity {
	/**
     * A függvény vissza adja az {@code Entity} paramétereit veszzővel elválasztva, ez megkönnyíti az SQL parancsok létrehozását.
     * @return paraméterek értéke veszzővel elválasztva
     */
	public String values();
	/**
     * A függvény visszaadja {@code Entity} paramétereinek neveit egy string tömbben
     * @return paraméterek nevei string tömbben
     */
	public String[] getFields();
	 /**
     * A függvény vissza adja a {@code Entity} paramétereit egy string tömbben
     * @return paraméterek értéke string tömbben
     */
	public String[] getTableRow();
}
