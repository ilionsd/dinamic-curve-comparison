package com.insign.dinamic_curves.points;

import com.insign.common.function.Point2D;

/**
 * Created by ilion on 26.04.2015.
 */
public interface SignaturePoint extends Comparable<SignaturePoint> {

	Point2D getValue();
	double getArgument();
	Point2D getTangent();
}
