package neko.movie.nekomoviecommonbase.utils.exception;

public class StockNotEnoughException extends RuntimeException {
    public StockNotEnoughException(){

    }

    public StockNotEnoughException(String message) {
        super(message);
    }
}
