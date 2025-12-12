package model.exceptions;

public abstract class ToyException extends RuntimeException {

    public ToyException(String message) {
        super(message);
    }
}
