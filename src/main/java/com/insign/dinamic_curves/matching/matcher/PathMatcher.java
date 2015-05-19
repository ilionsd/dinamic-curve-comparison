package com.insign.dinamic_curves.matching.matcher;

import com.insign.common.function.Point2D;
import com.insign.dinamic_curves.Chord;
import com.insign.dinamic_curves.SortedCollection;
import com.insign.dinamic_curves.matching.Displacement2D;
import com.insign.dinamic_curves.matching.MetricsTable;
import com.insign.dinamic_curves.matching.MetricsTableIndices;
import com.insign.dinamic_curves.matching.PathMatching;
import com.insign.dinamic_curves.matching.metric.ChordPlaceMetric;
import com.insign.dinamic_curves.matching.metric.ChordSizeMetric;
import com.insign.dinamic_curves.matching.metric.JuravlevMetric;
import com.insign.dinamic_curves.matching.metric.Metric;
import com.insign.dinamic_curves.points.Extreme;

import java.util.*;

/**
 * Created by ilion on 18.04.2015.
 */
public class PathMatcher implements Matcher<SortedCollection<Extreme>> {
	public static final Metric<Chord, Point2D> PLACE_METRIC = ChordPlaceMetric.getMeter();
	public static final Metric<Chord, Point2D> SIZE_METRIC = ChordSizeMetric.getMeter();
	public static final Metric<Chord, Integer> JURAVLEV_METRIC = JuravlevMetric.getMeter();



	protected static double METRIC_THRESHOLD = 100;

	private static final Displacement2D[] DISPLACEMENTS = {
			new Displacement2D(1, 1),
			new Displacement2D(1, 3),
			new Displacement2D(3, 1)
	};

	private SortedCollection<Extreme> reference = null;

	@Override
	public PathMatching match(SortedCollection<Extreme> extremes) {

		MetricsTable metricsTable = new MetricsTable(reference, extremes);

		List<MetricsTableIndices> tablePath = new ArrayList<MetricsTableIndices>();

		MetricsTableIndices indices = new MetricsTableIndices(-1, -1);
		while (indices.i() < metricsTable.getRowsCount() && indices.j() < metricsTable.getColumnsCount()) {
			double metric = Double.MAX_VALUE;
			MetricsTableIndices metricIndices = null;
			for (Displacement2D displacement : DISPLACEMENTS) {
				MetricsTableIndices nextIndices = indices.displace(displacement);
				double nextMetric = metricsTable.metricEvaluator(nextIndices);
				if (Double.compare(nextMetric, metric) < 0) {
					metric = nextMetric;
					metricIndices = nextIndices;
				}
			}
			indices = metricIndices;
			tablePath.add(indices);
		}

		double optimalMetric = 0;
		for (MetricsTableIndices optimalIndices : tablePath) {
			optimalMetric += metricsTable.metricEvaluator(optimalIndices);
		}

		PathMatching.Builder builder = PathMatching.newBuilder()
				.setMetricsTable(metricsTable)
				.setTablePath(tablePath)
				.setOptimalMetric(optimalMetric);

		if (Double.compare(optimalMetric, METRIC_THRESHOLD) < 0)
			return builder.setIsMatch(true).build();
		return builder.setIsMatch(false).build();
	}

	@Override
	public SortedCollection<Extreme> getReference() {
		return reference;
	}

	@Override
	public PathMatcher setReference(SortedCollection<Extreme> reference) {
		this.reference = reference;
		return this;
	}






}
