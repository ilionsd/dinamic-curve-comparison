package com.insign.dinamic_curves.points;

import com.insign.common.function.Point2D;

/**
 * Created by ilion on 26.04.2015.
 */
public class ExtremeImpl extends AbstractSignaturePoint implements Extreme {

	private Extreme.Type extremumType;

	public ExtremeImpl(double argument, Point2D value, Point2D tangentVector, Extreme.Type extremumType) {
		super(argument, value, tangentVector);
		this.extremumType = extremumType;
	}

	@Override
	public Type getExtremumType() {
		return extremumType;
	}
}
