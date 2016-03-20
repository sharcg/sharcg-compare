package com.sharcg.jvm;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaCompilerTest {
	
	public boolean compilerJavaSource(String className, String source) throws Exception {
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		
		StringObject stringObject = new StringObject(className, source);
		
		Iterable<? extends JavaFileObject> fileObjects =  Arrays.asList(stringObject);
		
		// 获取编译类根路径，不然会报找不到类的错误
		String path = Class.class.getClass().getResource("/").getPath();
		System.out.println("path:" + path);
		Iterable< String> options = Arrays.asList("-d", path); 
		
		CompilationTask  task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
		
		System.out.println(stringObject.getCharContent(true));
		
		return task.call();
		
	}
	
	public boolean compilerJavaSourceFile(String className, String source) throws Exception {
		String path = Class.class.getClass().getResource("/").getPath(); 
		//生成ava源 文件
		FileWriter fw = new FileWriter(path + className + ".java");
		fw.write(source);
		fw.flush();
		fw.close();
		
		//编译java源文件
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> fileObject = fileManager.getJavaFileObjects(path+className+".java");
		
		CompilationTask  task = compiler.getTask(null, fileManager, null, null, null, fileObject);
		
		return task.call();
		
	}
	
	
	public static void main(String[] args) throws Exception {
		JavaCompilerTest javaCompilerTest = new JavaCompilerTest();
		String className = "HelloWord"; 
		String rn = "\n"; 
		String source = 
				"package com.sharcg.jvm;" + rn + rn + 
				"public class " + className + " {" + rn + 
				"    public void test() {" + rn + 
				"        System.out.println(\"hello world!\");" + rn + 
				"    }" + rn + 
				"}";
		
		boolean result = javaCompilerTest.compilerJavaSourceFile (className, source);
		
		if(result) {
			System.out.println("complier ok!");
		} else {
			System.out.println("complier error");
		}
		
	}

}

class StringObject extends SimpleJavaFileObject { 
    private String contents = null;
    public StringObject(String className, String contents) throws Exception { 
        super(URI.create("string:///" + className.replace('.', '/') 
                + Kind.SOURCE.extension), Kind.SOURCE); 
        this.contents = contents; 
    }
    public CharSequence getCharContent(boolean ignoreEncodingErrors) 
            throws IOException { 
        return contents; 
    } 
}
