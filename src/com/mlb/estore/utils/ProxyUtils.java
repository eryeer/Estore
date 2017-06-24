//  事务的动态代理工具类,在servlet中直接创建代理对象即可完成service层的事务代理
package com.mlb.estore.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unchecked")
public class ProxyUtils<T> {
	private T target;

	public ProxyUtils(T target) {
		this.target = target;
	}

	public T getProxy() {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						if (method.getName().startsWith("add")
								|| method.getName().startsWith("delete")
								|| method.getName().startsWith("update")) {
							Logger logger = LogManager.getLogger(target
									.getClass());
							try {
								TransactionManager.begin();
								return method.invoke(target, args);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								logger.error(e);
								TransactionManager.rollback();
							} finally {
								TransactionManager.end();
							}
						}
						return method.invoke(target, args);
					}
				});
	}
}
