package com.insign.dinamic_curves.matching.matcher;

import com.insign.common.function.Point2D;
import com.insign.dinamic_curves.SortedCollection;
import com.insign.dinamic_curves.matching.*;
import com.insign.dinamic_curves.matching.metric.EuclideanMetric;
import com.insign.dinamic_curves.matching.metric.Metric;
import com.insign.dinamic_curves.matching.metric.MetricEvaluator;
import com.insign.dinamic_curves.points.Extreme;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ilion on 25.05.2015.
 */
public class IntermediateExtremesConformityResolver extends AbstractMatcher<SortedCollection<Extreme>> implements Resolver<SortedCollection<Extreme>> {
	public static final Metric<Point2D> EUCLIDEAN_METRIC = EuclideanMetric.getMeter();
	public static final double EUCLIDEAN_METRIC_WEIGHT = 1;
	public static final MetricEvaluator metricEvaluator = new MetricEvaluator()
			.setWeight(EUCLIDEAN_METRIC.getName(), EUCLIDEAN_METRIC_WEIGHT);

	public static final Displacement2D[] DISPLACEMENTS = new Displacement2D[] {
			new Displacement2D(1, 1),
			new Displacement2D(0, 1),
			new Displacement2D(1, 0)
	};

	@Override
	public IntermediateExtremesConformitySolution resolve(SortedCollection<Extreme> toResolve) {
		MetricsTableBuilder<Point2D> metricsTableBuilder = new MetricsTableBuilder<Point2D>()
				.addMetric(EUCLIDEAN_METRIC)
				.setMetricEvaluator(metricEvaluator);

		List<Point2D> refPoint2DList = toPoint2DList(getReference());
		List<Point2D> toResolvePoint2DList = toPoint2DList(toResolve);

		MetricsTable metricsTable = metricsTableBuilder.build(refPoint2DList, toResolvePoint2DList);

		List<MetricsTableIndices> tablePath = metricsTable.optimize(DISPLACEMENTS);

		double optimalMetric = 0;
		LinkedHashMap<Extreme, Extreme> extremeConformity = new LinkedHashMap<Extreme, Extreme>();
		for (MetricsTableIndices optimalIndices : tablePath) {
			optimalMetric += metricsTable.get(optimalIndices);
			Extreme refExtreme = getReference().get(optimalIndices.i());
			Extreme toRslvExtreme = toResolve.get(optimalIndices.j());
			extremeConformity.put(refExtreme, toRslvExtreme);
		}

		IntermediateExtremesConformitySolution.Builder builder = IntermediateExtremesConformitySolution.newBuilder()
				.setOptimalMetric(optimalMetric)
				.setExtremeConformity(extremeConformity);

		return builder.build();
	}

	@Override
	public IntermediateExtremesConformityResolver setReference(SortedCollection<Extreme> reference) {
		super.setReference(reference);
		return this;
	}

	private static List<Point2D> toPoint2DList(SortedCollection<Extreme> sortedCollection) {
		List<Point2D> point2DList = new ArrayList<Point2D>();
		for (Extreme extreme : sortedCollection) {
			point2DList.add(extreme.getValue());
		}
		return point2DList;
	}
}
