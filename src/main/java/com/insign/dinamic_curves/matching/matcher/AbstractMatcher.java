package com.insign.dinamic_curves.matching.matcher;

/**
 * Created by ilion on 10.05.2015.
 */
public abstract class AbstractMatcher {

	protected boolean nullMatch(Object reference, Object toCompare) {

		boolean isMatch = true;

		if (reference == null)
			isMatch = toCompare == null;
		else if (toCompare == null)
			isMatch = false;

		return isMatch;
	}
}
