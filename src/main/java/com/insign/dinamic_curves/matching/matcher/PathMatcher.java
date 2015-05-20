package com.insign.dinamic_curves.matching.matcher;

import com.insign.common.function.Point2D;
import com.insign.dinamic_curves.Chord;
import com.insign.dinamic_curves.ListUtils;
import com.insign.dinamic_curves.Signature;
import com.insign.dinamic_curves.SortedCollection;
import com.insign.dinamic_curves.matching.*;
import com.insign.dinamic_curves.matching.metric.*;
import com.insign.dinamic_curves.points.BoundaryPoint;
import com.insign.dinamic_curves.points.Extreme;
import com.insign.dinamic_curves.points.SignaturePoint;

import java.util.*;

/**
 * Created by ilion on 18.04.2015.
 */
public class PathMatcher implements Matcher<Signature> {
	public static final Metric<Chord> PLACE_METRIC = ChordPlaceMetric.getMeter();
	public static final Metric<Chord> SIZE_METRIC = ChordSizeMetric.getMeter();
	public static final Metric<Chord> JURAVLEV_METRIC = JuravlevMetric.getMeter();
	public static final Metric<Point2D> EUCLIDEAN_METRIC = EuclideanMetric.getMeter();

	protected static double PLACE_METRIC_WEIGHT = 1;
	protected static double SIZE_METRIC_WEIGHT = 1;
	protected static double JURAVLEV_METRIC_WEIGHT = 100;


	public static final MetricEvaluator pathMetricEvaluator = new MetricEvaluator()
			.setWeight(PLACE_METRIC.getName(), PLACE_METRIC_WEIGHT)
			.setWeight(SIZE_METRIC.getName(), SIZE_METRIC_WEIGHT)
			.setWeight(JURAVLEV_METRIC.getName(), JURAVLEV_METRIC_WEIGHT);



	protected static double METRIC_THRESHOLD = 100;

	private static final Displacement2D[] DISPLACEMENTS = {
			new Displacement2D(1, 1),
			new Displacement2D(1, 3),
			new Displacement2D(3, 1)
	};

	private Signature reference = null;

	@Override
	public PathMatching match(Signature signature) {

		SortedCollection<Extreme> refVerticalExtremes = reference.getVerticalExtremes();
		SortedCollection<Extreme> sigVerticalExtremes = signature.getVerticalExtremes();

		MetricsTableBuilder<Chord> chordMetricsTableBuilder = new MetricsTableBuilder<Chord>()
				.addMetric(PLACE_METRIC)
				.addMetric(SIZE_METRIC)
				.addMetric(JURAVLEV_METRIC)
				.setMetricEvaluator(pathMetricEvaluator);

		List<Chord> refVerticalExtremesChordList = getChordList(refVerticalExtremes);
		List<Chord> sigVerticalExtremesChordList = getChordList(sigVerticalExtremes);

		MetricsTable metricsTable = chordMetricsTableBuilder.build(refVerticalExtremesChordList, sigVerticalExtremesChordList);

		List<MetricsTableIndices> tablePath = metricsTable.optimize(DISPLACEMENTS);

		double optimalMetric = 0;
		for (MetricsTableIndices optimalIndices : tablePath) {
			optimalMetric += metricsTable.get(optimalIndices);
		}

		for (int k = 1; k < tablePath.size(); k++) {
			int refLeftIndex = tablePath.get(k - 1).i();
			int refRightIndex = tablePath.get(k).i();
			int sigLeftIndex = tablePath.get(k - 1).j();
			int sigRightIndex = tablePath.get(k).j();
			SortedCollection<Extreme> refIntermediateHorizontalExtremes = reference.getHorizontalExtremes()
					.getMoreThan(refVerticalExtremes.get(refLeftIndex))
					.getLessThan(refVerticalExtremes.get(refRightIndex));
			SortedCollection<Extreme> sigIntermediateHorizontalExtremes = signature.getHorizontalExtremes()
					.getMoreThan(sigVerticalExtremes.get(sigLeftIndex))
					.getLessThan(sigVerticalExtremes.get(sigRightIndex));

		}


		List<Integer> conformity = new ArrayList<Integer>(reference.getSkeleton().size());
		ListUtils.setAll(conformity, -1);

		for (MetricsTableIndices index : tablePath) {
			int refIndex = getPointIndex(index.i(), reference.getSkeleton());
			int sigIndex = getPointIndex(index.j(), signature.getSkeleton());
			conformity.set(refIndex, sigIndex);
		}

		for (int k = 0; k < refVerticalExtremes.size(); k++) {

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
	public Signature getReference() {
		return reference;
	}

	@Override
	public PathMatcher setReference(Signature reference) {
		this.reference = reference;
		return this;
	}

	private int getPointIndex(int verticalExtremeIndex, SortedCollection<? extends SignaturePoint> points) {
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

	private static List<Chord> getChordList(SortedCollection<Extreme> extremes) {
		List<Chord> chordList = new ArrayList<Chord>();
		for (int start = 0; start < extremes.size(); start++) {
			//-- Primary chord --
			int end = start + 1;
			if (end < extremes.size()) {
				chordList.add(new Chord(extremes.get(start).getValue(), extremes.get(end).getValue()));

				//-- 2nd kind variability change --
				end = start + 2;
				if (end < extremes.size())
					chordList.add(new Chord(extremes.get(start).getValue(), extremes.get(end).getValue()));
			}
		}
		return chordList;
	}




}
