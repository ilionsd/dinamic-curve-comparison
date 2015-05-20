package com.insign.dinamic_curves.matching.metric;

import com.insign.common.function.Point2D;
import com.insign.dinamic_curves.Chord;

/**
 * Created by ilion on 22.04.2015.
 */
public class ChordPlaceMetric implements Metric<Chord> {
	public static final String NAME = ChordPlaceMetric.class.getName();
	private static final Metric<Chord> meter = new ChordPlaceMetric();

	public static Metric<Chord> getMeter() {
		return meter;
	}

	public ChordPlaceMetric() {}

	@Override
	public Double getMetric(Chord a, Chord b) {
		double xMetric = placeMetric(a.getStart().getX(), a.getEnd().getX(), b.getStart().getX(), b.getEnd().getX());
		double yMetric = placeMetric(a.getStart().getY(), a.getEnd().getY(), b.getStart().getY(), b.getEnd().getY());
		return xMetric + yMetric;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private static double placeMetric(double start1, double end1, double start2, double end2) {
		return Math.abs((start1 + end1) / 2.0 - (start2 + end2) / 2.0);
	}
}
