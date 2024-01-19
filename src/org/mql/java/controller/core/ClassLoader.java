package org.mql.java.controller.core;


import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;


public class ClassLoader {
	
	
	public static Class<?> loadClass(String name,String basePath) {
		try {
			File file = new File(basePath);
			URL[] url = { file.toURI().toURL() };
			@SuppressWarnings("resource")
			URLClassLoader urlclassloader = new URLClassLoader(url);
				return urlclassloader.loadClass(name);
			
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage() );
		}
		return null;
	}
	
	
	

}
