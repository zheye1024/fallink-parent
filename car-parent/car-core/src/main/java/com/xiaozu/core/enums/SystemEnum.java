package com.xiaozu.core.enums;

import java.util.*;

public enum SystemEnum {

    APP(1, "分时/长短租app"),
    OFFICIAL(2, "公务"),
    OPERATE(3, "运营"),
    MONITOR(4, "监控"),
    ENGINE(5, "车机"),
    PSRS_LESSOR_APP(6, "全国用出租方APP"),
    PSRS(7, "全国用PC"),
    PSRS_TENANTRY_APP(8, "全国用承租方APP"),
    TSPORT_OPERATE(9, "物流运营"),
    TSPORT_DRIVER_APP(10, "司机/实际承运人APP"),
    TSPORT_SHIPPER(11, "托运方"),
    TSPORT_RECEIVER(12, "物流收货方H5"),
    ;

    private Integer code;
    private String codeName;

    SystemEnum(Integer code, String string) {
        this.code = code;
        this.codeName = string;
    }

    public static List<SystemEnum> getAlls() {
        return Arrays.asList(SystemEnum.values());
    }

    public static SystemEnum getEnum(Integer code) {
        for (SystemEnum enumExample : SystemEnum.values()) {
            if (enumExample.getCode() == code) {
                return enumExample;
            }
        }
        return null;
    }

    public static List allEnums() {
        List res = new ArrayList();
        for (SystemEnum srAppSystemEnum : getAlls()) {
            Map<String, Object> param = new HashMap<>();
            param.put("Key", srAppSystemEnum.getCode());
            param.put("value", srAppSystemEnum.getCodeName());
            res.add(param);
        }
        return res;
    }

    public Integer getCode() {
        return code;
    }


    public String getCodeName() {
        return codeName;
    }

}
