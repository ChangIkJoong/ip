package Model;

import Exceptions.BruceException;
import Exceptions.UnknownInputException;
import Model.TaskVariants.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.List;

public class dataHandler {
    private static dataHandler INSTANCE = null;

    private static final Path DIRECTORY = Paths.get(System.getProperty("user.home"), "bruceData");
    private static final Path FILE = DIRECTORY.resolve("bruce.txt");

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

    public static dataHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new dataHandler();
        }
        return INSTANCE;
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(FILE)) {
            return tasks;
        }
        loadInput(tasks);
        return tasks;
    }

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
        try (FileWriter fw = new FileWriter(FILE.toString())) {
            for (Task task : tasks) {
                encodeTask(task, fw);
            }
        } catch (IOException e) {
            System.err.println("Error while saving tasks in I/O: " + e.getMessage());
        }
    }

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