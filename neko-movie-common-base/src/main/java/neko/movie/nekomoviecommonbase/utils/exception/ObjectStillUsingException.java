package neko.movie.nekomoviecommonbase.utils.exception;

public class ObjectStillUsingException extends RuntimeException {
    public ObjectStillUsingException(){

    }

    public ObjectStillUsingException(String message) {
        super(message);
    }
}
