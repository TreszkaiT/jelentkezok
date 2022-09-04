package com.example.app.exception;

public class InvalidBeanWriteException extends RuntimeException{

    //private List<String> message;

    public InvalidBeanWriteException(String message) {  // , List<String> messages) {
        super(message);
        //this.message = messages;
    }

    //@Override
    //public List<String> getMessage() {return  message;};
}
