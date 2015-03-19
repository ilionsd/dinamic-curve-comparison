package com.insign.dccomparison;

import com.insign.common.function.Point2D;

import java.util.Map;
import java.util.SortedMap;

/**
 * Created by ilion on 20.02.2015.
 */
public abstract class AbstractSignature implements Signature {
	@Override
	public abstract SortedMap<Double, Point2D> getSkeleton();
}
