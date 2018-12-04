package com.fallink.parent.generator.mapper;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 表示在SQL后面添加排序语句
 * 用在参数类
 */
@Target({ TYPE})
@Retention(RUNTIME)
public @interface OrderByClause {
	//例如：raw_add_time DESC
	//或者：order_info_id DESC , raw_add_time ASC
	String value() default "";
}
