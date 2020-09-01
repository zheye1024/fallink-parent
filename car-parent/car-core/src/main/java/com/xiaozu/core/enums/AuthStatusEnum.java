package com.xiaozu.core.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 认证状态
 * 状态("wait":"待认证";"process":"处理中";"success":"成功";"fail":"失败")
 */
public enum AuthStatusEnum {
    WAIT("wait", "待认证"),
    PROCESS("process", "处理中"),
    SUCCESS("success", "成功"),
    FAIL("fail", "失败"),
    OVERDUE("overdue", "过期"),
    WILL_OVERDUE("will_overdue", "即将过期"),
    ;

    private String code;
    private String codeName;

    AuthStatusEnum(String code, String string) {
        this.code = code;
        this.codeName = string;
    }

    public static List<AuthStatusEnum> getAlls() {
        return Arrays.asList(AuthStatusEnum.values());
    }

    public static AuthStatusEnum getEnum(String code) {
        for (AuthStatusEnum enumExample : AuthStatusEnum.values()) {
            if (enumExample.getCode().equals(code)) {
                return enumExample;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }


    public String getCodeName() {
        return codeName;
    }
}
