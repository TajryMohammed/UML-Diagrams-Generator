package org.mql.java.ui.classdiagram;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.mql.java.controller.XMLNode;
import org.mql.java.controller.core.EntityRelationAnalyzer;
import org.mql.java.models.ModEntityDrawer;
import org.mql.java.models.ModRelationship;
import org.w3c.dom.Document;

public class ClassPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int MARGIN = 20;

	private volatile List<ModEntityDrawer> classEntities;
	private ModEntityDrawer selectedClassEntity = null;
	private Point clickPoint = null;
	//private int frameWidth;

	private List<RelationsLine> relations;
	//private ModEntityDrawer classEntity;
	//private ModEntityDrawer modEntities;

	public ClassPanel(String xmlFilePath, int frameWidth) throws Exception {
		//this.frameWidth = frameWidth;

		XMLNode xmlReader = new XMLNode();
		Document document = xmlReader.parseXML(xmlFilePath);


		classEntities = xmlReader.extractClassEntities(document, frameWidth);
		relations = new ArrayList<>(); 

		updateRelationships();

		addMouseListener(new ClassPanelMouseListener());
		addMouseMotionListener(new ClassPanelMouseMotionListener());
	}

	public void updateRelationships() throws ClassNotFoundException {
		relations.clear();

		for (int i = 0; i < classEntities.size(); i++) {
			ModEntityDrawer sourceEntity = classEntities.get(i);

			for (int j = i + 1; j < classEntities.size(); j++) {
				ModEntityDrawer targetEntity = classEntities.get(j);

				List<ModRelationship> relationships = RelationshipAnalyzer.analyzeRelationships(sourceEntity,
						targetEntity);

				for (ModRelationship relationship : relationships) {

					relations.add(
							new RelationsLine(sourceEntity, targetEntity, relationship.getRelationshipType(), this));
				}
			}
		}

		repaint();
	}

	public ClassPanel(ModEntityDrawer classEntity) {
		//this.classEntity = classEntity;
	}

	public int getMargin() {
		return MARGIN;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (ModEntityDrawer classEntity : classEntities) {
			classEntity.draw(g);
		}

		for (RelationsLine relation : relations) {
			relation.draw(g);
		}
	}

	public class ClassPanelMouseListener extends MouseAdapter {
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

			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			if (selectedClassEntity != null) {
				selectedClassEntity.setBorderColor(ModEntityDrawer.BORDER_COLOR_NORMAL);
				repaint();
			}
		}
	}

	public class ClassPanelMouseMotionListener extends MouseMotionAdapter {
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

	public void setClassEntities(List<ModEntityDrawer> classEntities) {
		this.classEntities = classEntities;
		repaint();
	}

}
