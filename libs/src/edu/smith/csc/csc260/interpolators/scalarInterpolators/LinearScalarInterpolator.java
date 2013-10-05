package edu.smith.csc.csc260.interpolators.scalarInterpolators;

import edu.smith.csc.csc260.interpolation.AbstractInterpolator;
import edu.smith.csc.csc260.interpolation.easing.EasingFunction;

public class LinearScalarInterpolator extends AbstractInterpolator implements ScalarInterpolator {
	float startValue;
	float endValue;
	
	public LinearScalarInterpolator(float startValue, float endValue, EasingFunction easing) {
		super(easing);
		
		this.startValue = startValue;
		this.endValue = endValue;
	}

	@Override
	public float getScalar() {
		float t = easing.getT();
		return startValue * (1.0f - t) + endValue * t;
	}

}
