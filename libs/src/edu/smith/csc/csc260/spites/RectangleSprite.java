package edu.smith.csc.csc260.spites;

import edu.smith.csc.csc260.core.SmithPApplet;

public class RectangleSprite extends Sprite {
	// use getters and setters to access height and width
	private float width;
	private float height;
	private float halfWidth;
	private float halfHeight;
	
	public RectangleSprite(float width, float height) {
		this.width = width;
		this.height = height;
		halfWidth = width *.5f;
		halfHeight = height * .5f;
	}
	
	@Override
	public void render(SmithPApplet pApplet) {
		pApplet.rect(-halfWidth, -halfHeight, width, height);
	}
	
	public void setWidth(float w) {
		width = w;
		halfWidth = width *.5f;
	}
	public void setHeight(float h) {
		height = h;
		halfHeight = height *.5f;
	}
	
	public float getWidth() { return width; }
	public float getHeight() { return height; }
}
