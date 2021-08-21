package com.epam.esm.exceptionHandling.exceptions;

public class ClientError extends RuntimeException{

    public ClientError(){
        super("Client Side Error");
    }
}
