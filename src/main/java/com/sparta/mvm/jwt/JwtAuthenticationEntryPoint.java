package com.sparta.mvm.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mvm.exception.ErrorEnum;
import com.sparta.mvm.exception.TestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Enumeration;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        Enumeration<String> exceptionName = request.getAttributeNames();
        while(exceptionName.hasMoreElements())
        {
            String exception = exceptionName.nextElement();
            if(exception.equals("NOT_VALID_TOKEN")){
                ErrorEnum e = (ErrorEnum)request.getAttribute(exception);
                TestResponse testResponse = new TestResponse(e.getStatusCode(), e.getMsg());
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(testResponse));
            }
            else if(exception.equals("EXPIRED_TOKEN")){
                ErrorEnum e = (ErrorEnum)request.getAttribute(exception);
                TestResponse testResponse = new TestResponse(e.getStatusCode(), e.getMsg());
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(testResponse));
            }
            else if(exception.equals("USER_NOT_FOUND")){
                ErrorEnum e = (ErrorEnum)request.getAttribute(exception);
                TestResponse testResponse = new TestResponse(e.getStatusCode(), e.getMsg());
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(testResponse));
            }
            else if(exception.equals("BAD_PASSWORD")){
                ErrorEnum e = (ErrorEnum)request.getAttribute(exception);
                TestResponse testResponse = new TestResponse(e.getStatusCode(), e.getMsg());
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(testResponse));
            }
        }
    }
}
