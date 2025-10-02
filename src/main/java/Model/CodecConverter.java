package Model;

import Exceptions.BruceException;
import Exceptions.UnknownInputException;

import Model.TaskVariants.Deadline;
import Model.TaskVariants.Event;
import Model.TaskVariants.Task;
import Model.TaskVariants.Todo;


/**
 * Converts {@link Task} objects to and from a text file format.
 * <p>
 * The on-disk line format is:
 * </p>
 * <ul>
 *   <li><strong>Todo</strong>: {@code T | <completed> | <description>}</li>
 *   <li><strong>Deadline</strong>: {@code D | <completed> | <description> | <byDate>}</li>
 *   <li><strong>Event</strong>: {@code E | <completed> | <description> | <start> | <end>}</li>
 * </ul>
 */
public class CodecConverter {
    /**
     * Encodes {@link Task} to a converted string suitable for text file saving.
     *
     * @param task the task to encode
     * @return the encoded line
     * @throws BruceException if the task type is unsupported
     */
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

    /**
     * Encodes a {@link Deadline} task as {@code D | <completed> | <description> | <byDate>}.
     *
     * @param task the deadline task
     * @return the encoded string
     */
    private static String deadlineCodec(Deadline task) {
        String taskVariant = "D";
        String completionBoolean = String.valueOf(task.isCompleted());
        String taskDescription = task.getTaskDescription();
        String deadlineDate = String.valueOf(task.getEndDate());

        String outputString = taskVariant + " | " +
                completionBoolean + " | " +
                taskDescription + " | " +
                deadlineDate;

        return outputString;
    }

    /**
     * Encodes an {@link Event} task as
     * {@code E | <completed> | <description> | <start> | <end>}.
     *
     * @param task the event task
     * @return the encoded string
     */
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

    /**
     * Encodes a to-do task as {@code T | <completed> | <description>}.
     *
     * @param task the to-do task
     * @return the encoded string
     */
    private static String toDoCodec(Todo task) {
        String taskVariant = "T";
        String completionBoolean = String.valueOf(task.isCompleted());
        String taskDescription = task.getTaskDescription();

        String outputString = taskVariant + " | " +
                completionBoolean + " | " +
                taskDescription;

        return outputString;
    }

    /**
     * Decodes a line (inputString) into a {@link Task} that the model can process.
     *
     * @param inputString the raw line read in
     * @return a new {@link Task} reconstructed from the line
     * @throws UnknownInputException if the line is malformed or the variant is unknown
     */
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

    /**
     * Creates a {@link Task} from the input tokens and variants.
     *
     * @param inputString original line
     * @param dataInput   tokenized fields
     * @param taskVariant variant code ({@code 'T'}, {@code 'D'}, {@code 'E'})
     * @return the constructed task
     * @throws UnknownInputException if the variant code is not recognized
     */
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

    /**
     * Validates and constructs an {@link Event} from tokens:
     * {@code E | <completed> | <description> | <start> | <end>}.
     *
     * @param inputString     original line
     * @param dataInput       tokenized fields
     * @param taskDescription description field from the input
     * @return a new {@link Event}
     * @throws UnknownInputException if the token count is not exactly 5 (length of an Event)
     */
    private static Event decodeEvent(String inputString, String[] dataInput, String taskDescription) throws UnknownInputException {
        if (dataInput.length != 5) {
            throw new UnknownInputException("Bad input line: " + inputString);
        }
        return new Event(taskDescription, dataInput[3], dataInput[4]);
    }

    /**
     * Validates and constructs a {@link Deadline} from tokens:
     * {@code D | <completed> | <description> | <byDate>}.
     *
     * @param inputString     original line
     * @param dataInput       tokenized fields
     * @param taskDescription description field from the input
     * @return a new {@link Deadline}
     * @throws UnknownInputException if the token count is not exactly 4 (length of a Deadline)
     */
    private static Deadline decodeDeadline(String inputString, String[] dataInput, String taskDescription) throws UnknownInputException {
        if (dataInput.length != 4) {
            throw new UnknownInputException("Bad input line: " + inputString);
        }
        return new Deadline(taskDescription, dataInput[3]);
    }
}