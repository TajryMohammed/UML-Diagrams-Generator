package org.mql.java.examples;

import java.util.Arrays;
import java.util.List;

import org.mql.java.models.ModConstant;
import org.mql.java.models.*;
import org.mql.java.controller.*;

public class Examples {
	
	
	public Examples() {
		
		exp01();
		
	}
	
	
	
	void exp01() {
		
		
	        String basePath = "D:\\Eng-cours\\Dev\\Java Advanced\\Java WorkSpace\\p03-Annotaions and Reflection";

	        
	        ModProject modProject = ProjectParser.parseProject(
	                Arrays.asList("org.mql.java.models", "org.mql.java.reflection"),
	                basePath
	        );

	        
	        displayProject(modProject);
	    
	
		
		
	}
	
	
	private static void displayProject(ModProject modProject) {
	    for (ModPackage modPackage : modProject.getPackages()) {
	        System.out.println("Package: " + modPackage.getName());
	        for (ModClass modClass : modPackage.getClasses()) {
	            System.out.println("  Class: " + modClass.getName());

	            
	            System.out.println("    Attributes:");
	            for (ModAttribute modAttribute : modClass.getAttributes()) {
	                System.out.println("      " + modAttribute.getName() + ": " + modAttribute.getType());
	            }

	            
	            System.out.println("    Methods:");
	            for (ModMethod modMethod : modClass.getMethods()) {
	                System.out.println("      " + modMethod.getName() + ": " + modMethod.getTypeReturn());
	            }
	        }
	    }
	}
	
	
	
	
	public static void main(String[] args) {
		new Examples();
	}
	
}
