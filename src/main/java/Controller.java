import java.util.Scanner;

public class Controller {
    private final Model model;
    private final View view;
    private boolean isRunning = true;
    private final Scanner scanner = new Scanner(System.in);

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        View.greetUser();
        Scanner scanner = new Scanner(System.in);
        while (isRunning) {
            String inputPrompt = scanner.nextLine();
            taskManager(inputPrompt);
            View.printLine();
        }
    }

    private void exitProgram() {
        View.viewExit();
        isRunning = false;
    }

    public void markTaskDone(String prompt) {
        String[] parts = prompt.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;
        boolean isCompleted = model.markDone(index);
        if (isCompleted) {
            view.viewTaskMarked(model.getTask(index));
        } else {
            View.viewError();
        }
    }

    public void unmarkTaskDone(String prompt) {
        String[] parts = prompt.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;
        boolean isCompleted = model.unmarkDone(index);
        if (isCompleted) {
            view.viewTaskUnmarked(model.getTask(index));
        } else {
            View.viewError();
        }
    }

    private void newTodoTask(String inputPrompt) {
        Task newInput = new Todo(inputPrompt);
        model.addTask(newInput);
        view.viewTaskAdded(newInput, model.getTaskCount());
    }

    private void newDeadlineTask(String inputPrompt) {
        String[] parts = inputPrompt.split("/by ");
        if (parts.length == 2) {
            Task newInput = new Deadline(parts[0], parts[1]);
            model.addTask(newInput);
            view.viewTaskAdded(newInput, model.getTaskCount());
        } else {
            View.viewError();
        }
    }

    private void newEventTask(String inputPrompt) {
        String[] parts = inputPrompt.split(" /from | /to ");
        if (parts.length == 3) {
            Task newInput = new Event(parts[0], parts[1], parts[2]);
            model.addTask(newInput);
            view.viewTaskAdded(newInput, model.getTaskCount());
        } else {
            View.viewError();
        }
    }

    private void taskManager(String inputPrompt) {
        if (inputPrompt.equalsIgnoreCase("Bye.")) {
            exitProgram();
        } else if (inputPrompt.isEmpty()) {
            View.viewError();
        } else if (inputPrompt.toLowerCase().startsWith("mark ")) {
            markTaskDone(inputPrompt);
        } else if (inputPrompt.toLowerCase().startsWith("unmark ")) {
            unmarkTaskDone(inputPrompt);
        } else if (inputPrompt.equalsIgnoreCase("list")) {
            view.viewTaskList(model.getTasks());
        } else if (inputPrompt.toLowerCase().startsWith("deadline ")) {
            newDeadlineTask(inputPrompt);
        } else if (inputPrompt.toLowerCase().startsWith("event ")) {
            newEventTask(inputPrompt);
        } else {
            newTodoTask(inputPrompt);
        }
    }
}
