package cli;

/**
 * Az osztály a kivételeket kezeli
 * @since 01-03-2021  
 */

public class ExceptionHandler {
	
    /**
     * Hiba esetén kiírja a hibaüzenetet, majd felfüggeszti a program futását
     * @param e kivétel
     */
    public static void handle(Exception e){
        System.out.println(e.getMessage());
        InputManager.waitForInput("");
    }
}
