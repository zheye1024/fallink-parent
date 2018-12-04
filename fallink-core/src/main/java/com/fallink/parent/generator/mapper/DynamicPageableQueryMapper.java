package com.fallink.parent.generator.mapper;

import com.fallink.parent.generator.param.BasePageableQueryParam;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


public interface DynamicPageableQueryMapper<T,P extends BasePageableQueryParam>  {

	@SelectProvider(type = DynamicPageableQueryProvider.class, method = "dynamicSQL")
    List<T> dynamicPageableQuery(BasePageableQueryParam param);

	@SelectProvider(type = DynamicPageableQueryProvider.class, method = "dynamicSQL")
    Long dynamicPageableCountQuery(BasePageableQueryParam param);

}