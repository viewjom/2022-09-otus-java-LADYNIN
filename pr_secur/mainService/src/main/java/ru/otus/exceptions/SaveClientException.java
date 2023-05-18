package ru.otus.exceptions;

import org.thymeleaf.exceptions.TemplateInputException;

public class SaveClientException extends TemplateInputException {
    public SaveClientException(String message) {
        super(message);
    }
}
