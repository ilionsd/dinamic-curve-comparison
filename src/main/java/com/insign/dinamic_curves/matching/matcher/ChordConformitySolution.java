package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.Chord;
import com.insign.dinamic_curves.matching.MetricsTable;
import com.insign.dinamic_curves.matching.MetricsTableIndices;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ilion on 22.05.2015.
 */
public class ChordConformitySolution implements Solution, Cloneable {
	Integer[] indexSolution = null;
	LinkedHashMap<Chord, Chord> chordSolution = null;
	private MetricsTable metricsTable = null;
	private List<MetricsTableIndices> tablePath = null;
	private double optimalMetric = 0;

	@Override
	public LinkedHashMap<Chord, Chord> getSolution() {
		return getChordSolution();
	}

	public LinkedHashMap<Chord, Chord> getChordSolution() {
		return chordSolution;
	}

	public Integer[] getIndexSolution() {
		return null;
	}

	public MetricsTable getMetricsTable() {
		return metricsTable;
	}

	public List<MetricsTableIndices> getTablePath() {
		return tablePath;
	}

	public double getOptimalMetric() {
		return optimalMetric;
	}

	public Object clone() {
		ChordConformitySolution clone = null;
		try {
			clone = (ChordConformitySolution)super.clone();
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	public static Builder newBuilder() {
		return new ChordConformitySolution().new Builder();
	}

	public class Builder {
		ChordConformitySolution build() {
			return (ChordConformitySolution)ChordConformitySolution.this.clone();
		}

		public Builder setChordSolution(LinkedHashMap<Chord, Chord> chordSolution) {
			ChordConformitySolution.this.chordSolution = chordSolution;
			return this;
		}

		public Builder setIndexSolution(Integer[] indexSolution) {
			ChordConformitySolution.this.indexSolution = indexSolution;
			return this;
		}

		public Builder setMetricsTable(MetricsTable metricsTable) {
			ChordConformitySolution.this.metricsTable = metricsTable;
			return this;
		}

		public Builder setTablePath(List<MetricsTableIndices> tablePath) {
			ChordConformitySolution.this.tablePath = tablePath;
			return this;
		}

		public Builder setOptimalMetric(double optimalMetric) {
			ChordConformitySolution.this.optimalMetric = optimalMetric;
			return this;
		}
	}
}
