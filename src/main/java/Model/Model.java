package Model;

import Model.TaskVariants.Task;

import java.util.ArrayList;
//import java.time.LocalDate;

/**
 * The model for managing {@link Task} items (MVC).
 * <p>
 * Provides a simple singleton-backed store with operations:
 * add, read, mark/unmark completion, delete, and count. Task indices
 * are zero-based internally, while {@code Task.taskId} is maintained
 * as in model stored via {@link #updateCounter(int)}.
 * </p>
 */

public class Model {
    /** The program's main list for all tasks (in insertion order). */
    private final ArrayList<Task> tasks;

    /**
     * Initializes the model, then loading tasks from persistence via {@code dataHandler}.
     */
    private static Model INSTANCE = new Model();
    private Model() {
        dataHandler.getInstance();
        tasks = dataHandler.getInstance().loadTasks();
    }

    /**
     * Returns the singleton instance of the {@code Model}.
     * <p>
     * Lazily re-initializes if the instance was cleared (defensive).
     * </p>
     *
     * @return the global {@code Model} instance
     */
    public static Model getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task the task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieves the task at a given zero-based index.
     *
     * @param index the index to fetch from the task list.
     * @return the task at {@code index}
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the instance in model with the list of tasks.
     * Mutations of the returned list will affect the model directly.
     *
     * @return the backing {@link ArrayList} of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Marks the task at {@code index} as complete, if within range.
     *
     * @param index zero-based index of the task to mark
     * @return {@code true} if the task was succesfully marked, {@code false} if {@code index} is not within range.
     */
    public boolean markDone(int index) {
        if (rangeCheck(index)) {
            tasks.get(index).markDone();
            return true;
        }
        return false;
    }

    /**
     * Marks the task at {@code index} as not complete, if within range.
     *
     * @param index zero-based index of the task to unmark
     * @return {@code true} if the task was succesfully unmarked, {@code false} if {@code index} was invalid.
     */
    public boolean unmarkDone(int index) {
        if (rangeCheck(index)) {
            tasks.get(index).unmarkDone();
            return true;
        }
        return false;
    }

    /**
     * Returns the number of tasks currently in the model.
     *
     * @return task count
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Deletes the task at {@code taskIndex} and updates the task IDs afterward.
     *
     * @param taskIndex zero-based index of the task to remove
     * @return the removed task
     * @throws IndexOutOfBoundsException if {@code taskIndex} is out of range
     */
    public Task deleteTask(int taskIndex) {
        Task task = tasks.remove(taskIndex);
        updateCounter(taskIndex);
        return task;
    }

    /**
     * Reassigns one-based {@code taskId}s after a deletion and decrements the global counter.
     * After removing the task at {@code taskIndex}, it updates each remaining task's {@code taskId}.
     *
     * @param taskIndex the index from which to start re-numbering.
     */
    private void updateCounter(int taskIndex) {
        for (int i = taskIndex; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            task.setTaskId(i + 1);
        }
        Task.decrementCounter();
    }

    private boolean rangeCheck(int index) {
        return (index >= 0 && index < tasks.size());
    }

}
