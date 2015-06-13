package com.insign.dinamic_curves.matching.metric;

import com.insign.common.function.Point2D;
import com.insign.dinamic_curves.Chord;

/**
 * Created by ilion on 02.05.2015.
 */
public class JuravlevMetric implements Metric<Chord> {

	private static final double SAME_ORIENTED = 0;
	private static final double CROSS_ORIENTED = 1;

	private static final double THRESHOLD = Math.sqrt(2.0) / 2.0;

	public static final String NAME = JuravlevMetric.class.getName();
	private static final Metric<Chord> meter = new JuravlevMetric();

	public static Metric<Chord> getMeter() {
		return meter;
	}

	@Override
	public Double getMetric(Chord a, Chord b) {
		Point2D aVector = new Point2D(a.getEnd().getX() - a.getStart().getX(), a.getEnd().getY() - a.getStart().getY());
		Point2D bVector = new Point2D(b.getEnd().getX() - b.getStart().getX(), b.getEnd().getY() - b.getStart().getY());

		double cosAlpha = Point2D.asVector.angleCos(aVector, bVector);

		double metric;
		if (Double.compare(THRESHOLD, Math.abs(cosAlpha)) >= 0)
			metric = SAME_ORIENTED;
		else
			metric = CROSS_ORIENTED;

		return metric;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
