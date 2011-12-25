package com.android.orm.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ReflectionUtil {
	
	/**
	 * get methods should have no arguments, they should be written as java standards get<FieldName> e.g for fieldName identity getMethod's name should be getIdentity
	 * 
	 * @param clazz target class
	 * @param fieldName field's name
	 * @return getter method for specified field.
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method findGetMethod(Class<?> clazz, String fieldName) throws SecurityException, NoSuchMethodException {
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		return clazz.getMethod(methodName);
	}
	
	/**
	 * set methods should have only one argument in type of fieldType they should be written as java standards set<FieldName> e.g for fieldName identity setMethod's name should be setIdentity
	 * 
	 * @param clazz target class
	 * @param fieldType field's type
	 * @param fieldName field's name
	 * @return setter method for specified field.
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method findSetMethod(Class<?> clazz, Class<?> fieldType, String fieldName) throws SecurityException, NoSuchMethodException {
		String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		return clazz.getMethod(methodName, fieldType);
	}
	
	/**
	 * searches for field type in clazz and its super classes.
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return type of the field if it is found else null
	 */
	public static Class<?> findFieldType(Class<?> clazz, String fieldName) {
		Class<?> type = clazz;
		while (!type.equals(Object.class)) {
			try {
				return type.getDeclaredField(fieldName).getType();
			}
			catch (SecurityException e) {
				throw e;
			}
			catch (NoSuchFieldException e) {
				type = type.getSuperclass();
			}
		}
		return null;
	}
	
	/**
	 * @param o
	 * @param fieldName
	 * @param fieldType
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T> T invokeGetMethod(Object o, String fieldName, Class<T> fieldType) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		return fieldType.cast(o.getClass().getMethod(methodName).invoke(o));
	}
}
