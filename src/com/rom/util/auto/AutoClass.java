package com.rom.util.auto;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *   自动创建java类
 * @author zfy
 *
 */
public class AutoClass {
	
	
	String projectpath = System.getProperty("user.dir")+"/src/";  // 项目src路径
	String packagename = "com.rom";								  // 公共包名 
	String filepath = "com/rom/";								// 文件路径
	
	
	/**
	 *   创建dao
	 * @param daoName
	 */
	private void createDao(String daoName){
		String folder = projectpath + filepath+gettoLowerCase(daoName)+"/dao/";
		this.createFileDirectory(folder);
		
		String fileName = folder+daoName+"Dao.java";
		
		try {
			File newdao = new File(fileName);
			FileWriter fw = new FileWriter(newdao);
			fw.write("package "+packagename+"."+gettoLowerCase(daoName)+".dao;\r\n\r\nimport java.util.List;\r\nimport java.util.Map;\r\npublic interface "+daoName+"Dao{\r\npublic List<Map<String,Object>> getlist();\r\n}");
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
	private void createDaoImpl(String daoImplName){
		String folder = projectpath+filepath+gettoLowerCase(daoImplName)+"/dao/";
		this.createFileDirectory(folder);
		String fileName = folder+daoImplName+"DaoImpl.java";
		File newdaoimpl = new File(fileName);
		FileWriter fw;
		try {
			fw = new FileWriter(newdaoimpl);
			fw.write("package "+packagename+"."+gettoLowerCase(daoImplName)+".dao;\r\nimport java.util.List;\r\nimport java.util.Map;\r\n"
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
	private void createService(String serviceName){
		
		//  保存java类的文件夹  如果不存在将创建该文件夹
		String folder = projectpath+filepath+gettoLowerCase(serviceName)+"/service/";
		this.createFileDirectory(folder);
		
		//  要创建java类的全路径  包括文件名称
		String fileName = folder+serviceName+"Service.java";
		
		File newService = new File(fileName);
		try {
			FileWriter fw = new FileWriter(newService);
			fw.write("package "+packagename+"."+gettoLowerCase(serviceName)+".service;\r\nimport java.util.List;\r\nimport java.util.Map;\r\npublic interface "+serviceName
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
	private void createServiceImpl(String serviceNameImpl){ 
		
		String folder = projectpath+filepath+gettoLowerCase(serviceNameImpl)+"/service/";
		this.createFileDirectory(folder);
		String fileName = folder+serviceNameImpl+"ServiceImpl.java";
		File newService = new File(fileName);
		try {
			FileWriter fw = new FileWriter(newService);
			fw.write("package "+packagename+"."+gettoLowerCase(serviceNameImpl)+".service;\r\nimport java.util.List;\r\n"
					+ "import java.util.Map;\r\nimport "+packagename+"."+gettoLowerCase(serviceNameImpl)+".dao."+serviceNameImpl+"Dao;\r\n"
					+ "public class "+serviceNameImpl+"ServiceImpl implements "+serviceNameImpl+"Service{\r\n"
					+ "private "+serviceNameImpl+"Dao "+gettoLowerCase(serviceNameImpl)+"Dao = null; "
					+ "\rpublic "+serviceNameImpl+"Dao get"+serviceNameImpl+"Dao(){\r\treturn "+gettoLowerCase(serviceNameImpl)+"Dao;\r\n}  "
					+ "public void set"+serviceNameImpl+"Dao("+serviceNameImpl+"Dao "+gettoLowerCase(serviceNameImpl)+"Dao){\r\n"
					+ "this."+gettoLowerCase(serviceNameImpl)+"Dao="+gettoLowerCase(serviceNameImpl)+"Dao;\r\n}"
					+ "public List<Map<String,Object>> getlist(){\r\nreturn "+gettoLowerCase(serviceNameImpl)+"Dao.getlist();\r\n}\r\n}");
			fw.flush();
			fw.close();
			this.showinfo(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *   创建controller 文件
	 * @param controllername
	 */
	private void createController(String controllername){
		String folder = projectpath + filepath + gettoLowerCase(controllername) + "/controller/";
		createFileDirectory(folder);
		String filename = folder + controllername + "Controller.java";
		File newController = new File(filename);
		try {
			FileWriter fw = new FileWriter(newController);
			fw.write("package "+packagename+"."+gettoLowerCase(controllername)+".controller;\r\n"
					+ "import java.util.List;\r\n"
					+ "import java.util.Map;\r\n"
					+ "import "+packagename+"."+gettoLowerCase(controllername)+".service."+controllername+"Service;\r\n"
					+ "public class "+controllername+"Controller {\r\n"
					+ "\tprivate "+controllername+"Service "+gettoLowerCase(controllername)+"Service = null; "
					+ "\r\tpublic "+controllername+"Service get"+controllername+"Service(){\r\treturn "+gettoLowerCase(controllername)+"Service;\r\n}  "
					+ "public void set"+controllername+"Service("+controllername+"Service "+gettoLowerCase(controllername)+"Service){\r\n"
					+ "this."+gettoLowerCase(controllername)+"Service="+gettoLowerCase(controllername)+"Service;\r\n}"
					+ "public List<Map<String,Object>> getlist(){\r\nreturn "+gettoLowerCase(controllername)+"Service.getlist();\r\n}\r\n}");
			fw.flush();
			fw.close();
			this.showinfo(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 *   将字符串首字母转换成小写
	 * @param classname 类名
	 * @return
	 */
	private String gettoLowerCase(String classname){
		return classname.substring(0,1).toLowerCase()+classname.substring(1);
	}
	
	
	/**
	 *   将字符串首字母转换成大写
	 * @param classname  类名称
	 * @return
	 */
	@SuppressWarnings("unused")
	private String gettoUpperCase(String classname){
		return classname.substring(0,1).toUpperCase()+classname.substring(1);
	}
	
	
	/**
	 *   判断文件夹是否存在  如果不存在将创建一个新的文件夹
	 * @param folder    要判断的文件夹的路径名称 
	 * @return        boolean类型 true 文件夹存在
	 */
	private void createFileDirectory(String folder){
		File file = new File(folder);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 *     显示信息
	 * @param info
	 */
	private void showinfo(String info){
		System.out.println("创建文件："+ info+ "成功！");
	}
	
	
	
	
	public static void main(String[] args) {
		
		String classname = "";
		while(classname.trim().equals("")){
			System.out.println("类名不能为空;请输入类名:");
			Scanner as = new Scanner(System.in);
			classname = as.nextLine();
		}
		AutoClass autoClass = new AutoClass();
		autoClass.createDao(classname);
		autoClass.createDaoImpl(classname);
		autoClass.createService(classname);
		autoClass.createServiceImpl(classname);
		autoClass.createController(classname);
	}
}
