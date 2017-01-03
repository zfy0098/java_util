package com.reflect;

public class ReflectTest {

	
	private int id;
	private String name;
	
	
	public ReflectTest(){
		System.out.println("无参数构造函数");
	}
	
	public ReflectTest(int id, String name){
		this.id = id;
		this.name = name;
		System.out.println("有参数的构造");
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String Test(String name){
		return "this is " + name;
	}
}
