package com.example.lab1.services;

public class ServiceResult {
    public ServiceCode id;
    public String message;

    public ServiceResult(ServiceCode id, String message){
        this.id = id;
        this.message = message;
    }
}
