package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.matching.Matching;

/**
 * Created by ilion on 18.04.2015.
 */
public interface Matcher<T> {
	Matching match(T obj);

	T getReference();
	Matcher<T> setReference(T toCompare);
}
