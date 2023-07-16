package neko.movie.nekomoviecommonbase.utils.exception;

public class FileTypeNotSupportException extends RuntimeException {
    public FileTypeNotSupportException(){

    }

    public FileTypeNotSupportException(String message) {
        super(message);
    }
}
