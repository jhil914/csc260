package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import edu.smith.csc.csc260.core.SmithPApplet;

public class particleexplosion extends SmithPApplet{
	Particle [] pickles = new Particle [100];
	 
	 
	public void setup () {
	  
	 size (500,500);
	 smooth ();
	  
	 for (int i=0; i<pickles.length; i++) {
	   pickles [i] = new Particle ();
	 }
	  
	  
	}
	 
	 
	public void draw () {
	  background (0); //clear the background
	   
	   for (int i=0; i<pickles.length; i++) {
	  pickles[i].update();
	   }
	   
	}
	 
	class Particle {
	  
	  float x;
	  float y;
	   
	  float velX; // speed or velocity
	  float velY;
	   
	   
	  Particle () {
	   //x and y position to be in middle of screen
	    x = width/2;
	    y = height/2;
	     
	    velX = random (-10,10);
	    velY = random (-10,10);
	     
	    
	  }
	   
	  void update () {
	     
	    x+=velX;
	    y+=velY;
	     
	    fill (255);
	    ellipse (x,y,10,10);
	  }
	   
	   
	}

}
