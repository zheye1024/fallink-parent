/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录:
 * zisi@yiji.com:2017-05-22 11:37创建
 */
package com.fallink.parent.generator.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Jpa和Mybatis分页查询计算页数/起点工具类
 */
public class PageUtil {
    /**
     * 根据请求的页数和每页数量算出分页查询的起始量
     *
     * @param page
     * @param pageSize
     * @return
     */
    public static Integer calcStart(Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            return (page - 1) * pageSize;
        } else {
            return 0;
        }
    }

    /**
     * 根据查询Order获取Pageable实例
     *
     * @param page
     * @param pageSize
     * @return
     */
    public static Pageable getPageable(int page, int pageSize) {
        // JPA pageable的page从0开始
        return PageRequest.of(page - 1, pageSize);
    }

    /**
     * 根据查询Order获取Pageable实例，并设置排序
     *
     * @param page
     * @param pageSize
     * @param sort
     * @return
     */
    public static Pageable getPageable(int page, int pageSize, Sort sort) {
        // JPA pageable的page从0开始
        return PageRequest.of(page - 1, pageSize, sort);
    }
}
