package org.mql.java.models;

/*********************************/

public class ModClassPosition {
    public static final int CLASS_WIDTH = 100;
    public static final int CLASS_HEIGHT = 60;

    public String className;
    public int x;
    public int y;

    public ModClassPosition(String className, int x, int y) {
        this.className = className;
        this.x = x;
        this.y = y;
    }

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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

	public static int getClassWidth() {
		return CLASS_WIDTH;
	}

	public static int getClassHeight() {
		return CLASS_HEIGHT;
	}
    
	public boolean hasValidCoordinates() {
        return x != 0 || y != 0; // Adjust this condition based on your requirements
    }
	
	public void setDefaultCoordinates() {
        if (x == 0 && y == 0) {
            // Set default coordinates here
            x = 100; // Example default x-coordinate
            y = 100; // Example default y-coordinate
        }
    }
}


