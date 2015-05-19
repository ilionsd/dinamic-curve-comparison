package com.insign.dinamic_curves.matching;

import com.insign.common.function.Point2D;
import com.insign.dinamic_curves.Chord;
import com.insign.dinamic_curves.SortedCollection;
import com.insign.dinamic_curves.matching.matcher.PathMatcher;
import com.insign.dinamic_curves.points.Extreme;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ilion on 06.05.2015.
 */
public class MetricsTable{
	protected static double PLACE_METRIC_MULTIPLIER = 1;
	protected static double SIZE_METRIC_MULTIPLIER = 1;
	protected static double JURAVLEV_METRIC_MULTIPLIER = 100;

	Map<String, Object>[][] metricsTable = null;
	private int rowsCount = 0;
	private int columnsCount = 0;

	public MetricsTable(SortedCollection<Extreme> reference, SortedCollection<Extreme> toCompare) {
		List<Chord> refChords = getChordList(reference);
		List<Chord> toCmpChords = getChordList(toCompare);
		generateMetricsTable(refChords, toCmpChords);
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public int getColumnsCount() {
		return columnsCount;
	}

	public double metricEvaluator(MetricsTableIndices indices) {
		double metric = 0;
		Map<String, Object> metricsItem = metricsTable[indices.i()][indices.j()];

		Point2D placeMetric = (Point2D) metricsItem.get(PathMatcher.PLACE_METRIC.getName());
		Point2D sizeMetric = (Point2D) metricsItem.get(PathMatcher.SIZE_METRIC.getName());
		Integer juravlevMetric = (Integer) metricsItem.get(PathMatcher.JURAVLEV_METRIC.getName());

		metric += (placeMetric.getX() + placeMetric.getY()) * PLACE_METRIC_MULTIPLIER;
		metric += (sizeMetric.getX() + sizeMetric.getY()) * SIZE_METRIC_MULTIPLIER;
		metric += juravlevMetric * JURAVLEV_METRIC_MULTIPLIER;

		return metric;
	}



	protected void generateMetricsTable(List<Chord> referenceChords, List<Chord> toCompareChords) {
		rowsCount = referenceChords.size();
		columnsCount = toCompareChords.size();

		metricsTable = (Map<String, Object>[][]) Array.newInstance(HashMap.class, rowsCount, columnsCount);

		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < columnsCount; j++) {
				Map<String, Object> metricsItem = new HashMap<String, Object>();
				Point2D placeMetric = PathMatcher.PLACE_METRIC.getMetric(referenceChords.get(i), toCompareChords.get(j));
				metricsItem.put(PathMatcher.PLACE_METRIC.getName(), placeMetric);
				Point2D sizeMetric = PathMatcher.SIZE_METRIC.getMetric(referenceChords.get(i), toCompareChords.get(j));
				metricsItem.put(PathMatcher.SIZE_METRIC.getName(), sizeMetric);
				Integer juravlevMetric = PathMatcher.JURAVLEV_METRIC.getMetric(referenceChords.get(i), toCompareChords.get(j));
				metricsItem.put(PathMatcher.JURAVLEV_METRIC.getName(), juravlevMetric);
				metricsTable[i][j] = metricsItem;
			}
		}
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
