package Model;

import Model.TaskVariants.Task;

import java.util.ArrayList;

public class Model {
    private final ArrayList<Task> tasks = new ArrayList<>();

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
}
