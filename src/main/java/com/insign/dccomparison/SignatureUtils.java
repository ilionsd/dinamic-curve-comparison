package com.insign.dccomparison;

import com.insign.common.function.*;
import com.insign.common.function.differentialgeometry.NaturalParametricCurve;
import com.insign.common.function.differentialgeometry.NaturalCubicSplineParametricCurve;
import com.insign.common.function.differentialgeometry.CubicSplineParametricCurve;
import com.insign.common.function.integration.Integral;
import com.insign.common.function.integration.Intergrate;
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
		List<Point2D> xsExtremum = new ArrayList<Point2D>();
		List<Point2D> ysExtremum = new ArrayList<Point2D>();

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


}












