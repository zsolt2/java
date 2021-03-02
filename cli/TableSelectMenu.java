package cli;

import api.*;

/**
 * Tábla kiválasztását kezelő menü. Lekérdezi az adatbázisból az összes elérhető táblát, és ezek közül tudunk választani egyet.
 * @since 01-03-2021 
 */

public class TableSelectMenu {
	/**
	 * Tábla kiválasztását kezelő menü. Lekérdezi az adatbázisból az összes elérhető táblát, és ezek közül tudunk választani egyet.
	 * @return - Lekérdezett tábla neve
	 */
	public static String getChoosenTable() {
		try {
			//Az összes tábla neve lekérdezése
			Table t = Main.getDbm().getAllTableNames();
			String[] tables = t.getColumn(1);
			
			
			
			if (tables.length == 0) {
				System.out.println("Nincsennek táblák az adatbázisban!");
			}
			//Menü létrehozása a lekérdezett tábla nevekkel
			Menu tableSelectMenu = new Menu("Válasszon egy táblát: ", tables, "");
			while (true) {
				int selectedTable = tableSelectMenu.handleMenu();
				if (selectedTable > 0 && selectedTable <= tables.length) {
					return tables[selectedTable - 1];
				} else {
					return "";
				}
			}
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}
		return "";
	}
}
