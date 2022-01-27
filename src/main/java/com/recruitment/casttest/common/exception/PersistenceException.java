package com.recruitment.casttest.common.exception;

public class PersistenceException extends CommonsException {

    public PersistenceException(){
        super();
    }

    public PersistenceException(String message){
        super(message);
    }

    public PersistenceException(Throwable throwable){
        super(throwable);
    }

    public PersistenceException(String message, Throwable throwable){
        super(message, throwable);
    }

}
