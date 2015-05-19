package com.insign.dinamic_curves;

import com.insign.common.function.Point2D;
import com.insign.dinamic_curves.points.SignaturePoint;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by ilion on 20.02.2015.
 */
public class SignatureImpl extends AbstractSignature {
	private SortedCollection<SignaturePoint> skeleton = new SortedCollection<SignaturePoint>();


	public SignatureImpl(SortedCollection<SignaturePoint> skeleton) {
		this.skeleton.addAll(skeleton);
	}

	@Override
	public SortedCollection<SignaturePoint> getSkeleton() {
		return skeleton;
	}
}
