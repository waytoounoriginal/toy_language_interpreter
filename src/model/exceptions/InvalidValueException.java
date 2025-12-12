package model.exceptions;

public class InvalidValueException extends ToyException {
    public InvalidValueException(String message) {
        super(message);
    }
    public InvalidValueException() {
        super("Invalid value encountered.");
    }
}
