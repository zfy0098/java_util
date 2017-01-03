package com.rom.util.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
	
	
	/*
	 * getDeclaredMethod  获取的是类自身声明的所有方法，包含public、protected和private方法。
	 * getMethod()获取的是类的所有共有方法，这就包括自身的所有public方法，和从基类继承的、从接口实现的所有public方法。
	 * 
	 * setAccessible(true)  是否忽略访问权限的限制 如果为true 将直接访问
	 * 
	 * 	getFields			返回的是申明为public的属性，包括父类中定义，
	 * 						返回一个包含某些 Field 对象的数组，这些对象反映此 Class 对象所表示的类或接口的所有可访问公共字段
	 * 
	 *	getDeclaredFields	返回的是指定类定义的所有定义的属性，不包括父类的。
	 *						返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段
	 * 
	 * 
	 * 
	 */
	
	public static void main(String[] args) throws Exception {
		
		Class<?> cls = Class.forName("com.reflect.ReflectTest");
		
		System.out.println("methods name is :");
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName()); 
		}
		System.out.println("===================================================");
		System.out.println("DeclaredFields name is :"); 
		Field[] fields = cls.getDeclaredFields();
		ReflectTest reflectTest = new ReflectTest();
		for ( Field field : fields) {
			field.setAccessible(true);
			System.out.println(field);
			System.out.println("属性名:" + field.getName() + " 属性值:" + field.get(reflectTest));  
		}
		System.out.println("===================================================");
		System.out.println("getFields name is :"); 
		fields = cls.getFields();
		for ( Field field : fields) {
			System.out.println(field);
		}
		System.out.println("===================================================");
		System.out.println("调用test 方法");
		Method method = cls.getDeclaredMethod("Test", new Class[]{String.class});
		method.setAccessible(true);
		
		String name = (String)method.invoke(cls.newInstance(), "小明");
		System.out.println("Test 方法的返回值" + name); 
		
		System.out.println("===================================================");
		//  调用接口的方法  
		Class<?> cls2 = Class.forName("com.reflect.Subject");
		Method m = cls2.getDeclaredMethod("doSomething");
		m.invoke(RealSubject.class.newInstance());
		
		
		//  动态代理
		RealSubject real = new RealSubject();
		Subject proxySubject = (Subject) Proxy.newProxyInstance(
				Subject.class.getClassLoader(), new Class[] { Subject.class },
				new ProxyHandler(real));

		proxySubject.doSomething();
		
		
	}
}
