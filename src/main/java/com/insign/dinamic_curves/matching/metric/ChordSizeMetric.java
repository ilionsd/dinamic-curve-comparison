package com.insign.dinamic_curves.matching.metric;

import com.insign.common.function.Point2D;
import com.insign.dinamic_curves.Chord;

/**
 * Created by ilion on 22.04.2015.
 */
public class ChordSizeMetric implements Metric<Chord> {
	public static final String NAME = ChordSizeMetric.class.getName();
	private static final Metric<Chord> meter = new ChordSizeMetric();

	public static Metric<Chord> getMeter() {
		return meter;
	}

	public ChordSizeMetric() {}

	@Override
	public Double getMetric(Chord a, Chord b) {
		double xMetric = sizeMetric(a.getStart().getX(), a.getEnd().getX(), b.getStart().getX(), b.getEnd().getX());
		double yMetric = sizeMetric(a.getStart().getY(), a.getEnd().getY(), b.getStart().getY(), b.getEnd().getY());
		return xMetric + yMetric;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private static double sizeMetric(double start1, double end1, double start2, double end2) {
		return Math.abs(Math.abs(start1 - end1) - Math.abs(start2 - end2));
	}
}
