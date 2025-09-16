package UI;

import Model.TaskVariants.Deadline;
import Model.TaskVariants.Event;
import Model.TaskVariants.Task;
import Model.TaskVariants.Todo;

public class View {
    public static final String INLINE_TEXT_LINES = "____________________________________________________________";
    public static final String BOT_NAME = "Bruce";
    public static final String BOT_LOGO =
            "██████  ██████  ██    ██  ██████ ███████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██████  ██    ██ ██      █████\n" +
                    "██   ██ ██   ██ ██    ██ ██      ██\n" +
                    "██████  ██   ██  ██████   ██████ ███████";


    public static void printLine() {
        System.out.println(INLINE_TEXT_LINES);
    }

    public static void greetUser() {
        View.printLine();
        System.out.println(BOT_LOGO + "\n" + INLINE_TEXT_LINES);
        System.out.println("Hello! I'm " + BOT_NAME + "!");
        System.out.println("For full list of instructions, type: help");
        System.out.println("What can I do for you?\n" + INLINE_TEXT_LINES);
    }

    public void viewTaskAdded(Task task, int total) {
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + viewTask(task));
        System.out.println(" Now you have " + total + " tasks in the list.");
    }

    public static void viewExit() {
        System.out.println("Bye.");
    }

    public void viewError() {
        System.out.println("Error. Please try again.");
    }

    public void viewError(String message) {
        System.out.println(message);
    }

    public void viewTaskList(java.util.List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (Task task : tasks) {
            System.out.println(" " + task.getTaskId() + "." + viewTask(task));
        }
    }

    public void viewTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + viewTask(task));
    }

    public void viewTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + viewTask(task));
    }

    private String viewTask(Task task) {
        String resultString = "";
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

    public String isCompleteStatus(Task task) {
        return task.isCompleted() ? "[X] " : "[ ] ";
    }

    public void succesfullyDeletedTask(Task deletedTask) {
        System.out.println("the Task: " + deletedTask.getTaskDescription() + " has successfully been deleted.");
    }

    public static void showInstructions() {
        //DISCLAIMER: ChatGPT was used to generate this list of commands.
        System.out.println("List of commands:");
        System.out.println("  todo <description>                    : Adds a Todo task.");
        System.out.println("  deadline <desc> /by <date>            : Adds a Deadline task with a due date.");
        System.out.println("  event <desc> /from <start> /to <end>  : Adds an Event task with start and end times.");
        System.out.println("  list                                  : Shows all tasks.");
        System.out.println("  mark <task number>                    : Marks a task as done.");
        System.out.println("  unmark <task number>                  : Marks a task as not done.");
        System.out.println("  bye.                                  : Exits the chatbot.");
        System.out.println("  help                                  : Shows this instruction list.");
    }
}