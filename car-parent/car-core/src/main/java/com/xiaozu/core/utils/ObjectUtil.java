package com.xiaozu.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectUtil {
	/**
	 * 对象转换成map
	 * @param obj 代转对象
	 * @param map 转换成map
	 */
	public static void obj2Hash(Object obj, Map<String, String> map) {
		if (obj == null || map == null) {
			return;
		}
		Class<? extends Object> clazz = obj.getClass();
		Class<? extends Object> superClazz=clazz.getSuperclass();
		List<Field> fields =new ArrayList<Field>();
		if (superClazz!=null) {
			Field[] superFields =superClazz.getDeclaredFields();
			for (int i = 0; i < superFields.length; i++) {
				if (Modifier.isStatic(superFields[i].getModifiers()) || Modifier.isFinal(superFields[i].getModifiers())) {
					continue;
				}
				fields.add(superFields[i]);
			}
		}
		Field[] localFields = clazz.getDeclaredFields();
		if (localFields!=null) {
			for (int i = 0; i < localFields.length; i++) {
				if (Modifier.isStatic(localFields[i].getModifiers()) || Modifier.isFinal(localFields[i].getModifiers())) {
					continue;
				}
				fields.add(localFields[i]);
			}
		}
		for (Field field : fields) {
			String fieldName = field.getName();
			String typeName = field.getType().getName();
			StringBuffer methodName = new StringBuffer("get");
			methodName.append(fieldName.substring(0, 1).toUpperCase());
			methodName.append(fieldName.substring(1, fieldName.length()));
			Method method;
			try {
				method = clazz.getMethod(methodName.toString());
				Object res = method.invoke(obj);
				if (res != null) {
					if ("java.util.Date".equals(typeName)) {
						SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						res=fmt.format(res);
					}
					map.put(fieldName, res.toString());
				} else {
					// map.put(fieldName,null);
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 对象转换成map
	 * @param obj 代转对象
	 * @param map 转换成map
	*/
	public static void Hash2obj( Map<String, String> map,Object obj) {
		if (obj == null || map == null) {
			return;
		}
		Class<? extends Object> clazz = obj.getClass();
		Class<? extends Object> superClazz=clazz.getSuperclass();
		List<Field> fields =new ArrayList<Field>();
		if (superClazz!=null) {
			Field[] superFields =superClazz.getDeclaredFields();
			for (int i = 0; i < superFields.length; i++) {
				if (Modifier.isStatic(superFields[i].getModifiers()) || Modifier.isFinal(superFields[i].getModifiers())) {
					continue;
				}
				fields.add(superFields[i]);
			}
		}
		Field[] localFields = clazz.getDeclaredFields();
		if (localFields!=null) {
			for (int i = 0; i < localFields.length; i++) {
				if (Modifier.isStatic(localFields[i].getModifiers()) || Modifier.isFinal(localFields[i].getModifiers())) {
					continue;
				}
				fields.add(localFields[i]);
			}
		}
		for (Field field : fields) {
			String fieldName = field.getName();
			String typeName = field.getType().getName();
			Object val = map.get(fieldName);
			StringBuffer methodName = new StringBuffer("set");
			methodName.append(fieldName.substring(0, 1).toUpperCase());
			methodName.append(fieldName.substring(1, fieldName.length()));
			Method method;
			try {
				method = clazz.getMethod(methodName.toString(),field.getType());
				if ("java.util.Date".equals(typeName)&&val!=null&&!"".equals(val)) {
					SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					val=fmt.parseObject(val.toString());
				}else if (!"java.util.Date".equals(typeName)&&val!=null&&!"".equals(val)) {
					Class<?> cl=Class.forName(typeName);
					val=cl.getConstructor(String.class).newInstance(val);
				}
				method.invoke(obj,val);
			} catch (NoSuchMethodException e) {
				System.out.println(fieldName+":"+val+"出错");
				e.printStackTrace();
			} catch (SecurityException e) {
				System.out.println(fieldName+":"+val+"出错");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println(fieldName+":"+val+"出错");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				System.out.println(fieldName+":"+val+"出错");
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				System.out.println(fieldName+":"+val+"出错");
				e.printStackTrace();
			} catch (ParseException e) {
				System.out.println(fieldName+":"+val+"出错");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println(typeName+":类出错");
				e.printStackTrace();
			} catch (InstantiationException e) {
				System.out.println(fieldName+":"+val+"出错");
				e.printStackTrace();
			}

		}
	}

	public static void obj2Hash(List<Object> objList, List<Map<String, String>> mapList) {
		if(mapList == null) {
			return;
		}
		if(objList != null && objList.size() >0) {
			for(Object obj : objList) {
				Map<String, String> map = new HashMap<String, String>();
				obj2Hash(obj, map);
				mapList.add(map);
			}
		}
	}

}
