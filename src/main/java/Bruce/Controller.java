package Bruce;

import Exceptions.BruceException;
import Exceptions.EmptyDescriptionException;
import Exceptions.UnknownInputException;

import java.util.Scanner;

import Model.TaskVariants.Deadline;
import Model.TaskVariants.Event;
import Model.dataHandler;
import Model.Model;
import Model.TaskVariants.Task;
import Model.TaskVariants.Todo;
import UI.View;


/**
 * Controller for the Bruce task manager (MVC).
 * Reads user input, interprets commands, applies changes to the {@link Model},
 * and delegates rendering to the {@link View}.
 * Tasks goes via {@link dataHandler} after each handled input.
 * Parse/validation errors are mapped.
 */
public class Controller {
    private final Model model;
    private final View view;
    private boolean isRunning = true;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Creates a controller that wires a model to a view.
     *
     * @param model the application model
     * @param view  the console view
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }


    /**
     * Starts the controller loop: reads commands, handles them, and executes after each step.
     * On each iteration, input is processed through {@link #handleInput(String)}.
     * Tasks are saved via {@link dataHandler#saveTasks(java.util.ArrayList)} in a {@code finally} block
     * if it manages to pass all the error handlings safe guards.
     */
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
                dataHandler.getInstance().saveTasks(Model.getInstance().getTasks());
                View.printLine();
            }
        }
    }

    /**
     * Saves tasks, prints exit message, and stops the main loop.
     */
    private void exitProgram() {
        dataHandler.getInstance().saveTasks(model.getTasks());
        View.viewExit();
        isRunning = false;
    }

    /**
     * Checks and validates the indexing of the input prompt to ensure index
     * exists within bounds. Helper function for marking tasks or unmarking.
     *
     * @param inputPrompt full user input containing a command and a task number (e.g., {@code "mark 3"})
     * @return zero-based index into the model
     * @throws IndexOutOfBoundsException if the index token is missing or outside valid bounds
     * @throws NumberFormatException     if the index token is not an integer
     */
    private int checkIndexing(String inputPrompt) throws IndexOutOfBoundsException {
        String[] parts = inputPrompt.trim().split("\\s+");
        int index = Integer.parseInt(parts[1]) - 1;
        if (index >= 0 && index < model.getTasks().size()) {
            return index;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Marks a task as done by processing the index from {@code inputPrompt}.
     * If the task was already complete, throws a {@link BruceException}
     * to prompt the user to pick another task.
     *
     * @param inputPrompt command with index, e.g., {@code "mark 2"}
     * @throws BruceException if the task is already completed or input is invalid
     */
    public void markTaskDone(String inputPrompt) throws BruceException {
        int index = checkIndexing(inputPrompt);
        Task task = model.getTasks().get(index);
        boolean previouslyCompleted = task.isCompleted();
        boolean isCompleted = model.markDone(index);
        if (previouslyCompleted != isCompleted) {
            view.viewTaskMarked(model.getTask(index));
        } else {
            throw new BruceException("This task has been already completed. " +
                    "Please select an uncompleted task.");
        }
    }

    /**
     * Unmarks a task (sets to not done) by parsing the index from {@code inputPrompt}.
     * If the task was not completed, throws a {@link BruceException}.
     *
     * @param inputPrompt command with index, e.g., {@code "unmark 5"}
     * @throws BruceException if the task was not previously completed or input is invalid
     */
    public void unmarkTaskDone(String inputPrompt) throws BruceException {
        int index = checkIndexing(inputPrompt);
        Task task = model.getTasks().get(index);
        boolean previouslyCompleted = task.isCompleted();
        boolean isCompleted = model.unmarkDone(index);
        if (previouslyCompleted == isCompleted) {
            view.viewTaskUnmarked(model.getTask(index));
        } else {
            throw new BruceException("This task has not yet been completed. " +
                    "Please select an uncompleted task.");
        }
    }

    /**
     * Adds a ToDoTask based on the user's input.
     *
     * @param inputPrompt raw user input
     * @throws BruceException if description is missing/empty
     */
    private void addTodoTask(String inputPrompt) throws BruceException {
        String revisedInputPrompt = inputPrompt.replaceFirst("(?i)todo", "").trim();
        if (revisedInputPrompt.isEmpty()) {
            throw new EmptyDescriptionException("Description cannot be empty. " +
                    "Type \"help\" for commands.");
        }
        Task newInput = new Todo(revisedInputPrompt);
        model.addTask(newInput);
        view.viewTaskAdded(newInput, model.getTaskCount());
    }

    /**
     * Adds a {@link Deadline} task from input of the form:
     * {@code "deadline <desc> /by <date>"}.
     *
     * @param inputPrompt raw user input beginning with {@code "deadline"}
     * @throws BruceException if description or date is missing/empty
     */
    private void addDeadlineTask(String inputPrompt) throws BruceException {
        String[] parts = inputPrompt.trim().split(" /by ");
        parts[0] = parts[0].replaceFirst("(?i)deadline", "").trim();

        boolean isValid = parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty();

        if (isValid) {
            throw new EmptyDescriptionException("Description or deadline date cannot be empty. " +
                    "Type \"help\" for commands.");
        } else {
            Task newInput = new Deadline(parts[0], parts[1]);
            model.addTask(newInput);
            view.viewTaskAdded(newInput, model.getTaskCount());
        }
    }

    /**
     * Adds an {@link Event} task from input of the form:
     * {@code "event <desc> /from <start> /to <end>"}.
     *
     * @param inputPrompt raw user input beginning with {@code "event"}
     * @throws BruceException if any of description/start/end is missing/empty
     */
    private void addEventTask(String inputPrompt) throws BruceException {
        String[] parts = inputPrompt.trim().split(" /from | /to ");
        parts[0] = parts[0].replaceFirst("(?i)event", "").trim();

        boolean isValid = parts.length != 3 || parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty();

        if (isValid) {
            throw new EmptyDescriptionException("Description or event to/from date cannot be empty. " +
                    "Type \"help\" for commands.");
        } else {
            Task newInput = new Event(parts[0], parts[1], parts[2]);
            model.addTask(newInput);
            view.viewTaskAdded(newInput, model.getTaskCount());
        }
    }

    /**
     * Deletes a task by index and prints the updated task list.
     *
     * @param inputPrompt command with index, e.g., {@code "delete 1"}
     * @throws IndexOutOfBoundsException if the index is invalid
     * @throws NumberFormatException     if the index token is not an integer
     */
    private void removeTask(String inputPrompt) {
        int index = checkIndexing(inputPrompt);
        Task deletedTask = model.deleteTask(index);
        view.successfullyDeletedTask(deletedTask);
        view.viewTaskList(model.getTasks());
    }

    /**
     * Finds tasks whose descriptions contain the provided keyword
     * and displays these via View.
     *
     * @param inputPrompt command plus keyword
     */
    private void findTasks(String inputPrompt) {
        String inputWord = inputPrompt.replaceFirst("find", "").trim();
        if (inputWord.isEmpty()) {
            view.viewError("Please provide a keyword to search for. Example: 'find book'");
            return;
        }
        var matchingTasks = new java.util.ArrayList<Task>();
        for (Task task : model.getTasks()) {
            if (task.getTaskDescription().toLowerCase().contains(inputWord.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        view.viewFoundTasks(matchingTasks);
    }

    /**
     * Processes the command word and sends it to the appropriate handler.
     *
     * @param inputPrompt raw user input
     * @throws BruceException if the command is unknown or a handler reports a domain error
     */
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
        case "find" -> findTasks(inputPrompt);
        default -> throw new UnknownInputException(command);
        }
    }


}
