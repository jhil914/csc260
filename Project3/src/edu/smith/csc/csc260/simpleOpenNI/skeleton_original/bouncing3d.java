package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import processing.core.PApplet;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Point;

public class bouncing3d extends SmithPApplet{
	int xPos=320;
	int yPos=240;
	int zPos=1;
	 
	int xSpeed=2;
	int ySpeed=2;
	int zSpeed=2;
	 
	int xDirection=1;
	int yDirection=1;
	int zDirection=1;
	 
	float[] xpos = new float[50];
	float[] ypos = new float[50];
	float[] zpos = new float[50];
	public Point startP;
	public Point endP;

	// Variables for SunLight
	float[] xpos1 = new float[50];
	float[] ypos1 = new float[50];
	float[ ]zpos1 = new float[50];
	// end of variables
	 int count = 0;
	 
	 int centerx = 0;
	 int eyez = 0;
	public void setup() {
	 
	  size (640, 480, P3D);
	  smooth();
	}
	public void drawSunLight2() {
		// System.out.println("drawing sunlight");
		float r = 0;
		float theta = 0;
		// Shift array values
		
			for (int i = 0; i < xpos1.length - 1; i++) {
				// Shift all elements down one spot.
				// xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at
				// the
				// second to last element.
				xpos1[i] = xpos1[i + 1];
				ypos1[i] = ypos1[i + 1];
				zpos1[i] = zpos1[i+1];
			}

			// New location
			xpos1[xpos1.length - 1] = xPos	; // Update
																		// the
			// last spot in
			// the array
			// with the mouse location.
			ypos1[ypos1.length - 1] = yPos;
			zpos1[zpos1.length-1]=zPos;
			for (int i = 1; i < xpos1.length; i = i * 2) {

				for (int j = 1; j < xpos1.length; j++) {

					float x = cos(theta);// equations that creates the parabolic
											// movement of the sprites

					float y = 4 * sin(theta);
					float z = cos(theta);
					noStroke();

					
					// Draw an ellipse for each element in the arrays.
					
					//ellipse(xpos1[j] * x, ypos1[j] * y, i * 80 / xpos1.length,
					//		i * 80 / xpos1.length);
					
					 
					  sphere(50);
					  //translate (xpos1[j]*x, ypos1[j] * y, zpos1[j]);
					  fill((int) 200 - j * 100 / xpos1.length,
								(int) 255 - j * 100 / xpos1.length,
								(int) 200 - j * 100 / xpos1.length,
								100);
					theta += j * 3;

					r = (float) (r + 0.05 * j);
				}

			}
		}
	

	/** Draw SunLight
	 */

	public void draw() {
	 
	  background(1);
	  drawSunLight2();
	  lights();
	 
	 
	  // box setup
	 
	  stroke(255);
	 
	  // background square
	 
	  line(0, 0, -500, width, 0, -500);
	  line(0, 0, -500, 0, height, -500);
	  line(0, height, -500, width, height, -500);
	  line(width, height, -500, width, 0, -500);
	 
	  // perspective lines
	 
	  line(0, 0, -500, 0, 0, 0);
	  line(width, 0, -500, width, 0, 0);
	  line(0, height, -500, 0, height, 0);
	  line(width, height, -500, width, height, 0);
	 
	 
	  // inital ball set up
	 
	  translate (xPos, yPos, -zPos);
	  sphere(50);
	  noFill();
	 
	  // motion setup
	 
	  xPos = xPos + (xSpeed * xDirection); 
	  yPos = yPos + (ySpeed * yDirection);
	  zPos = zPos + (zSpeed * zDirection);
	 
	  if (xPos>width-50) {
	    xDirection*=-1;
	  }
	 
	  if (yPos>height-50) {
	    yDirection*=-1;
	  }
	 
	  if (zPos>500) {
	    zDirection*=-1;
	  }
	 
	  if (xPos<50) {
	    xDirection*=-1;
	  }
	 
	  if (yPos<50) {
	    yDirection*=-1;
	  }
	 
	  if (zPos<0) {
	    zDirection*=-1;
	  }
	 
	  //reversal
	 
	  
	 count ++;
		  camera(width/2, height/2, (height/2) / tan(PI/6)+eyez, // eyeX, eyeY, eyeZ
				  width/2+centerx, height/2, 0.0f, // centerX, centerY, centerZ
			         0.0f, 1.0f, 0.0f); // upX, upY, upZ
			  
	  
	}
	public void keyPressed() {
		  if(key==CODED){
		    if(      keyCode==LEFT){  centerx-=10;
		    } if(keyCode==RIGHT){ centerx+=10;
		    } if(keyCode==UP){    eyez-=10;
		    } if(keyCode==DOWN){  eyez+=10;
		    }
		  }
		  
		  redraw();
		}
	
	
}
