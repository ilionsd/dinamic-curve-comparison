package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.Chord;
import com.insign.dinamic_curves.matching.MetricsTable;
import com.insign.dinamic_curves.matching.MetricsTableIndices;

import java.util.List;

/**
 * Created by ilion on 21.05.2015.
 */
public class ChordMatching implements Matching {

	private MetricsTable metricsTable = null;
	private List<MetricsTableIndices> tablePath = null;
	private double optimalMetric = 0;
	private boolean isMatch = false;

	private ChordMatching() {}

	@Override
	public boolean isMatch() {
		return isMatch;
	}

	public MetricsTable getMetricsTable() {
		return metricsTable;
	}

	public List<MetricsTableIndices> getTablePath() { return tablePath; }

	public double getOptimalMetric() { return optimalMetric; }

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
		return new ChordMatching().new Builder();
	}

	public class Builder {

		private Builder() {}

		public Builder setMetricsTable(MetricsTable metricsTable) {
			ChordMatching.this.metricsTable = metricsTable;
			return this;
		}

		public Builder setTablePath(List<MetricsTableIndices> tablePath) {
			ChordMatching.this.tablePath = tablePath;
			return this;
		}

		public Builder setOptimalMetric(double optimalMetric) {
			ChordMatching.this.optimalMetric = optimalMetric;
			return this;
		}

		public Builder setIsMatch(boolean isMatch) {
			ChordMatching.this.isMatch = isMatch;
			return this;
		}

		public ChordMatching build() {
			return (ChordMatching)ChordMatching.this.clone();
		}
	}
}
