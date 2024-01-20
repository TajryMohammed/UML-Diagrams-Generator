package org.mql.java.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.mql.java.controller.XMLNode;
import org.mql.java.models.ClassEntity;
import org.w3c.dom.*;
import java.util.List;



public class ClassPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    public static final int MARGIN = 20;

    private List<ClassEntity> classEntities;
    private ClassEntity selectedClassEntity = null;
    private Point clickPoint = null;
    private int frameWidth;

    public ClassPanel(String xmlFilePath, int frameWidth) throws Exception {
        this.frameWidth = frameWidth;


        XMLNode xmlReader = new XMLNode();
        Document document = xmlReader.parseXML(xmlFilePath);


        classEntities = xmlReader.extractClassEntities(document, frameWidth);


        addMouseListener(new ClassPanelMouseListener());
        addMouseMotionListener(new ClassPanelMouseMotionListener());
    }

    public int getMargin() {
        return MARGIN;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

 
        for (ClassEntity classEntity : classEntities) {
            classEntity.draw(g);
        }
    }

    private class ClassPanelMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            selectedClassEntity = null;
            clickPoint = null;

            
            for (ClassEntity classEntity : classEntities) {
                if (classEntity.getBounds().contains(e.getPoint())) {
                    selectedClassEntity = classEntity;
                    clickPoint = e.getPoint();
                    break;
                }
            }

            repaint();
        }

        public void mouseReleased(MouseEvent e) {

            if (selectedClassEntity != null) {
                selectedClassEntity.setBorderColor(ClassEntity.BORDER_COLOR_NORMAL);
                repaint();
            }
        }
    }

    private class ClassPanelMouseMotionListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {

            if (selectedClassEntity != null) {
                int deltaX = e.getX() - clickPoint.x;
                int deltaY = e.getY() - clickPoint.y;
                selectedClassEntity.moveBy(deltaX, deltaY);
                clickPoint = e.getPoint();
                selectedClassEntity.setBorderColor(ClassEntity.DRAGGING_BORDER_COLOR);
                repaint();
                
                
            }
        }
    }
}
