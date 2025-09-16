package Model;

import Exceptions.BruceException;
import Exceptions.UnknownInputException;
import Model.TaskVariants.Task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.List;

public class dataHandler {
    private static dataHandler INSTANCE = null;

    private static final Path DATA_DIR = Paths.get(System.getProperty("user.home"), "bruceData");
    private static final Path DATA_FILE = DATA_DIR.resolve("bruce.txt");

    private dataHandler() {
        try {
            if (!Files.exists(DATA_DIR)) {
                Files.createDirectories(DATA_DIR);
            }
            if (!Files.exists(DATA_FILE)) {
                Files.createFile(DATA_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize directory, check directory and file: ", e);
        }
    }

    public static dataHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new dataHandler();
        }
        return INSTANCE;
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(DATA_FILE)) {
            return tasks;
        }
        loadInput(tasks);
        return tasks;
    }

    private static void loadInput(ArrayList<Task> tasks) {
        try {
            List<String> inputList = Files.readAllLines(DATA_FILE);
            for (String input : inputList) {
                convertInput(tasks, input);
            }
        } catch (IOException e) {
            System.err.println("Error while loading tasks from I/O: " + e.getMessage());
        }
    }

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

    public void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = Files.newBufferedWriter(DATA_FILE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            for (Task task : tasks) {
                encodeTask(task, writer);
            }
        } catch (IOException e) {
            System.err.println("Error while saving tasks in I/O: " + e.getMessage());
        }
    }

    private static void encodeTask(Task task, BufferedWriter writer) throws IOException {
        String encoded;
        try {
            encoded = CodecConverter.encodeToFile(task);
        } catch (BruceException e) {
            throw new RuntimeException(e);
        }
        writer.write(encoded);
        writer.newLine();
    }
}