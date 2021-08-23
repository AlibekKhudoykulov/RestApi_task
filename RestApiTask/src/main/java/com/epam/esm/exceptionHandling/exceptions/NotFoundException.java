package com.epam.esm.exceptionHandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    public NotFoundException(Integer id){
        super("Requested resource not found (id = "+ id+")");
    }
    public NotFoundException(String name){
        super("Requested resource not found (name = "+ name+")");
    }

}
