package com.xiaozu.core.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozu.core.constant.Const;
import com.xiaozu.core.filter.SQLFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Query<T> {

    public IPage<T> getPage(BaseQueryDTO params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(BaseQueryDTO params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long pageNo = params.getPageNo() != null ? params.getPageNo() : 1;
        long pageSize = params.getPageSize() != null ? params.getPageSize() : 10;
        //分页对象
        Page<T> page = new Page<>(pageNo, pageSize);
        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject(params.getSidx());
        String order = params.getOrder();
        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Const.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        //默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }
}
