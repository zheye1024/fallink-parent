package com.xiaozu.core.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zheye
 * @Date 2020/6/29 0029 15:21
 * @Version 1.0
 */
@Data
@ApiModel(value = "分页对象")
public class BaseQueryDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(notes = "当前页码",example = "1")
    private Integer pageNo = 1;
    @ApiModelProperty(notes = "每页总数",example = "10")
    private Integer pageSize = 10;

    /*================ 扩展字段 ================*/
    @ApiModelProperty(notes = "排序字段")
    private String sidx;
    @ApiModelProperty(notes = "排序方式")
    private String order;
    @ApiModelProperty(notes = "升序")
    private String asc = "asc";
    /*================ 扩展字段 ================*/

    public Map<String, Object> maps() {
        Map<String, Object> map = new HashMap<>();
        Class clazz = this.getClass();
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                Object v = null;
                try {
                    f.setAccessible(true);
                    v = f.get(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                map.put(f.getName(), v);
            }
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);
        return map;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
