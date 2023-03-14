package ru.otus.exceptions;


import org.thymeleaf.exceptions.TemplateInputException;

public class KladrException extends TemplateInputException {
    public KladrException(String message) {
        super(message);
    }
}