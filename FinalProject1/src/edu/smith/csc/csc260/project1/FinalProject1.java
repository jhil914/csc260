package edu.smith.csc.csc260.project1;

/**This program shows three scenes:
 * day scene with a spider-like animal with sunlight radiations from it,
 * night scene with a spider-like animal with sunlight-like radiations from it, and
 * night scene with a worm-like animal that produces colorful bouncing balls
 * 
 * Day and night scenes alternate as user shakes the mouse fast for 5 frames.
 * Among night scene, the worm scene and spider scene alternate.
 * 
 * The sunlight-like circles are arranged in different parabola functions according to 
 * the mouse location. 
 * 
 * Two trees are in all scenes. The big tree acts as a trap while the small tree
 * damages everything. When the mouse is over big tree, the animal
 * transforms into an aggressive one, confined in the tree branches. Confinement of animal
 * might hint the user to shake the mouse in frustration. Shaking the mouse will free the
 * animal and change to another scene. When the mouse is over small tree, the whole scene is
 * destroyed as if an ink drawing is soaked with water. Shaking the mouse is the only way
 * to escape to stop this damage and restart the scene.
 * 
 * @author Hee Jin, Yamin Tun
 * @version Oct 22 2013
 * csc 260
 * @citation snake() http://www.learningprocessing.com/examples/chapter-9/example-9-8/
 * 			collision() http://processing.org/examples/circlecollision.html
 * 			createsunlight() http://www.openprocessing.org/sketch/22005
 * 			drawing trees http://processing.org/examples/tree.html
 */
import java.util.ArrayList;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class FinalProject1 extends SmithPApplet {
	private static final long serialVersionUID = 1L; 
	
	// Variables for spider-like cursor
	// Radius of spider body
	float spiderRad = 30;
	
	// Variables for day-night transition
	int timer = 0; //timer for the frames with fast mouse speed
	float speed = 1; //mouse speed
	
	/*Boolean of whether it is daytime or night time*/
	boolean isDark = false;
	
	/*Boolean of whether it is in transition state from day to night or vice versa*/
	boolean inTransition = false;	
	
	/*Boolean of whether it is night scene with worm*/
	boolean isNightwithWorm = false;

	/*Boolean of whether mouse is over big tree or small tree*/
	boolean isinBigTree = false;
	boolean isinSmallTree = false;

	/*Positions for locations of two trees*/
	float bigTreeX = 150f;
	float smallTreeX = 650f;
	
	/* Transparency value of blackness for day-night transition*/
	float alpha = 0; 
	
	/*Counter for alternating worm and spider-like creature*/
	int night_counter = 0;
	
	// Variables for worm
	public ArrayList<BouncingSprite> s;
	float[] xpos = new float[50];
	float[] ypos = new float[50];
	public Point startP;
	public Point endP;

	// Variables for SunLight
	float[] xpos1 = new float[50];
	float[] ypos1 = new float[50];
	public int dim;
	float[] SunLight_color = { 60f, 60f, 10f, 60f };
	/** sets up initial background of the scene*/
	public void setup() {
		super.setup();

		size(800, 400);
		frameRate(30);
		smooth();
		colorMode(RGB, 100, 100, 100, 100);
		background(100, 100, 95); // yellowish color

		fill(0);
		s = new ArrayList<BouncingSprite>();

	}

	
	public void draw() {

		super.draw();

		strokeWeight(2f);
		
		//check mouse speed, determine if it should be day scene or night scene
		checkMouseSpeedforTransition();
		
		// for night time
		if (isDark) {
			
			//if it is not day-to-night transition time,
			if (!inTransition){

				//clear every frame if mouse is not over small tree
				if( !isinSmallTree) {
					clearPrintsNight();
				}
			
				//draw sunlight for if it is not worm scene
				if(!isNightwithWorm){
					drawSunLight();
				}
				
				stroke(100);
				
			}
			
			//if it is night scene with worm, draw only two trees
			if(isNightwithWorm){
				drawTrees();
				checkMouseStatus();
			}
			
			//if it is night scene with spider, draw only two trees, spider and sunlight
			else {
				drawSpiderScene();
			}		
		}

		// for day time
		else {

			//remove all night-time bouncing sprites from arraylist
			for (int i = 0; i < s.size(); i++) {
				BouncingSprite bSprite = s.get(i);
				removeSprite(bSprite);
				s.remove(i);
			}
			
			//if it is not transition time
			if (!inTransition) {
				
				//as long as the mouse is not over small tree, 
				//clear all drawings from previous frame
				if (!isinSmallTree) {
					clearPrintsDay();
				}
				
				drawSunLight();
				stroke(0);
				
			}			
			drawSpiderScene();			
		}
		
	}
	
	

	/**Check mouse speed. If the speed is greater than 150 pixel for 5 frames 
	 * (not continuous), do night-to-day or day-to-night transition.
	 */
	public void checkMouseSpeedforTransition(){
		speed = abs((mouseX - pmouseX) + (mouseY - pmouseY));

		// wait till user swipe mouse very fast for 5 frames
		if (speed > 150 && timer < 5) {
			timer++;
		}

		/** TO NIGHT */
		// if user swipes mouse for more than 5 frames, transform to night
		if (timer >= 5) {
			
			// randomize SunLight color for every scene
			for (int i = 0; i < SunLight_color.length; i++) {
				SunLight_color[i] = randomizeFloat(80f, 100f);
			}
			
			//if it is daytime
			if (!isDark) {
				
				//now is the transition time from day to night
				if (alpha < 100) {
					inTransition = true;
				
					//draw full-screen dark rectangle with increasing alpha value
					fill(0, 0, 0, alpha);
					rect(0, 0, width, height);
					alpha += 5;
					
					//increase the whiteness of stroke 
					stroke(100 - alpha);

				}
				
				//now is the night time.
				else {
					//count as one night for alternating night scene
					night_counter++;  
					//alternate this variable as true and false
					isNightwithWorm= (night_counter % 2 ==0 );
					
					isDark = true;
					background(0, 0, 0);
					
					//reset values for the next day-night transition
					timer = 0; 
					alpha = 0; 
					inTransition = false; 
					isinSmallTree = false;
					isinBigTree = false;	

				}
			}

			/** TO DAY */
			// if user swipes mouse for more than 5 frames, transform to day
			else {
				
				//now is the transition time from night to day
				if (alpha < 100) {
					inTransition = true;
					
					//draw full-screen white rectangle with increasing alpha value
					fill(100, 100, 95, alpha);
					rect(0, 0, width, height);
					alpha += 5;
					
					//increase the darkness of stroke 
					stroke(alpha);
				}
				
				// now is day time
				else {
					isDark = false;
					inTransition = false;
					background(100, 100, 95);
					
					//reset values for the next day-night transition
					timer = 0;
					alpha = 0;
					isinSmallTree = false;
					isinBigTree = false;
					
					
				}
			}
		}

	}
	
	/**Draw semi-transparent black rectangle over the entire screen to
	 * create fade effect for night time
	 */
	public void clearPrintsNight() {
		fill(0, 0, 0, 50);
		rect(0, 0, width, height);
	}

	/**Draw semi-transparent white rectangle over the entire screen to
	 * create fade effect for day time
	 */
	public void clearPrintsDay() {
		fill(100, 100, 95, 20);
		rect(0, 0, width, height);
	}

	/**Draw two trees on the screen*/
	public void drawTrees() {
		// draw big tree at position (bigTreeX, height)
		translate(bigTreeX, height);
		drawBranch_r((height) / 4f, PI / 4, (height) / 4f, PI / 4, 0.3f, 0.3f,
				0.4f, 0.5f); // k*PI/4);
		translate(-bigTreeX, -height);

		// draw small tree at position (smallTreeX, height)
		translate(smallTreeX, height);
		drawBranch_r((height) / 8f, PI / 4, (height) / 8f, PI / 4, 0.3f, 0.5f,
				0.1f, 0.2f);
		translate(-smallTreeX, -height);

	}

	/**Check if mouse is over any tree or over background and 
	 * translate the location of objects accordingly
	 */
	public void checkMouseStatus() {
		
		//if mouse is over big tree
		if (isinBigTree) {
			
			//draw legs for the spider-like animal 
			translate((bigTreeX - 50f) + mouseX * 100f / width,
					(height / 2f + 50f) + mouseY * (height / 2f - 100f)
							/ height);
			drawRandomLegs();
			translate(- ((bigTreeX - 50f) + mouseX * 100f / width),
					-((height / 2f + 50f) + mouseY * (height / 2f - 100f)
							/ height));
		} 
		
		//if mouse is over small tree
		else if (isinSmallTree) {
			
			if (isNightwithWorm) {
				filter(BLUR, 1f);
			}

		} 
		
		//if mouse is over background
		else {
			
			//if it is daytime, or night time with spider scene
			if (!isDark || !isNightwithWorm) {
				translate(mouseX, mouseY);
			}

			// if mouse touches the big tree
			if (mouseX > bigTreeX - 50f && mouseX < bigTreeX + 50f
					&& mouseY > height / 2f + 50f && mouseY <= height - 50f) {
				isinBigTree = true;
			}

			// if mouse touches the small tree
			else if (mouseX > smallTreeX - 25f && mouseX < smallTreeX + 25f
					&& mouseY > height - 50f && mouseY <= height) {
				isinSmallTree = true;
			}
		}

	}

	/**Draw two trees and spider*/
	public void drawSpiderScene() {

		//blur all drawings
		filter(BLUR, 1f);

		//draw moving trees
		drawTrees();
		
		//check if mouse is over any tree
		checkMouseStatus();
		
		//draw spider body
		spiderRad = randomizeFloat(1f, 15f);
		fill(0);  
		ellipse(0, 0, spiderRad * 2f, spiderRad * 2f);

		//draw spider legs
		drawLinearLegs();
		translate(-mouseX, -mouseY);

	}

	/**Draw constantly-spaced shorter legs for spider*/
	public void drawLinearLegs() {
		for (float angle = 0; angle < 2 * PI; angle += PI / 6) {
			float lenLine = randomizeFloat(0, 40f);
			line  (spiderRad * cos(angle), 
					spiderRad * sin(angle),
					(spiderRad + lenLine) * cos(angle), 
					(spiderRad + lenLine) * sin(angle));
		}
	}
	
	/**Draw randomly-spaced longer legs for spider*/
	public void drawRandomLegs() {
		float angle = 0f;
		
		while (angle < 2f * PI) {
			strokeWeight(2f);
			float lenLine = randomizeFloat(0, 100f);
			angle += randomizeFloat(0, PI / 8); // 0 to 45 deg
			line   (spiderRad * cos(angle), 
					spiderRad * sin(angle),
					(spiderRad + lenLine) * cos(angle), 
					(spiderRad + lenLine) * sin(angle));
		}

	}

	/** Return random float value between min and max */
	public float randomizeFloat(float min, float max) {
		return min + (float) Math.random() * max;
	}

	/** Return random long value between min and max */
	public long randomizeDuration(long min, long max) {
		return min + (long) (Math.random() * max);
	}

	/** Draw a tree using recursion.
	 
	 */
	public void drawBranch_r(float h, float angle, float h1, float angle1,
			float h_i, float angle_i, float h_range, float angle_range) {

		h *= randomizeFloat(h_i, h_i + h_range);
		angle *= randomizeFloat(angle_i, angle_i + angle_range);

		h1 *= randomizeFloat(h_i, h_i + h_range);
		angle1 *= randomizeFloat(angle_i, angle_i + angle_range);

		//as long as height of current branch is greater than 2 pixel
		if (h > 2) {
			//draw left branch
			pushMatrix();
			rotate(angle);
			line(0, 0, 0, -h);
			translate(0, -h); // move to the end of branch
			drawBranch_r(h, angle, h1, angle1, h_i, angle_i, h_range,
					angle_range);
			popMatrix();

			//draw right branch
			pushMatrix();
			rotate(-angle1);
			line(0, 0, 0, -h1);
			translate(0, -h1); // move to the end of branch
			drawBranch_r(h, angle, h1, angle1, h_i, angle_i, h_range,
					angle_range);
			popMatrix();

		} 

	}

	/** Draw SunLight
	 */
	public void drawSunLight() {
		float r = 0;
		float theta = 0;
		// Shift array values
		for (int i = 0; i < xpos1.length - 1; i++) {
			// Shift all elements down one spot.
			// xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at the
			// second to last element.
			xpos1[i] = xpos1[i + 1];
			ypos1[i] = ypos1[i + 1];
		}

		// New location
		xpos1[xpos1.length - 1] = mouseX; // Update the last spot in the array
											// with the mouse location.
		ypos1[ypos1.length - 1] = mouseY;

		
		for (int i = 1; i < xpos1.length; i = i * 2) {
			
			
			for (int j = 1; j < xpos1.length; j++) {

				float x = cos(theta);//equations that creates the parabolic movement of the sprites

				float y = 4 * sin(theta);

				noStroke();
				
				fill((int) SunLight_color[0] - j * 100 / xpos1.length,
						(int) SunLight_color[1] - j * 100 / xpos1.length,
						(int) SunLight_color[2] - j * 100 / xpos1.length, 60);
				// Draw an ellipse for each element in the arrays.
				ellipse(xpos1[j] * x, ypos1[j] * y, i * 80 / xpos1.length, i
						* 80 / xpos1.length);
				theta += j * 3;

				r = (float) (r + 0.05 * j);
			}

		}
	}
/** creates the snake-like figure at mouse point during night scenes*/
	public void snake() {
		for (int i = 0; i < xpos.length - 1; i++) {
			// Shift all elements down one spot.
			// xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at the
			// second to last element.
			xpos[i] = xpos[i + 1];
			ypos[i] = ypos[i + 1];
		}

		// New location
		xpos[xpos.length - 1] = mouseX; // Update the last spot in the array
										// with the mouse location.
		ypos[ypos.length - 1] = mouseY;

		for (int i = 0; i < xpos.length; i++) {	
			noStroke();
			fill((int) 255 - i * 255 / xpos.length);
			// Draw an ellipse for each element in the arrays.
			ellipse(xpos[i], ypos[i], i * 50 / xpos.length, i * 50
					/ xpos.length);

		}
	}

	public void mouseMoved() {

		// Shift array values

		if (isDark && !isinBigTree && isNightwithWorm) {
			collide();
			snake();
			//creates bouncing sprite everytime the mouse speed is 6 and the total bouncing sprite is less than 100
			if (speed == 6) {
				if (s.size() < 100) {
					
					BouncingSprite bs2 = new BouncingSprite(width, height,
							new Point((float) Math.random() / 5, (float) Math
									.random() / 5, 0),
							(int) (Math.random() * 20));
					bs2.setLocation(new Point(mouseX, mouseY));
					bs2.setFill(new Color((float) (Math.random() * 255),
							(float) (Math.random() * 255), (float) (Math
									.random() * 255), 150));

					bs2.setNoStroke(true);
					addSprite(bs2);
					s.add(bs2);//adds the sprites in a seperate array to keep track
					
				}

			}
		}

	}
/**keeps track of collisions made with the sprites and the mouse point */
	public void collide() {

		for (int i = 0; i < s.size(); i++) {
			BouncingSprite bounce = s.get(i);
			float dx = mouseX - bounce.getLocation().getX();
			float dy = mouseY - bounce.getLocation().getY();
			float dis = (float) Math.sqrt(dy * dy + dx * dx);
			float vx = bounce.getvel().getX();
			float vy = bounce.getvel().getY();
			//during each collisions, the sprites are bounced off to random directions
			if (Math.abs(mouseX - mouseX) >= 8) {
				if (dis <= 50 + bounce.getRadius()) {
					System.out.println("collide");
					if (mouseX - mouseX < 0) {// going left

						bounce.setVel(vx, -vy);
					}

					else {
						bounce.setVel(vx, -vy);

					}
				}
			}
		}
	}

}
