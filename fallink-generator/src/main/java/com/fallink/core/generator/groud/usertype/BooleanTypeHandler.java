/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
 
/*
 * 修订记录:
 * zisi@yiji.com:2017-05-22 11:00创建
 */
package com.fallink.core.generator.groud.usertype;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Mybatis对用户自定义类型的支持。
 *  Boolean对象与数据库值Y/N/NULL之间的转换器.若数据库DO中使用了布尔值,请使用封装对象;
 *  若想防止应用中报空指针,数据中对应列需要设置Y或者N的默认值
 */
@MappedTypes(Boolean.class)
@MappedJdbcTypes(value = { JdbcType.VARCHAR, JdbcType.CHAR }, includeNullJdbcType = true)
public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Boolean aBoolean, JdbcType jdbcType) throws SQLException {
        if(aBoolean.equals(Boolean.TRUE)){
            preparedStatement.setString(i,"Y");
        }else{
            preparedStatement.setString(i,"N");
        }
    }

    @Override
    public Boolean getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String value = resultSet.getString(s);
        if(StringUtils.equals(value,"Y")){
            return Boolean.TRUE;
        }else if(StringUtils.equals(value,"N")){
            return Boolean.FALSE;
        } else if(null == value){
            return null;
        }
        else {
            throw new SQLException("布尔对象的数据库值只能是Y或者N或者NULL");
        }
    }

    @Override
    public Boolean getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        if(StringUtils.equals(value,"Y")){
            return Boolean.TRUE;
        }else if(StringUtils.equals(value,"N")){
            return Boolean.FALSE;
        } else if(null == value){
            return null;
        }
        else {
            throw new SQLException("布尔对象的数据库值只能是Y或者N或者NULL");
        }
    }

    @Override
    public Boolean getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);
        if(StringUtils.equals(value,"Y")){
            return Boolean.TRUE;
        }else if(StringUtils.equals(value,"N")){
            return Boolean.FALSE;
        } else if(null == value){
            return null;
        }
        else {
            throw new SQLException("布尔对象的数据库值只能是Y或者N或者NULL");
        }
    }
}
