package com.insign.dinamic_curves.matching.matcher;

/**
 * Created by ilion on 31.05.2015.
 */
public class SignatureMatching implements Matching, Cloneable {

	private boolean isPathMatch = false;
	private double pathMatchingOptimalMetric = 0;

	private boolean isFormAverageDifferenceMatch = false;
	private double formMatchingAverageDifference = 0;

	private boolean isFormMaxDifferenceMatch = false;
	private double formMatchingMaxDifference = 0;

	private SignatureMatching() {}

	@Override
	public boolean isMatch() {
		return isPathMatch && isFormAverageDifferenceMatch && isFormMaxDifferenceMatch;
	}

	@Override
	public Object clone() {
		SignatureMatching clone = null;
		try {
			clone = (SignatureMatching)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
		return clone;
	}

	public static Builder newBuilder() {
		return new SignatureMatching().new Builder();
	}

	public class Builder {
		public SignatureMatching build() {
			return (SignatureMatching)SignatureMatching.this.clone();
		}

		public Builder setIsPathMatch(boolean isPathMatch) {
			SignatureMatching.this.isPathMatch = isPathMatch;
			return this;
		}

		public Builder setPathMatchingOptimalMetric(double pathMatchingOptimalMetric) {
			SignatureMatching.this.pathMatchingOptimalMetric = pathMatchingOptimalMetric;
			return this;
		}

		public Builder setIsFormAverageDifferenceMatch(boolean isFormAverageDifferenceMatch) {
			SignatureMatching.this.isFormAverageDifferenceMatch = isFormAverageDifferenceMatch;
			return this;
		}

		public Builder setFormMatchingAverageDifference(double formMatchingAverageDifference) {
			SignatureMatching.this.formMatchingAverageDifference = formMatchingAverageDifference;
			return this;
		}

		public Builder setIsFormMaxDifferenceMatch(boolean isFormMaxDifferenceMatch) {
			SignatureMatching.this.isFormMaxDifferenceMatch = isFormMaxDifferenceMatch;
			return this;
		}

		public Builder setFormMatchingMaxDifference(double formMatchingMaxDifference) {
			SignatureMatching.this.formMatchingMaxDifference = formMatchingMaxDifference;
			return this;
		}
	}
}
