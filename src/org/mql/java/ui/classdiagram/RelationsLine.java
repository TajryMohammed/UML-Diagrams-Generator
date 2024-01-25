package org.mql.java.ui.classdiagram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.List;

import org.mql.java.common.RelationshipType;
import org.mql.java.controller.core.EntityRelationAnalyzer;
import org.mql.java.models.ModEntity;
import org.mql.java.models.ModEntityDrawer;
import org.mql.java.models.ModRelationship;

public class RelationsLine {

	private ModEntityDrawer sourceEntity;
	private ModEntityDrawer targetEntity;
	// private RelationshipType type;
	// private ClassPanel classPanel;

	public RelationsLine(ModEntityDrawer sourceEntity, ModEntityDrawer targetEntity, RelationshipType type,
			ClassPanel classPanel) {
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		// this.type = type;
		// this.classPanel = classPanel;
	}

	public ModEntity getSourceEntity() {
		return sourceEntity;
	}

	public ModEntity getTargetEntity() {
		return targetEntity;
	}

	public void draw(Graphics g) {
		Point sourceCenter = getCenter(sourceEntity.getBounds());
		Point targetCenter = getCenter(targetEntity.getBounds());

		g.setColor(Color.BLACK);

		Point startPoint = getIntersectionPoint(sourceCenter, targetCenter, sourceEntity.getBounds());
		Point endPoint = getIntersectionPoint(targetCenter, sourceCenter, targetEntity.getBounds());

		EntityRelationAnalyzer entityRelationAnalyzer = new EntityRelationAnalyzer();
		List<ModRelationship> relationships = entityRelationAnalyzer.parseRelationships(sourceEntity, targetEntity);

		RelationshipType dynamicType = !relationships.isEmpty() ? relationships.get(0).getRelationshipType()
				: RelationshipType.GENERALIZATION;

		switch (dynamicType) {
		case ASSOCIATION:
			drawAssociationArrow(g, startPoint, endPoint);
			break;
		case DEPENDENCY:
			drawDependencyArrow(g, startPoint, endPoint);
			break;
		case AGGREGATION:
			drawAggregationArrow(g, startPoint, endPoint);
			break;
		case COMPOSITION:
			drawCompositionArrow(g, startPoint, endPoint);
			break;
		case IMPLEMENTATION:
			drawImplementationArrow(g, startPoint, endPoint);
			break;
		case GENERALIZATION:
			drawGeneralizationArrow(g, startPoint, endPoint);
			break;
		}
	}

	private Point getCenter(Rectangle bounds) {
		int centerX = bounds.x + bounds.width / 2;
		int centerY = bounds.y + bounds.height / 2;
		return new Point(centerX, centerY);
	}

	private Point getIntersectionPoint(Point source, Point target, Rectangle bounds) {
		Point intersectionTop = getIntersectionPoint(source, target, bounds.x, bounds.y, bounds.x + bounds.width,
				bounds.y);
		Point intersectionBottom = getIntersectionPoint(source, target, bounds.x, bounds.y + bounds.height,
				bounds.x + bounds.width, bounds.y + bounds.height);
		Point intersectionLeft = getIntersectionPoint(source, target, bounds.x, bounds.y, bounds.x,
				bounds.y + bounds.height);
		Point intersectionRight = getIntersectionPoint(source, target, bounds.x + bounds.width, bounds.y,
				bounds.x + bounds.width, bounds.y + bounds.height);
		Point closestIntersection = getClosestPoint(source, target, intersectionTop, intersectionBottom,
				intersectionLeft, intersectionRight);

		return closestIntersection;
	}

	private Point getClosestPoint(Point source, Point target, Point... points) {
		Point closestPoint = points[0];
		double minDistance = source.distance(closestPoint) + target.distance(closestPoint);

		for (Point point : points) {
			double distance = source.distance(point) + target.distance(point);
			if (distance < minDistance) {
				minDistance = distance;
				closestPoint = point;
			}
		}

		return closestPoint;
	}

	private Point getIntersectionPoint(Point source, Point target, int x1, int y1, int x2, int y2) {
		double slope = (double) (target.y - source.y) / (target.x - source.x);

		int x = (int) ((y1 - source.y) / slope + source.x);
		int y = y1;

		if (x < x1 || x > x2) {
			x = x2;
			y = (int) (slope * (x2 - source.x) + source.y);
		}

		return new Point(x, y);
	}

	private void drawCompositionArrow(Graphics g, Point source, Point target) {
		int arrowLength = 15;
		int arrowWidth = 15;

		g.drawLine(source.x, source.y, target.x, target.y);
		drawCompositionArrowhead(g, target, source, arrowLength, arrowWidth);
		drawCompositionDiamond(g, target, arrowLength, arrowWidth);
	}

	private void drawCompositionArrowhead(Graphics g, Point tip, Point tail, int arrowLength, int arrowWidth) {
		double angle = Math.atan2(tip.y - tail.y, tip.x - tail.x);

		int x1 = (int) (tip.x - arrowLength * Math.cos(angle - Math.PI / 6));
		int y1 = (int) (tip.y - arrowLength * Math.sin(angle - Math.PI / 6));

		int x2 = (int) (tip.x - arrowLength * Math.cos(angle + Math.PI / 6));
		int y2 = (int) (tip.y - arrowLength * Math.sin(angle + Math.PI / 6));

		Polygon arrowhead = new Polygon();
		arrowhead.addPoint(tip.x, tip.y);
		arrowhead.addPoint(x1, y1);
		arrowhead.addPoint(x2, y2);

		g.fillPolygon(arrowhead);
	}

	private void drawCompositionDiamond(Graphics g, Point tip, int arrowLength, int arrowWidth) {
		int x1 = tip.x - arrowLength;
		int y1 = tip.y - arrowWidth / 2;

		int x2 = tip.x - arrowLength * 2;
		int y2 = tip.y;

		int x3 = tip.x - arrowLength;
		int y3 = tip.y + arrowWidth / 2;

		int[] xPoints = { x1, x2, x3, tip.x };
		int[] yPoints = { y1, y2, y3, tip.y };

		g.fillPolygon(xPoints, yPoints, 4);
	}

	private void drawAggregationArrow(Graphics g, Point source, Point target) {
		int arrowLength = 15;
		int arrowWidth = 15;

		g.drawLine(source.x, source.y, target.x, target.y);
		drawAggregationArrowhead(g, target, source, arrowLength, arrowWidth);
	}

	private void drawAggregationArrowhead(Graphics g, Point tip, Point tail, int arrowLength, int arrowWidth) {
		double angle = Math.atan2(tip.y - tail.y, tip.x - tail.x);

		int x1 = (int) (tip.x - arrowLength * Math.cos(angle - Math.PI / 6));
		int y1 = (int) (tip.y - arrowLength * Math.sin(angle - Math.PI / 6));

		int x2 = (int) (tip.x - arrowLength * Math.cos(angle + Math.PI / 6));
		int y2 = (int) (tip.y - arrowLength * Math.sin(angle + Math.PI / 6));

		Polygon arrowhead = new Polygon();
		arrowhead.addPoint(tip.x, tip.y);
		arrowhead.addPoint(x1, y1);
		arrowhead.addPoint(x2, y2);

		g.fillPolygon(arrowhead);
	}

	private void drawDependencyArrow(Graphics g, Point source, Point target) {
		g.drawLine(source.x, source.y, target.x, target.y);
		drawArrowHead(g, target, source);
	}

	private void drawAssociationArrow(Graphics g, Point source, Point target) {
		g.drawLine(source.x, source.y, target.x, target.y);
		drawArrowHead(g, target, source);
	}

	private void drawImplementationArrow(Graphics g, Point source, Point target) {
		int arrowLength = 15;
		int arrowWidth = 15;

		g.drawLine(source.x, source.y, target.x, target.y);
		drawImplementationArrowhead(g, target, source, arrowLength, arrowWidth);
	}

	private void drawImplementationArrowhead(Graphics g, Point tip, Point tail, int arrowLength, int arrowWidth) {
		double angle = Math.atan2(tip.y - tail.y, tip.x - tail.x);

		int x1 = (int) (tip.x - arrowLength * Math.cos(angle - Math.PI / 6));
		int y1 = (int) (tip.y - arrowLength * Math.sin(angle - Math.PI / 6));

		int x2 = (int) (tip.x - arrowLength * Math.cos(angle + Math.PI / 6));
		int y2 = (int) (tip.y - arrowLength * Math.sin(angle + Math.PI / 6));

		Polygon arrowhead = new Polygon();
		arrowhead.addPoint(tip.x, tip.y);
		arrowhead.addPoint(x1, y1);
		arrowhead.addPoint(x2, y2);

		g.fillPolygon(arrowhead);
	}

	private void drawGeneralizationArrow(Graphics g, Point source, Point target) {
		int arrowLength = 15;
		int arrowWidth = 15;

		g.drawLine(source.x, source.y, target.x, target.y);
		drawGeneralizationArrowhead(g, target, source, arrowLength, arrowWidth);
	}

	private void drawGeneralizationArrowhead(Graphics g, Point tip, Point tail, int arrowLength, int arrowWidth) {
		double angle = Math.atan2(tip.y - tail.y, tip.x - tail.x);

		int x1 = (int) (tip.x - arrowLength * Math.cos(angle - Math.PI / 6));
		int y1 = (int) (tip.y - arrowLength * Math.sin(angle - Math.PI / 6));

		int x2 = (int) (tip.x - arrowLength * Math.cos(angle + Math.PI / 6));
		int y2 = (int) (tip.y - arrowLength * Math.sin(angle + Math.PI / 6));

		Polygon arrowhead = new Polygon();
		arrowhead.addPoint(tip.x, tip.y);
		arrowhead.addPoint(x1, y1);
		arrowhead.addPoint(x2, y2);

		g.fillPolygon(arrowhead);
	}

	private void drawArrowHead(Graphics g, Point tip, Point tail) {
		double angle = Math.atan2(tip.y - tail.y, tip.x - tail.x);
		int arrowLength = 15;
		// int arrowWidth = 15;

		int x1 = (int) (tip.x - arrowLength * Math.cos(angle - Math.PI / 6));
		int y1 = (int) (tip.y - arrowLength * Math.sin(angle - Math.PI / 6));

		int x2 = (int) (tip.x - arrowLength * Math.cos(angle + Math.PI / 6));
		int y2 = (int) (tip.y - arrowLength * Math.sin(angle + Math.PI / 6));

		Polygon arrowhead = new Polygon();
		arrowhead.addPoint(tip.x, tip.y);
		arrowhead.addPoint(x1, y1);
		arrowhead.addPoint(x2, y2);

		g.fillPolygon(arrowhead);
	}

}
