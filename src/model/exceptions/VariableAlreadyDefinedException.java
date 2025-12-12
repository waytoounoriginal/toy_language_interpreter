package model.exceptions;

public class VariableAlreadyDefinedException extends ToyException {
    public VariableAlreadyDefinedException(String message) {
        super(message);
    }

    public VariableAlreadyDefinedException() {
        super("Variable was already declared!");
    }
}
