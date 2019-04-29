/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved.
 */

/*
 * 修订记录：
 * woniu@yiji.com 2017年06月02日 11:19:34 创建
 */
package com.fallink.core.generator.groud.mapper;


public class ParamColumn {

	private String paramName;

	private Class typeClass;

	private String symbol;

	private String columnName;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Class getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(Class typeClass) {
		this.typeClass = typeClass;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}