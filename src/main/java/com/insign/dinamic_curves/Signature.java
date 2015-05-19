package com.insign.dinamic_curves;


import com.insign.dinamic_curves.points.Extreme;
import com.insign.dinamic_curves.points.SignaturePoint;

/**
 * Created by ilion on 20.02.2015.
 */
public interface Signature {
	SortedCollection<SignaturePoint> getSkeleton();

	SortedCollection<Extreme> getVerticalExtremes();
	SortedCollection<Extreme> getHorizontalExtremes();
	SortedCollection<Extreme> getCurvatureExtremes();

}










