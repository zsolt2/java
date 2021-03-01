package cli;

public class Menu {
	private String header;
	private String[] fields;
	private String footer;

	public Menu(String header, String[] fields, String footer) {
		if (header != null)
			this.header = header;
		else
			this.header = "Válassz egy lehetőséget: ";
		this.fields = fields;
		this.footer = footer;
	}

	public void print() {
		Utils.clearScreen();
		System.out.println(header);
		int i = 0;
		for (i = 0; i < fields.length; i++) {
			String s = (i + 1) + ".) " + fields[i];
			System.out.println(s);
		}
		System.out.println((i + 1) + ".) Vissza");
		System.out.println(footer);
	}

	public int handleMenu() {
		print();
		return Utils.readIntInRange(1, this.fields.length + 1);
	}

	public int getBackCode() {
		return fields.length + 1;
	}

}
