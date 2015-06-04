package com.insign.dinamic_curves.matching.matcher;

import com.insign.common.function.Point2D;
import com.insign.dinamic_curves.Chord;
import com.insign.dinamic_curves.Signature;
import com.insign.common.collection.SortedCollection;
import com.insign.dinamic_curves.points.BoundaryPoint;
import com.insign.dinamic_curves.points.Extreme;
import com.insign.dinamic_curves.points.SignaturePoint;

import java.util.*;

/**
 * Created by ilion on 18.04.2015.
 */
public class PathMatcher extends AbstractMatcher<Signature> implements Matcher<Signature> {

	protected static double METRIC_THRESHOLD = 100;

	@Override
	public PathMatching match(Signature signature) {
		boolean isNullMatch = super.nullMatch(signature);
		if (!isNullMatch)
			return null;
		else {
			SortedCollection<Extreme> refVerticalExtremes = getReference().getVerticalExtremes();
			SortedCollection<Extreme> sigVerticalExtremes = signature.getVerticalExtremes();

			List<Chord> refVerticalExtremesChords = getChordList(refVerticalExtremes);
			List<Chord> sigVerticalExtremesChords = getChordList(sigVerticalExtremes);

			ChordConformityResolver ccr = new ChordConformityResolver().setReference(refVerticalExtremesChords);
			ChordConformitySolution ccs = ccr.resolve(sigVerticalExtremesChords);

			PathMatching.Builder builder = PathMatching.newBuilder()
					.setOptimalMetric(ccs.getOptimalMetric());

			if (Double.compare(ccs.getOptimalMetric(), METRIC_THRESHOLD) > 0) {
				//--  --
				builder.setIsMatch(false);
			}
			else {
				builder.setIsMatch(true);
				SortedMap<Extreme, Extreme> extremesConformity = new TreeMap<Extreme, Extreme>(new Comparator<Extreme>() {
					@Override
					public int compare(Extreme o1, Extreme o2) {
						return o1.compareTo(o2);
					}
				});
				//-- Vertical extremes conformity --
				extremesConformity.putAll(toExtremesConformity(ccs.getChordSolution(), refVerticalExtremes, sigVerticalExtremes));
				//-- + horizontal extremes conformity --
				extremesConformity.putAll(intermediateExtremesConformity(extremesConformity, getReference().getHorizontalExtremes(), signature.getHorizontalExtremes()));
				//-- + curvature extremes conformity--
				extremesConformity.putAll(intermediateExtremesConformity(extremesConformity, getReference().getCurvatureExtremes(), signature.getCurvatureExtremes()));

				builder.setExtremesConformity(extremesConformity);
			}

			return builder.build();
		}
	}

	@Override
	public PathMatcher setReference(Signature reference) {
		super.setReference(reference);
		return this;
	}

	private Map<Extreme, Extreme> intermediateExtremesConformity(SortedMap<Extreme, Extreme> extremesConformity, SortedCollection<Extreme> refExtremes, SortedCollection<Extreme> sigExtremes) {
		List<Map.Entry<Extreme, Extreme>> extremesConformityList = new ArrayList<Map.Entry<Extreme, Extreme>>(extremesConformity.entrySet());

		Map<Extreme, Extreme> intermediateConformity = new HashMap<Extreme, Extreme>();

		for (int k = 1; k < extremesConformityList.size(); k++) {
			Extreme refLeftExtreme = extremesConformityList.get(k - 1).getKey();
			Extreme refRightExtreme = extremesConformityList.get(k).getKey();
			Extreme sigLeftExtreme= extremesConformityList.get(k - 1).getValue();
			Extreme sigRightExtreme = extremesConformityList.get(k).getValue();
			SortedCollection<Extreme> refIntermediateHorizontalExtremes = refExtremes
					.getMoreThan(refLeftExtreme)
					.getLessThan(refRightExtreme);
			SortedCollection<Extreme> sigIntermediateHorizontalExtremes = sigExtremes
					.getMoreThan(sigLeftExtreme)
					.getLessThan(sigRightExtreme);

			IntermediateExtremesConformityResolver iecr = new IntermediateExtremesConformityResolver().setReference(refIntermediateHorizontalExtremes);
			IntermediateExtremesConformitySolution iecs = iecr.resolve(sigIntermediateHorizontalExtremes);

			intermediateConformity.putAll(iecs.getExtremesConformity());
		}
		return intermediateConformity;
	}

	private SortedMap<Extreme, Extreme> toExtremesConformity(Map<Chord, Chord> chordConformity, SortedCollection<Extreme> refVerticalExtremes, SortedCollection<Extreme> sigVerticalExtremes) {
		SortedMap<Extreme, Extreme> extremeConformity = new TreeMap<Extreme, Extreme>(new Comparator<Extreme>() {
			@Override
			public int compare(Extreme o1, Extreme o2) {
				return o1.compareTo(o2);
			}
		});

		for (Map.Entry<Chord, Chord> entry : chordConformity.entrySet()) {
			Chord regChord = entry.getKey();
			Chord sigChord = entry.getValue();

			Extreme refChordStart = getIfContains(regChord.getStart(), refVerticalExtremes);
			Extreme sigChordStart = getIfContains(sigChord.getStart(), sigVerticalExtremes);
			if (refChordStart != null && sigChordStart != null && !extremeConformity.containsKey(refChordStart))
				extremeConformity.put(refChordStart, sigChordStart);

			Extreme refChordEnd = getIfContains(regChord.getEnd(), refVerticalExtremes);
			Extreme sigChordEnd = getIfContains(sigChord.getEnd(), sigVerticalExtremes);
			if (refChordEnd != null && sigChordEnd != null && !extremeConformity.containsKey(refChordEnd))
				extremeConformity.put(refChordEnd, sigChordEnd);
		}

		return extremeConformity;
	}

	private Extreme getIfContains(Point2D item, SortedCollection<Extreme> collection) {
		for (int k = 0; k < collection.size(); k++) {
			if (collection.get(k).getValue().equals(item))
				return collection.get(k);
		}
		return null;
	}

	private int getPointIndex(int verticalExtremeIndex, SortedCollection<? extends SignaturePoint> points) {
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


	private static List<Chord> getChordList(SortedCollection<Extreme> extremes) {
		List<Chord> chordList = new ArrayList<Chord>();
		for (int start = 0; start < extremes.size(); start++) {
			//-- Primary chord --
			int end = start + 1;
			if (end < extremes.size()) {
				chordList.add(new Chord(extremes.get(start).getValue(), extremes.get(end).getValue()));

				//-- 2nd kind variability change --
				end = start + 2;
				if (end < extremes.size())
					chordList.add(new Chord(extremes.get(start).getValue(), extremes.get(end).getValue()));
			}
		}
		return chordList;
	}




}
