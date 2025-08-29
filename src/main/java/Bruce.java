import java.util.ArrayList;
import java.util.Scanner;

public class Bruce {
    static final String LINES = "____________________________________________________________";
    static final String NAME = "Bruce";
    static boolean isRunning = true;
    static final String LOGO =
            "██████  ██████  ██    ██  ██████ ███████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██████  ██    ██ ██      █████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██   ██  ██████   ██████ ███████";
    static Scanner in = new Scanner(System.in);
    static ArrayList<Task> tasks = new ArrayList<>();

    public static void greetUser() {
        System.out.println(LOGO + "\n" + LINES);
        System.out.println("Hello! I'm " + NAME + "!");
        System.out.println("What can I do for you?\n" + LINES);
    }

    public static void exitProgram() {
        System.out.println("Bye. Hope to see you again soon!");
        isRunning = false;
    }

    public static String inputFromUser() {
        return in.nextLine();
    }

    public static void markDone(String prompt) {
        String[] parts = prompt.split(" ");
        int number = Integer.parseInt(parts[1]) - 1;
        if (number < tasks.size()) {
            Task task = tasks.get(number);
            task.markDone();
            System.out.println("Nice! I've marked this task as done: ");
            outputInterface(task);
        } else {
            System.out.println("Task does not exist. Please try again.");
        }
    }

    public static void markUndone(String prompt) {
        String[] parts = prompt.split(" ");
        int number = Integer.parseInt(parts[1]) - 1;
        if (number < tasks.size()) {
            Task task = tasks.get(number);
            task.unmarkDone();
            System.out.println("OK, I've marked this task as not done yet: ");
            outputInterface(task);
        } else {
            System.out.println("Task does not exist. Please try again.");
        }
    }

    public static void outputInterface(Task task) {
        if (task.isCompleted()) {
            System.out.println("[X] " + task.getTaskId() + "." + task.getTaskDescription());
        } else {
            System.out.println("[ ] " + task.getTaskId() + "." + task.getTaskDescription());
        }
    }

    private static void taskManager(String inputPrompt) {
        if (inputPrompt.equals("bye") || inputPrompt.equals("Bye")) {
            exitProgram();
        } else if (inputPrompt.isEmpty()) {
            System.out.println("Please try again.");
        } else if (inputPrompt.startsWith("mark ")) {
            markDone(inputPrompt);
        } else if (inputPrompt.startsWith("unmark ")) {
            markUndone(inputPrompt);
        } else if (inputPrompt.equals("list")) {
            System.out.println("Here are the tasks in your list: ");
            for (Task task : tasks) {
                outputInterface(task);
            }
        } else {
            Task newInput = new Task(inputPrompt);
            tasks.add(newInput);
            System.out.println("added: " + inputPrompt);
        }
    }


    public static void main(String[] args) {
        greetUser();
        String inputPrompt;

        while (isRunning) {
            inputPrompt = inputFromUser();
            taskManager(inputPrompt);
            System.out.println(LINES);
        }
    }
}
