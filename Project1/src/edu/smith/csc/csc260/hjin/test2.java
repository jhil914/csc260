package edu.smith.csc.csc260.hjin;

import processing.core.PApplet;
import edu.smith.csc.csc260.core.SmithPApplet;

public class test2 extends PApplet{

	
	public void setup() {
		super.setup();
		  size(640, 360, P3D);
		  noStroke();
		  colorMode(RGB, 1);
		  fill((int) 0.4);
		}

		public void draw() {
			super.draw();
		  background(0);
		  translate(width / 2, height / 2);
		  // Set the specular color of lights that follow
		  lightSpecular(1, 1, 1);
		  directionalLight(0.8f, 0.8f, 0.8f, 0f, 0f, -1f);
		  float s = mouseX /(width);
		  specular(s, s, s);
		  sphere(120);
		}

	
	
}
