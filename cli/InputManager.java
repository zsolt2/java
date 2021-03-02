package cli;

import java.util.Scanner;
/**
 * Ez az osztály statikus metódusokat tartalmaz, amely a változók beolvasát kezeli a felhasználótól.
 * @since 01-03-2021 
 */
public class InputManager {

	public static char delimiter = '\t';

	private static Scanner in = new Scanner(System.in);
	/**
	 * Egész szám beolvasása
	 * @param msg a beolvassás előtt megjelenő szöveg
	 * @return beolvasott szám
	 */
	public static int readInt( String msg) {
		boolean ok = false;

		int input = 0;
		do {
			System.out.println(msg);
			String s = in.nextLine();
			try {
				input = Integer.parseInt(s);
				ok = true;
			} catch (Exception e) {
				System.out.println("Nem megfelő input");
			}
		} while (ok != true);
		return input;
	}
	/**
	 * Egész szám beolvasása egy adott zárt intervallumban
	 * @param min alsó határ (zárt)
	 * @param max felső határ (zárt)
	 * @param msg a beolvassás előtt megjelenő szöveg
	 * @return beolvasott egész szám
	 */
	public static int readIntInRange(int min, int max, String msg) {
		boolean ok = false;
		int input = max;
		do {
			if( msg != null ){
				System.out.println(msg);
			}
			String s = in.nextLine();
			try {
				input = Integer.parseInt(s);
				if (input >= min && input <= max)
					ok = true;
				else
					System.out.println("Nem megfelő input");
			} catch (Exception e) {
				ExceptionHandler.handle(e);
			}
		} while (ok != true);
		return input;
	}

	/**
	 * A futtatás leállítása addig, amíg nincs input.
	 * @param msg megjelenő üzenet. Ha az értéke üreshalmaz akkor {@code "Nyomjon Entert a visszalépéshez!"} alapértéket vesz fel.
	 */

	public static void waitForInput(String msg) {
		if (msg.isEmpty()) {
			msg = "Nyomjon Entert a visszalépéshez!";
		}
		System.err.println(msg);
		in.nextLine();
	}
	/**
	 * Konzol törlése
	 */
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	/**
	 * Szöveg beolvasása
	 * @param msg a beolvasás előtt megjelenő szöveg
	 * @return beolvasott szöveg
	 */
	public static String readString(String msg) {
		System.out.println(msg);
		return in.nextLine();
	}

}
