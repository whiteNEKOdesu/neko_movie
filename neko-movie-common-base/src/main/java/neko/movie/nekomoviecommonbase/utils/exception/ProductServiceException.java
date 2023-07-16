package neko.movie.nekomoviecommonbase.utils.exception;

public class ProductServiceException extends RuntimeException {
    public ProductServiceException(){

    }

    public ProductServiceException(String message) {
        super(message);
    }
}
