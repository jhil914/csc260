package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.Sprite;

public class CircleSprite extends Sprite {
	// use getters and setters to access height and width
	private float radius;
	
	public CircleSprite(float r) {
		this.radius = r;
	}
	
	@Override
	public void render(SmithPApplet pApplet) {
		pApplet.ellipse(radius, radius, 2*radius, 2*radius);
	}
	
	public void setRadius(float r) {
		radius = r;
		
	}
	
	
	public float getRadius() { return radius; }
	
}
