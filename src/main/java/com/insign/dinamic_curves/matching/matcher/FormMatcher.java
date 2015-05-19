package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.SortedCollection;
import com.insign.dinamic_curves.matching.FormMatching;
import com.insign.dinamic_curves.points.SignaturePoint;

/**
 * Created by ilion on 06.05.2015.
 */
public class FormMatcher implements Matcher<SortedCollection<SignaturePoint>> {
	protected static double ANGLE_DIFFERENCE_LIMIT = 1;

	private SortedCollection<SignaturePoint> toCompare = null;

	@Override
	public FormMatching match(SortedCollection<SignaturePoint> signaturePoints) {



		return null;
	}

	@Override
	public SortedCollection<SignaturePoint> getReference() {
		return toCompare;
	}

	@Override
	public FormMatcher setReference(SortedCollection<SignaturePoint> toCompare) {
		this.toCompare = toCompare;
		return this;
	}
}
