public class Task {
    private String taskDescription;
    private boolean completed;
    private int taskId;
    private static int counter = 1;

    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
        this.completed = false;
        this.taskId = counter++;
    }

    public void setTaskID(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markDone() {
        this.completed = true;
    }

    public void unmarkDone() {
        this.completed = false;
    }


}
