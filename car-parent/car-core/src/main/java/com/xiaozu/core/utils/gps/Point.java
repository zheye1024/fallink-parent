package com.xiaozu.core.utils.gps;

import com.xiaozu.core.base.BaseOrder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author:80906
 * @Des:
 * @Date:2019/3/23
 */
@Getter
@Setter
public class Point extends BaseOrder {
    private double y;
    private double x;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
