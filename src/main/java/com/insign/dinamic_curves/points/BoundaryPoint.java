package com.insign.dinamic_curves.points;

/**
 * Created by ilion on 26.04.2015.
 */
public interface BoundaryPoint extends SignaturePoint{
	Extreme toVerticalExtreme();
	Extreme toHorizontalExtreme();
	Extreme toExtreme(Extreme.Type type);
}
