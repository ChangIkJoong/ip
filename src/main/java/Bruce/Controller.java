package Bruce;

import Exceptions.BruceException;
import Exceptions.EmptyDescriptionException;
import Exceptions.UnknownInputException;
import Model.TaskVariants.Deadline;
import Model.TaskVariants.Event;
import Model.Model;
import Model.dataHandler;
import Model.TaskVariants.Task;
import Model.TaskVariants.Todo;
import UI.View;

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
        View.greetUser();
        while (isRunning) {
            String inputPrompt = scanner.nextLine();
            try {
                handleInput(inputPrompt);
            } catch (NumberFormatException e) {
                view.viewError("Task number must be a whole number. Example: \"mark 3\"");
            } catch (IndexOutOfBoundsException e) {
                view.viewError("Missing task number. Example: \"mark 3\"");
            } catch (BruceException e) {
                view.viewError(e.getMessage());
            } catch (Exception e) {
                view.viewError("Unexpected error: " + e.getMessage());
            } finally {
                View.printLine();
            }
        }
    }

    private void exitProgram() {
        dataHandler.getInstance().saveTasks(model.getTasks());
        View.viewExit();
        isRunning = false;
    }

    private int checkIndexing(String inputPrompt) throws IndexOutOfBoundsException {
        String[] parts = inputPrompt.trim().split("\\s+");
        int index = Integer.parseInt(parts[1]) - 1;
        if (index >= 0 && index < model.getTasks().size()) {
            return index;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void markTaskDone(String inputPrompt) throws BruceException {
        int index = checkIndexing(inputPrompt);
        Task task = model.getTasks().get(index);
        boolean previouslyCompleted = task.isCompleted();
        boolean isCompleted = model.markDone(index);
        if (previouslyCompleted != isCompleted) {
            view.viewTaskMarked(model.getTask(index));
        } else {
            throw new BruceException("This task has been already completed. Please select an uncompleted task.");
        }
    }

    public void unmarkTaskDone(String inputPrompt) throws BruceException {
        int index = checkIndexing(inputPrompt);
        Task task = model.getTasks().get(index);
        boolean previouslyCompleted = task.isCompleted();
        boolean isCompleted = model.unmarkDone(index);
        if (previouslyCompleted == isCompleted) {
            view.viewTaskUnmarked(model.getTask(index));
        } else {
            throw new BruceException("This task has not yet been completed. Please select an uncompleted task.");
        }
    }

    private void addTodoTask(String inputPrompt) throws BruceException {
        String revisedInputPrompt = inputPrompt.replaceFirst("(?i)todo", "").trim();
        if (revisedInputPrompt.isEmpty()) {
            throw new EmptyDescriptionException("Description cannot be empty. Type \"help\" for commands.");
        }
        Task newInput = new Todo(revisedInputPrompt);
        model.addTask(newInput);
        view.viewTaskAdded(newInput, model.getTaskCount());
    }

    private void addDeadlineTask(String inputPrompt) throws BruceException {
        String[] parts = inputPrompt.trim().split(" /by ");
        parts[0] = parts[0].replaceFirst("(?i)deadline", "").trim();

        boolean isValid = parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty();

        if (isValid) {
            throw new EmptyDescriptionException("Description or deadline date cannot be empty. Type \"help\" for commands.");
        } else {
            Task newInput = new Deadline(parts[0], parts[1]);
            model.addTask(newInput);
            view.viewTaskAdded(newInput, model.getTaskCount());
        }
    }

    private void addEventTask(String inputPrompt) throws BruceException {
        String[] parts = inputPrompt.trim().split(" /from | /to ");
        parts[0] = parts[0].replaceFirst("(?i)event", "").trim();

        boolean isValid = parts.length != 3 || parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty();

        if (isValid) {
            throw new EmptyDescriptionException("Description or event to/from date cannot be empty. Type \"help\" for commands.");
        } else {
            Task newInput = new Event(parts[0], parts[1], parts[2]);
            model.addTask(newInput);
            view.viewTaskAdded(newInput, model.getTaskCount());
        }
    }

    private void removeTask(String inputPrompt) {
        int index = checkIndexing(inputPrompt);
        Task deletedTask = model.deleteTask(index);
        view.succesfullyDeletedTask(deletedTask);
        view.viewTaskList(model.getTasks());
    }

    private void handleInput(String inputPrompt) throws BruceException {
        String command = inputPrompt.trim().split(" ")[0].toLowerCase();

        switch (command) {
        case "bye." -> exitProgram();
        case "mark" -> markTaskDone(inputPrompt);
        case "unmark" -> unmarkTaskDone(inputPrompt);
        case "deadline" -> addDeadlineTask(inputPrompt);
        case "event" -> addEventTask(inputPrompt);
        case "todo" -> addTodoTask(inputPrompt);
        case "delete" -> removeTask(inputPrompt);

        case "list" -> view.viewTaskList(model.getTasks());
        case "help" -> View.showInstructions();

        default -> throw new UnknownInputException(command);
        }
    }
}
