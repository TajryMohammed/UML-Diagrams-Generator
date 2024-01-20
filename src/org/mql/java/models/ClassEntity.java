package org.mql.java.models;

import java.awt.*;

import org.mql.java.ui.ClassPanel;
import org.w3c.dom.*;

public class ClassEntity {
	
	
	
	private String className;
    private NodeList attributeNodes;
    private NodeList methodNodes;
    private boolean isInterface;
    private int x;
    private int y;
    private Color borderColor;
	
	
    private static final int LINE_HEIGHT = 20;
    public static final Color BORDER_COLOR_NORMAL = Color.BLACK;
    public static final Color DRAGGING_BORDER_COLOR = Color.BLUE;
    private static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 12);
    public static final int MIN_WIDTH = 200; // Minimum width for the entity
    public static final int MIN_HEIGHT = 100; // Minimum height for the entity

    

    public ClassEntity(Element classElement, int x, int y) {
        this.className = classElement.getAttribute("name");
        this.attributeNodes = classElement.getElementsByTagName("attribute");
        this.methodNodes = classElement.getElementsByTagName("method");
        this.isInterface = classElement.getNodeName().equals("interface");
        this.x = x;
        this.y = y;
        this.borderColor = BORDER_COLOR_NORMAL;
    }
    
    
    
    

    public void draw(Graphics g) {
        int requiredWidth = calculateRequiredWidth(g);
        int requiredHeight = calculateRequiredHeight(g);

        if (isInterface) {
            g.setColor(new Color(144, 238, 144));
        } else {
            g.setColor(new Color(255, 191, 0));
        }

        g.fillRect(x, y, requiredWidth, requiredHeight);
        g.setColor(borderColor);
        g.drawRect(x, y, requiredWidth, requiredHeight);
        g.drawRect(x + 1, y + 1, requiredWidth - 2, requiredHeight - 2);


        g.setFont(BOLD_FONT);
        g.setColor(Color.BLACK);

        int nameY = y + LINE_HEIGHT;
        if (isInterface) {
            String interfaceName = "<<" + className + ">>";
            int interfaceNameX = x + requiredWidth / 2 - g.getFontMetrics().stringWidth(interfaceName) / 2;
            g.drawString(interfaceName, interfaceNameX, nameY);
            nameY += 20; 
        } else {
            int classNameX = x + requiredWidth / 2 - g.getFontMetrics().stringWidth(className) / 2;
            g.drawString(className, classNameX, nameY);
        }

   
        g.drawLine(x, nameY + 5, x + requiredWidth, nameY + 5);


        for (int i = 0; i < attributeNodes.getLength(); i++) {
            Element attributeElement = (Element) attributeNodes.item(i);
            String attributeName = attributeElement.getAttribute("name");
            String attributeType = attributeElement.getAttribute("type");
            String modifierType = attributeElement.getAttribute("modifierType");
            g.setFont(BOLD_FONT);
            g.drawString(modifierType + " " + attributeName + ": " + attributeType,
                    x + 10, nameY + (i + 2) * LINE_HEIGHT + 5);
        }


        g.drawLine(x, nameY + (attributeNodes.getLength() + 2) * LINE_HEIGHT + 10,
                x + requiredWidth, nameY + (attributeNodes.getLength() + 2) * LINE_HEIGHT + 10);

    
        for (int i = 0; i < methodNodes.getLength(); i++) {
            Element methodElement = (Element) methodNodes.item(i);
            String methodName = methodElement.getAttribute("name");
            String returnType = methodElement.getAttribute("return-type");
            String modifierType = methodElement.getAttribute("modifierType");
            g.setFont(BOLD_FONT);
            g.drawString(modifierType + " " + methodName + "(): " + returnType,
                    x + 10, nameY + (i + attributeNodes.getLength() + 3) * LINE_HEIGHT + 10);
        }

     
        g.setFont(g.getFont().deriveFont(Font.PLAIN));
    }
    
    
    
    
   

    public Rectangle getBounds() {
        return new Rectangle(x, y, calculateRequiredWidth(null), calculateRequiredHeight(null));
    }

    
    
    
    
    public int calculateRequiredWidth(Graphics g) {
        int maxLineWidth = 0;

        // Calculate  max width among class name attributes and methods
        if (g != null) {
            FontMetrics fontMetrics = g.getFontMetrics(BOLD_FONT);

            int classNameWidth;
            if (isInterface) {
                String interfaceName = "<<" + className + ">>";
                classNameWidth = fontMetrics.stringWidth(interfaceName);
            } else {
                classNameWidth = fontMetrics.stringWidth(className);
            }

            maxLineWidth = Math.max(maxLineWidth, classNameWidth);

            for (int i = 0; i < attributeNodes.getLength(); i++) {
                Element attributeElement = (Element) attributeNodes.item(i);
                String attributeName = attributeElement.getAttribute("name");
                String attributeType = attributeElement.getAttribute("type");
                String modifierType = attributeElement.getAttribute("modifierType");
                int lineLength = fontMetrics.stringWidth(modifierType + " " + attributeName + ": " + attributeType);
                maxLineWidth = Math.max(maxLineWidth, lineLength);
            }

            for (int i = 0; i < methodNodes.getLength(); i++) {
                Element methodElement = (Element) methodNodes.item(i);
                String methodName = methodElement.getAttribute("name");
                String returnType = methodElement.getAttribute("return-type");
                String modifierType = methodElement.getAttribute("modifierType");
                int lineLength = fontMetrics.stringWidth(modifierType + " " + methodName + "(): " + returnType);
                maxLineWidth = Math.max(maxLineWidth, lineLength);
            }
        }

        
        return Math.max(MIN_WIDTH, maxLineWidth + 20); //  padding  20 pixels
    }

    
    
    public int calculateRequiredHeight(Graphics g) {
      
        int numLines = attributeNodes.getLength() + methodNodes.getLength() + 4; 
        return numLines * LINE_HEIGHT + 10; 
    }
    
    
    

    
    public void moveBy(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        
        x = Math.max(ClassPanel.MARGIN, x); 
        y = Math.max(ClassPanel.MARGIN, y);
    }

    
    
    
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    
    
    
    public boolean isInterface() {
        return isInterface;
    }
    
    
    
}
