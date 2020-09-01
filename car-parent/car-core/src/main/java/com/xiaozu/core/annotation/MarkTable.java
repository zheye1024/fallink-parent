package com.xiaozu.core.annotation;


import com.xiaozu.core.enums.MarkTypeEnum;

import java.lang.annotation.*;

/**
 * @author zheye
 * @Title: Desensitized
 * @Copyright: Copyright (c) 2016
 * @Description: 敏感信息注解标记
 * @Company: lxjr.com
 * @Created on 2020/03/13
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MarkTable {

    /*脱敏类型(规则)*/
    MarkTypeEnum type();

    /*指定判断注解是否生效的方法名*/
    String isEffectiveMethod() default "";

}
