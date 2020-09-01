package com.xiaozu.core.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:yanxiao
 * @Des:
 * @Date:2019/3/19
 */
@Data
public class PageDTO<T> {
    @ApiModelProperty(notes = "数据记录")
    private List<T> records;
    @ApiModelProperty(notes = "数据总数")
    private Long total;
    @ApiModelProperty(notes = "每页总数")
    private Long size = 20L;
    @ApiModelProperty(notes = "当前页码")
    private Long current = 1L;
    @ApiModelProperty(notes = "总页码")
    private Long totalPage;
    @ApiModelProperty(notes = "动态参数，前端透传回传服务端")
    private String dyParams;
    @ApiModelProperty(notes = "扩展字段")
    private Map<String, Object> extend = new HashMap<>();

    public PageDTO putExt(String key, Object object) {
        this.extend.put(key, object);
        return this;
    }

    private void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public PageDTO() {
    }

    public PageDTO(Long current, Long size) {
        if (current != null && current != 0) {
            this.size = size;
        }
        if (size != null && size != 0) {
            this.current = current;
        }
    }

    /**
     * 分页
     *
     * @param list     列表数据
     * @param total    总记录数
     * @param pageSize 每页记录数
     * @param pageNo   当前页数
     */
    public PageDTO(List<T> list, Long total, Long pageSize, Long pageNo) {
        this.records = list;
        this.total = total;
        this.size = pageSize;
        this.current = pageNo;
        this.totalPage = Long.valueOf((int) Math.ceil((double) total / pageSize));
    }
}
