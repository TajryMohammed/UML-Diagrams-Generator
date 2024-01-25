package org.mql.java.models;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.ui.classdiagram.ClassPanel;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ModEntityDrawer extends ModEntity {

	// Attributes :

	private String className;
	private NodeList attributeNodes;
	private NodeList methodNodes;
	private boolean isInterface;
	private int x;
	private int y;
	private Color borderColor;

	private int width;
	private int height;
	private String superclassName;

	public static final int LINE_HEIGHT = 20;
	public static final Color BORDER_COLOR_NORMAL = Color.BLACK;
	public static final Color DRAGGING_BORDER_COLOR = Color.BLUE;
	public static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 12);
	public static final int MIN_WIDTH = 200;
	public static final int MIN_HEIGHT = 100;

	// Constructors :

	public ModEntityDrawer(Element classElement, int x, int y) {
		this.className = classElement.getAttribute("name");
		this.attributeNodes = classElement.getElementsByTagName("attribute");
		this.methodNodes = classElement.getElementsByTagName("method");
		this.isInterface = classElement.getNodeName().equals("interface");
		this.x = x;
		this.y = y;
		this.borderColor = BORDER_COLOR_NORMAL;
	}

	// Methods

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

		for (int i = 0; i < methodNodes.getLength(); i++) {
			Element methodElement = (Element) methodNodes.item(i);
			String methodName = methodElement.getAttribute("name");
			String returnType = methodElement.getAttribute("return-type");
			String modifierType = methodElement.getAttribute("modifierType");
			String parameters = methodElement.getAttribute("parameters");

			g.setFont(BOLD_FONT);
			g.drawString(modifierType + " " + methodName + "(" + parameters + "): " + returnType, x + 10,
					nameY + (i + attributeNodes.getLength() + 3) * LINE_HEIGHT + 10);
		}

		for (int i = 0; i < attributeNodes.getLength(); i++) {
			Element attributeElement = (Element) attributeNodes.item(i);
			String attributeName = attributeElement.getAttribute("name");
			String attributeType = attributeElement.getAttribute("type");
			String modifierType = attributeElement.getAttribute("modifierType");
			g.setFont(BOLD_FONT);
			g.drawString(modifierType + " " + attributeName + ": " + attributeType, x + 10,
					nameY + (i + 2) * LINE_HEIGHT + 5);
		}

		g.drawLine(x, nameY + (attributeNodes.getLength() + 2) * LINE_HEIGHT + 10, x + requiredWidth,
				nameY + (attributeNodes.getLength() + 2) * LINE_HEIGHT + 10);

		g.setFont(g.getFont().deriveFont(Font.PLAIN));
	}

	public int calculateRequiredWidth(Graphics g) {
		int maxLineWidth = 0;

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

		return Math.max(MIN_WIDTH, maxLineWidth + 20); 
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

	public boolean isInterface() {
		return isInterface;
	}

	public List<String> getAttributeNames() {
		List<String> attributeNames = new ArrayList<>();
		for (ModAttribute attribute : entityAttributes) {
			attributeNames.add(attribute.getAttributeName());
		}
		return attributeNames;
	}

	public List<String> getMethodNames() {
		List<String> methodNames = new ArrayList<>();
		if (entityMethods != null) {
			for (ModMethod method : entityMethods) {
				methodNames.add(method.getMethodName());
			}
		}
		return methodNames;
	}

	public boolean hasMethod(String methodName, String returnType, String parameters) {
		if (entityMethods != null) {
			for (ModMethod method : entityMethods) {
				if (method.getMethodName().equals(methodName) && method.getReturnType().equals(returnType)
						&& method.getMethodParameters().equals(parameters)) {
					return true;
				}
			}
		}
		return false;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, calculateRequiredWidth(null), calculateRequiredHeight(null));
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setLocation(Point location) {
		this.x = location.x;
		this.y = location.y;
	}

	public String getClassName() {
		return className;
	}

	public NodeList getMethodNodes() {
		return methodNodes;
	}

	public NodeList getAttributeNodes() {
		return attributeNodes;
	}

	public String getSuperclassName() {
		return superclassName;
	}

}
