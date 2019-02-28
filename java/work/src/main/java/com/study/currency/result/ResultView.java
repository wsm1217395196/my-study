package com.study.currency.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 结果视图类
 */
@ApiModel(value = "结果视图类", description = "")
public class ResultView {
    /**
     * 状态码
     */
    @ApiModelProperty(name = "code", value = "状态码", example = "200")
    private Integer code;

    /**
     * 消息
     */
    @ApiModelProperty(name = "msg", value = "消息", example = "操作成功")
    private String msg;

    /**
     * 数据
     */
    @ApiModelProperty(name = "data", value = "数据", example = "")
    private Object data;

//    /**
//     * 时间
//     */
//    @ApiModelProperty(name = "time", value = "时间", example = "1541486801195")
//    private long time;

    /**
     * 成功
     *
     * @return 结果视图
     */
    public static ResultView success() {
        return new ResultView();
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return 结果视图
     */
    public static ResultView success(Object data) {
        return new ResultView(data);
    }

    /**
     * 错误
     *
     * @param resultEnum 结果枚举
     * @return 结果视图
     */
    public static ResultView error(ResultEnum resultEnum) {
        return new ResultView(resultEnum.getCode(), resultEnum.getMsg());
    }

    /**
     * 自定义错误消息
     *
     * @param msg 错误消息
     * @return 结果视图
     */
    public static ResultView error(String msg) {
        return new ResultView(ResultEnum.CODE_2.getCode(), msg);
    }

    private ResultView() {
        this.code = ResultEnum.CODE_1.getCode();
        this.msg = ResultEnum.CODE_1.getMsg();
    }

    private ResultView(Object data) {
        this.data = data;
        this.code = ResultEnum.CODE_1.getCode();
        this.msg = ResultEnum.CODE_1.getMsg();
//        this.time = System.currentTimeMillis();
    }

    private ResultView(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
//        this.time = System.currentTimeMillis();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
