package model.exceptions;

public class InvalidCommandArgumentsException extends ToyException {
    public InvalidCommandArgumentsException(String message) {
        super(message);
    }
    public InvalidCommandArgumentsException() {
        super("Invalid command arguments passed to command.");
    }
}
