package com.insign.dinamic_curves.matching.metric;

/**
 * Created by ilion on 22.04.2015.
 */
public interface Metric<T> {
	Double getMetric(T obj1, T obj2);
	String getName();
}
