package com.example.schoolmanagementsoftware.Api;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}