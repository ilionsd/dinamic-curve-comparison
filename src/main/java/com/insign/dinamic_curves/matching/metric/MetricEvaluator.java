package com.insign.dinamic_curves.matching.metric;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ilion on 16.05.2015.
 */
public class MetricEvaluator {
	public static final double DEFAULT_WEIGHT = 1;

	private Map<String, Double> weights = new HashMap<String, Double>();

	public MetricEvaluator() {}
	public MetricEvaluator(Map<String, Double> weights) {
		this.weights.putAll(weights);
	}

	public MetricEvaluator setWeight(String metricName, double weight) {
		weights.put(metricName, weight);
		return this;
	}

	public Double evaluate(Map<String, Double> metrics) {
		Set<String> metricsKeySet = metrics.keySet();
		double result = 0;
		for (String metricName : metricsKeySet) {
			Double weight = weights.getOrDefault(metricName, DEFAULT_WEIGHT);
			result += weight * metrics.get(metricName);
		}
		return result;
	}
}
