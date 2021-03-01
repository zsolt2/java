package cli;

import api.*;

public class Main {

	private static Dbmng dbm = new Dbmng("D:\\inventory.db");

	public static void main(String[] args) {
		MainMenu.Print();
	}

	public static Dbmng getDbm() {
		return dbm;
	}

}
