package com.insign.dccomparison;

import com.insign.common.function.Point2D;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by ilion on 20.02.2015.
 */
public class SignatureImpl extends AbstractSignature {
	private SortedMap<Double, Point2D> skeleton;

	public SignatureImpl(SortedMap<Double, Point2D> skeleton) {
		this.skeleton = new TreeMap<Double, Point2D>(skeleton);
	}

	@Override
	public SortedMap<Double, Point2D> getSkeleton() {
		return skeleton;
	}
}
