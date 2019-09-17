package study.exception;

import com.study.exception.MyRuntimeException;
import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MyRuntimeException.class)
    public ResultView defaultErrorHandler(MyRuntimeException e) {
        e.printStackTrace();
        return e.getResultView();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResultView defaultErrorHandler(RuntimeException e) {

        if (e instanceof MaxUploadSizeExceededException) {
            return ResultView.error("上传的文件不能大于" + maxRequestSize + "！");
        }

        e.printStackTrace();
        createLogger(e);
        return ResultView.error(ResultEnum.CODE_666);
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
