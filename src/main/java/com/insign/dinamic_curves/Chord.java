package com.insign.dinamic_curves;

import com.insign.common.function.Point2D;

/**
 * Created by ilion on 18.04.2015.
 */
public class Chord {
	private Point2D start = new Point2D();
	private Point2D end = new Point2D();

	public Chord(){}

	public Chord(Point2D start, Point2D end) {
		if (Double.compare(start.getX(), end.getX()) < 0) {
			setStart(start);
			setEnd(end);
		}
		else {
			setStart(end);
			setEnd(start);
		}
	}

	public Point2D getStart() {
		return start;
	}

	public Point2D getEnd() {
		return end;
	}

	public void setStart(Point2D point) {
		start.setX(point.getX());
		start.setY(point.getY());
	}

	public void setEnd(Point2D point) {
		end.setX(point.getX());
		end.setY(point.getY());
	}
}
