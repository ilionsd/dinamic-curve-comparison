package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.Signature;
import com.insign.dinamic_curves.matching.FormMatching;
import com.insign.dinamic_curves.matching.Matching;
import com.insign.dinamic_curves.matching.PathMatching;

/**
 * Created by ilion on 18.04.2015.
 */
public class SignatureMatcher extends AbstractMatcher implements Matcher<Signature> {

	private Signature reference = null;

	@Override
	public Matching match(Signature toCompare) {
		boolean nullMatch = super.nullMatch(getReference(), toCompare);

		if (nullMatch) {

			PathMatcher pathMatcher = new PathMatcher().setReference(getReference().getVerticalExtremes());
			PathMatching pathMatching = pathMatcher.match(toCompare.getVerticalExtremes());
			boolean isMatch = pathMatching.isMatch();

			if (isMatch) {

				FormMatcher formMatcher = new FormMatcher().setReference(getReference().getSkeleton());
				FormMatching formMatching = formMatcher.match(toCompare.getSkeleton());
			}
		}
		return null;
	}

	@Override
	public Signature getReference() {
		return reference;
	}

	@Override
	public Matcher<Signature> setReference(Signature toCompare) {
		this.reference = toCompare;
		return this;
	}
}
