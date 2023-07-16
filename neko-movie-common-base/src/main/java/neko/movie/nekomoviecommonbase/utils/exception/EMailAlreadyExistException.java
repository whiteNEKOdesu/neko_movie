package neko.movie.nekomoviecommonbase.utils.exception;

public class EMailAlreadyExistException extends RuntimeException {
    public EMailAlreadyExistException(){

    }

    public EMailAlreadyExistException(String message) {
        super(message);
    }
}
