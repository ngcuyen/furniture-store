package com.hutech.furniturestore.constants;

public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    private String dateTime;
    private String messageConstants;

    public ApiResponse(int statusCode, String message, T data, String dateTime, String messageConstants) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.dateTime = dateTime;
        this.messageConstants = messageConstants;
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessageConstants() {
        return messageConstants;
    }

    public void setMessageConstants(String messageConstants) {
        this.messageConstants = messageConstants;
    }
}
