package com.sparta.mvm.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// 모든 API(Controller)가 호출될 때,
// Request 정보(Request URL, HTTP Method)를 Log로 출력
@Component
@Aspect
@Slf4j
public class Logger {
    @Pointcut("execution(* com.sparta.mvm.controller.*.*(..))")
    private void allController() {
    }

    @Before("allController()")
    public void beforeRequestInfo(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info(request.getMethod() + " : " + request.getRequestURI());
    }

}
