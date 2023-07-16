package neko.movie.nekomoviecommonbase.utils.exception;

public class UserNameRepeatException extends RuntimeException {
    public UserNameRepeatException(){

    }

    public UserNameRepeatException(String message) {
        super(message);
    }
}
