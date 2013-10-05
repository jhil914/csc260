package edu.smith.csc.csc260.interpolators.pointInterpolators;

import edu.smith.csc.csc260.interpolation.AbstractInterpolator;
import edu.smith.csc.csc260.interpolation.easing.EasingFunction;
import edu.smith.csc.csc260.util.Point;

public class LinearPointInterpolator extends AbstractInterpolator implements PointInterpolator {
	Point startPoint;
	Point endPoint;
	Point curPoint;


	public LinearPointInterpolator(Point startPoint, Point endPoint, EasingFunction easing) {
		super(easing);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.curPoint = new Point(startPoint);
	}
	public Point getPoint() {
		float t = easing.getT();
		float oneMinT = 1.0f- t;
		curPoint.set(
				startPoint.getX() * oneMinT + endPoint.getX() * t, 
				startPoint.getY() * oneMinT + endPoint.getY() * t, 
				startPoint.getZ() * oneMinT + endPoint.getZ() * t);
				
		return curPoint;
		
	}

}
