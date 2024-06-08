package com.sparta.mvm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TestResponse {
    private final int statusCode;
    private final String msg;
}
