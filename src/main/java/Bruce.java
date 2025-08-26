import java.util.ArrayList;
import java.util.Scanner;

public class Bruce {
    static String lines = "____________________________________________________________";
    static String name = "Bruce";
    static boolean isRunning = true;
    static String logo =
            "██████  ██████  ██    ██  ██████ ███████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██████  ██    ██ ██      █████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██   ██  ██████   ██████ ███████";
    static Scanner in = new Scanner(System.in);
    static ArrayList<String> tasks = new ArrayList<>();

    public static void greetUser() {
        System.out.println(logo + "\n" + lines);
        System.out.println("Hello! I'm " + name + "!");
        System.out.println("What can I do for you?\n" + lines);
    }

    public static void exitProgram() {
        System.out.println("Bye. Hope to see you again soon!");
        isRunning = false;
    }

    public static String inputFromUser() {
        return in.nextLine();
    }

    public static void main(String[] args) {
        greetUser();
        String inputPrompt;

        while (isRunning) {

            inputPrompt = inputFromUser();
            taskManager(inputPrompt);
            System.out.println(lines);
        }
    }

    private static void taskManager(String inputPrompt) {
        if (inputPrompt.equals("bye") || inputPrompt.equals("Bye")) {
            exitProgram();
        } else if (inputPrompt.equals("list")) {
            for (String task : tasks) {
                System.out.println(task);
            }
        } else {
            tasks.add(inputPrompt);
            System.out.println("added: " + inputPrompt);
        }
    }
}
