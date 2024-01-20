package org.mql.java.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {


	private static final long serialVersionUID = 1L;



	public MainFrame() {
    	
    	
        this.setTitle("UML Diagram Viewer");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        

       
        JButton classDiagramButton = new JButton("Diagramme de Classe");
        JButton packageDiagramButton = new JButton("Diagramme de Package");

  
        
        
        classDiagramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
 
                showClassDiagram();
            }
        });

        
        


        JPanel mainPanel = new JPanel();
        mainPanel.add(classDiagramButton);
        mainPanel.add(packageDiagramButton);

        add(mainPanel);
    }

    private void showClassDiagram() {
        
    	new ClassDiagram().setVisible(true);
            
    }

    

    public static void main(String[] args) {

        new MainFrame().setVisible(true);
    }
}
