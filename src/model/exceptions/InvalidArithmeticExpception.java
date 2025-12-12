package model.exceptions;

public class InvalidArithmeticExpception extends ToyException {
    public InvalidArithmeticExpception(String message) {
        super(message);
    }

    public InvalidArithmeticExpception() {
        super("Invalid expression.");
    }
}
