package com.sharcg.jvm;

import java.io.File;

public class JavaClassLoadTest {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println(new JavaClassLoadTest().caculator("((1+2)*4+3)*2-1"));
//		new JavaClassLoadTest().test("12", "23","34");
		
	}
	
	public double caculator(String expr) throws Exception {
		String className = "CaculatorTest";
		String source = 
				"public class " + className + " {" +
				"public double caculator() {" +
				"return " + expr + ";" +
				"}" +
				"}";
		boolean result = new JavaCompilerTest().compilerJavaSourceFile(className, source);
		if(result) {
			System.out.println("complier ok!");
			System.out.println(JavaClassLoadTest.class.getClassLoader());
			Class<?> clazz = Class.forName(className);
			File fileJava = new File(Class.class.getResource("/").getPath() + className + ".java");
			File fileClass = new File(Class.class.getResource("/").getPath() + className + ".class");
			fileJava.delete();
			fileClass.delete();
			
			Object value = clazz.getMethod("caculator", new Class<?>[]{}).invoke(clazz.newInstance(), new Object[]{});
			return (Double)value;
		} else {
			System.out.println("算术表达式参数不正确");
		}
		return 0;
	}
	
	public void test(String b,String ...a) {
		
		for(String str: a) {
			System.out.println(str);
		}
	}

}
