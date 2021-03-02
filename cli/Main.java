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
	 * @param a program nem használja fel a atandard inputról beérkező adatokat
	 */
	public static void main(String[] args) {
		try {
			dbm.init();
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionHandler.handle(e);
		}
		
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
