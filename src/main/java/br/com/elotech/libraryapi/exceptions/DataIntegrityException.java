package br.com.elotech.libraryapi.exceptions;

public class DataIntegrityException extends RuntimeException{

    public DataIntegrityException(String message) {
        super(message);
    }
}
