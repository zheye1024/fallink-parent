/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved.
 */

/*
 * 修订记录：
 * woniu@yiji.com 2017年05月24日 15:14:38 创建
 */
package com.fallink.core.generator.helper;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

public class BicycleCommentGenerator implements CommentGenerator {

	public BicycleCommentGenerator() {
		super();
	}

	public void addJavaFileComment(CompilationUnit compilationUnit) {
		return;
	}

	/**
	 * xml中的注释
	 *
	 * @param xmlElement
	 */
	public void addComment(XmlElement xmlElement) {
		xmlElement.addElement(new TextElement("<!--"));
		StringBuilder sb = new StringBuilder();
		sb.append("  WARNING - ");
		sb.append(MergeConstants.NEW_ELEMENT_TAG);
		xmlElement.addElement(new TextElement(sb.toString()));
		xmlElement.addElement(new TextElement("-->"));
	}

	public void addRootComment(XmlElement rootElement) {
		return;
	}



	/**
	 * Example使用
	 *
	 * @param innerClass
	 * @param introspectedTable
	 */
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
	}

	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addConfigurationProperties(Properties properties) {

	}

	/**
	 * 给字段添加数据库备注
	 *
	 * @param field
	 * @param introspectedTable
	 * @param introspectedColumn
	 */
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			field.addJavaDocLine("/**");
			StringBuilder sb = new StringBuilder();
			sb.append(" * ");
			sb.append(introspectedColumn.getRemarks());
			field.addJavaDocLine(sb.toString());
			field.addJavaDocLine(" */");
		}
		//添加注解
		if (field.isTransient()) {
			//@Column
			field.addAnnotation("@Transient");
		}
		for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
			if (introspectedColumn == column) {
				field.addAnnotation("@Id");
				field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
				break;
			}
		}
		String column = introspectedColumn.getActualColumnName();
		if (StringUtility.stringContainsSpace(column) || introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
			column = introspectedColumn.getContext().getBeginningDelimiter()
					 + column
					 + introspectedColumn.getContext().getEndingDelimiter();
		}

	    field.addAnnotation("@Column(name = \"" + column + "\")");

		if (introspectedColumn.isIdentity()) {
			if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
				field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
			} else {
				field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
			}
		} else if (introspectedColumn.isSequenceColumn()) {
			field.addAnnotation("@SequenceGenerator(name=\"\",sequenceName=\"" + introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement() + "\")");
		}
	}

	/**
	 * Example使用
	 *
	 * @param field
	 * @param introspectedTable
	 */
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
	}

	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

	}

	/**
	 * @param method
	 * @param introspectedTable
	 */
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
	}

	/**
	 * getter方法注释
	 *
	 * @param method
	 * @param introspectedTable
	 * @param introspectedColumn
	 */
	public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		StringBuilder sb = new StringBuilder();
		method.addJavaDocLine("/**");
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" * 获取");
			sb.append(introspectedColumn.getRemarks());
			method.addJavaDocLine(sb.toString());
			method.addJavaDocLine(" *");
		}
		sb.setLength(0);
		sb.append(" * @return ");
		sb.append(introspectedColumn.getActualColumnName());
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" - ");
			sb.append(introspectedColumn.getRemarks());
		}
		method.addJavaDocLine(sb.toString());
		method.addJavaDocLine(" */");
	}

	/**
	 * setter方法注释
	 *
	 * @param method
	 * @param introspectedTable
	 * @param introspectedColumn
	 */
	public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		StringBuilder sb = new StringBuilder();
		method.addJavaDocLine("/**");
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" * 设置");
			sb.append(introspectedColumn.getRemarks());
			method.addJavaDocLine(sb.toString());
			method.addJavaDocLine(" *");
		}
		Parameter parm = method.getParameters().get(0);
		sb.setLength(0);
		sb.append(" * @param ");
		sb.append(parm.getName());
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" ");
			sb.append(introspectedColumn.getRemarks());
		}
		method.addJavaDocLine(sb.toString());
		method.addJavaDocLine(" */");
	}

	/**
	 * Example使用
	 *
	 * @param innerClass
	 * @param introspectedTable
	 * @param markAsDoNotDelete
	 */
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
	}
}