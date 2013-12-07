package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import edu.smith.csc.csc260.interpolation.AbstractInterpolator;
import edu.smith.csc.csc260.interpolation.easing.EasingFunction;
import edu.smith.csc.csc260.interpolators.pointInterpolators.PointInterpolator;
import edu.smith.csc.csc260.util.Point;

public class noiseinterpolate extends AbstractInterpolator implements PointInterpolator{
int x;
int y;
int z;
Point init;
float noisescale= 0.02f;
Point returnvalue = new Point();
	public noiseinterpolate(Point p, EasingFunction easing) {
		super(easing);
		this.init = p;
		
	}

	@Override
	public Point getPoint() {
		float x = init.getX()*noisescale;
		float y = init.getY()*noisescale;
		
		
		return null;
	}

}
