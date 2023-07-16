package neko.movie.nekomoviecommonbase.utils.exception;

public class NoSuchResultException extends RuntimeException {
    public NoSuchResultException(){

    }

    public NoSuchResultException(String message) {
        super(message);
    }
}
