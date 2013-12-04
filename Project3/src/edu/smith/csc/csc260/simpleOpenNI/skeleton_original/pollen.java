package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Color;

public class pollen extends SmithPApplet{
	int nPoints = 4096; // points to draw
	float complexity = 8; // wind complexity
	float maxMass = .8f; // max pollen mass
	float timeSpeed = .02f; // wind variation speed
	float phase = TWO_PI; // separate u-noise from v-noise
	 
	float windSpeed = 40; // wind vector magnitude for debug
	int step = 10; // spatial sampling rate for debug
	 
	float[] pollenMass;
	float[][] points;
	 
	boolean debugMode = false;
	 
	public void setup() {
	  size(640,480, P3D);
	   
	  points = new float[nPoints][2];
	  pollenMass = new float[nPoints];
	  for(int i = 0; i < nPoints; i++) {
	    points[i] = new float[]{width/2,height/2};
	    pollenMass[i] = random(0, maxMass);
	  }
	  noiseDetail(14);
	  background(255);
	}
	 
	public void draw() { 
	  float t = frameCount * timeSpeed;

	  line(0, 0, -2000, width, 0, -2000);
	  line(0, 0, -2000, 0, height, -2000);
	  line(0, height, -2000, width, height, -2000);
	  line(width, height, -2000, width, 0, -2000);
	 
	  // perspective lines
	 
	  line(0, 0, -2000, 0, 0, 0);
	  line(width, 0, -2000, width, 0, 0);
	  line(0, height, -2000, 0, height, 0);
	  line(width, height, -2000, width, height, 0);
	 
	 
	    stroke(0, 10);
	  
	  for(int i = 0; i < 3; i++) {
	    float x = points[i][0];
	    float y = points[i][1];
	     
	    float normx = norm(x, 0, width);
	    float normy = norm(y, 0, height);
	    float mx = norm(x, mouseX-50, mouseX);
	    float my = norm(y,mouseY-50,mouseY);
	    float u = noise(t + phase, normx * complexity + phase, normy * complexity + phase);
	    float v = noise(t - phase, normx * complexity - phase, normy * complexity + phase);
	    float mouse1 = noise(t + phase, mx * complexity - phase, my * complexity + phase);
	    float mouse2 = noise(t - phase, mx * complexity - phase, my * complexity + phase);
	    float speed = (noise(t, u, v)) / (pollenMass[i]);
if(Math.abs(mouseX-width/2)<=10){
	    	//maybe add a noise value that would lure the x and y towards the mouse??
	// also look for the tree growing from the ceiling code
	x += lerp(-speed, speed, mouse1);
	y += lerp(-speed, speed, mouse2);
	    	System.out.println("true");
	    }
else{
	System.out.println("false");
	x += lerp(-speed, speed, u);
    y += lerp(-speed, speed, v);
}

	    ellipse(x, y, 10, 10);
	    //System.out.println("speed: "+speed+"  u: "+u+"  v: "+v);
	    x += lerp(-speed, speed, u);
	    y += lerp(-speed, speed, v);
	     
	    /**if(x < 0 || x > width || y < 0 || y > height) {
	        x = random(0, width);
	        y = random(0, height);
	      }
*/
	   
	       // point(x, y);
	       
	    points[i][0] = x;
	    points[i][1] = y;
	    
	    
	  }
	}
	 
	public void mousePressed() {
	  setup();
	}
	 
	public void keyPressed() {
	  debugMode = !debugMode;
	  background(255);
	}

}
