package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.matching.Referenceable;

/**
 * Created by ilion on 10.05.2015.
 */
public abstract class AbstractMatcher<T> implements Referenceable<T>{

	protected T reference = null;

	protected boolean nullMatch(T toCompare) {

		boolean isMatch = true;

		if (reference == null)
			isMatch = toCompare == null;
		else if (toCompare == null)
			isMatch = false;

		return isMatch;
	}

	@Override
	public AbstractMatcher<T> setReference(T reference) {
		this.reference = reference;
		return this;
	}

	@Override
	public T getReference() {
		return reference;
	}

	public enum NullMatching {BOTH_NULL, ONE_OF_NULL, BOTH_NOT_NULL};
}
