package cli;

import api.Complaint;
import api.Product;

/**
 * Ez az osztály statickus metódusokat tartalmaz, a termékek és panaszok beolvasását teszi lehetővé
 * @since 01-03-2021 
 */


public class EntityCreator {
	/**
	 * Egy új Termék beolvasása
	 * @return új termék
	 */
	public static Product createProduct() {
		System.out.println("Új termék felvitele:");
		Product p = new Product();
		boolean ok = false;
		do {
			//beolvasássuk az elsődleges kulcsot a felhasználótól
			int pid = InputManager.readIntInRange(0, Integer.MAX_VALUE, "Pid: ");
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

		String name = InputManager.readString("Név: ");
		p.setName(name);

		int price = InputManager.readIntInRange(0, Integer.MAX_VALUE, "Ár: ");
		p.setPrice(price);

		int stock = InputManager.readIntInRange(0, Integer.MAX_VALUE, "Mennység: ");
		p.setStock(stock);

		return p;
	}

	/**
	 * Egy új Panasz beovasása
	 * @return új panasz
	 */
	public static Complaint createComplaint() {
		System.out.println("Új panasz felvitele:");
		Complaint c = new Complaint();
		boolean ok = false;
		do {
			//beolvassuk az elsődleges kulcsot a felhasználótól
			int cid = InputManager.readIntInRange(0, Integer.MAX_VALUE, "Cid: ");

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
			//beolvassuk a termékre mutató másodlagos kulcsot a felhasználótól
			int pid = InputManager.readIntInRange(0, Integer.MAX_VALUE,"Pid: ");
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

		String description = InputManager.readString("Leírás: ");

		c.setDescription(description);
		return c;
	}
}
