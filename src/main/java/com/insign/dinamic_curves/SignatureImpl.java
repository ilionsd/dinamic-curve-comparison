package com.insign.dinamic_curves;

import com.insign.common.collection.SortedCollection;
import com.insign.dinamic_curves.points.SignaturePoint;

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
