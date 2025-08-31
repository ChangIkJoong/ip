import java.util.ArrayList;
import java.util.Scanner;

public class Bruce {
    static final String INLINE_TEXT_LINES = "____________________________________________________________";
    static final String BOT_NAME = "Bruce";
    static final String BOT_LOGO =
            "██████  ██████  ██    ██  ██████ ███████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██████  ██    ██ ██      █████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██   ██  ██████   ██████ ███████";

    static Scanner inputFromKeyboard = new Scanner(System.in);
    static ArrayList<Task> tasks = new ArrayList<>();
    static boolean isRunning = true;
    static TaskView view = new TaskView();

    private static void greetUser() {
        System.out.println(BOT_LOGO + "\n" + INLINE_TEXT_LINES);
        System.out.println("Hello! I'm " + BOT_NAME + "!");
        System.out.println("What can I do for you?\n" + INLINE_TEXT_LINES);
    }

    private static void exitProgram() {
        view.viewExit();
        isRunning = false;
    }

    private static String inputFromUser() {
        return inputFromKeyboard.nextLine();
    }

    private static void markDone(String prompt) {
        String[] parts = prompt.split(" ");
        int number = Integer.parseInt(parts[1]) - 1;
        if (number < tasks.size()) {
            Task task = tasks.get(number);
            task.markDone();
            view.viewTaskMarked(task);
        } else {
            view.viewError();
        }
    }

    private static void markUndone(String prompt) {
        String[] parts = prompt.split(" ");
        int number = Integer.parseInt(parts[1]) - 1;
        if (number < tasks.size()) {
            Task task = tasks.get(number);
            task.unmarkDone();
            view.viewTaskUnmarked(task);
        } else {
            view.viewError();
        }
    }

    private static void newTodoTask(String inputPrompt) {
        Task newInput = new Todo(inputPrompt);
        tasks.add(newInput);
        view.viewTaskAdded(newInput, tasks.size());
    }

    private static void newDeadlineTask(String inputPrompt) {
        String[] parts = inputPrompt.split("/by ");
        if (parts.length == 2) {
            Task newInput = new Deadline(parts[0], parts[1]);
            tasks.add(newInput);
            view.viewTaskAdded(newInput, tasks.size());
        } else {
            view.viewError();
        }
    }

    private static void newEventTask(String inputPrompt) {
        String[] parts = inputPrompt.split(" /from | /to ");
        if (parts.length == 3) {
            Task newInput = new Event(parts[0], parts[1], parts[2]);
            tasks.add(newInput);
            view.viewTaskAdded(newInput, tasks.size());
        } else {
            view.viewError();
        }
    }

    private static void taskManager(String inputPrompt) {
        if (inputPrompt.equalsIgnoreCase("bye")) {
            exitProgram();
        } else if (inputPrompt.isEmpty()) {
            view.viewError();
        } else if (inputPrompt.toLowerCase().startsWith("mark ")) {
            markDone(inputPrompt);
        } else if (inputPrompt.toLowerCase().startsWith("unmark ")) {
            markUndone(inputPrompt);
        } else if (inputPrompt.equalsIgnoreCase("list")) {
            view.viewTaskList(tasks);
        } else if (inputPrompt.toLowerCase().startsWith("deadline ")) {
            newDeadlineTask(inputPrompt);
        } else if (inputPrompt.toLowerCase().startsWith("event ")) {
            newEventTask(inputPrompt);
        } else {
            newTodoTask(inputPrompt);
        }
    }


    public static void main(String[] args) {
        greetUser();
        String inputPrompt;

        while (isRunning) {
            inputPrompt = inputFromUser();
            taskManager(inputPrompt);
            System.out.println(INLINE_TEXT_LINES);
        }
    }
}
