//package com.sparta.mvm.exception;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler({CustomException.class})
//    protected ResponseEntity handleCustomException(CustomException ex) {
//        return ResponseEntity.ok().body(CommonResponse.builder()
//                .statusCode(ex.getStatusEnum().getStatusCode())
//                .msg(ex.getStatusEnum().getMsg())
//                .build());
//    }
//
//    // 나머지 에러
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<CommonResponse> handleException(Exception ex) {
//        return ResponseEntity.ok().body(CommonResponse.builder()
//                .statusCode(400)
//                .msg(ex.getMessage())
//                .build());
//    }
//}
