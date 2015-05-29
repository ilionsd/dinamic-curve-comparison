package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.matching.MetricsTable;
import com.insign.dinamic_curves.matching.MetricsTableIndices;
import com.insign.dinamic_curves.points.Extreme;

import java.util.List;
import java.util.SortedMap;

/**
 * Created by ilion on 06.05.2015.
 */
public class PathMatching implements Matching, Cloneable {

	private double optimalMetric = 0;
	private boolean isMatch = false;
	private SortedMap<Extreme, Extreme> extremesConformity = null;


	private PathMatching() {}

	@Override
	public boolean isMatch() {
		return isMatch;
	}

	public double getOptimalMetric() { return optimalMetric; }

	public SortedMap<Extreme, Extreme> getExtremesConformity() {
		return extremesConformity;
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

		public Builder setOptimalMetric(double optimalMetric) {
			PathMatching.this.optimalMetric = optimalMetric;
			return this;
		}

		public Builder setExtremesConformity(SortedMap<Extreme, Extreme> extremesConformity) {
			PathMatching.this.extremesConformity = extremesConformity;
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
