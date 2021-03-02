package cli;

/**
 * Menü
 * @since 01-03-2021 
 */

public class Menu {
	/**Menü felett megjelenő szöveg */
	private String header;
	/**Menü lehetőségek*/
	private String[] options;
	/**Menü alatt emgjelenő szöveg*/
	private String footer;

	/**
	 * Konstruktor. Ha {@code header} paramétertnek {@code null} értéket adunk meg akkor felvesz egy alapértelmezett értéket: "Válassz egy lehetőséget: "
	 * @param header Menü felett megjelenő szöveg
	 * @param fields Menü lehetőségek
	 * @param footer Menü alatt emgjelenő szöveg
	 */
	public Menu(String header, String[] fields, String footer) {
		if (header != null)
			this.header = header;
		else
			this.header = "Válassz egy lehetőséget: ";
		this.options = fields;
		this.footer = footer;
	}

	/**
	 * Menü kiírása. 
	 */
	public void print() {
		Utils.clearScreen();
		System.out.println(header);
		int i = 0;
		for (i = 0; i < options.length; i++) {
			String s = (i + 1) + ".) " + options[i];
			System.out.println(s);
		}
		System.out.println((i + 1) + ".) Vissza");
		System.out.println(footer);
	}

	/**
	 * A menü kiírása, és a választott opció bekérdezése
	 * @return bekérdezett opció
	 */
	public int handleMenu() {
		print();
		return Utils.readIntInRange(1, this.options.length + 1, null);
	}

	/**
	 * Az utolsó opció mindig a {@code "vissza"} opció. Ezzel a függvénnyel lekérdezhetjük, hogy mi a {@code "vissza"} opció kódja.
	 * @return - a {@code "vissza"} opció kódja
	 */

	public int getBackCode() {
		return options.length + 1;
	}

}
