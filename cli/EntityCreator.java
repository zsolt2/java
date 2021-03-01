package cli;

import api.Complaint;
import api.Product;

public class EntityCreator {
	public static Product createProduct() {
		System.out.println("Ăšj termĂ©k felvitele:");
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
					System.out.println("Ilyen kulcsĂş termĂ©k mĂˇr lĂ©tezik: ");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		} while (!ok);

		String name = Utils.readString("NĂ©v: ");
		p.setName(name);

		System.out.println("Ă�r: ");
		int price = Utils.readIntInRange(0, Integer.MAX_VALUE);
		p.setPrice(price);

		System.out.println("MennyisĂ©g: ");
		int stock = Utils.readIntInRange(0, Integer.MAX_VALUE);
		p.setStock(stock);

		return p;
	}

	public static Complaint createComplaint() {
		System.out.println("Ăšj panasz felvitele:");
		Complaint c = new Complaint();
		boolean ok = false;
		do {
			System.out.println("Cid: ");
			int cid = Utils.readIntInRange(0, Integer.MAX_VALUE);

			try {
				if (Main.getDbm().doesComplaintExist(cid) == 0) {
					System.out.println("Beolvasott cid: " + cid);
					c.setCid(cid);
					ok = true;
				} else {
					System.out.println("Ilyen kulcsĂş panasz mĂˇr lĂ©tezik!");
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
					System.out.println("Nincs ilyen kulcsĂş termĂ©k!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		} while (!ok);

		String description = Utils.readString("LeĂ­rĂˇs: ");

		c.setDescription(description);
		return c;
	}
}
