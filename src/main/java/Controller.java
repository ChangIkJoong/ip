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

    public void runProgram() {
        view.greetUser();
        while (isRunning) {
            String inputPrompt = scanner.nextLine();
            handleInput(inputPrompt);
            view.printLine();
        }
    }

    private void exitProgram() {
        view.viewExit();
        isRunning = false;
    }

    public void markTaskDone(String prompt) {
        String[] parts = prompt.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;
        boolean isCompleted = model.markDone(index);
        if (isCompleted) {
            view.viewTaskMarked(model.getTask(index));
        } else {
            view.viewError();
        }
    }

    public void unmarkTaskDone(String prompt) {
        String[] parts = prompt.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;
        boolean isCompleted = model.unmarkDone(index);
        if (isCompleted) {
            view.viewTaskUnmarked(model.getTask(index));
        } else {
            view.viewError();
        }
    }

    private void addTodoTask(String inputPrompt) {
        Task newInput = new Todo(inputPrompt);
        model.addTask(newInput);
        view.viewTaskAdded(newInput, model.getTaskCount());
    }

    private void addDeadlineTask(String inputPrompt) {
        String[] parts = inputPrompt.split("/by ");
        if (parts.length == 2) {
            parts[0] = parts[0].replaceFirst("(?i)deadline", "").trim();
            Task newInput = new Deadline(parts[0], parts[1]);
            model.addTask(newInput);
            view.viewTaskAdded(newInput, model.getTaskCount());
        } else {
            view.viewError();
        }
    }

    private void addEventTask(String inputPrompt) {
        String[] parts = inputPrompt.split(" /from | /to ");
        if (parts.length == 3) {
            parts[0] = parts[0].replaceFirst("(?i)event", "").trim();
            Task newInput = new Event(parts[0], parts[1], parts[2]);
            model.addTask(newInput);
            view.viewTaskAdded(newInput, model.getTaskCount());
        } else {
            view.viewError();
        }
    }

    private void handleInput(String inputPrompt) {
        String command = inputPrompt.trim().split(" ")[0].toLowerCase();
        switch (command) {
        case "bye." -> exitProgram();
        case "" -> { /* TODO, need to in next session add the exception here */ }
        case "mark" -> markTaskDone(inputPrompt);
        case "unmark" -> unmarkTaskDone(inputPrompt);
        case "list" -> view.viewTaskList(model.getTasks());
        case "deadline" -> addDeadlineTask(inputPrompt);
        case "event" -> addEventTask(inputPrompt);
        default -> addTodoTask(inputPrompt);
        }
    }
}
