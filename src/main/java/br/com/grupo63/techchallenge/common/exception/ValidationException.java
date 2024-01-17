package br.com.grupo63.techchallenge.common.exception;


public class ValidationException extends GenericException {

    public ValidationException(String name, String description) {
        super(name, description);
    }
}
