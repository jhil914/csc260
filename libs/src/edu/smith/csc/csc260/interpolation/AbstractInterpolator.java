package edu.smith.csc.csc260.interpolation;

import edu.smith.csc.csc260.interpolation.easing.EasingFunction;

public abstract class AbstractInterpolator implements Interpolator {
	protected EasingFunction easing;
	
	public AbstractInterpolator(EasingFunction easing) {
		this.easing = easing;
	}
	
	
	public void setEasingFunction(EasingFunction easing) {
		this.easing = easing;
	}
	
	public EasingFunction getEasingFunction() {
		return easing;
	}
	
	public void updateEasing(long curTime) {
		easing.calcT(curTime);
	}
	
}
