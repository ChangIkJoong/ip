package Exceptions;

public class UnknownInputException extends DukeException {
    public UnknownInputException(String inputCommand) {
        super("Input command: " + inputCommand + " is invalid. Type \"help\" for valid input commands.");
    }
}
