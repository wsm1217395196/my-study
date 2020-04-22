package com.study.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 运行时异常
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResultView runtimeException(RuntimeException e) {

        if (e instanceof MaxUploadSizeExceededException) {
            return ResultView.error("上传的文件不能大于" + maxRequestSize + "！");
        }

        e.printStackTrace();
        createLogger(e);
        return ResultView.error(ResultEnum.CODE_666);
    }

    /**
     * 自定义运行时异常
     */
    @ExceptionHandler(value = MyRuntimeException.class)
    public ResultView myRuntimeException(MyRuntimeException e) {
        e.printStackTrace();
        return e.getResultView();
    }

    /**
     * service层的参数校验异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultView constraintViolationExceptionHandler(ConstraintViolationException e) {
        // 拼接错误
        StringBuilder detailMessage = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            // 拼接内容到其中
            detailMessage.append(constraintViolation.getMessage());
        }
        // 包装结果
        return ResultView.validError(ResultEnum.CODE_400.getMsg() + "：" + detailMessage);
    }

    /**
     * controller层的参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultView methodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 拼接错误
        StringBuilder detailMessage = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            // 拼接内容到其中
            detailMessage.append(fieldError.getDefaultMessage());
        }
        // 包装结果
        return ResultView.validError(ResultEnum.CODE_400.getMsg() + "：" + detailMessage);
    }

    @ExceptionHandler(value = BlockException.class)
    public ResultView blockExceptionHandler(BlockException e) {
        return ResultView.error("请求被拦截，拦截类型为 " + e.getClass().getSimpleName());
    }


    /**
     * 打印关键log信息
     *
     * @param e
     */
    private void createLogger(Exception e) {
        logger.info(e.getMessage());
        logger.info(e.getStackTrace()[0].toString());

        logger.error(e.getMessage());
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            logger.error(stackTraceElement.toString());
        }
    }
}
