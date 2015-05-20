package com.insign.dinamic_curves.matching;

import com.insign.dinamic_curves.Chord;
import com.insign.dinamic_curves.SortedCollection;
import com.insign.dinamic_curves.matching.matcher.PathMatcher;
import com.insign.dinamic_curves.matching.metric.Metric;
import com.insign.dinamic_curves.matching.metric.MetricEvaluator;
import com.insign.dinamic_curves.points.Extreme;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by ilion on 20.05.2015.
 */
public class MetricsTableBuilder<T> {
	private List<Metric<T>> metrics = new ArrayList<Metric<T>>();
	private MetricEvaluator metricEvaluator = new MetricEvaluator();

	public MetricsTableBuilder addMetric(Metric<T> metric) {
		metrics.add(metric);
		return this;
	}

	public MetricsTableBuilder setMetricEvaluator(MetricEvaluator metricEvaluator) {
		this.metricEvaluator = metricEvaluator;
		return this;
	}

	public MetricsTable build(Collection<? extends T> reference, Collection<? extends T> toCompare) {

		int rowsCount = reference.size();
		int columnsCount = toCompare.size();
		double[][] metricsTable = (double[][]) Array.newInstance(double.class, rowsCount, columnsCount);

		Iterator<? extends T> refIterator = reference.iterator();
		int i = 0;
		while (refIterator.hasNext()) {
			T refItem = refIterator.next();
			Iterator<? extends T> toCmpIterator = toCompare.iterator();
			int j = 0;
			while (toCmpIterator.hasNext()) {
				T toCmpItem = toCmpIterator.next();
				metricsTable[i][j] = metricEvaluator.evaluate(getMetricsMap(refItem, toCmpItem));
				j++;
			}
			i++;
		}
		return new MetricsTable(metricsTable, rowsCount, columnsCount);
	}

	private Map<String, Double> getMetricsMap(T ref, T toCmp) {
		Map<String, Double> metricValues = new HashMap<String, Double>();
		for (Metric<T> metric : metrics) {
			metricValues.put(metric.getName(), metric.getMetric(ref, toCmp));
		}
		return metricValues;
	}

}
