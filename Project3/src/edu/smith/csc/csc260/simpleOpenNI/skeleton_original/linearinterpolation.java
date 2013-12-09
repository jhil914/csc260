package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import processing.core.PVector;
import edu.smith.csc.csc260.core.SmithPApplet;

public class linearinterpolation extends SmithPApplet{
	PVector startP;
	PVector endP;
	 
	 
	public void setup() {
	  size(600, 600);
	 
	  startP = new PVector(random(0, width), random(0, height));
	  endP = new PVector(random(0, width), random(0, height));
	}
	 
	 
	public void draw() {
	  background(40);
	 
	  float progress = map(mouseX, 0, width, 0, 1);        // mouse movement gives a value between 0 and 1
	 
	  noFill();
	  stroke(255);
	 
	  line(startP.x, startP.y, endP.x, endP.y);
	  text("Start", startP.x + 10, startP.y);
	  text("End", endP.x + 10, endP.y);
	 
	  float px = lerp(startP.x, endP.x, progress);     // we can calculate x and y separately
	  float py = lerp(startP.y, endP.y, progress);
	   
	  ellipse(px, py, 10, 10);
	}

}
