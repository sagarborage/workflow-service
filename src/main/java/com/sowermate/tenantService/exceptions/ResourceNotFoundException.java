package com.sowermate.tenantService.exceptions;

public class ResourceNotFoundException extends RuntimeException {
/*
    public  ResourceNotFoundException(){
        super("Resource not found on server !!");
    }
    public  ResourceNotFoundException(String message){
        super(message);
    }*/

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s is not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
