package cli;

import java.util.Scanner;

public class Utils {

	public static char delimiter = '\t';

	private static Scanner in = new Scanner(System.in);

	public static int readInt() {
		boolean ok = false;

		int input = 0;
		do {
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

	public static int readIntInRange(int min, int max) {
		boolean ok = false;
		int input = max;
		do {
			String s = in.nextLine();
			try {
				input = Integer.parseInt(s);
				if (input >= min && input <= max)
					ok = true;
				else
					System.out.println("Nem megfelő input");
			} catch (Exception e) {
				System.out.println("Nem megfelelő input");
			}
		} while (ok != true);
		return input;
	}

	public static void waitForInput(String msg) {
		if (msg.isEmpty()) {
			msg = "Nyomjon Entert a visszalépéshez!";
		}
		System.err.println(msg);
		in.nextLine();
	}

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static String readString(String msg) {
		System.out.println(msg);
		return in.nextLine();
	}

}
