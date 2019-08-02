package com.study.exception;

import com.study.result.ResultView;

/**
 * 自定义运行时异常
 */
public class MyRuntimeException extends RuntimeException {

    private ResultView resultView;

    public MyRuntimeException(ResultView resultView) {
        super(resultView.getMsg());
        this.resultView = resultView;
    }

    public ResultView getResultView() {
        return resultView;
    }

    public void setResultView(ResultView resultView) {
        this.resultView = resultView;
    }
}
