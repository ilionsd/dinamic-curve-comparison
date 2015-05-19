package com.insign.dinamic_curves.matching.metric;

/**
 * Created by ilion on 22.04.2015.
 */
public interface Metric<Obj, Value> {
	Value getMetric(Obj obj1, Obj obj2);
	String getName();
}
