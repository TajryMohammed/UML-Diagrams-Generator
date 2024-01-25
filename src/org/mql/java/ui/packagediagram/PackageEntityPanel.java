package org.mql.java.ui.packagediagram;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.mql.java.models.ModEntityDrawer;

public class PackageEntityPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<ModEntityDrawer> classEntities;
	private ModEntityDrawer selectedClassEntity = null;
	private Point clickPoint = null;

	public PackageEntityPanel(PackageEntity packageEntity) {

		this.classEntities = new ArrayList<>(packageEntity.getClassEntities());

		int xOffset = 70;
		int yOffset = 70;
		int margin = 20;

		for (int i = 0; i < classEntities.size(); i++) {
			ModEntityDrawer classEntity = classEntities.get(i);

			int entityX = xOffset + i * (100 + margin);
			int entityY = yOffset;

			classEntity.setLocation(new Point(entityX, entityY));
		}

		addMouseListener(new PackageEntityMouseListener());
		addMouseMotionListener(new PackageEntityMouseMotionListener());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (ModEntityDrawer classEntity : classEntities) {
			classEntity.draw(g);
		}
	}

	private class PackageEntityMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			selectedClassEntity = null;
			clickPoint = null;
			for (ModEntityDrawer classEntity : classEntities) {
				if (classEntity.getBounds().contains(e.getPoint())) {
					selectedClassEntity = classEntity;
					clickPoint = e.getPoint();
					break;
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (selectedClassEntity != null) {
				selectedClassEntity.setBorderColor(ModEntityDrawer.BORDER_COLOR_NORMAL);
				repaint();
			}
		}
	}

	private class PackageEntityMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (selectedClassEntity != null) {
				int deltaX = e.getX() - clickPoint.x;
				int deltaY = e.getY() - clickPoint.y;

				selectedClassEntity.moveBy(deltaX, deltaY);

				clickPoint = e.getPoint();
				selectedClassEntity.setBorderColor(ModEntityDrawer.DRAGGING_BORDER_COLOR);
				repaint();
			}
		}
	}
}