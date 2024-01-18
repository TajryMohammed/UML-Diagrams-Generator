package org.mql.java.examples;

import org.mql.java.controller.ProjectParser;


public class Examples {

    public Examples() {
   
    }

    public Examples(String path) {
		try {
			ProjectParser project=new ProjectParser(path);
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		} 
	}

    public static void main(String[] args) {
        new Examples("D:\\Eng-cours\\Dev\\Java Advanced\\Java WorkSpace\\p03-Annotaions and Reflection");
    }
}