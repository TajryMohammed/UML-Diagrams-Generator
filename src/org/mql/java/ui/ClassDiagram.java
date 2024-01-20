package org.mql.java.ui;


import javax.swing.*;


public class ClassDiagram extends JFrame {

	private static final long serialVersionUID = 1L;

	
	
	public ClassDiagram() {
    	
    	
        this.setSize(1200, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Class Diagram");
        this.setLocationRelativeTo(null);

        
        try {
            ClassPanel classPanel = new ClassPanel("resources/project_structure.xml", getWidth());
            add(classPanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    
}
