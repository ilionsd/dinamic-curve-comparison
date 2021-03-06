package com.insign.dinamic_curves;

import com.insign.common.collection.SortedCollection;
import com.insign.common.function.*;
import com.insign.common.function.differentialgeometry.NaturalParametricCurve;
import com.insign.common.function.differentialgeometry.NaturalCubicSplineParametricCurve;
import com.insign.common.function.differentialgeometry.CubicSplineParametricCurve;
import com.insign.common.function.integration.Integral;
import com.insign.common.function.integration.Intergrate;
import com.insign.common.function.interpolation.CubicSpline;
import com.insign.common.function.interpolation.SplineSegment;
import com.insign.dinamic_curves.points.BoundaryPointImpl;
import com.insign.dinamic_curves.points.Extreme;
import com.insign.dinamic_curves.points.ExtremeImpl;
import com.insign.dinamic_curves.points.SignaturePoint;
import com.insign.utils.DoubleUtils;

import java.util.*;

/**
 * Created by ilion on 05.03.2015.
 */
public class SignatureUtils {
	public SignatureUtils() {super();}

	private final static double INTEGRATION_STEP = 0.01;
	private final static double LOCAL_EXTREME_AREA = 60;
	private final static double INTEGRATION_PRECISION = 0.001;
	private final static double LIMIT_CURVATURE_COEFFICIENT = 1.15;

	public static Signature createFromCurve(CubicSplineParametricCurve curve) {

		Map<Double, Point2D> criticalPoints = new LinkedHashMap<Double, Point2D>();

		return null;
	}

	public static Signature createFromCurve(final NaturalParametricCurve curve) {
		return null;
	}

	/**
	 * Receive @code NaturalSplineParametricCurve based on cubic splines and creates from it skeleton view of signature
	 * @param curve - NaturalSplineParametricCurve based on cubic splines
	 * @return Signature instance
	 */
	public static Signature createFromCurve(final NaturalCubicSplineParametricCurve curve) {
		List<Point2D> xsExtremum = getExtremumPoints(curve.getX());
		List<Point2D> ysExtremum = getExtremumPoints(curve.getY());
		List<Point2D> csExtremum = getCurvatureExtremumPoints(curve.getX(), curve.getY());

		SortedCollection<SignaturePoint> skeleton = new SortedCollection<SignaturePoint>();

		skeleton.add(new BoundaryPointImpl(curve.getParameterMin(), curve.valueIn(curve.getParameterMin()), curve.derivative(1, curve.getParameterMin())));
		skeleton.add(new BoundaryPointImpl(curve.getParameterMax(), curve.valueIn(curve.getParameterMax()), curve.derivative(1, curve.getParameterMax())));

		for (Point2D extremumPoint : xsExtremum) {
			Point2D value = curve.valueIn(extremumPoint.getX());
			Point2D derivative = curve.derivative(1, extremumPoint.getX());
			Extreme extreme = new ExtremeImpl(extremumPoint.getX(), value, derivative, Extreme.Type.HORIZONTAL);
			skeleton.add(extreme);
		}

		for (Point2D extremumPoint : ysExtremum) {
			Point2D value = curve.valueIn(extremumPoint.getX());
			Point2D derivative = curve.derivative(1, extremumPoint.getX());
			Extreme extreme = new ExtremeImpl(extremumPoint.getX(), value, derivative, Extreme.Type.VERTICAL);
			skeleton.add(extreme);
		}

		List<Extreme> csSelected = new ArrayList<Extreme>();
		for (int k = 1; k < skeleton.size(); k++) {
			double csStart = skeleton.get(k - 1).getArgument() + LOCAL_EXTREME_AREA;
			double csEnd = skeleton.get(k).getArgument() - LOCAL_EXTREME_AREA;
			if (Double.compare(csStart, csEnd) >= 0)
				continue;
			int maxIndex = -1;
			double maxValue = 0;
			for (int csIndex = 0; csIndex < csExtremum.size(); csIndex++) {
				Point2D csExtreme = csExtremum.get(csIndex);
				if (DoubleUtils.isBetween(csExtreme.getX(), csStart, csEnd)) {
					if (Double.compare(csExtreme.getY(), maxValue) > 0) {
						maxIndex = csIndex;
						maxValue = csExtreme.getY();
					}
				}
			}

			if (maxIndex != -1) {
				Point2D extremumPoint = csExtremum.get(maxIndex);
				Point2D value = curve.valueIn(extremumPoint.getX());
				Point2D derivative = curve.derivative(1, extremumPoint.getX());
				Extreme extreme = new ExtremeImpl(extremumPoint.getX(), value, derivative, Extreme.Type.CURVATURE);
				csSelected.add(extreme);
			}
		}

		skeleton.addAll(csSelected);

		return new SignatureImpl(skeleton);
	}

	private static double calculateAverageCurvature(final NaturalParametricCurve curve) {
		Arrow<Double, Double> curvature = new Arrow<Double, Double>() {
			@Override
			public Double valueIn(Double x) {
				//-- [d^2 / (ds)^2](r) --
				Point2D d2rds2 = curve.derivative(2, x);
				return Math.sqrt(d2rds2.getX() * d2rds2.getX() + d2rds2.getY() * d2rds2.getY());
			}
		};
		Integral curvatureIntegral = new Integral(curvature, curve.getParameterMin(), curve.getParameterMax());
		double curvatureIntegralValue = Intergrate.GaussLegendre.fivePointRule(curvatureIntegral, INTEGRATION_PRECISION);
		double averageCurvature = curvatureIntegralValue / (curve.getParameterMax() - curve.getParameterMin());
		return averageCurvature;
	}

	private LinkedHashMap<Double, Point2D> getCriticalPoints(NaturalParametricCurve curve) {
		return null;
	}

	public static List<Point2D> getExtremumPoints(final CubicSpline cubicSpline) {
		List<Point2D> extremumPoints = new ArrayList<Point2D>();
		for (int k = 0; k < cubicSpline.segmentCount(); k++) {
			SplineSegment segment = cubicSpline.get(k);
			/*
			f(x)=ax^3+bx^2+cx+d
			f'(x)=3ax^2+2bx+c
			 */
			double[] coefficients = new double[3];
			for (int index = 1; index <= segment.power(); index++)
				coefficients[index - 1] = segment.get(index) * index;
			double discriminant = coefficients[1] * coefficients[1] - 4.0 * coefficients[2] * coefficients[0];
			List<Double> roots = new ArrayList<Double>();
			if (Double.compare(Math.abs(discriminant), 1e-8) <= 0) {
				//-- 1 root --
				roots.add((-coefficients[1]) / (2 * coefficients[2]));
			}
			else if (discriminant > 0) {
				//-- 2 roots --
				double discriminantSqrt = Math.sqrt(discriminant);
				roots.add((-coefficients[1] + discriminantSqrt) / (2.0 * coefficients[2]));
				roots.add((-coefficients[1] - discriminantSqrt) / (2.0 * coefficients[2]));
			}
			for (double root : roots) {
				double shiftedRoot = root + segment.getLeftBound();
				if (segment.isIn(shiftedRoot)) {
					Point2D extremum = new Point2D(shiftedRoot, segment.valueIn(shiftedRoot));
					extremumPoints.add(extremum);
				}
			}
		}
		return extremumPoints;
	}

	public static List<Point2D> getCurvatureExtremumPoints(final CubicSpline xs, final CubicSpline ys) {
		List<Point2D> extremumPoints = new ArrayList<Point2D>();
		double[] xKnots = xs.getKnots();
		double[] yKnots = ys.getKnots();
		double[] overallKnots = com.insign.utils.ArrayUtils.union(xKnots, yKnots);
		for (int k = 1; k < overallKnots.length; k++) {
			double middle = (overallKnots[k - 1] + overallKnots[k]) / 2.0;
			int xsIndex = xs.getSegment(middle),
				ysIndex = ys.getSegment(middle);
			SplineSegment xsSegment = xs.get(xsIndex),
						  ysSegment = ys.get(ysIndex);
			double[] xsCoefficients = new double[2],
					 ysCoefficients = new double[2];
			for (int power = 2; power <= xsSegment.power(); power++)
				xsCoefficients[power - 2] = xsSegment.get(power);
			for (int power = 2; power <= ysSegment.power(); power++)
				ysCoefficients[power - 2] = ysSegment.get(power);
			double sForbidden = Double.NaN;
			if (Double.compare(Math.abs(xsCoefficients[0] - ysCoefficients[0]), 1e-8) <= 0 && Double.compare(Math.abs(xsCoefficients[1] - ysCoefficients[1]), 1e-8) <= 0 && Double.compare(Math.abs(xsSegment.getLeftBound() - ysSegment.getLeftBound()), 1e-8) <= 0) {
				sForbidden = xsCoefficients[0] / (3.0 * xsCoefficients[1]) + xsSegment.getLeftBound();
			}
			double a1a1 = xsCoefficients[1] * xsCoefficients[1];
			double a2a2 = ysCoefficients[1] * ysCoefficients[1];
			double a1b1_plus_a2b2 = xsCoefficients[0] * xsCoefficients[1] + ysCoefficients[0] * ysCoefficients[1];
			double a1a1_plus_a2a2 = a1a1 + a2a2;
			double a1a1s01_plus_a2a2s02 = a1a1 * xsSegment.getLeftBound() + a2a2 * ysSegment.getLeftBound();
			double s = (3.0 * a1a1s01_plus_a2a2s02 - a1b1_plus_a2b2) / (3.0 * a1a1_plus_a2a2);
			if (Double.compare(Math.abs(s - sForbidden), 1e-8) > 0 && xsSegment.isIn(s) && ysSegment.isIn(s)) {
				double b1b1_plus_b2b2 = xsCoefficients[0] * xsCoefficients[0] + ysCoefficients[0] * ysCoefficients[0];
				double cs = Math.sqrt(36.0 * a1a1_plus_a2a2 * s * s + 24.0 * a1b1_plus_a2b2 * s + 4.0 * b1b1_plus_b2b2);
				Point2D extremum = new Point2D(s, cs);
				extremumPoints.add(extremum);
			}
		}
		return extremumPoints;
	}


}












