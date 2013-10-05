package edu.smith.csc.csc260.interpolation;

import edu.smith.csc.csc260.interpolation.easing.EasingFunction;

public interface Interpolator {
	public void setEasingFunction(EasingFunction easing);
	
	public EasingFunction getEasingFunction() ;
	
	public void updateEasing(long curTime);
}
