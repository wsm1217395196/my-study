package com.study.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.study.result.ResultView;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 服务异常处理
 */
@Data
@RestControllerAdvice
public class ServiceExceptionHandler extends GlobalExceptionHandler {

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    @ExceptionHandler(value = BlockException.class)
    public ResultView blockExceptionHandler(BlockException e) {
        return ResultView.error("请求被拦截，拦截类型为 " + e.getClass().getSimpleName());
    }

    public ServiceExceptionHandler() {
    }

    public ServiceExceptionHandler(String maxRequestSize) {
        super(maxRequestSize);
    }
}
