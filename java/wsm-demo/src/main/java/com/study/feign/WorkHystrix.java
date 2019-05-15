package com.study.feign;

import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.springframework.stereotype.Component;

@Component
public class WorkHystrix implements WorkFeign {

    @Override
    public ResultView getById(Long id) {
        return ResultView.error(ResultEnum.CODE_3);
    }
}
