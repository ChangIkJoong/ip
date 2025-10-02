package Model;

import Exceptions.BruceException;
import Exceptions.UnknownInputException;
import Model.TaskVariants.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;


/**
 * Simple file-backed process layer for Bruce tasks.
 * Stores tasks in a UTF-8 text file using {@link CodecConverter}'s
 * codec format. The file is created under the user's home directory:
 * <pre>{@code ${user.home}/bruceData/bruce.txt}</pre>
 */
public class dataHandler {
    /**
     * Singleton instance.
     */
    private static dataHandler INSTANCE = null;

    /**
     * Directory used for storing Bruce data (e.g., {@code ~/bruceData}).
     */
    private static final Path DIRECTORY = Paths.get(System.getProperty("user.home"), "bruceData");
    /**
     * Main data file (e.g., {@code ~/bruceData/bruce.txt}).
     */
    private static final Path FILE = DIRECTORY.resolve("bruce.txt");

    /**
     * Initializes and ensures the data directory and file exist.
     *
     * @throws RuntimeException if the directory or file cannot be created
     */
    private dataHandler() {
        try {
            if (!Files.exists(DIRECTORY)) {
                Files.createDirectories(DIRECTORY);
            }
            if (!Files.exists(FILE)) {
                Files.createFile(FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize directory, check directory and file: ", e);
        }
    }

    /**
     * Returns the singleton instance of the data handler.
     *
     * @return global {@code dataHandler} instance
     */
    public static dataHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new dataHandler();
        }
        return INSTANCE;
    }

    /**
     * Loads all tasks from disk.
     * If the data file does not exist yet, returns an empty list.
     * Lines that cannot be decoded will throw a {@link RuntimeException}
     * from {@link #convertInput(ArrayList, String)}.
     *
     * @return {@link ArrayList} containing the loaded tasks
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(FILE)) {
            return tasks;
        }
        loadInput(tasks);
        return tasks;
    }

    /**
     * Reads all lines from the data file and converts each to a {@link Task}.
     * I/O errors are logged to command.
     *
     * @param tasks list to append decoded tasks into
     */
    private static void loadInput(ArrayList<Task> tasks) {
        try {
            List<String> inputList = Files.readAllLines(FILE);
            for (String input : inputList) {
                convertInput(tasks, input);
            }
        } catch (IOException e) {
            System.err.println("Error while loading tasks from I/O: " + e.getMessage());
        }
    }

    /**
     * Converts a single file line into a {@link Task} and appends it to {@code tasks}.
     * Error lines throw a {@link RuntimeException}
     *
     * @param tasks list to receive the decoded task
     * @param input raw line from the data file
     */
    private static void convertInput(ArrayList<Task> tasks, String input) {
        if (input.trim().isEmpty()) {
            return;
        }
        try {
            Task task = CodecConverter.decodeFromFile(input);
            tasks.add(task);
        } catch (UnknownInputException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Process all tasks to disk, overwriting the existing file.
     * Each task is written on its own line using {@link CodecConverter#encodeToFile(Task)}.
     * I/O errors are logged to command.
     *
     * @param tasks the tasks to save
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try (FileWriter fw = new FileWriter(FILE.toString())) {
            for (Task task : tasks) {
                encodeTask(task, fw);
            }
        } catch (IOException e) {
            System.err.println("Error while saving tasks in I/O: " + e.getMessage());
        }
    }

    /**
     * Encodes a task and writes it to the provided {@link FileWriter}, followed by a newline.
     *
     * @param task the task to encode and write
     * @param fw   open file writer positioned for writing
     * @throws IOException if the write operation fails
     */
    private static void encodeTask(Task task, FileWriter fw) throws IOException {
        String encodedTask;
        try {
            encodedTask = CodecConverter.encodeToFile(task);
        } catch (BruceException e) {
            throw new RuntimeException(e);
        }
        fw.write(encodedTask + System.lineSeparator());
    }
}