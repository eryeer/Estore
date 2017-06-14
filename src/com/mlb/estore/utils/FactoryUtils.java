package com.mlb.estore.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class FactoryUtils {
	/**
	 * 
	 * @param clazz接口类
	 * @return 接口实现类的实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazz) {
		// 返回接口名
		String simpleName = clazz.getSimpleName();
		// 自定义名instance,语言环境zh,国别代号CN
		ResourceBundle resourceBundle = ResourceBundle.getBundle("instance",
				new Locale("zh", "CN"));
		String implName = resourceBundle.getString(simpleName);
		try {
			return (T) Class.forName(implName).newInstance();
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
