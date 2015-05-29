package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.Chord;
import com.insign.dinamic_curves.SortedCollection;
import com.insign.dinamic_curves.matching.Displacement2D;
import com.insign.dinamic_curves.matching.MetricsTable;
import com.insign.dinamic_curves.matching.MetricsTableBuilder;
import com.insign.dinamic_curves.matching.MetricsTableIndices;
import com.insign.dinamic_curves.matching.metric.*;
import com.insign.dinamic_curves.points.BoundaryPoint;
import com.insign.dinamic_curves.points.Extreme;
import com.insign.dinamic_curves.points.SignaturePoint;

import java.util.*;

/**
 * Created by ilion on 10.05.2015.
 */
public class ChordConformityResolver extends AbstractMatcher<List<? extends Chord>> implements Resolver<List<? extends Chord>> {

	public static final Metric<Chord> PLACE_METRIC = ChordPlaceMetric.getMeter();
	public static final Metric<Chord> SIZE_METRIC = ChordSizeMetric.getMeter();
	public static final Metric<Chord> JURAVLEV_METRIC = JuravlevMetric.getMeter();

	protected static double PLACE_METRIC_WEIGHT = 1;
	protected static double SIZE_METRIC_WEIGHT = 1;
	protected static double JURAVLEV_METRIC_WEIGHT = 100;

	private static final MetricEvaluator metricEvaluator = new MetricEvaluator()
			.setWeight(PLACE_METRIC.getName(), PLACE_METRIC_WEIGHT)
			.setWeight(SIZE_METRIC.getName(), SIZE_METRIC_WEIGHT)
			.setWeight(JURAVLEV_METRIC.getName(), JURAVLEV_METRIC_WEIGHT);

	private static final Displacement2D[] DISPLACEMENTS = {
			new Displacement2D(1, 1),
			new Displacement2D(1, 3),
			new Displacement2D(3, 1)
	};

	@Override
	public ChordConformitySolution resolve(List<? extends Chord> chords) {
		MetricsTableBuilder<Chord> chordMetricsTableBuilder = new MetricsTableBuilder<Chord>()
				.addMetric(PLACE_METRIC)
				.addMetric(SIZE_METRIC)
				.addMetric(JURAVLEV_METRIC)
				.setMetricEvaluator(metricEvaluator);

		MetricsTable metricsTable = chordMetricsTableBuilder.build(getReference(), chords);

		List<MetricsTableIndices> tablePath = metricsTable.optimize(DISPLACEMENTS);

		double optimalMetric = 0;
		for (MetricsTableIndices optimalIndices : tablePath) {
			optimalMetric += metricsTable.get(optimalIndices);
		}

		Integer[] indexSolution = new Integer[getReference().size()];
		com.insign.common.ArrayUtils.setAll(indexSolution, null);

		LinkedHashMap<Chord, Chord> chordSolution = new LinkedHashMap<Chord, Chord>();

		for (MetricsTableIndices indices : tablePath) {
			indexSolution[indices.i()] = indices.j();
			chordSolution.put(getReference().get(indices.i()), chords.get(indices.j()));
		}

		ChordConformitySolution.Builder builder = ChordConformitySolution.newBuilder()
				.setMetricsTable(metricsTable)
				.setTablePath(tablePath)
				.setOptimalMetric(optimalMetric)
				.setIndexSolution(indexSolution)
				.setChordSolution(chordSolution);

		return builder.build();
	}

	int getPointIndex(int verticalExtremeIndex, SortedCollection<? extends SignaturePoint> points) {
		int index = -1;
		for (int k = 0; k < points.size(); k++) {
			SignaturePoint point = points.get(k);
			if (point instanceof BoundaryPoint)
				index++;
			else if (point instanceof Extreme && Extreme.Type.VERTICAL.equals(((Extreme) point).getExtremumType()))
				index++;
			if (index == verticalExtremeIndex)
				return k;
		}
		return -1;
	}

	@Override
	public ChordConformityResolver setReference(List<? extends Chord> reference) {
		super.setReference(reference);
		return this;
	}
}


