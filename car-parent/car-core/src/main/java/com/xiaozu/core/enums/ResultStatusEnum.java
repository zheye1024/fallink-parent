package com.xiaozu.core.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 响应状态码
 */
public enum ResultStatusEnum {

    NO_VAL_ERR("500", "无效配置!"),
    CHINA_UPLOAD_ERR("500", "数据宝上传失败!"),
    TOKEN_NULL("507", "token为空!"),
    USER_INVALID("506", "账户已禁用/失效!"),
    TOKEN_FAIL("505", "token失效!"),
    FACE_VERIFY_FAIL("508", "人脸校验失败!"),

    PHONE_EXIST("600", "手机号已存在!"),
    EXIST_AUDIT("605", "已存在审核申请!"),
    FIND_FAIL("606", "未查到数据!"),
    OPERATION_FAIL("607", "操作失败!"),
    USNAME_OR_PASSWORD_FAIL("608", "用户名或密码错误!"),
    OFTEN_CODE("609", "短信请求频繁!"),
    PHONE_NOT_EXIST("610", "手机号不存在!"),
    SHORT_CODE_ERROR("612", "短信验证码错误!"),
    SHORT_CODE_EXPIRE("613", "短信验证码过期!"),
    SHORT_CODE_UNWANTED("614", "该运单货源方不需要发送收货验证码短信!"),
    FIRST_OPEN_ERROR("615", "首次解锁失败(需要上传图片附件)!"),
    FILE_FAIL("616", "文件上传错误!"),
    RESERVE_ERROR_PHONE("617", "预留手机号不匹配!"),
    VEHICLE_RETURN_OIL_STATE("630", "请检查车辆是否熄火!"),
    VEHICLE_RETURN_LOCK_CAR("631", "请检查车辆是否上锁!"),

    AUDIT_STATUS_INIT("700", "审核初始化中!"),
    REGISTRATION_AUDIT("701", "注册审核中!"),
    APPROVAL_OF_REGISTRATION("702", "注册审核通过!"),
    REGISTRATION_AUDIT_FAILED("703", "注册审核不通过!"),
    CHANGE_AUDIT("704", "变更审核中!"),
    APPROVAL_OF_CHANGE_AUDIT("705", "变更审核通过!"),
    CHANGE_AUDIT_FAILED("706", "变更审核不通过!"),

    TOO_OFTEN("800", "操作太频繁!"),
    TOO_OFTEN_L("802", "操作太频繁!"),
    TOO_OFTEN_IP("803", "操作太频繁!"),
    EXIST_RUNNING_WAY_BILL("801", "已存在待收货的订单!"),

    HAS_IN_TRANSIT_WAYBILL("901", "您存在运输中订单!"),
    UNRELATED_DRIVER("902", "暂未开通权限，请联系管理员！"),
    NOT_CERTIFIED("903", "您还未认证通过!"),
    /**
     * 用户认证
     */
    REAL_NAME_SUCCESS("10000", "实名认证成功！"),
    REAL_NAME_FAIL("10001", "实名认证失败！"),
    REAL_NAME_ERROR("10002", "实名认证异常！"),

    PARAM_ERROR("601", "参数错误!"),
    PARAM_ILLEGAL("602", "包含非法字符!"),
    NO_FOUND("603", "未查询到数据!"),
    NO_AUTH("608", "没有权限!"),
    ;

    private String code;
    private String msg;

    ResultStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static List<ResultStatusEnum> getAlls() {
        return Arrays.asList(ResultStatusEnum.values());
    }

    public static ResultStatusEnum getEnum(String code) {
        for (ResultStatusEnum enumExample : ResultStatusEnum.values()) {
            if (enumExample.getCode().equals(code)) {
                return enumExample;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
