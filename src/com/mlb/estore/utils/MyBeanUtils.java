//手动实现beanutils. Just for exercise
package com.mlb.estore.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("all")
public class MyBeanUtils {
	public static void populate(Object obj, HttpServletRequest request) {
		Class clazz = obj.getClass();
		Map<String, String[]> map = request.getParameterMap();
		for (String key : map.keySet()) {
			String methodName = "set" + key.substring(0, 1).toUpperCase()
					+ key.substring(1);
			Field field = null;
			try {
				field = clazz.getDeclaredField(key);
			} catch (Exception e1) {

				continue;
			}

			try {
				Method method = clazz.getMethod(methodName, field.getType());
				if (method != null) {
					method.invoke(obj, map.get(key)[0]);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

}
