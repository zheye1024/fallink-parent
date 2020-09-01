package com.xiaozu.core.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author zheye
 * @Date 2020/5/26 0026 21:15
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel(value = "业务参数基对象")
public class BaseDTO implements Serializable {

    /**
     * 商户号
     */
    @ApiModelProperty(value = "商户号", name = "merchantNo")
    private String merchantNo;

    /**
     * 登录平台标记
     */
    @ApiModelProperty(value = "登录平台标记【1:app;2:公务;3:运营;4:监控;" +
            "5:车机;6:全国用出租方APP;7:全国用PC;8:全国用承租方APP,9:物理运营,10:司机/实际承运人APP,11:托运方,12:H5收货方】", name = "system")
    @NotBlank(message = "登录平台标记为空")
    private String system;

    /**
     * 业务系统
     */
    @ApiModelProperty(value = "业务系统【time_share_rent:分时租赁;" +
            "long_short_rent:长短租;" +
            "car_tsport:智慧物流;" +
            "car_psrs:一站买全国用】", name = "systemSource")
    private String systemSource;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
