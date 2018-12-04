package com.fallink.parent.generator.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


public class DynamicPageableQueryProvider extends MapperTemplate {
	private final static Logger logger = LoggerFactory.getLogger(DynamicPageableQueryProvider.class);
	protected Map<String, Class<?>> paramClassMap = new HashMap();
	
	protected static final String START = "Start"; //>=
	protected static final String END = "End"; // <=
	protected static final String LIKE = "Like";// LIKE
	protected static final String LIST_IN = "ListIn";// IN
	
	public DynamicPageableQueryProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	/**
	 * 动态参数查询条数
	 * @param ms
	 * @return
	 */
	public String dynamicPageableCountQuery(MappedStatement ms) {
		Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) ");
		sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
		sql.append(whereAllIfColumns(entityClass, ms));
		logger.debug("dynamicPageableCountQuery={}", sql);
		return sql.toString();
	}
	
	/**
	 * 动态参数查询列表
	 * @param ms
	 * @return
	 */
	public String dynamicPageableQuery(MappedStatement ms) {
		Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		setResultType(ms, entityClass);
		sql.append(SqlHelper.selectAllColumns(entityClass));
		sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
		sql.append(whereAllIfColumns(entityClass, ms));
		sql.append(orderByColumn(entityClass, ms));
		sql.append(limit());
		logger.debug("dynamicPageableQuery={}", sql);
		return sql.toString();
	}
	
	/************************* 以下为辅助方法 ************************/
	
	/**
	 *
	 * @param ms
	 * @return
	 */
	protected String whereAllIfColumns(Class<?> entityClass, MappedStatement ms) {
		StringBuilder sql = new StringBuilder();
		Class<?> paramClass = getParamClass(ms);
		List<ParamColumn> paramColumns = getParamColumn(paramClass);
		//添加参数父类的创建时间参数
		paramColumns.addAll(getRawAddTimeParamColumn());
		sql.append("<where>");
		//获取全部列
		Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
		Map<String, EntityColumn> columnNameMap = getColumnNameMap(columnList);
		for (ParamColumn paramColumn : paramColumns) {
			if (!columnNameMap.keySet().contains(paramColumn.getColumnName())) {
				throw new MapperException(paramClass.getName() + "." + paramColumn.getParamName() + "无法找到表对应字段!");
			}
			EntityColumn column = columnNameMap.get(paramColumn.getColumnName());
			
			sql.append(getIfNotNull(paramColumn,
				" AND " + getColumnHolder(paramColumn.getSymbol(), column, paramColumn.getParamName()), true));
		}
		sql.append("</where>");
		return sql.toString();
	}
	
	protected String limit() {
		String limit = " <if test=\"start != null and limit != null\"> "
						+ "  LIMIT #{start, jdbcType=INTEGER},#{limit, jdbcType=INTEGER} " + "</if> ";
		return limit;
	}
	
	/**
	 * 得到参数Class
	 * @param ms
	 * @return
	 */
	private Class<?> getParamClass(MappedStatement ms) {
		String msId = ms.getId();
		if (paramClassMap.containsKey(msId)) {
			return paramClassMap.get(msId);
		} else {
			Class<?> mapperClass = getMapperClass(msId);
			Type[] types = mapperClass.getGenericInterfaces();
			for (Type type : types) {
				if (type instanceof ParameterizedType) {
					ParameterizedType t = (ParameterizedType) type;
					if (t.getRawType() == this.mapperClass
						|| this.mapperClass.isAssignableFrom((Class<?>) t.getRawType())) {
						Class<?> paramType = (Class<?>) t.getActualTypeArguments()[1];
						entityClassMap.put(msId, paramType);
						return paramType;
					}
				}
			}
		}
		throw new MapperException("无法获取Mapper<P>泛型类型:" + msId);
	}
	
	/**
	 * EntityColumn的Set加工成Map<String,EntityColumn>
	 * @param columnList
	 * @return
	 */
	private Map<String, EntityColumn> getColumnNameMap(Set<EntityColumn> columnList) {
		Map<String, EntityColumn> columnNameMap = new HashMap<>();
		for (EntityColumn column : columnList) {
			columnNameMap.put(column.getProperty(), column);
		}
		return columnNameMap;
	}
	
	/**
	 * 获得参数父类固定的创建日期参数
	 * @return
	 */
	private List<ParamColumn> getRawAddTimeParamColumn() {
		List<ParamColumn> paramColumnList = new ArrayList();
		ParamColumn paramColumn1 = new ParamColumn();
		paramColumn1.setParamName("createTimeStart");
		paramColumn1.setColumnName("rawAddTime");
		paramColumn1.setTypeClass(Date.class);
		paramColumn1.setSymbol("&gt;=");
		paramColumnList.add(paramColumn1);
		
		ParamColumn paramColumn2 = new ParamColumn();
		paramColumn2.setParamName("createTimeEnd");
		paramColumn2.setColumnName("rawAddTime");
		paramColumn2.setTypeClass(Date.class);
		paramColumn2.setSymbol("&lt;=");
		paramColumnList.add(paramColumn2);
		return paramColumnList;
	}
	
	/**
	 * 获得参数对象参数类型列表
	 * @param paramClass
	 * @return
	 */
	private List<ParamColumn> getParamColumn(Class<?> paramClass) {
		List<ParamColumn> paramColumnList = new ArrayList();
		Field[] fields = paramClass.getDeclaredFields();
		for (Field f : fields) {
			ParamColumn paramColumn = new ParamColumn();
			if (f.getName().endsWith(START)) {
				paramColumn.setParamName(f.getName());
				paramColumn.setColumnName(f.getName().substring(0, f.getName().lastIndexOf(START)));
				paramColumn.setSymbol("&gt;=");
			} else if (f.getName().endsWith(END)) {
				paramColumn.setParamName(f.getName());
				paramColumn.setColumnName(f.getName().substring(0, f.getName().lastIndexOf(END)));
				paramColumn.setSymbol("&lt;=");
			} else if (f.getName().endsWith(LIKE)) {
				paramColumn.setParamName(f.getName());
				paramColumn.setColumnName(f.getName().substring(0, f.getName().lastIndexOf(LIKE)));
				paramColumn.setSymbol("LIKE");
			} else if (f.getName().endsWith(LIST_IN)) {
				if (Collection.class.isAssignableFrom(f.getType())) {
					paramColumn.setParamName(f.getName());
					paramColumn.setColumnName(f.getName().substring(0, f.getName().lastIndexOf(LIST_IN)));
					paramColumn.setSymbol("IN");
				} else {
					throw new MapperException(LIST_IN + "结尾的参数必须是Collection类型");
				}
			} else {
				paramColumn.setParamName(f.getName());
				paramColumn.setColumnName(f.getName());
				paramColumn.setSymbol("=");
			}
			paramColumn.setTypeClass(f.getType());
			paramColumnList.add(paramColumn);
		}
		return paramColumnList;
	};
	
	/**
	 * 返回格式如：<if test="createTimeStart != null">
	 * @param paramColumn
	 * @param contents
	 * @param empty
	 * @return
	 */
	private String getIfNotNull(ParamColumn paramColumn, String contents, boolean empty) {
		StringBuilder sql = new StringBuilder();
		sql.append("<if test=\"");
		sql.append(paramColumn.getParamName()).append(" != null");
		if (empty && paramColumn.getTypeClass().equals(String.class)) {
			sql.append(" and ");
			sql.append(paramColumn.getParamName()).append(" != '' ");
		}
		sql.append("\">");
		sql.append(contents);
		sql.append("</if>");
		return sql.toString();
	}
	
	/**
	 * 返回格式如: age [=][>=][<=][IN] #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 * @param symbol = &gt;= &lt;= IN
	 * @param column
	 * @param paramColumnName
	 * @return
	 */
	private String getColumnHolder(String symbol, EntityColumn column, String paramColumnName) {
		StringBuffer sb = new StringBuffer(column.getColumn() + " ");
		sb.append(symbol);
		if (symbol.equals("IN")) {
			sb.append("<foreach collection=\""	+ paramColumnName
						+ "\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >");
			sb.append("#{item}");
			sb.append("</foreach>");
		} else {
			sb.append("#{");
			sb.append(paramColumnName);
			if (column.getJdbcType() != null) {
				sb.append(",jdbcType=");
				sb.append(column.getJdbcType().toString());
			} else if (column.getTypeHandler() != null) {
				sb.append(",typeHandler=");
				sb.append(column.getTypeHandler().getCanonicalName());
			} else if (!column.getJavaType().isArray()) {//当类型为数组时，不设置javaType#103
				sb.append(",javaType=");
				sb.append(column.getJavaType().getCanonicalName());
			}
			sb.append("}");
		}
		return sb.toString();
	}
	
	private String orderByColumn(Class<?> entityClass, MappedStatement ms) {
		String orderByClause = null;
		EntityTable table = EntityHelper.getEntityTable(entityClass);
		if (table.getOrderByClause() != null) {
			orderByClause = table.getOrderByClause();
		}
		Class<?> paramClass = getParamClass(ms);
		OrderByClause clause = paramClass.getAnnotation(OrderByClause.class);
		if (clause != null) {
			orderByClause = clause.value();
			table.setOrderByClause(orderByClause);
		}
		if (StringUtils.isNotBlank(orderByClause)) {
			StringBuilder sql = new StringBuilder();
			sql.append(" ORDER BY ");
			sql.append(orderByClause);
			return sql.toString();
		} else {
			return "";
		}
	}
}