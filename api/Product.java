package api;

/**
 * Ez az osztály a termékek tárolására szolgál.
 * Implementálja az Entity interfészt
 *  @since 01-03-2021 
 */

public class Product implements Entity {
	private int pid;
	private String name ="";
	private int price;
	private int stock;

	/**
     * Paraméter nélküli konstruktor
     */
	public Product() {
	}

	/**
	 * Konstruktor
	 * @param pid a termék id-je, az adatbázisban az eslődleges kulcs 
	 * @param name a termék neve
	 * @param price a termék ára 
	 * @param stock	a termék mennyisége
	 */

	public Product(int pid, String name, int price, int stock) {
		this.pid = pid;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "{" + " pid='" + getPid() + "'" + ", name='" + getName() + "'" + ", price='" + getPrice() + "'"
				+ ", stock='" + getStock() + "'" + "}";
	}

	/**
     * A függvény vissza adja a termék paramétereit veszzővel elválasztva, ez megkönnyíti az SQL parancsok létrehozását.
     * @return paraméterek értéke veszzővel elválasztva
     */
	public String values() {
		return "'" + getPid() + "','" + getName() + "','" + getPrice() + "','" + getStock() + "'";
	}
	/**
     * A függvény visszaadj a termék paramétereinek a neveit egy string tömbben
     * @return paraméterek nevei string tömbben
     */
	public String[] getFields() {
		return new String[] { "pid", "name", "price", "stock" };
	}
	/**
     * A függvény vissza adja a termék paramétereit egy string tömbben
     * @return paraméterek értéke string tömbben
     */
	public String[] getTableRow() {
		return new String[] { String.valueOf(getPid()), getName(), String.valueOf(getPrice()),
				String.valueOf(getStock()) };
	}

}