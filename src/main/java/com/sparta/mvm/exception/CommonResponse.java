package com.sparta.mvm.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommonResponse<T> {
    private String msg;
    private int statusCode;
    private T data;
}