package cli;

import java.util.Scanner;

public class Utils {

    public static char delimiter = '\t';

    private static Scanner in = new Scanner(System.in);

	public static int readInt() {
        boolean ok = false;
        
        int input = 0;
        do{
            String s = in.nextLine();
            try {
                input = Integer.parseInt(s);
                ok = true;
            } catch (Exception e) {
                System.out.println("Nem megfeleo input");
            }
        }while( ok != true);
        return input;
    }

    public static int readIntInRange( int min, int max) {
        boolean ok = false;
        int input = max;
        do{
            String s = in.nextLine();
            try {
                input = Integer.parseInt(s);
                if( input >= min && input <= max )
                    ok = true;
                else
                    System.out.println("Nem megfelo input");
            } catch (Exception e) {
                System.out.println("Nem megfeleo input");
            }
        }
        while( ok != true);
        return input;
    }

    public static void waitForInput( String msg){
        if( msg.isEmpty() ){
            msg = "Nyomj Entert a visszalépéshez!";
        }
        System.err.println(msg);
        in.nextLine();
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

    public static String readString( String msg ){
        System.out.println(msg);
        return in.nextLine();
    }

    /*public static boolean doesArrayContainString(String[] strArray, String str){
        for(String s: strArray ){
            if( s == str )
                return true;
        }
        return false;
    }

    public static void printTable( ArrayList<? extends Agent> agents ){
        if( agents.isEmpty() == true )
            return;
        
        int numColumns =  agents.get(0).getFields().length;
        ArrayList<String[]> rows = new ArrayList<String[]>();
        int[] maxLengths = new int[numColumns];

        for (Agent agent : agents) {
            rows.add(agent.getTableRow());
        }

        for( int i = 0; i < maxLengths.length; i++ ){
            for( String[] s: rows ){
                if( maxLengths[i] < s[i].length() )
                    maxLengths[i] = s[i].length();
            }
        }

        String Template = "| ";
        for( int j = 0; j < maxLengths.length; j++ ){
            Template += "%" + (j==0?"-":"") +  ( maxLengths[j] + 2) + "s |";
        }
        System.out.println(Template);
        
        String header = String.format( Template , (Object[]) agents.get(0).getFields() );
        System.out.println(header);

        String line = "";
        for( int i = 0; i < header.length(); i++ )
            line += "=";
        
        System.out.println(line);
        Template += "%n";
        for (Agent agent : agents) {
            System.out.printf( Template,(Object[]) agent.getTableRow() );
        }
        System.out.println(line);
    }*/
}
