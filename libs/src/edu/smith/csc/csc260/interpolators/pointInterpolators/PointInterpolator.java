package edu.smith.csc.csc260.interpolators.pointInterpolators;

import edu.smith.csc.csc260.interpolation.Interpolator;
import edu.smith.csc.csc260.util.Point;

public interface PointInterpolator extends  Interpolator {

	
	public Point getPoint();
	
}