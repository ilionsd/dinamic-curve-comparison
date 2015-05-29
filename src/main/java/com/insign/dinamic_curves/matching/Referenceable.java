package com.insign.dinamic_curves.matching;

/**
 * Created by ilion on 21.05.2015.
 */
public interface Referenceable<T> {
	Referenceable<T> setReference(T reference);
	T getReference();
}
