package cli;

import api.*;

/**
 * Main osztály
 */
public class Main {
	//Adatbázis kezelő 
	private static Dbmng dbm = new Dbmng("D:\\inventory.db");
	
	
	/**
	 * A program belépési pontja
	 * @param args nem használt argumentum
	 */
	public static void main(String[] args) {
		MainMenu.Print();
	}
	
	/**
	 * Adatbázis kezelő elérését biztosítja a többi programrész számára
	 * @return adatbázis manager 
	 */
	public static Dbmng getDbm() {
		return dbm;
	}

}
