package com.insign.dinamic_curves.matching.matcher;

/**
 * Created by ilion on 07.05.2015.
 */
public class FormMatching implements Matching, Cloneable {
	private boolean isAverageMatch = false;
	private boolean isMaxMatch = false;
	private double averageThreshold = 0;
	private double maxThreshold = 0;
	private double maxDifference = 0;
	private int maxDifferenceIndex = 0;
	private double averageDifference = 0;

	@Override
	public boolean isMatch() {
		return isAverageMatch && isMaxMatch;
	}

	public boolean isAverageMatch() {
		return isAverageMatch;
	}

	public boolean isMaxMatch() {
		return isMaxMatch;
	}

	public double getAverageThreshold() {
		return averageThreshold;
	}

	public double getMaxThreshold() {
		return maxThreshold;
	}

	public double getMaxDifference() {
		return maxDifference;
	}

	public int getMaxDifferenceIndex() {
		return maxDifferenceIndex;
	}

	public double getAverageDifference() {
		return averageDifference;
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

		public Builder setIsAverageMatch(boolean isAverageMatch) {
			FormMatching.this.isAverageMatch = isAverageMatch;
			return this;
		}

		public Builder setIsMaxMatch(boolean isMaxMatch) {
			FormMatching.this.isMaxMatch = isMaxMatch;
			return this;
		}

		public Builder setAverageThreshold(double threshold) {
			FormMatching.this.averageThreshold = threshold;
			return this;
		}

		public Builder setMaxThreshold(double maxThreshold) {
			FormMatching.this.maxThreshold = maxThreshold;
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
