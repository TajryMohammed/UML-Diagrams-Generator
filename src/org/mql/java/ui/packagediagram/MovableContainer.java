package org.mql.java.ui.packagediagram;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

public class MovableContainer extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private Point initialClick;
	private boolean resizing = false;
	private int resizeRegion = -1;
	private Dimension originalSize;

	public MovableContainer() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				resizing = getResizeRegion(e.getPoint()) != -1;
				updateCursor(e.getPoint());
				if (resizing) {
					originalSize = getSize();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (resizing) {
					originalSize = null;
				}
				resizing = false;
				setCursor(Cursor.getDefaultCursor());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				updateCursor(e.getPoint());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!resizing && getComponentAt(e.getPoint()) == null) {
					setCursor(Cursor.getDefaultCursor());
				}
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (resizing) {
					handleResizing(e.getPoint());
				} else {
					handleDragging(e.getPoint());
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				updateCursor(e.getPoint());
			}
		});
	}

	private void handleDragging(Point currentPoint) {
		int thisX = getLocation().x;
		int thisY = getLocation().y;

		int xMoved = (thisX + currentPoint.x) - (thisX + initialClick.x);
		int yMoved = (thisY + currentPoint.y) - (thisY + initialClick.y);

		int X = thisX + xMoved;
		int Y = thisY + yMoved;
		setLocation(X, Y);
	}

	private void handleResizing(Point currentPoint) {
		if (resizeRegion != -1 && originalSize != null) {
			Dimension currentSize = originalSize.getSize();
			int newWidth = currentSize.width;
			int newHeight = currentSize.height;

			switch (resizeRegion) {
			case Cursor.N_RESIZE_CURSOR:
				newHeight = originalSize.height - (currentPoint.y - initialClick.y);
				break;
			case Cursor.S_RESIZE_CURSOR:
				newHeight = originalSize.height + (currentPoint.y - initialClick.y);
				break;
			case Cursor.W_RESIZE_CURSOR:
				newWidth = originalSize.width - (currentPoint.x - initialClick.x);
				break;
			case Cursor.E_RESIZE_CURSOR:
				newWidth = originalSize.width + (currentPoint.x - initialClick.x);
				break;
			case Cursor.NW_RESIZE_CURSOR:
				newWidth = originalSize.width - (currentPoint.x - initialClick.x);
				newHeight = originalSize.height - (currentPoint.y - initialClick.y);
				break;
			case Cursor.NE_RESIZE_CURSOR:
				newWidth = originalSize.width + (currentPoint.x - initialClick.x);
				newHeight = originalSize.height - (currentPoint.y - initialClick.y);
				break;
			case Cursor.SW_RESIZE_CURSOR:
				newWidth = originalSize.width - (currentPoint.x - initialClick.x);
				newHeight = originalSize.height + (currentPoint.y - initialClick.y);
				break;
			case Cursor.SE_RESIZE_CURSOR:
				newWidth = originalSize.width + (currentPoint.x - initialClick.x);
				newHeight = originalSize.height + (currentPoint.y - initialClick.y);
				break;
			default:
				break;
			}

			newWidth = Math.max(newWidth, getMinimumSize().width);
			newHeight = Math.max(newHeight, getMinimumSize().height);

			setSize(new Dimension(newWidth, newHeight));
		}
	}

	private int getResizeRegion(Point point) {
		int tolerance = 8;
		int width = getWidth();
		int height = getHeight();

		if (point.y < tolerance) {
			if (point.x < tolerance) {
				return Cursor.NW_RESIZE_CURSOR;
			} else if (point.x > width - tolerance) {
				return Cursor.NE_RESIZE_CURSOR;
			} else {
				return Cursor.N_RESIZE_CURSOR;
			}
		} else if (point.y > height - tolerance) {
			if (point.x < tolerance) {
				return Cursor.SW_RESIZE_CURSOR;
			} else if (point.x > width - tolerance) {
				return Cursor.SE_RESIZE_CURSOR;
			} else {
				return Cursor.S_RESIZE_CURSOR;
			}
		} else if (point.x < tolerance) {
			return Cursor.W_RESIZE_CURSOR;
		} else if (point.x > width - tolerance) {
			return Cursor.E_RESIZE_CURSOR;
		}

		return -1;
	}

	private void updateCursor(Point currentPoint) {
		int newResizeRegion = getResizeRegion(currentPoint);
		if (newResizeRegion != -1 || resizing) {
			resizeRegion = newResizeRegion;
			setCursor(Cursor.getPredefinedCursor(resizeRegion));
		} else {
			setCursor(Cursor.getDefaultCursor());
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 500);
	}

}
