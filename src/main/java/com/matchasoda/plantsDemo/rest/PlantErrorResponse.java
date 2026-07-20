package com.matchasoda.plantsDemo.rest;

public class PlantErrorResponse {

    private int status;
    private String errorMessage;

    public PlantErrorResponse() {

    }

    public PlantErrorResponse(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
