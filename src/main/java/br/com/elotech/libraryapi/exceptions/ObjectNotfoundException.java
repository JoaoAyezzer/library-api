package br.com.elotech.libraryapi.exceptions;

public class ObjectNotfoundException extends RuntimeException {
    public ObjectNotfoundException(String message) {
        super(message);
    }
}
