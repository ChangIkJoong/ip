package Model;

import Exceptions.BruceException;
import Exceptions.UnknownInputException;
import Model.TaskVariants.Task;
import Model.TaskVariants.Todo;
import Model.TaskVariants.Deadline;
import Model.TaskVariants.Event;


/* example given in the instructions
T | 1 | read book
D | 0 | return book | June 6th
E | 0 | project meeting | Aug 6th 2-4pm
T | 1 | join sports club
TODO use a ”|" delimiter
*/

public class CodecConverter {

    public static String encodeToFile(Task task) throws BruceException {
        if (task instanceof Deadline) {
            return deadlineCodec((Deadline) task);
        } else if (task instanceof Event) {
            return eventCodec((Event) task);
        } else if (task instanceof Todo) {
            return toDoCodec((Todo) task);
        } else {
            throw new UnknownInputException("Invalid input, please try again.");
        }
    }

    private static String deadlineCodec(Deadline task) {
        String taskVariant = "D";
        String completionBoolean = String.valueOf(task.isCompleted());
        String taskDescription = task.getTaskDescription();
        String deadlineDate = task.getEndDate();

        String outputString = taskVariant + " | " +
                completionBoolean + " | " +
                taskDescription + " | " +
                deadlineDate;

        return outputString;
    }

    private static String eventCodec(Event task) {
        String taskVariant = "E";
        String completionBoolean = String.valueOf(task.isCompleted());
        String taskDescription = task.getTaskDescription();
        String startDate = task.getStartDate();
        String endDate = task.getEndDate();

        String outputString = taskVariant + " | " +
                completionBoolean + " | " +
                taskDescription + " | " +
                startDate + " | " +
                endDate;

        return outputString;
    }

    private static String toDoCodec(Todo task) {
        String taskVariant = "T";
        String completionBoolean = String.valueOf(task.isCompleted());
        String taskDescription = task.getTaskDescription();

        String outputString = taskVariant + " | " +
                completionBoolean + " | " +
                taskDescription;

        return outputString;
    }

    public static Task decodeFromFile(String inputString) throws UnknownInputException {
        String[] dataInput = inputString.split("\\s*\\|\\s*");
        if (dataInput.length < 3) {
            throw new UnknownInputException("Bad input line: " + inputString);
        }

        for (int i = 0; i < dataInput.length; i++) {
            dataInput[i] = dataInput[i].trim();
        }

        char taskVariant = dataInput[0].charAt(0);
        boolean isCompleted = "true".equals(dataInput[1]);
        Task newTask = getInputTask(inputString, dataInput, taskVariant);

        if (isCompleted) {
            newTask.markDone();
        }
        return newTask;
    }

    private static Task getInputTask(String inputString, String[] dataInput, char taskVariant) throws UnknownInputException {
        String taskDescription = dataInput[2];

        Task newTask = switch (taskVariant) {
            case 'T' -> new Todo(taskDescription);
            case 'D' -> decodeDeadline(inputString, dataInput, taskDescription);
            case 'E' -> decodeEvent(inputString, dataInput, taskDescription);
            default -> throw new UnknownInputException("Unknown task variant: " + taskVariant);
        };
        return newTask;
    }

    private static Event decodeEvent(String inputString, String[] dataInput, String taskDescription) throws UnknownInputException {
        if (dataInput.length != 5) {
            throw new UnknownInputException("Bad input line: " + inputString);
        }
        return new Event(taskDescription, dataInput[3], dataInput[4]);
    }

    private static Deadline decodeDeadline(String inputString, String[] dataInput, String taskDescription) throws UnknownInputException {
        if (dataInput.length != 4) {
            throw new UnknownInputException("Bad input line: " + inputString);
        }
        return new Deadline(taskDescription, dataInput[3]);
    }
}