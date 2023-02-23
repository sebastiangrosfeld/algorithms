package pl.edu.pw.ee.exception;

public class ReadingGraphFromFileException extends RuntimeException {

    public ReadingGraphFromFileException(String message) {
        super(message);
    }

    public ReadingGraphFromFileException(String message, Throwable cause) {
        super(message, cause);
    }

}
