package com.study.feign;

import com.study.MyConstant;
import com.study.result.ResultView;
import org.springframework.stereotype.Component;

@Component
public class WorkHystrix implements WorkFeign {

    @Override
    public ResultView getById(Long id) {
        return ResultView.hystrixError(MyConstant.wsm_work);
    }
}
