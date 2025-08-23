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
    //static String[] taskList = new String[10];

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
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
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
        } else {
            System.out.println(inputPrompt);
        }
    }
}
