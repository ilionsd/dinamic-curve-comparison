package com.insign.dinamic_curves;

import com.insign.dinamic_curves.points.BoundaryPoint;
import com.insign.dinamic_curves.points.BoundaryPointImpl;
import com.insign.dinamic_curves.points.Extreme;
import com.insign.dinamic_curves.points.SignaturePoint;

/**
 * Created by ilion on 20.02.2015.
 */
public abstract class AbstractSignature implements Signature {

	@Override
	public abstract SortedCollection<SignaturePoint> getSkeleton();

	protected BoundaryPoint getMinBound() {
		SignaturePoint point = getSkeleton().getFirst();
		BoundaryPoint bp;
		if (point instanceof BoundaryPoint)
			bp = (BoundaryPoint)point;
		else
			bp = new BoundaryPointImpl(point.getArgument(), point.getValue(), point.getTangent());
		return bp;
	}

	protected BoundaryPoint getMaxBound() {
		SignaturePoint point = getSkeleton().getLast();
		BoundaryPoint bp;
		if (point instanceof BoundaryPoint)
			bp = (BoundaryPoint)point;
		else
			bp = new BoundaryPointImpl(point.getArgument(), point.getValue(), point.getTangent());
		return bp;
	}

	@Override
	public SortedCollection<Extreme> getVerticalExtremes() {
		SortedCollection<Extreme> extremes = new SortedCollection<Extreme>();
		extremes.add(getMinBound().toVerticalExtreme());
		extremes.add(getMaxBound().toVerticalExtreme());
		extremes.addAll(getExtremes(Extreme.Type.VERTICAL));
		return extremes;
	}

	@Override
	public SortedCollection<Extreme> getHorizontalExtremes() {
		SortedCollection<Extreme> extremes = new SortedCollection<Extreme>();
		extremes.add(getMinBound().toHorizontalExtreme());
		extremes.add(getMaxBound().toHorizontalExtreme());
		extremes.addAll(getExtremes(Extreme.Type.HORIZONTAL));
		return extremes;
	}

	@Override
	public SortedCollection<Extreme> getCurvatureExtremes() {
		SortedCollection<Extreme> extremes = new SortedCollection<Extreme>();
		extremes.addAll(getExtremes(Extreme.Type.CURVATURE));
		return extremes;
	}

	protected SortedCollection<Extreme> getExtremes(Extreme.Type type) {
		if (type == null)
			return null;
		SortedCollection<Extreme> extremes = new SortedCollection<Extreme>();

		for (SignaturePoint point : getSkeleton()) {
			if (point instanceof Extreme) {
				Extreme extreme = (Extreme)point;
				if (type.equals(extreme.getExtremumType())) {
					extremes.add(extreme);
				}
			}
		}
		return extremes;
	}
}
