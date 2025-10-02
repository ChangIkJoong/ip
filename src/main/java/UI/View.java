package UI;

import Model.TaskVariants.Deadline;
import Model.TaskVariants.Event;
import Model.TaskVariants.Task;
import Model.TaskVariants.Todo;


/**
 * Console-facing view for the Bruce task manager (MVC).
 * Responsible for rendering strings to standard output: greetings, lists,
 * status updates, error messages, and command instructions. It formats
 * {@link Task} variants ({@link Todo}, {@link Deadline}, {@link Event})
 * into natural and readable lines.
 */
public class View {
    /** Horizontal text separator for the different sections in console output. */
    public static final String INLINE_TEXT_LINES = "____________________________________________________________";

    /** The bot's name shown in the output when called. */
    public static final String BOT_NAME = "Bruce";

    /** ASCII render of logo. */
    public static final String BOT_LOGO =
            "██████  ██████  ██    ██  ██████ ███████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██████  ██    ██ ██      █████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██   ██  ██████   ██████ ███████";

    /**
     * Prints the horizontal text separator to the console.
     */
    public static void printLine() {
        System.out.println(INLINE_TEXT_LINES);
    }

    /**
     * Greets the user with the bot logo, name, and a short prompt.
     */
    public static void greetUser() {
        View.printLine();
        System.out.println(BOT_LOGO + "\n" + INLINE_TEXT_LINES);
        System.out.println("Hello! I'm " + BOT_NAME + "!");
        System.out.println("For full list of instructions, type: help");
        System.out.println("What can I do for you?\n" + INLINE_TEXT_LINES);
    }

    /**
     * Renders the confirmation when a new task was added and shows the new total count.
     *
     * @param task  the new added task
     * @param total the total number of tasks now in the list
     */
    public void viewTaskAdded(Task task, int total) {
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + viewTask(task));
        System.out.println(" Now you have " + total + " tasks in the list.");
    }

    /**
     * Prints exit message.
     */
    public static void viewExit() {
        System.out.println("Bye.");
    }

    /**
     * Renders error message.
     */
    public void viewError(String message) {
        System.out.println(message);
    }

    /**
     * Renders the full list of tasks with their IDs.
     *
     * @param tasks is the list of the tasks to display
     */
    public void viewTaskList(java.util.List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (Task task : tasks) {
            System.out.println(" " + task.getTaskId() + "." + viewTask(task));
        }
    }

    /**
     * Renders confirmation when a task was marked as complete.
     *
     * @param task the task that was marked complete
     */
    public void viewTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + viewTask(task));
    }

    /**
     * Renders a confirmation when a task was unmarked (set to incomplete).
     *
     * @param task the task that was unmarked
     */
    public void viewTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + viewTask(task));
    }

    /**
     * Formats and renders a {@link Task} into a string, including type,
     * completion status, description, and relevant dates for {@link Deadline}
     * and {@link Event}.
     *
     * @param task the task to format to string
     * @return the re-formatted string render of the task
     */
    private String viewTask(Task task) {
        String resultString;
        if (task instanceof Todo) {
            resultString = "[T]" + isCompleteStatus(task) + task.getTaskDescription();
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            resultString = "[D]" + isCompleteStatus(task) + deadline.getTaskDescription() + " (by: " + deadline.getEndDate() + ")";
        } else if (task instanceof Event) {
            Event event = (Event) task;
            resultString = "[E]" + isCompleteStatus(task) + event.getTaskDescription() + " (from: " + event.getStartDate() + " to: " + event.getEndDate() + ")";
        } else {
            resultString = isCompleteStatus(task) + task.getTaskDescription();
        }
        return resultString;
    }

    /**
     * Returns the bracketed completion indicating status for a task.
     *
     * @param task the task whose status is to be checked
     * @return {@code "[X] "} if complete; otherwise {@code "[ ] "}
     */
    public String isCompleteStatus(Task task) {
        return task.isCompleted() ? "[X] " : "[ ] ";
    }

    /**
     * Renders a confirmation that a task was successfully deleted.
     *
     * @param deletedTask the task that was deleted
     */
    public void successfullyDeletedTask(Task deletedTask) {
        System.out.println("the Task: " + deletedTask.getTaskDescription() + " has successfully been deleted.");
    }

    /**
     * Renders a list of tasks returned from a search/filter operation.
     *
     * @param tasks the tasks that matched the search
     */
    public void viewFoundTasks (java.util.List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println(" " + task.getTaskId() + "." + viewTask(task));
        }
    }

    /**
     * Prints the supported command list and usage hints for this chatbot.
     * <p>
     * DISCLAIMER: GPT was used partly to generate this list of commands.
     * </p>
     */
    public static void showInstructions() {
        //DISCLAIMER: GPT was used partly to generate this list of commands.
        System.out.println("List of commands:");
        System.out.println("  help                                  : Shows this instruction list.");
        System.out.println("  todo <description>                    : Adds a Todo task.");
        System.out.println("  deadline <desc> /by <date>            : Adds a Deadline task with a due date.");
        System.out.println("  event <desc> /from <start> /to <end>  : Adds an Event task with start and end times.");
        System.out.println("  list                                  : Shows all tasks.");
        System.out.println("  find <keyword>                        : Show list with tasks including the keyword.");
        System.out.println("  mark <task number>                    : Marks a task as done.");
        System.out.println("  unmark <task number>                  : Marks a task as not done.");
        System.out.println("  delete <task number>                  : Marks a task as not done.");
        System.out.println("  find <key word>                       : Marks a task as not done.");
        System.out.println("  bye.                                  : Exits the chatbot.");
    }
}