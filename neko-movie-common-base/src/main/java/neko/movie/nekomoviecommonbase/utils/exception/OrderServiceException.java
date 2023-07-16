package neko.movie.nekomoviecommonbase.utils.exception;

public class OrderServiceException extends RuntimeException {
    public OrderServiceException(){

    }

    public OrderServiceException(String message) {
        super(message);
    }
}
