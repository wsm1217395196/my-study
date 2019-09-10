package com.study.exception;

import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyRuntimeException.class)
    public ResultView defaultErrorHandler(MyRuntimeException e) {
        e.printStackTrace();
        return e.getResultView();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResultView defaultErrorHandler(RuntimeException e) {
        e.printStackTrace();
        return ResultView.error(ResultEnum.CODE_666);
    }
}
