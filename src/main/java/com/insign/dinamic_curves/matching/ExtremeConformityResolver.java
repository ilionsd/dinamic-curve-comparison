package com.insign.dinamic_curves.matching;

import com.insign.dinamic_curves.ListUtils;
import com.insign.dinamic_curves.SortedCollection;
import com.insign.dinamic_curves.points.BoundaryPoint;
import com.insign.dinamic_curves.points.Extreme;
import com.insign.dinamic_curves.points.SignaturePoint;

import java.util.*;

/**
 * Created by ilion on 10.05.2015.
 */
public class ExtremeConformityResolver implements ConformityResolwer<SignaturePoint> {

	private List<MetricsTableIndices> verticalExtremesConformity = null;


	@Override
	public Collection<Integer> resolve(SortedCollection<? extends SignaturePoint> obj1, SortedCollection<? extends SignaturePoint> obj2) {
		if (verticalExtremesConformity == null)
			return null;

		List<Integer> conformity = new ArrayList<Integer>(obj1.size());
		ListUtils.setAll(conformity, -1);

		int verticalExtremeNumber = 0;

		for (MetricsTableIndices index : verticalExtremesConformity) {
			int index1 = getPointIndex(index.i(), obj1);
			int index2 = getPointIndex(index.j(), obj2);
			conformity.set(index1, index2);
		}

		int leftExtreme = -1;
		int rightExtreme = -1;
		for (int k = 0; k < conformity.size(); k++) {
			if (conformity.get(k) != -1) {
				leftExtreme = rightExtreme;
				rightExtreme = k;
				if (leftExtreme != -1) {
					//-- Conform extremes --
					for (int i = leftExtreme + 1; i < rightExtreme; i++) {

					}
				}
			}
		}
		return null;
	}

	int getPointIndex(int verticalExtremeIndex, SortedCollection<? extends SignaturePoint> points) {
		int index = -1;
		for (int k = 0; k < points.size(); k++) {
			SignaturePoint point = points.get(k);
			if (point instanceof BoundaryPoint)
				index++;
			else if (point instanceof Extreme && Extreme.Type.VERTICAL.equals(((Extreme) point).getExtremumType()))
				index++;
			if (index == verticalExtremeIndex)
				return k;
		}
		return -1;
	}
}
