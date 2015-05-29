package com.insign.dinamic_curves.matching.matcher;

/**
 * Created by ilion on 07.05.2015.
 */
public class FormMatching implements Matching, Cloneable {
	private boolean isMatch = false;
	private double threshold = 0;
	private double maxDifference = 0;
	private int maxDifferenceIndex = 0;
	private double averageDifference = 0;

	@Override
	public boolean isMatch() {
		return isMatch;
	}

	@Override
	public Object clone() {
		FormMatching clone = null;
		try {
			clone = (FormMatching)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
		return clone;
	}

	private FormMatching() {}

	public static Builder newBuilder() {
		return new FormMatching().new Builder();
	}

	public class Builder {
		public FormMatching build() {
			return (FormMatching)FormMatching.this.clone();
		}

		public Builder setIsMatch(boolean isMatch) {
			FormMatching.this.isMatch = isMatch;
			return this;
		}

		public Builder setThreshold(double threshold) {
			FormMatching.this.threshold = threshold;
			return this;
		}

		public Builder setMaxDifference(double maxDifference) {
			FormMatching.this.maxDifference = maxDifference;
			return this;
		}

		public Builder setMaxDifferenceIndex(int maxDifferenceIndex) {
			FormMatching.this.maxDifferenceIndex = maxDifferenceIndex;
			return this;
		}

		public Builder setAverageDifference(double averageDifference) {
			FormMatching.this.averageDifference = averageDifference;
			return this;
		}
	}
}
