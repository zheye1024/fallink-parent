/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
 
/*
 * 修订记录:
 * zisi@yiji.com:2017-05-22 11:08创建
 */
package com.fallink.parent.generator.param;


import java.util.Date;

/**
 * Mybatis动态分页查询入参基类
 */
public class BasePageableQueryParam {

    /**
     * 默认不分页，Mapper.xml中参考代码为：
     * <pre>
     *{@code
     *  <if test="param.start != null and param.limit != null">
     *      LIMIT #{param.start, jdbcType=INTEGER},#{param.limit, jdbcType=INTEGER}
     *  </if>
     *}
     * </pre>
     */
    public BasePageableQueryParam() {
        this.start = null;
        this.limit = null;
    }

    /**
     * 根据传入分页参数进行分页，Mapper.xml中参考代码为：
     * <pre>
     *{@code
     *  <if test="param.start != null and param.limit != null">
     *      LIMIT #{param.start, jdbcType=INTEGER},#{param.limit, jdbcType=INTEGER}
     *  </if>
     *}
     * </pre>
     */
	public BasePageableQueryParam(Integer page, Integer pageSize) {
		calcStartAndLimit(page, pageSize);
	}

    /**
     * 重新计算分页参数
     * @param page
     * @param pageSize
     */
	public void calcStartAndLimit(Integer page, Integer pageSize) {
		this.start = calcStart(page, pageSize);
		this.limit = pageSize;
	}
	
    protected Integer calcStart(Integer page, Integer pageSize) {
        if (page == null || pageSize == null) {
            return null;
        }
        if (page < 1) {
            page = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10; // 默认10条每页
        }

        return (page - 1) * pageSize;
    }

    /**
     * 分页查询起点，为null时表示不需要分页
     */
    private Integer start;

    /**
     * 分页查询最大数量，为null时表示不需要分页
     */
    private Integer limit;

    private Date createTimeStart;

    private Date createTimeEnd;

    public Integer getStart() {
        return start;
    }

    public Integer getLimit() {
        return limit;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    @Override
    public String toString() {
        return this.toString();
    }
}
