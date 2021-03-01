package cli;

import api.Complaint;
import api.Product;

public class EntityCreator {
	public static Product createProduct() {
		System.out.println("Új termék felvitele:");
		Product p = new Product();
		boolean ok = false;
		do {
			System.out.println("Pid: ");
			int pid = Utils.readIntInRange(0, Integer.MAX_VALUE);
			try {
				if (Main.getDbm().doesProductExist(pid) == 0) {
					p.setPid(pid);
					ok = true;
				} else {
					System.out.println("Ilyen kulcsú termék már létezik: ");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		} while (!ok);

		String name = Utils.readString("Név: ");
		p.setName(name);

		System.out.println("Ár: ");
		int price = Utils.readIntInRange(0, Integer.MAX_VALUE);
		p.setPrice(price);

		System.out.println("Mennység: ");
		int stock = Utils.readIntInRange(0, Integer.MAX_VALUE);
		p.setStock(stock);

		return p;
	}

	public static Complaint createComplaint() {
		System.out.println("Új panasz felvitele:");
		Complaint c = new Complaint();
		boolean ok = false;
		do {
			System.out.println("Cid: ");
			int cid = Utils.readIntInRange(0, Integer.MAX_VALUE);

			try {
				if (Main.getDbm().doesComplaintExist(cid) == 0) {
					c.setCid(cid);
					ok = true;
				} else {
					System.out.println("Ilyen kulcsú panasz már létezik!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		} while (!ok);

		ok = false;
		do {
			System.out.println("Pid: ");
			int pid = Utils.readIntInRange(0, Integer.MAX_VALUE);
			try {
				if (Main.getDbm().doesProductExist(pid) != 0) {
					c.setPid(pid);
					ok = true;
				} else {
					System.out.println("Nincs ilyen kulcsú termék!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		} while (!ok);

		String description = Utils.readString("Leírás: ");

		c.setDescription(description);
		return c;
	}
}
