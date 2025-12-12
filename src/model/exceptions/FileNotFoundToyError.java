package model.exceptions;

public class FileNotFoundToyError extends ToyException{
    public FileNotFoundToyError(String message) {
        super(message);
    }
    public FileNotFoundToyError() {
        super("File was not found.");
    }
}
