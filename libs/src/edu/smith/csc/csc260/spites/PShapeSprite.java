package edu.smith.csc.csc260.spites;

import processing.core.PShape;
import edu.smith.csc.csc260.core.SmithPApplet;

public class PShapeSprite extends Sprite {
	PShape shape;
	
	
	public void setup(SmithPApplet spa) {
		shape = spa.createShape();
		shape.beginShape();
		shape.fill(0,255,0);
		shape.vertex(-10,-10);
		shape.vertex(10,-10);
		shape.vertex(10,10);
		shape.vertex(-10,10);
		shape.endShape(SmithPApplet.CLOSE);		
	}
	
	@Override
	public void render(SmithPApplet spa) {
		spa.shape(shape);

	}

}
