package com.xiaozu.core.base;


import com.xiaozu.core.enums.ResultStatusEnum;
import com.xiaozu.core.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author 80906
 * @date 2018-07-12 交互数据对象
 */
@Getter
@Setter
public class ResultModel<T> implements Serializable {

    private Boolean status = Boolean.TRUE;
    private String code = "200";
    private T data;
    private String msg = "操作成功";

    public ResultModel() {
    }

    public ResultModel(Boolean status) {
        super();
        this.status = status;
    }

    public ResultModel(Boolean status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
    }

    public ResultModel(Boolean status, T data) {
        super();
        this.status = status;
        this.data = data;
    }

    public ResultModel(Boolean status, String code, String msg) {
        super();
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public ResultModel(Boolean status, String code, T data, String msg) {
        super();
        this.status = status;
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static ResultModel getFailResult(String code, String msg) {
        return new ResultModel(Boolean.FALSE, code, msg);
    }

    public static ResultModel getFailResult(ResultStatusEnum status) {
        return new ResultModel(Boolean.FALSE, status.getCode(), status.getMsg());
    }

    public static ResultModel getSuccessResult() {
        return new ResultModel(Boolean.TRUE);
    }

    public static ResultModel getSuccessResult(Object data) {
        return new ResultModel(Boolean.TRUE, data);
    }

    public static ResultModel getSuccessResult(String msg) {
        return new ResultModel(Boolean.TRUE, msg);
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public T result() {
        if (this.data == null) {
            throw new BusinessException(ResultStatusEnum.NO_FOUND);
        }
        return data;
    }

}
