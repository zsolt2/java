package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Ez az osztály az adatok tárolására szolgál
 * Implementálja az Entity interfészt
 * @since 01-03-2021 
 */

public class Table {
	/**
	 * Privát Index osztály.
	 * A táblában található oszlop nevét és sorszámát tárolja
	 */
	private static class Index {
		String name; // az oszlop neve
		int num;     // az oszlop sorszáma

		public Index(String name, int num) {
			this.name = name;
			this.num = num;
		}
	}
	/** Az oszlopok nevei */
	private String[] header = null;	
	/**A tábla sorai */    	
	private ArrayList<String[]> rows = null;	
	/**A tábla oszlopai */
	private HashMap<Index, String[]> columns = null;

	/**
	 * Konstruktor
	 * @param header a tábla oszlopainak a neve
	 * @param rows a tábla sorai
	 */

	public Table(String[] header, ArrayList<String[]> rows) {
		this.header = header;
		this.rows = rows;
		generateColumns();
	}

	/**
	 * Konstrukotr
	 * Tábla létrehozása {@code Entity} tömbből
	 * @param entities panaszok vagy termékek tömbje
	 */

	public Table(ArrayList<? extends Entity> entities) {
		if (entities.isEmpty()) {
			this.header = null;
			this.rows = null;
			this.columns = null;
			return;
		}
		this.header = entities.get(0).getFields();
		this.rows = new ArrayList<String[]>();
		for (Entity a : entities) {
			this.rows.add(a.getTableRow());
		}
		generateColumns();
	}
	/**
	 * Ez a függvény az oszlopokat hozza létre
	 */
	private void generateColumns(){
		this.columns = new HashMap<Index, String[]>();

		for (int i = 0; i < header.length; i++) {
			String[] column = new String[rows.size()];
			for (int j = 0; j < rows.size(); j++) {
				column[j] = rows.get(j)[i];
			}
			columns.put(new Index(header[i], i + 1), column);
		}
	}

	/**
	 * Visszaakja az oszlopok neveit
	 * @return oszlopok nevei
	 */
	public String[] getHeader() {
		return this.header;
	}
	
	public void setHeader(String[] header) {
		this.header = header;
	}

	/**
	 * Rekordok lekérdezése
	 * @return A táblában taláható sorok
	 */
	public ArrayList<String[]> getRows() {
		return this.rows;
	}

	public void setRows(ArrayList<String[]> rows) {
		this.rows = rows;
	}

	/**
	 * Oszlop kiíratása
	 */

	public void print() {
		//Ha a táblában nincsennek sorok kilép
		if (rows.isEmpty() == true)
			return;
		//Oszlopok száma
		int numColumns = header.length;
		
		//Minden oszlopban meghatározzuk hogy melyik a leghosszabb elem
		int[] maxLengths = new int[numColumns];

		for (int i = 0; i < maxLengths.length; i++) {
			maxLengths[i] = header[i].length();
			for (String[] s : rows) {
				if (s[i] != null)
					if (maxLengths[i] < s[i].length())
						maxLengths[i] = s[i].length();
			}
		}

		//Minden oszlopban a leghosszabb elemhez igazítjuk az oszlop szélességét
		String Template = "| ";
		for (int j = 0; j < maxLengths.length; j++) {
			Template += "%"  + (maxLengths[j] + 2) + "s |";
		}
		
		//Az oszlop fejlécének létrehozása
		String headerLine = String.format(Template, (Object[]) header);
		
		//Sorokat elválasztó vonalak létrehozása
		String lineBold = "";
		String line = "";
		for (int i = 0; i < headerLine.length(); i++) {
			lineBold += "=";
			line += "-";
		}

		//Fejléc kiírása
		System.out.println(lineBold);
		System.out.println(headerLine);
		System.out.println(lineBold);

		Template += "%n";
		//Srorok kiírása
		for (String[] row : rows) {
			System.out.printf(Template, (Object[]) row);
			if (row != rows.get(rows.size() - 1))
				System.out.println(line);
		}
		System.out.println(lineBold);
	}
	/**
	 * Oszlop lekérdezése az oszlop neve szerint
	 * Nemlétező oszlopnév esetén {@code null} értékkel tér vissza
	 * @param columnName az oszlop neve
	 * @return az oszlopban található adatok
	 */
	public String[] getColumn(String columnName) {
		Set<Index> indexes = columns.keySet();
		for (Index i : indexes) {
			if (i.name == columnName) {
				return columns.get(i);
			}
		}
		return null;
	}

	/**
	 * Oszlop lekérdezése az oszlop sorszáma szerint
	 * Nemlétező sorszáma esetén {@code null} értékkel tér vissza
	 * @param columnNum az oszlop sorszáma
	 * @return az oszlopban található adatok
	 */
	public String[] getColumn(int columnNum) {
		Set<Index> indexes = columns.keySet();
		for (Index i : indexes) {
			if (i.num == columnNum) {
				return columns.get(i);
			}
		}
		return null;
	}

	/**
	 * Tartalmaz-e a tábla adatokat
	 * @return {@code true} - a tábla üres
	 * 	       {@code false} - a tábla nem üres
	 */
	public boolean isEmpty() {
		return rows.size() == 0;
	}

}
