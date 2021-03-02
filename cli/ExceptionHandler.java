package cli;

public class ExceptionHandler {
    public static void handle(Exception e){
        System.out.println(e.getMessage());
        Utils.waitForInput("");
    }
}
