package model.exceptions;

public class FileAlreadyOpenedException extends ToyException {
    public FileAlreadyOpenedException(String message) {
        super(message);
    }
    public FileAlreadyOpenedException() {
        super("File is already open");
    }
}
