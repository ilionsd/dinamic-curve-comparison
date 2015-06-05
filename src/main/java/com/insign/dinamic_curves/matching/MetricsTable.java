package com.insign.dinamic_curves.matching;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilion on 06.05.2015.
 */
public class MetricsTable{

	double[][] metricsTable = null;
	private int rowsCount = 0;
	private int columnsCount = 0;

	MetricsTable(double[][] metricsTable, int rowsCount, int columnsCount) {
		this.metricsTable = metricsTable;
		this.rowsCount = rowsCount;
		this.columnsCount = columnsCount;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public int getColumnsCount() {
		return columnsCount;
	}

	public double get(MetricsTableIndices indices) {
		return metricsTable[indices.i()][indices.j()];
	}

	public double get(int i, int j) {
		return metricsTable[i][j];
	}

	private boolean isIn(MetricsTableIndices indices) {
		return indices.i() >= 0
				&& indices.i() < getRowsCount()
				&& indices.j() >= 0
				&& indices.j() < getColumnsCount();
	}

	public List<MetricsTableIndices> optimize(Displacement2D[] displacements) {
		List<MetricsTableIndices> tablePath = new ArrayList<MetricsTableIndices>();

		MetricsTableIndices indices = new MetricsTableIndices(-1, -1);
		while (indices.i() < getRowsCount() - 1 && indices.j() < getColumnsCount() - 1) {
			double metric = Double.MAX_VALUE;
			MetricsTableIndices metricIndices = null;
			for (Displacement2D displacement : displacements) {
				MetricsTableIndices nextIndices = indices.displace(displacement);
				if (isIn(nextIndices)) {
					double nextMetric = get(nextIndices);
					if (Double.compare(nextMetric, metric) < 0) {
						metric = nextMetric;
						metricIndices = nextIndices;
					}
				}
			}
			indices = metricIndices;
			tablePath.add(indices);
		}
		return tablePath;
	}
}
