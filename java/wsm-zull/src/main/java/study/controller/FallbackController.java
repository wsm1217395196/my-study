package study.controller;

import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用熔断器
 */
@RestController
@RequestMapping("common")
public class FallbackController {

    @RequestMapping("fallback")
    public ResultView fallback() {
        return ResultView.error(ResultEnum.CODE_3);
    }
}
