package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.Signature;

/**
 * Created by ilion on 18.04.2015.
 */
public class SignatureMatcher extends AbstractMatcher<Signature> implements Matcher<Signature> {

	@Override
	public SignatureMatching match(Signature signature) {
		boolean nullMatch = super.nullMatch(signature);

		if (nullMatch) {
			SignatureMatching.Builder builder = SignatureMatching.newBuilder();
			PathMatcher pathMatcher = new PathMatcher().setReference(reference);
			PathMatching pathMatching = pathMatcher.match(signature);
			boolean isPathMatch = pathMatching.isMatch();
			builder.setIsPathMatch(isPathMatch)
					.setPathMatchingOptimalMetric(pathMatching.getOptimalMetric());
			if (isPathMatch) {
				FormMatcher formMatcher = new FormMatcher();
				FormMatching formMatching = formMatcher.match(pathMatching.getExtremesConformity());

				builder.setIsFormAverageDifferenceMatch(formMatching.isAverageMatch())
						.setIsFormMaxDifferenceMatch(formMatching.isMaxMatch())
						.setFormMatchingAverageDifference(formMatching.getAverageDifference())
						.setFormMatchingMaxDifference(formMatching.getMaxDifference());
			}

			return builder.build();
		}
		return null;
	}
}
