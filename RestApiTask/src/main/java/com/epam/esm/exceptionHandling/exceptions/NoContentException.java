package com.epam.esm.exceptionHandling.exceptions;

public class NoContentException extends RuntimeException{
    public NoContentException(){
        super("There is not any content");
    }
}
