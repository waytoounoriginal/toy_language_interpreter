package model.exceptions;

public class EmptyExecutionStackException extends ToyException {
  public EmptyExecutionStackException(String message) {
    super(message);
  }
  public EmptyExecutionStackException() {
    super("Execution stack is empty.");
  }
}
