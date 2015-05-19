package com.insign.dinamic_curves.matching;

/**
 * Created by ilion on 03.05.2015.
 */
public class MetricsTableIndices {
	private int i, j;

	public MetricsTableIndices(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int i() {
		return i;
	}

	public int j() {
		return j;
	}

	public MetricsTableIndices displace(Displacement2D displacement) {
		return new MetricsTableIndices(i() + displacement.getDx(), j() + displacement.getDy());
	}
}
