package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.core.SmithPApplet;

public class snake extends SmithPApplet{
	// Learning Processing
	// Daniel Shiffman
	// http://www.learningprocessing.com

	// Example 9-8: A snake following the mouse

	// Declare two arrays with 50 elements.
	int[] xpos = new int[50]; 
	int[] ypos = new int[50];
	public int canvasH = 500;
	public int canvasW = 500;
	public void setup() {
		super.setup();
	  size(500,500);
	  
	  smooth();
	  // Initialize all elements of each array to zero.
	  for (int i = 0; i < xpos.length; i ++ ) {
	    xpos[i] = 0; 
	    ypos[i] = 0;
	  }
	}

	public void draw() {
		super.draw();
	  background(255);
	  
	  // Shift array values
	  for (int i = 0; i < xpos.length-1; i ++ ) {
	    // Shift all elements down one spot. 
	    // xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at the second to last element.
	    xpos[i] = xpos[i+1];
	    ypos[i] = ypos[i+1];
	  }
	  
	  // New location
	  xpos[xpos.length-1] = mouseX; // Update the last spot in the array with the mouse location.
	  ypos[ypos.length-1] = mouseY;
	  if(!mousePressed){
	  // Draw everything
	  for (int i = 1; i < xpos.length; i = i*2) {
	     // Draw an ellipse for each element in the arrays. 
	     // Color and size are tied to the loop's counter: i.
	    noStroke();
	    fill((int)255-i*255/xpos.length);
	    ellipse(xpos[i],ypos[i],i*100/xpos.length,i*100/xpos.length);
	    
	    ellipse(xpos[i]*i/xpos.length,ypos[i]*i/xpos.length,i*100/xpos.length,i*100/xpos.length);
	  
	  }
	  }
	  else{
		  for (int i = 1; i < xpos.length; i++) {
			     // Draw an ellipse for each element in the arrays. 
			     // Color and size are tied to the loop's counter: i.
			    noStroke();
			    fill((int)255-i*255/xpos.length);
			    ellipse(xpos[i],ypos[i],i*50/xpos.length,i*50/xpos.length);
			  }
	  }
	}
}
