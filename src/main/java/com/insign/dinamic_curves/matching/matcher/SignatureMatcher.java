package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.Signature;

/**
 * Created by ilion on 18.04.2015.
 */
public class SignatureMatcher extends AbstractMatcher<Signature> implements Matcher<Signature> {

	private Signature reference = null;

	@Override
	public Matching match(Signature signature) {
		boolean nullMatch = super.nullMatch(signature);

		if (nullMatch) {

			PathMatcher pathMatcher = new PathMatcher().setReference(reference);
			PathMatching pathMatching = pathMatcher.match(signature);
			boolean isMatch = pathMatching.isMatch();

			if (isMatch) {

				FormMatcher formMatcher = new FormMatcher();
				FormMatching formMatching = formMatcher.match(pathMatching.getExtremesConformity());
			}
		}
		return null;
	}
}
