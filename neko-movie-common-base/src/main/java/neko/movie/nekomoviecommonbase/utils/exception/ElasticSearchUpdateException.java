package neko.movie.nekomoviecommonbase.utils.exception;

public class ElasticSearchUpdateException extends RuntimeException {
    public ElasticSearchUpdateException(){

    }

    public ElasticSearchUpdateException(String message) {
        super(message);
    }
}
