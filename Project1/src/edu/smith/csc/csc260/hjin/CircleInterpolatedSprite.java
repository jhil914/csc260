package edu.smith.csc.csc260.hjin;

import processing.core.PShape;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.InterpolatedSprite;

public class CircleInterpolatedSprite extends InterpolatedSprite{
	
	public float diameter;
	public PShape myShape; 
	
	public CircleInterpolatedSprite(float diameter){
		super();
		this.diameter=diameter;
	}
	
	@Override
	public void render(SmithPApplet pApplet){
		myShape=pApplet.createShape();	
		pApplet.noStroke();
		pApplet.ellipse(0,0,diameter,diameter); 
	}
	public float getDiameter(){
		return diameter;
	}
}

