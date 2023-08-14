package neko.movie.nekomoviecommonbase.utils.exception;

public class RabbitMQMessageRejectException extends RuntimeException {
    public RabbitMQMessageRejectException(){

    }

    public RabbitMQMessageRejectException(String message) {
        super(message);
    }
}
