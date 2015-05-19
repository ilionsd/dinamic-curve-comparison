package com.insign.dinamic_curves.points;

import com.insign.common.function.Point2D;

/**
 * Created by ilion on 26.04.2015.
 */
public abstract class AbstractSignaturePoint implements SignaturePoint {
	private double argument;
	private Point2D value;
	private Point2D tangentVector;

	public AbstractSignaturePoint(double argument, Point2D value, Point2D tangentVector) {
		this.argument = argument;
		this.value = (Point2D)value.clone();
		this.tangentVector = (Point2D)tangentVector.clone();
	}

	@Override
	public double getArgument() {
		return argument;
	}

	@Override
	public Point2D getValue() {
		return value;
	}

	@Override
	public Point2D getTangent() {
		return tangentVector;
	}

	@Override
	public int compareTo(SignaturePoint o) {
		return Double.compare(getArgument(), o.getArgument());
	}
}
