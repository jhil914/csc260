package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import java.util.ArrayList;

import processing.core.PVector;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class growingtree extends SmithPApplet {
	// following pattern natural, explode then gather, user in range function
	int num = 20;
	int myLeavestotal = 3;

	Leaves[][] myLeaves = new Leaves[num][myLeavestotal]; // creating my object
	Leaves[] myLeaves2 = new Leaves[num]; // creating my object
	Leaves[] myLeaves3 = new Leaves[num]; // creating my object
	Color[] myColors = new Color[2]; // creating 2 differnt colors
	int[] xpos = new int[num];
	int[] ypos = new int[num];
	boolean following = false;
	int leaveID;
	int count = 0;
	// more organic and 3d
	public void setup() {
		size(640, 480);

	//	myColors[0] = new Color(150, 255, 46, 95); // allows me to set colors
													// for my color indices
	//	myColors[1] = new Color(12, 191, 159, 80);

		background(0);

		smooth();
		//not used for now (it's for the snake following mouse
		for (int i = 0; i < xpos.length; i++) {
			xpos[i] = 0;
			ypos[i] = 0;
		}
//makes the leaves
		Point randp = new Point(random(0,width), random(0,height));
		for (int i = 0; i < myLeaves.length; i++) {
			myLeaves[i][0] = new Leaves(width / 2, height / 2);
			myLeaves[i][0].setrandp(randp);
		}
		randp = new Point(random(0,width), random(0,height));
		for (int i = 0; i < myLeaves.length; i++) {
			myLeaves[i][1] = new Leaves(100, 100);
			myLeaves[i][0].setrandp(randp);
		}
		randp = new Point(random(0,width), random(0,height));
		for (int i = 0; i < myLeaves.length; i++) {
			myLeaves[i][2] = new Leaves(400, 400);
			myLeaves[i][0].setrandp(randp);
		}
	}

	public void draw() {

		background(0);

		noStroke();

		// fill(250, 255, 8,80);
		// ellipse(width/2,height/2, 100,100);
		
		for (int i = 0; i < xpos.length - 1; i++) {
			// Shift all elements down one spot.
			// xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at the
			// second to last element.
			xpos[i] = xpos[i + 1];
			ypos[i] = ypos[i + 1];
		}

		// New location
		xpos[xpos.length - 1] = (int) (mouseX+random(-2,2)); // Update the last spot in the array
										// with the mouse location.
		ypos[ypos.length - 1] = (int) (mouseY+random(-2,2));
		if (mousePressed) {
			
			for (int j = 0; j < myLeavestotal; j++) {
				following = false;
				
				for (int i = 1; i < myLeaves.length; i++) {
					
					myLeaves[i][j].mousenear(10);
					if (myLeaves[i][j].inRange ==true) {
						
							//System.out.println(j);
							if (myLeaves[i][j].colorstatus()
									.equals("green")) {
								// Draw an ellipse for each element in the
								// arrays.
								// Color and size are tied to the loop's
								// counter: i.
								// myLeaves[i][j].x = xpos[i]+(int)
								// random(-2,2);
								// myLeaves[i][j].y = ypos[i]+(int)
								// random(-2,2);
								float noisescale = 0.02f;
								float noiseval = noise(myLeaves[i][j].x * noisescale, myLeaves[i][j].y * noisescale);
								
									// Draw an ellipse for each element in the arrays.
									// Color and size are tied to the loop's counter: i.
								myLeaves[i][j].x = (int) (xpos[i] +noiseval*5);
								myLeaves[i][j].y = (int) (ypos[i] +noiseval*5);
									
								
								myLeaves[i][j].update();
								myLeaves[i][j].draw();
								//System.out.println(myLeaves[i][j].x);

							} else {
								myLeaves[i][j].outsidewindow();
								
								if(myLeaves[i][j].outside){
									//store the j so that with that j every i gets the same random point
									//System.out.println("outside");
									myLeaves[i][j].storeloc(myLeaves[i][j].x, myLeaves[i][j].y);
									if(count<width){
										count ++;
										}else{
											count =0;
										}
									if(myLeaves[i][j].randompoint!=null){
									myLeaves[i][j].gather(new Point (300,300));
									}
								}else{
									myLeaves[i][j].explode();
									Point random = new Point (random(0,width), random(0, height));
									myLeaves[i][j].setrandp(random);
								}
								myLeaves[i][j].draw();

							}

					
						
					} else {
						myLeaves[i][j].update();
						myLeaves[i][j].draw();
					}
				}
				/**
				 * for (int i=0; i < myLeaves.length; i++) {
				 * myLeaves[i].explode(); myLeaves[i].draw(); }
				 */
				
			}
		} else {
			for (int j = 0; j < myLeavestotal; j++) {
				for (int i = 0; i < myLeaves.length; i++) {

					myLeaves[i][j].update();
					myLeaves[i][j].draw();
				}
			}
		}
		filter(BLUR, 2);
	}

	/**
	 * public void mousePressed() { setup(); } public void keyPressed() { //
	 * System.out.println(true); for (int i=0; i < myLeaves.length; i++) {
	 * myLeaves[i].explode(); // myLeaves[i].draw(); }filter(BLUR, 2); }
	 */
	public void mouseReleased() {
		for (int j = 0; j < myLeavestotal; j++) {
			for (int i = 0; i < myLeaves.length; i++) {

				myLeaves[i][j].restore();
				myLeaves[i][j].mousenearfalse();
			}
		}
		
	}

	public boolean mouseinRange(int r, int x, int y) {
		boolean inRange = false;
		if (Math.abs(mouseX - x) <= r && Math.abs(mouseY - y) <= r) {
			inRange = true;
		}

		return inRange;

	}

	class Leaves {
		int velx = (int) random(-10, 10);
		int vely = (int) random(-10, 10);
		int initialx;
		int initialy;
		int x;// starts the leaves towards the center of the screen
		int y;
		Color c = myColors[floor(random(0, 2))];
		float noisescale = 0.02f;
		float times = 0;
		float n;
		int div;
		int changex;
		int changey;
		float a;
		float b;
		boolean outside = false;
		int range = -1;
		float R = 200;
		float G = 0;
		int colorchange = -1;
		float accelX, accelY;
		float springing = .0009f, damping = .98f;
		boolean inRange = false;
		int initx ;
		int inity ;
		int storedx;
		int storedy;
		PVector startP;
		PVector endP;
		Point randompoint;
		Leaves(int firstx, int firsty) {
			initialx = firstx;
			initialy = firsty;
			x = initialx;// starts the leaves towards the center of the screen
			y = initialy;
			initx = (int) (initialx);
			inity = (int) (initialy);
			//System.out.println(initialx + " " + initialy);
		}

		void update() {
			//changex = mouseX - x;
			//changey = mouseY - y;
			// System.out.println(y);
		//	float noiseval = noise(x * noisescale, y * noisescale);
			// System.out.println(noiseval);

		//	float dis = (float) Math
		//			.sqrt(changex * changex + changey * changey);
		//	times += 0.01f;
			if (Math.abs(x - initialx) > 100 || Math.abs(y - initialy) > 100) {
				range *= -1;
			}
			if (Math.abs(x - initialx) < 1 || Math.abs(y - initialy) < 1) {
				range = -1;
			}
			if (range > 0) {
				if (initialx - x > 0) {
					if (initialy - y > 0) {
						x = x + (int) random(0, 2);
						y = y + (int) random(0, 2);
					} else {
						x = x + (int) random(0, 2);
						y = y - (int) random(0, 2);
					}

				} else {
					if (initialy - y > 0) {
						x = x - (int) random(0, 2);
						y = y + (int) random(0, 2);
					} else {
						x = x - (int) random(0, 2);
						y = y - (int) random(0, 2);
					}
				}

			} else if (range < 0) {
				x = x + (int) random(-2, 2);
				y = y + (int) random(-2, 2);
			}
			/**
			 * if(x>200){
			 * 
			 * range *=-1; } if(x<10){ range = -1; }
			 * 
			 * if(range>0){ x=x-(int) (noiseval*5); y = (int) (noiseval*80+100);
			 * } else if(range<0){ x = x+(int) (noiseval*5); //this updates the
			 * position of the point and makes it move across the screen y =
			 * (int) (noiseval*80+100); } /** if(changey!=0&&changex!=0){ div =
			 * changey/changex;
			 * 
			 * if(div>=1){
			 * 
			 * a = div; b =1; System.out.println("a"); } else if(div!=0){
			 * System.out.println("b"); a = 1; b = 1/div; } } else{
			 * System.out.println("c"); a=2; b=2; }
			 */
		}
		public void mousenear(int r){
			if (Math.abs(mouseX - x) <= r && Math.abs(mouseY - y) <= r) {
				inRange = true;
			}
			
		}
		public void mousenearfalse(){
			inRange = false;
		}
		public void follow() {
			float noiseval = noise(x * noisescale, y * noisescale);
			for (int i = 0; i < xpos.length; i++) {
				// Draw an ellipse for each element in the arrays.
				// Color and size are tied to the loop's counter: i.
				initialx = (int) (xpos[i] +noiseval*5);
				initialy = (int) (ypos[i] +noiseval*5);
				
			}
			
		}
		public void setrandp(Point p){
			randompoint = p;
		}
public boolean outsidewindow(){
	
	if(x<0 ||x>width ||y<0 ||y>height){
		outside = true;
	}
	
	return outside;
}
		public void explode() {
			float noiseval = noise(x * noisescale, y * noisescale);

			x += velx + noiseval * 5;
			y += vely + (int) (noiseval * 5);
			
			storedx = x;
			storedy = y;
			// ellipse(x, y, 20,20);
		}
		public void storeloc(int locx, int locy){
			storedx = locx;
			storedy = locy;
		}
		public void gather( Point g){
			
			startP = new PVector(storedx, storedy);
			endP = new PVector(g.getX(),g.getY());
			
			float progress = map(count, 0, width, 0, 1);  

			  float px = lerp(startP.x, endP.x, progress);     // we can calculate x and y separately
			  float py = lerp(startP.y, endP.y, progress);
			 // System.out.println(count);
			   
			  x=(int) px;
			  y=(int) py;
			  if(progress>=0.9){
				  outside = false;
				  inRange = false;
				  initialx = x;
				  initialy = y;
			  }
		
		}
//this method isn't used for now
		public void change() {
			System.out.println(div);
			n = (float) (noise(times) * Math.sqrt(changex * changex + changey
					* changey));
			// System.out.println(mouseX+" "+x+" "+changex+" "+changey);
			if (changex > 0) {
				if (changey > 0) {
					// System.out.println("1");
					x += random(0, a);
					y += random(0, b);
				} else {
					// System.out.println("here");
					x += random(0, a);
					y -= random(0, b);
				}
			} else {
				if (changey > 0) {
					// System.out.println("there");
					x -= random(0, a);
					y += random(0, b);
				} else {
					// System.out.println("2");
					x -= random(0, a);
					y -= random(0, b);
				}
			}
		}

		public String colorstatus() {
			String stat = "nocolor";
			if (R >= 100) {
				stat = "red";
			} else if (G >= 100) {
				stat = "green";
			}

			return stat;
		}

		public void restore() {
			x = initialx;
			y = initialy;
		}

		public void draw() {

			if (colorchange > 0) {
				if (R <= 200 && R >= 0) {
					R -= 0.5;
					G += 0.5;
					if (R == 0) {
						colorchange *= -1;
					}
				}
			} else {
				if (R == 200) {
					colorchange *= -1;
				} else {

					R += 0.5;
					G -= 0.5;
				}
			}

			strokeWeight(5);
			fill(R, G, 100, 100);
			// fill(0,139,139,100);
			stroke(0, 10);
			ellipse(x, y, 20, 20);

			// ellipse(width/2, height/2, n,n);
		}

		/*
		 * void tree(float x, float y, float w, float l) { fill(152, 95, 60,
		 * 80); rect(height/2, width/2, 10, height/2);
		 */
	}

}
