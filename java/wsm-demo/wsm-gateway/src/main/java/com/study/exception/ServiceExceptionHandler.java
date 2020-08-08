package com.study.exception;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 服务异常处理
 */
@Data
@RestControllerAdvice
public class ServiceExceptionHandler extends GlobalExceptionHandler {

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    public ServiceExceptionHandler() {
    }

    public ServiceExceptionHandler(String maxRequestSize) {
        super(maxRequestSize);
    }
}
