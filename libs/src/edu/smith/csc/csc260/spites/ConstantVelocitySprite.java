package edu.smith.csc.csc260.spites;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Point;

public class ConstantVelocitySprite extends Sprite {
	protected Point velocity;
	protected float angularVelocity;
	/**
	 * A sprite the moves at a constant velocity
	 * @param velocity - in pixels per ms
	 */
	public ConstantVelocitySprite(Point velocity, float angularVelocity) {
		this.velocity = velocity;		
		this.angularVelocity = angularVelocity;
	}
	
	@Override
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		location.add(
				velocity.getX() * elapsedTime,
				velocity.getY() * elapsedTime,
				velocity.getZ() * elapsedTime);
		angle+=angularVelocity * elapsedTime;
	}

	@Override
	public void render(SmithPApplet pApplet) {
		pApplet.rect(-10, -10, 20, 20);
	}
}
