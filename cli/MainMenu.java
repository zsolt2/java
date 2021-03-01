package cli;

import api.Complaint;
import api.Product;
import api.Table;

public class MainMenu {
	public static void Print() {
		Menu mainMenu = new Menu("Főmenü", new String[] { "Tábla listázása", "Lekérdezés", "Tábla leírás",
				"Rekord hozzáadás", "Rekord törlése" }, "vege");
		boolean running = true;
		while (running == true) {
			switch (mainMenu.handleMenu()) {
			case 1: // tabla listázása
				printTableMenu();
				break;
			case 2: // tetszőleges lekérdezés
				sqlRequestMenu();
				break;
			case 3:// tábla leírasa
				describeTableMenu();
				break;
			case 4:// Rekord hozzáadása
				addRekordMenu();
				break;
			case 5:// Rekord törlése
				deleteRekordMenu();
				break;
			case 6:
				running = false;
				break;
			default:

				break;
			}
		}
	}

	private static void printTableMenu() {
		String tableName = null;
		do {
			tableName = TableSelectMenu.getChoosenTable();
			System.out.println(tableName);
			try {
				if (!tableName.isEmpty()) {
					Table t = Main.getDbm().selectQuery("select * from " + tableName + " ;");
					System.out.println(t);
					t.print();
					Utils.waitForInput("");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Utils.waitForInput("");
			}
		} while (!tableName.isEmpty());
	}

	private static void sqlRequestMenu() {
		boolean back = false;
		while (back == false) {
			// Utils.clearScreen();
			String command = Utils.readString("Adj meg egy SQL lekérdezést: ");
			try {
				Table t = Main.getDbm().selectQuery(command);
				if (t.isEmpty()) {
					System.out.println("A lekérdezett tábla üres");
				} else {
					t.print();
				}
			} catch (Exception e) {
				System.out.println("Hibás lekérdezés!");
				System.out.println(e.getMessage());
			} finally {
				String input = Utils.readString("Új lekérdezés(I/N): ");
				if (!input.equalsIgnoreCase("i"))
					back = true;
			}
		}
	}

	private static void describeTableMenu() {
		String tableName = "";
		do {
			tableName = TableSelectMenu.getChoosenTable();
			System.out.println(tableName);
			try {
				if (!tableName.isEmpty()) {
					Table t = Main.getDbm().tableInfo(tableName);
					t.print();
					Utils.waitForInput("");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Utils.waitForInput("");
			}
		} while (!tableName.isEmpty());
	}

	private static void addRekordMenu() {
		String tableName = "";
		do {
			tableName = TableSelectMenu.getChoosenTable();
			System.out.println(tableName);
			try {
				if (tableName.equalsIgnoreCase("product")) {
					Product p = EntityCreator.createProduct();
					Main.getDbm().insertProduct(p);
					System.out.println("Termék sikeresen beszúrva a táblába!");
					Utils.waitForInput("");
				} else if (tableName.equalsIgnoreCase("complaint")) {
					Complaint c = EntityCreator.createComplaint();
					System.out.println(c);
					Main.getDbm().insertComplaint(c);
					System.out.println("Panasz sikeresen beszúrva a táblába!");
					Utils.waitForInput("");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Utils.waitForInput("");
			}
		} while (!tableName.isEmpty());
	}

	private static void deleteRekordMenu() {
		String tableName = "";
		do {
			tableName = TableSelectMenu.getChoosenTable();
			System.out.println(tableName);
			try {
				if (tableName.equalsIgnoreCase("product")) {
					boolean ok = false;
					do {
						try {
							System.out.println("Pid:");
							int pid = Utils.readIntInRange(0, Integer.MAX_VALUE);
							System.out.println("Beolvasott pid: " + pid);
							Main.getDbm().deleteFromProduct(pid);
							ok = true;
						} catch (Exception e) {
							ok = false;
							System.out.println(e.getMessage());
							Utils.waitForInput("");
						}
					} while (!ok);
				} else if (tableName.equalsIgnoreCase("complaint")) {
					boolean ok = false;
					do {
						try {
							System.out.println("Cid:");
							int cid = Utils.readIntInRange(0, Integer.MAX_VALUE);
							Main.getDbm().deleteFromComplaint(cid);
							ok = true;
						} catch (Exception e) {
							ok = false;
							System.out.println(e.getMessage());
							Utils.waitForInput("");
						}
					} while (!ok);
				}
			} catch (Exception e) {

				System.out.println(e.getMessage());
				Utils.waitForInput("");
			}
		} while (!tableName.isEmpty());
	}

}
