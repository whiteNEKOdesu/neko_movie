package neko.movie.nekomoviecommonbase.utils.exception;

public class OrderOverTimeException extends RuntimeException {
    public OrderOverTimeException(){

    }

    public OrderOverTimeException(String message) {
        super(message);
    }
}
