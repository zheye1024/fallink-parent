package com.xiaozu.core.exception;

import com.xiaozu.core.enums.ResultStatusEnum;

public class BusinessException extends RuntimeException {
    private String code = "500";
    private String msg = "未定义异常!";

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BusinessException() {
    }

    public BusinessException(String code, String info) {
        super(info);
        this.code = code;
        this.msg = info;
    }

    public BusinessException(ResultStatusEnum resEnum) {
        super(resEnum.getMsg());
        this.code = resEnum.getCode();
        this.msg = resEnum.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public BusinessException code(String code) {
        this.code = code;
        return this;
    }

    public BusinessException msg(String msg) {
        this.msg = msg;
        return this;
    }
}
