import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Bruce {
    static String lines = "____________________________________________________________";
    static String name = "Bruce";
    static boolean isRunning = true;
    static String logo =
            "██████  ██████  ██    ██  ██████ ███████\n"+
            "██   ██ ██   ██ ██    ██ ██      ██\n"+
            "██████  ██████  ██    ██ ██      █████\n"+
            "██   ██ ██   ██ ██    ██ ██      ██\n"+
            "██████  ██   ██  ██████   ██████ ███████";

    public static void greetUser() {
        System.out.println(logo +"\n" + lines);
        System.out.println("Hello! I'm " + name + "!");
        System.out.println("What can I do for you?\n" + lines);
    }

    public static void exitProgram() {
        System.out.println("Bye. Hope to see you again soon!");
        isRunning = false;
    }

    public static String inputFromUser(){
        Scanner scanner = new Scanner(System.in);
        return "";
    }

    static List<String> verifiedCommands = new ArrayList<>();
    public static boolean verifyInput(String input) {
        return verifiedCommands.contains(input);
    };

    public static void main(String[] args) {
        greetUser();

        while (isRunning) {
            exitProgram();
        }
    }
}
