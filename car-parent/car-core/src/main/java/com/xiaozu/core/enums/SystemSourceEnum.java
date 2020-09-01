package com.xiaozu.core.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public enum SystemSourceEnum {

    TIME_SHARE_RENT("time_share_rent", "分时租赁"),
    LONG_SHORT_RENT("long_short_rent", "长短租"),
    CAR_TSPORT("car_tsport", "智慧物流"),
    CAR_PSRS("car_psrs", "一站买全国用"),

    ;

    private String code;
    private String codeName;

    SystemSourceEnum(String code, String string) {
        this.code = code;
        this.codeName = string;
    }

    public static List<SystemSourceEnum> getAlls() {
        return Arrays.asList(SystemSourceEnum.values());
    }

    public static SystemSourceEnum getEnum(String code) {
        for (SystemSourceEnum enumExample : SystemSourceEnum.values()) {
            if (enumExample.getCode().equals(code)) {
                return enumExample;
            }
        }
        return null;
    }

    public boolean contains(String code) {
        if (StringUtils.isBlank(code)) {
            return Boolean.FALSE;
        }
        return getEnum(code) != null;
    }

    public String getCode() {
        return code;
    }


    public String getCodeName() {
        return codeName;
    }

}
