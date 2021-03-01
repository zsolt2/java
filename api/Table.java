package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Table {

	private static class Index {
		String name;
		int num;

		public Index(String name, int num) {
			this.name = name;
			this.num = num;
		}
	}

	private String[] header;
	private ArrayList<String[]> rows;
	private HashMap<Index, String[]> columns;

	public Table() {
	}

	public Table(String[] header, ArrayList<String[]> rows) {
		this.header = header;
		this.rows = rows;

		this.columns = new HashMap<Index, String[]>();

		for (int i = 0; i < header.length; i++) {
			String[] column = new String[rows.size()];
			for (int j = 0; j < rows.size(); j++) {
				column[j] = rows.get(j)[i];
			}
			columns.put(new Index(header[i], i + 1), column);
		}

	}

	public Table(ArrayList<? extends Entity> agents) {
		if (agents.isEmpty()) {
			this.header = null;
			this.rows = null;
			this.columns = null;
			return;
		}
		this.header = agents.get(0).getFields();
		this.rows = new ArrayList<String[]>();
		for (Entity a : agents) {
			this.rows.add(a.getTableRow());
		}
		this.columns = new HashMap<Index, String[]>();
		for (int i = 0; i < header.length; i++) {
			String[] column = new String[rows.size()];
			for (int j = 0; j < rows.size(); j++) {
				column[j] = rows.get(j)[i];
			}
			columns.put(new Index(header[i], i + 1), column);
		}
	}

	public String[] getHeader() {
		return this.header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

	public ArrayList<String[]> getRows() {
		return this.rows;
	}

	public void setRows(ArrayList<String[]> rows) {
		this.rows = rows;
	}

	public void print() {
		if (rows.isEmpty() == true)
			return;

		int numColumns = header.length;

		int[] maxLengths = new int[numColumns];

		for (int i = 0; i < maxLengths.length; i++) {
			maxLengths[i] = header[i].length();
			for (String[] s : rows) {
				if (s[i] != null)
					if (maxLengths[i] < s[i].length())
						maxLengths[i] = s[i].length();
			}
		}

		String Template = "| ";
		for (int j = 0; j < maxLengths.length; j++) {
			Template += "%" /* + (j==0?"-":"") */ + (maxLengths[j] + 2) + "s |";
		}

		String headerLine = String.format(Template, (Object[]) header);
		// System.out.printf( Template,(Object[]) agents.get(0).getFields() )
		String lineBold = "";
		String line = "";
		for (int i = 0; i < headerLine.length(); i++) {
			lineBold += "=";
			line += "-";
		}

		System.out.println(lineBold);
		System.out.println(headerLine);
		System.out.println(lineBold);

		Template += "%n";
		for (String[] row : rows) {
			// System.out.println(agent.getTableRow());
			System.out.printf(Template, (Object[]) row);
			if (row != rows.get(rows.size() - 1))
				System.out.println(line);
		}
		System.out.println(lineBold);
	}

	public String[] getColumn(String columnName) {
		Set<Index> indexes = columns.keySet();
		for (Index i : indexes) {
			if (i.name == columnName) {
				return columns.get(i);
			}
		}
		return null;
	}

	public String[] getColumn(int columnNum) {
		Set<Index> indexes = columns.keySet();
		for (Index i : indexes) {
			if (i.num == columnNum) {
				return columns.get(i);
			}
		}
		return null;
	}

	public boolean isEmpty() {
		return rows.size() == 0;
	}

}
