package cli;

import api.*;

public class Main {
	//Adatbázis kezelő 
	private static Dbmng dbm = new Dbmng("D:\\inventory.db");

	public static void main(String[] args) {
		MainMenu.Print();
	}
	
	//Adatbázis kezelő lekérdezése
	public static Dbmng getDbm() {
		return dbm;
	}

}
