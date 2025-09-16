package Model;

import Model.TaskVariants.Task;

import java.util.ArrayList;

public class Model {
    private final ArrayList<Task> tasks;
    private static Model INSTANCE = new Model();

    private Model() {
        dataHandler.getInstance();
        tasks = dataHandler.getInstance().loadTasks();
    }

    public static Model getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public boolean markDone(int index) {
        if (rangeCheck(index)) {
            tasks.get(index).markDone();
            return true;
        }
        return false;
    }

    public boolean unmarkDone(int index) {
        if (rangeCheck(index)) {
            tasks.get(index).unmarkDone();
            return true;
        }
        return false;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    private boolean rangeCheck(int index) {
        return (index >= 0 && index < tasks.size());
    }

    public Task deleteTask(int taskIndex) {
        Task task = tasks.remove(taskIndex);
        updateCounter(taskIndex);
        return task;
    }

    private void updateCounter(int taskIndex) {
        for (int i = taskIndex; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            task.setTaskId(i + 1);
        }
        Task.decrementCounter();
    }

    //TODO add a sort by date?? :))
    /*private void sortTasks(int taskIndex) {
        for (int i = taskIndex; i < tasks.size(); i++) {
        }
    }*/
}
