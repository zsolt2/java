package cli;

import api.*;

public class TableSelectMenu {
	public static String getChoosenTable() {
		try {
			Table t = Main.getDbm().getAllTableNames();
			String[] tables = t.getColumn(1);
			if (tables == null) {
				System.out.println("Nincsennek táblák az adatbázisban!");
			}
			while (true) {
				Menu tableSelectMenu = new Menu("Válasszon egy táblát: ", tables, "");
				int selectedTable = tableSelectMenu.handleMenu();
				if (selectedTable > 0 && selectedTable <= tables.length) {
					return tables[selectedTable - 1];
				} else {
					return "";
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
}
