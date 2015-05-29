package com.insign.dinamic_curves.points;

/**
 * Created by ilion on 26.04.2015.
 */
public interface Extreme extends SignaturePoint {

	Extreme.Type getExtremumType();

	enum Type { VERTICAL, HORIZONTAL, CURVATURE }
}
