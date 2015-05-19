package com.insign.dinamic_curves.matching;

import java.util.List;

/**
 * Created by ilion on 06.05.2015.
 */
public class PathMatching implements Matching, Cloneable {

	private MetricsTable metricsTable = null;
	private List<MetricsTableIndices> tablePath = null;
	private double optimalMetric = 0;
	private boolean isMatch = false;


	private PathMatching() {}

	@Override
	public boolean isMatch() {
		return isMatch;
	}

	public MetricsTable getMetricsTable() {
		return metricsTable;
	}

	protected Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	public static Builder newBuilder() {
		return new PathMatching().new Builder();
	}

	public class Builder {

		private Builder() {}

		public Builder setMetricsTable(MetricsTable metricsTable) {
			PathMatching.this.metricsTable = metricsTable;
			return this;
		}

		public Builder setTablePath(List<MetricsTableIndices> tablePath) {
			PathMatching.this.tablePath = tablePath;
			return this;
		}

		public Builder setOptimalMetric(double optimalMetric) {
			PathMatching.this.optimalMetric = optimalMetric;
			return this;
		}

		public Builder setIsMatch(boolean isMatch) {
			PathMatching.this.isMatch = isMatch;
			return this;
		}

		public PathMatching build() {
			return (PathMatching)PathMatching.this.clone();
		}
	}
}
