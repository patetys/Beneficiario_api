package br.com.sompo.cotacaomulti.exception;

public class InvalidCnpjCpfException extends RuntimeException {
    public InvalidCnpjCpfException(String message) {
        super(message);
    }

    public InvalidCnpjCpfException(Throwable cause) {
        super(cause);
    }

    public InvalidCnpjCpfException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCnpjCpfException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
