package com.study.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用结果视图类
 */
@Data
@ApiModel(value = "ResultView对象", description = "通用结果视图类")
public class ResultView<T> {
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 消息
     */
    @ApiModelProperty(value = "消息")
    private String msg;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private T data;

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
    public static <T> ResultView<T> success(T data) {
        return new ResultView(data);
    }

    /**
     * 错误
     *
     * @return 结果视图
     */
    public static ResultView error() {
        return new ResultView(ResultEnum.CODE_2.getCode(), ResultEnum.CODE_2.getMsg());
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
     * 调用服务的错误
     *
     * @param serviceName 服务名
     * @return 结果视图
     */
    public static ResultView hystrixError(String serviceName) {
        ResultEnum resultEnum = ResultEnum.CODE_3;
        String msg = resultEnum.getMsg().replace("xxx", serviceName);
        return new ResultView(resultEnum.getCode(), msg);
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

    /**
     * 参数错误
     *
     * @param msg 错误消息
     * @return 结果视图
     */
    public static ResultView validError(String msg) {
        return new ResultView(ResultEnum.CODE_400.getCode(), msg);
    }

    private ResultView() {
        this.code = ResultEnum.CODE_1.getCode();
        this.msg = ResultEnum.CODE_1.getMsg();
    }

    private ResultView(T data) {
        this.data = data;
        this.code = ResultEnum.CODE_1.getCode();
        this.msg = ResultEnum.CODE_1.getMsg();
    }

    private ResultView(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
