package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.interpolation.AbstractInterpolator;
import edu.smith.csc.csc260.interpolation.easing.EasingFunction;
import edu.smith.csc.csc260.interpolation.easing.LinearEasingFunction;
import edu.smith.csc.csc260.interpolators.pointInterpolators.PointInterpolator;
import edu.smith.csc.csc260.util.Point;

public class Rectinterpolate extends AbstractInterpolator implements
		PointInterpolator {

Point center;
float radius;

Point returnvalue = new Point();

public Rectinterpolate(Point c, float r, EasingFunction easing) {
	super(easing);
	this.center = c;
	radius = r;
}
	@Override
	public Point getPoint() {
	float a= (float) ((float)easing.getT()*Math.PI*2.0f);
	float x = (float) Math.cos(a) *radius*2 + center.getX();
	float y = (float) Math.sin(a) *radius*(-0.9f) + center.getY();
	returnvalue.set(x,y,0);
		return returnvalue;
		
	}

}
