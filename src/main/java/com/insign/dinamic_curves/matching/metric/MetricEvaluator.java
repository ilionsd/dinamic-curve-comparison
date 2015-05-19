package com.insign.dinamic_curves.matching.metric;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ilion on 16.05.2015.
 */
public class MetricEvaluator {
	private Map<String, Double> weights = new HashMap<String, Double>();

	public MetricEvaluator setWeight(String metricName, double weight) {
		weights.put(metricName, weight);
		return this;
	}
}
