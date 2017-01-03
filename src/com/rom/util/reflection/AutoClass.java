package com.rom.util.reflection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AutoClass {
	
	
	String projectpath = System.getProperty("user.dir")+"/src/";  // 项目src路径
	String packagename = "com.rom";								  // 公共包名 
	String filepath = "com/rom/";								// 文件路径
	
	
	/**
	 *   创建dao
	 * @param daoName
	 */
	public void createDao(String daoName){
		String folder = projectpath+filepath+daoName+"/dao/";
		this.createFileDirectory(folder);
		
		String fileName = folder+daoName+"Dao.java";
		
		try {
			File newdao = new File(fileName);
			FileWriter fw = new FileWriter(newdao);
			fw.write("package "+packagename+"."+daoName+".dao;\r\n\r\nimport java.util.List;\r\nimport java.util.Map;\r\npublic interface "+daoName+"Dao{\r\npublic List<Map<String,Object>> getlist();\r\n}");
			fw.flush();
			fw.close();
			this.showinfo(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *   创建 daoImpl
	 * @param daoimplname
	 */
	public void createDaoImpl(String daoImplName){
		String folder = projectpath+filepath+daoImplName+"/dao/";
		this.createFileDirectory(folder);
		String fileName = folder+daoImplName+"DaoImpl.java";
		File newdaoimpl = new File(fileName);
		FileWriter fw;
		try {
			fw = new FileWriter(newdaoimpl);
			fw.write("package "+packagename+"."+daoImplName+".dao;\r\nimport java.util.List;\r\nimport java.util.Map;\r\n"
					+ "public class "+daoImplName+"DaoImpl implements "+daoImplName+"Dao {\r\npublic List<Map<String,Object>> getlist(){\r\nreturn null;}}");
			fw.flush();
			fw.close();
			this.showinfo(fileName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *   创建service
	 * @param serviceName
	 */
	public void createService(String serviceName){
		
		//  保存java类的文件夹  如果不存在将创建该文件夹
		String folder = projectpath+filepath+serviceName+"/service/";
		this.createFileDirectory(folder);
		
		//  要创建java类的全路径  包括文件名称
		String fileName = folder+serviceName+"Service.java";
		
		File newService = new File(fileName);
		try {
			FileWriter fw = new FileWriter(newService);
			fw.write("package "+packagename+"."+serviceName+".service;\r\nimport java.util.List;\r\nimport java.util.Map;\r\npublic interface "+serviceName
					+"Service{\r\npublic List<Map<String,Object>> getlist();\r\n}");
			fw.flush();
			fw.close();
			this.showinfo(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *    创建serviceImpl 文件
	 * @param serviceName
	 */
	public void createServiceImpl(String serviceNameImpl){
		
		String folder = projectpath+filepath+serviceNameImpl+"/service/";
		this.createFileDirectory(folder);
		String fileName = folder+serviceNameImpl+"ServiceImpl.java";
		File newService = new File(fileName);
		try {
			FileWriter fw = new FileWriter(newService);
			fw.write("package "+packagename+"."+serviceNameImpl+".service;\r\nimport java.util.List;\r\n"
					+ "import java.util.Map;\r\npublic class "+serviceNameImpl+"ServiceImpl implements "+serviceNameImpl+"Service{\r\npublic List<Map<String,Object>> getlist(){\r\nreturn null;\r\n}\r\n}");
			fw.flush();
			fw.close();
			this.showinfo(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 *   判断文件夹是否存在  如果不存在将创建一个新的文件夹
	 * @param folder    要判断的文件夹的路径名称 
	 * @return        boolean类型 true 文件夹存在
	 */
	public boolean createFileDirectory(String folder){
		File file = new File(folder);
		if(!file.exists()){
			file.mkdirs();
		}
		return true;
	}
	
	/**
	 *     显示信息
	 * @param info
	 */
	public void showinfo(String info){
		System.out.println("创建文件："+ info+ "成功！");
	}
	

	
	
	public static void main(String[] args) {
		
		String classname = "";
		while(classname.equals("")){
			System.out.println("类名不能为空;请输入类名:");
			Scanner as = new Scanner(System.in);
			classname = as.nextLine();
		}
		
		AutoClass autoClass = new AutoClass();
		autoClass.createDao(classname);
		autoClass.createDaoImpl(classname);
		autoClass.createService(classname);
		autoClass.createServiceImpl(classname);
	}
}
