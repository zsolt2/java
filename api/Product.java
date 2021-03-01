package api;

public class Product implements Entity {
	private int pid;
	private String name;
	private int price;
	private int stock;

	public Product() {
	}

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

	public String values() {
		return "'" + getPid() + "','" + getName() + "','" + getPrice() + "','" + getStock() + "'";
	}

	public String[] getFields() {
		return new String[] { "pid", "name", "price", "stock" };
	}

	public String[] getTableRow() {
		return new String[] { String.valueOf(getPid()), getName(), String.valueOf(getPrice()),
				String.valueOf(getStock()) };
	}

}