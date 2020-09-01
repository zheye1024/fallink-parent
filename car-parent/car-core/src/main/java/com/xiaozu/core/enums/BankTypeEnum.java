package com.xiaozu.core.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 银行卡类型
 * "credit":"信用卡";"cash":"储蓄卡"
 */
public enum BankTypeEnum {
    CREDIT("credit", "信用卡"),
    CASH("cash", "储蓄卡"),
    ;

    private String code;
    private String codeName;

    BankTypeEnum(String code, String string) {
        this.code = code;
        this.codeName = string;
    }

    public static List<BankTypeEnum> getAlls() {
        return Arrays.asList(BankTypeEnum.values());
    }

    public static BankTypeEnum getEnum(String code) {
        for (BankTypeEnum enumExample : BankTypeEnum.values()) {
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
