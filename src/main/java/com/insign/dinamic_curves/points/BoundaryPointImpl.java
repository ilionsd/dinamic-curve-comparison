package com.insign.dinamic_curves.points;

import com.insign.common.function.Point2D;

import java.util.Objects;

/**
 * Created by ilion on 26.04.2015.
 */
public class BoundaryPointImpl extends AbstractSignaturePoint implements BoundaryPoint {

	public BoundaryPointImpl(double argument, Point2D value, Point2D tangentVector) {
		super(argument, value, tangentVector);
	}

	@Override
	public Extreme toVerticalExtreme() {
		return toExtreme(Extreme.Type.VERTICAL);
	}

	@Override
	public Extreme toHorizontalExtreme() {
		return toExtreme(Extreme.Type.HORIZONTAL);
	}

	@Override
	public Extreme toExtreme(Extreme.Type type) {
		Objects.requireNonNull(type);
		return new ExtremeImpl(getArgument(), getValue(), getTangent(), type);
	}
}
