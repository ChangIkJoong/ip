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
        System.out.println("What can I do for you?\n" + INLINE_TEXT_LINES);
    }

    public void viewTaskAdded(Task task, int total) {
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + viewTask(task));
        System.out.println(" Now you have " + total + " tasks in the list.");
    }

    public void viewExit() {
        System.out.println("Bye.");
    }

    public void viewError() {
        System.out.println("Error. Please try again.");
    }

    public void viewError(String message) {
        System.out.println(message);
    }

    public void viewTaskList(java.util.List<Task> tasks) {
        System.out.println(" Here are the tasks in your list:");
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
}