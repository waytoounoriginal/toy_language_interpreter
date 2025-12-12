package model.exceptions;

public class VariableNotDeclaredException extends ToyException {
    public VariableNotDeclaredException(String message) {
        super(message);
    }
    public VariableNotDeclaredException() {
        super("Variable was not previously declared");
    }
}
