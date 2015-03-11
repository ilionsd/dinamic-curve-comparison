package com.insign.dccomparison;

import com.insign.common.function.*;
import com.insign.common.function.differentialgeometry.NaturalParametricCurve;
import com.insign.common.function.differentialgeometry.NaturalCubicSplineParametricCurve;
import com.insign.common.function.differentialgeometry.CubicSplineParametricCurve;
import com.insign.common.function.integration.Integral;
import com.insign.common.function.integration.Intergrate;
import com.insign.common.function.interpolation.CubicSpline;
import com.insign.common.function.interpolation.SplineSegment;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ilion on 05.03.2015.
 */
public class SignatureUtils {
	public SignatureUtils() {super();}

	private final static double INTEGRATION_STEP = 0.01;
	private final static double INTEGRATION_PRECISION = 0.001;
	private final static double LIMIT_CURVATURE_COEFFICIENT = 0.15;

	public static Signature createFromCurve(CubicSplineParametricCurve curve) {

		Map<Double, Point2D> criticalPoints = new LinkedHashMap<Double, Point2D>();

		return null;
	}

	public static Signature createFromCurve(final NaturalParametricCurve curve) {
		throw new NotImplementedException();
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

		throw new NotImplementedException();
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
			if (Double.compare(Math.abs(discriminant), Double.MIN_VALUE) <= 0) {
				//-- 1 root --
				roots.add((-coefficients[1]) / (2 * coefficients[2]));
			}
			else if (discriminant > 0) {
				//-- 2 roots --
				double discriminantSqrt = Math.sqrt(discriminant);
				roots.add((-coefficients[1] + discriminantSqrt) / (2 * coefficients[2]));
				roots.add((-coefficients[1] - discriminantSqrt) / (2 * coefficients[2]));
			}
			for (double root : roots) {
				if (segment.isIn(root)) {
					Point2D extremum = new Point2D(root, segment.valueIn(root));
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
		double[] overallKnots = com.insign.common.ArrayUtils.union(xKnots, yKnots);
		for (int k = 1; k < overallKnots.length; k++) {
			double middle = (overallKnots[k - 1] + overallKnots[k]) / 2.0;
			int xsIndex = xs.getSegment(middle),
				ysIndex = ys.getSegment(middle);
			SplineSegment xsSegment = xs.get(xsIndex),
						  ysSegment = ys.get(ysIndex);
			double[] xsCoefficients = new double[2],
					 ysCoefficients = new double[2];
			for (int power = 2; power < xsSegment.power(); power++)
				xsCoefficients[power - 2] = xsSegment.get(power);
			for (int power = 2; power < ysSegment.power(); power++)
				ysCoefficients[power - 2] = ysSegment.get(power);
			double sForbidden = Double.NaN;
			if (Double.compare(Math.abs(xsCoefficients[0] - ysCoefficients[0]), 1e-6) <= 0 && Double.compare(Math.abs(xsCoefficients[1] - ysCoefficients[1]), 1e-8) <= 0) {
				sForbidden = xsCoefficients[0] / (3.0 * xsCoefficients[1]);
			}
			double a1b1_plus_a2b2 = xsCoefficients[0] * xsCoefficients[1] + ysCoefficients[0] * ysCoefficients[1];
			double a1a1_plus_a2a2 = xsCoefficients[1] * xsCoefficients[1] + ysCoefficients[1] * ysCoefficients[1];
			double s = - (24.0 * a1b1_plus_a2b2) / (72.0 * a1a1_plus_a2a2);
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












