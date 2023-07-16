package neko.movie.nekomoviecommonbase.utils.exception;

public class RabbitMQSendException extends RuntimeException {
    public RabbitMQSendException(){

    }

    public RabbitMQSendException(String message) {
        super(message);
    }
}
