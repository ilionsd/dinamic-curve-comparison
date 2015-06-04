package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.points.Extreme;

import java.util.LinkedHashMap;

/**
 * Created by ilion on 27.05.2015.
 */
public class IntermediateExtremesConformitySolution implements Solution, Cloneable {

	private LinkedHashMap<Extreme, Extreme> extremesConformity = null;
	private double optimalMetric = 0;

	private IntermediateExtremesConformitySolution() {}

	@Override
	public Object getSolution() {
		return getExtremesConformity();
	}

	public LinkedHashMap<Extreme, Extreme> getExtremesConformity() {
		return extremesConformity;
	}

	public double getOptimalMetric() {
		return optimalMetric;
	}

	@Override
	public Object clone() {
		IntermediateExtremesConformitySolution clone = null;
		try {
			clone = (IntermediateExtremesConformitySolution)super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
		return clone;
	}

	public static Builder newBuilder() {
		return new IntermediateExtremesConformitySolution().new Builder();
	}

	public class Builder {
		public IntermediateExtremesConformitySolution build() {
			return (IntermediateExtremesConformitySolution)IntermediateExtremesConformitySolution.this.clone();
		}

		public Builder setExtremeConformity(LinkedHashMap<Extreme, Extreme> extremeConformity) {
			IntermediateExtremesConformitySolution.this.extremesConformity = extremeConformity;
			return this;
		}

		public Builder setOptimalMetric(double optimalMetric) {
			IntermediateExtremesConformitySolution.this.optimalMetric = optimalMetric;
			return this;
		}
	}
}
