package neko.movie.nekomoviecommonbase.utils.exception;

public class FileDeleteFailureException extends RuntimeException {
    public FileDeleteFailureException(){

    }

    public FileDeleteFailureException(String message) {
        super(message);
    }
}
