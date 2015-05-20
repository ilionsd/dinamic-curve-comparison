package com.insign.dinamic_curves.matching.metric;

import com.insign.common.function.Point2D;

/**
 * Created by ilion on 19.05.2015.
 */
public class EuclideanMetric implements Metric<Point2D> {
	public static final String NAME = EuclideanMetric.class.getName();

	private static final Metric<Point2D> meter = new EuclideanMetric();

	public static Metric<Point2D> getMeter() {
		return meter;
	}

	public EuclideanMetric() {}

	@Override
	public Double getMetric(Point2D obj1, Point2D obj2) {
		return Point2D.distance(obj1, obj2);
	}

	@Override
	public String getName() {
		return NAME;
	}
}
