package com.jasty.utils;

import com.jasty.core.InitProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Some useful reflection helpers
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class ReflectionUtils {

	public static Collection<Field> getAllInitProperties(Class<?> clazz) {
		Collection<Field> fields = new ArrayList<Field>();
		for (Field f : clazz.getDeclaredFields()) {
			if(!f.isSynthetic() && f.getAnnotation(InitProperty.class) != null)
				fields.add(f);
		}
		if (clazz.getSuperclass() != null) {
			fields.addAll(getAllInitProperties(clazz.getSuperclass()));
		}
		return fields;
	}
	
	public static Field findField(Class<?> clazz, String name) {
		if(clazz == null) return null;
		
		try {
			return clazz.getDeclaredField(name);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			return findField(clazz.getSuperclass(), name);
		}
	}
	
	public static void copyInitProperties(Object src, Object target) {
		Class<?> srcClass = src.getClass();
		
		for(Field f : getAllInitProperties(target.getClass())) {
			if(Modifier.isStatic(f.getModifiers())) continue;
			try {
				Field srcField = findField(srcClass, f.getName());
				if(srcField != null) {
					f.setAccessible(true);
					srcField.setAccessible(true);
					f.set(target, srcField.get(src));
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
