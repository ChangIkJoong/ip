public abstract class Task {
    private String taskDescription;
    private boolean isDone;
    private int taskId;
    private static int counter = 1;

    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
        this.isDone = false;
        this.taskId = counter++;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean isCompleted() {
        return isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmarkDone() {
        this.isDone = false;
    }
}
