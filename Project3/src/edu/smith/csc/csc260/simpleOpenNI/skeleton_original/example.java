package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import edu.smith.csc.csc260.core.SmithPApplet;

public class example extends SmithPApplet{
	public void setup() {
		  size(640, 360, P3D);
		  fill(204);
		}

		public void draw() {
		  lights();
		  background(0);
		  
		  // Change height of the camera with mouseY
		  camera(mouseX, mouseY, 220.0f, // eyeX, eyeY, eyeZ
		         0.0f, 0.0f, 0.0f, // centerX, centerY, centerZ
		         0.0f, 1.0f, 0.0f); // upX, upY, upZ
		  
		  noStroke();
		  box(90);
		  stroke(255);
		  line(-100, 0, 0, 100, 0, 0);
		  line(0, -100, 0, 0, 100, 0);
		  line(0, 0, -100, 0, 0, 100);
		}

}
