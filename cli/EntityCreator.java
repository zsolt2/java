package cli;

/**
 * Ez egy statikus osztály, a termékek és panaszok bekérdezését teszi lehetővé
 */

import api.Complaint;
import api.Product;

public class EntityCreator {
	/**
	 * Egy új Termék bekérdezése
	 * @return új termék
	 */
	public static Product createProduct() {
		System.out.println("Új termék felvitele:");
		Product p = new Product();
		boolean ok = false;
		do {
			//bekérdezzük az elsődleges kulcsot
			int pid = Utils.readIntInRange(0, Integer.MAX_VALUE, "Pid: ");
			try {
				//Ha a termék már léteszik, akkor hibaüzenetet kapunk
				if (Main.getDbm().doesProductExist(pid) == 0) {
					p.setPid(pid);
					ok = true;
				} else {
					System.out.println("Ilyen kulcsú termék már létezik: ");
				}
			} catch (Exception e) {
				ExceptionHandler.handle(e);
				return null;
			}
		} while (!ok);

		String name = Utils.readString("Név: ");
		p.setName(name);

		int price = Utils.readIntInRange(0, Integer.MAX_VALUE, "Ár: ");
		p.setPrice(price);

		int stock = Utils.readIntInRange(0, Integer.MAX_VALUE, "Mennység: ");
		p.setStock(stock);

		return p;
	}

	/**
	 * Egy új Panasz bekérdezése
	 * @return új panasz
	 */
	public static Complaint createComplaint() {
		System.out.println("Új panasz felvitele:");
		Complaint c = new Complaint();
		boolean ok = false;
		do {
			//bekérdezzük az elsődleges kulcsot
			int cid = Utils.readIntInRange(0, Integer.MAX_VALUE, "Cid: ");

			try {
				//Ha a panasz már léteszik, akkor hibaüzenetet kapunk
				if (Main.getDbm().doesComplaintExist(cid) == 0) {
					c.setCid(cid);
					ok = true;
				} else {
					System.out.println("Ilyen kulcsú panasz már létezik!");
				}
			} catch (Exception e) {
				ExceptionHandler.handle(e);
				return null;
			}
		} while (!ok);

		ok = false;
		do {
			//bekérdezzük a termékre mutató másodlagos kulcsot
			int pid = Utils.readIntInRange(0, Integer.MAX_VALUE,"Pid: ");
			try {
				//ha nem létezik ilyen termék akkor hibaüzenetet kapunk
				if (Main.getDbm().doesProductExist(pid) != 0) {
					c.setPid(pid);
					ok = true;
				} else {
					System.out.println("Nincs ilyen kulcsú termék!");
				}
			} catch (Exception e) {
				ExceptionHandler.handle(e);
				return null;
			}
		} while (!ok);

		String description = Utils.readString("Leírás: ");

		c.setDescription(description);
		return c;
	}
}
