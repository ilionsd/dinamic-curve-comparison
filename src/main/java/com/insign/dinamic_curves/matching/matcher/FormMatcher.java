package com.insign.dinamic_curves.matching.matcher;

import com.insign.common.function.Point2D;
import com.insign.common.collection.SortedCollection;
import com.insign.dinamic_curves.points.Extreme;
import com.insign.dinamic_curves.points.SignaturePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by ilion on 06.05.2015.
 */
public class FormMatcher implements Matcher<SortedMap<Extreme, Extreme>> {
	public static final double DEFAULT_ANGLE_AVERAGE_DIFFERENCE_LIMIT = Math.PI / 9.0;
	public static final double DEFAULT_ANGLE_MAX_DIFFERENCE_LIMIT = Math.PI / 6.0;

	private SortedCollection<SignaturePoint> toCompare = null;
	protected double angleAverageDifferenceLimit = DEFAULT_ANGLE_AVERAGE_DIFFERENCE_LIMIT;
	protected double angleMaxDifferenceLimit = DEFAULT_ANGLE_MAX_DIFFERENCE_LIMIT;

	@Override
	public FormMatching match(SortedMap<Extreme, Extreme> extremesConformity) {
		List<Map.Entry<Extreme, Extreme>> extremesConformityList = new ArrayList<Map.Entry<Extreme, Extreme>>(extremesConformity.entrySet());

		double maxDifference = 0;
		double averageDifference = 0;
		double sumDifference = 0;
		int maxDifferenceIndex = -1;

		for (int k = 1; k < extremesConformityList.size(); k++) {
			Map.Entry<Extreme, Extreme> leftEntry = extremesConformityList.get(k - 1);
			Map.Entry<Extreme, Extreme> rightEntry = extremesConformityList.get(k);
			double refAngle = Math.acos(
					Point2D.asVector.angleCos(leftEntry.getKey().getTangent(), rightEntry.getKey().getTangent()));
			double sigAngle = Math.acos(
					Point2D.asVector.angleCos(leftEntry.getValue().getTangent(), rightEntry.getValue().getTangent()));
			double difference = Math.abs(refAngle - sigAngle);
			sumDifference += difference;
			if (Double.compare(maxDifference, difference) < 0) {
				maxDifference = difference;
				maxDifferenceIndex = k;
			}
		}

		averageDifference = sumDifference / (extremesConformityList.size() - 1);
		FormMatching.Builder builder = FormMatching.newBuilder()
				.setMaxDifference(maxDifference)
				.setMaxDifferenceIndex(maxDifferenceIndex)
				.setAverageDifference(averageDifference)
				.setMaxDifference(maxDifference);

		boolean isAverageMatch = Double.compare(averageDifference, angleAverageDifferenceLimit) < 0;
		boolean isMaxMatch = Double.compare(maxDifference, angleMaxDifferenceLimit) < 0;

		builder.setIsAverageMatch(isAverageMatch)
				.setIsMaxMatch(isMaxMatch);

		return builder.build();
	}

	public FormMatcher setAngleAverageDifferenceLimit(double angleAverageDifferenceLimit) {
		this.angleAverageDifferenceLimit = angleAverageDifferenceLimit;
		return this;
	}

	public FormMatcher setAngleMaxDifferenceLimit(double angleMaxDifferenceLimit) {
		this.angleMaxDifferenceLimit = angleMaxDifferenceLimit;
		return this;
	}
}
