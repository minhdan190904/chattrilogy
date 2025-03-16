package org.example.chattrilogy.domain;

public class RestResponse<T> {
    private int statusCode;
    private String error;
    private T data;
    private Object message;

    public int getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public T getData() {
        return data;
    }

    public Object getMessage() {
        return message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(Object message) {
        this.message = message;
    }



}
