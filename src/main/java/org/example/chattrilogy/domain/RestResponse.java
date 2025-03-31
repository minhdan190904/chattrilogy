package org.example.chattrilogy.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestResponse<T> {
    private int statusCode;
    private String error;
    private T data;
    private Object message;
}
