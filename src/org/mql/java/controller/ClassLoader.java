package org.mql.java.controller;


import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;


public class ClassLoader {
	
	
	public static Class<?> loadClass(String name,String BasePath) {
		try {
			
			File file = new File(BasePath);
			URL[] link = { file.toURI().toURL() };
			
			try (URLClassLoader urlcl = new URLClassLoader(link)) {
				return urlcl.loadClass(name);
			}
			
		} catch (Exception e) {
			System.out.println("Class Not Found ! : " + e.getMessage());
		}
		return null;
	}
	
	
	

}
