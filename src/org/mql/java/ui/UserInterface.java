package org.mql.java.ui;

import javax.swing.JFrame;

public class UserInterface extends JFrame{
	
	
	public UserInterface() {
		
		this.setTitle("UML Diagrams Generator");
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
	}
	
	
	
	
	public static void main(String[] args) {
		new UserInterface();
	}

}
