package com.insign.dccomparison;

import com.insign.common.function.Point2D;

import java.util.Map;

/**
 * Created by ilion on 20.02.2015.
 */
public abstract class AbstractSignature implements Signature {
	public abstract Map<Double, Point2D> getSkeleton();
}
