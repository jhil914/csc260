package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

/**
 * CSC 260: Project 2- fireflies
 * This program creates an interactive environment in which firefly-like objects fall onto the 
 *silehoutte of the user. The program recognizes the clap gesture (two hands are at close locations)  *of user and creates his or her shadow image behind the user along with chime sound. The idea for
 * the program comes from inspiration of Hayao Miyazaki's Grave of Fireflies movie.
 * Reference: 
 * Code: Edited Professor Eitan's silehoutte's code from Piazza
 * Image uncovered by shadows: http://ayay.co.uk/background/digital_art/nature/forest-at-night-aurora-borealis/
 *
 * @version Nov 14, 2013
 * @author Hee Jin and Yamin Tun
`*/

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PImage;
import SimpleOpenNI.SimpleOpenNI;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Point;


public class project2 extends SmithPApplet {
	private static final long serialVersionUID = 1L;

	public SimpleOpenNI simpleOpenNI;
	ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>>();

    BodypartTrackingSprite left_hand,right_hand;

	int[] userMap;

	PImage backgroundImage, shadowImage, resultImage, forestImage;

	int userID, count;
	boolean tracking = false;
	boolean clapped = false;

    /*Number of fireflies*/
	final int n_fireflies = 20;

    /*Arraylist of x coordinate values for fireflies that touch user shadow*/
	ArrayList<Integer> touchedXs_shadow = new ArrayList<Integer>();
    /*Arraylist of y coordinate values for fireflies that touch user shadow*/
	ArrayList<Integer> touchedYs_shadow = new ArrayList<Integer>();

    /*Arraylist of x coordinate values for fireflies that are falling*/
	ArrayList<Integer> fireflyXs = new ArrayList<Integer>();
    /*Arraylist of y coordinate values for fireflies that are falling*/
	ArrayList<Integer> fireflyYs = new ArrayList<Integer>();
	/*Arraylist of y coordinate values for fireflies that are falling*/
	ArrayList<Integer> fireflyZs = new ArrayList<Integer>();
    /*Array of file names for chime soundtrack*/
	final String[] soundSelection = { "Chimes1.mp3", "Chimes2.mp3", "Chimes3.mp3" };

	Minim minim;
	AudioPlayer player;
	
	int xPos=320;
	int yPos=240;
	int zPos=1;
	 
	int xSpeed=4;
	int ySpeed=4;
	int zSpeed=4;
	 
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
	 int count2 = 0;
	 
	 int centerx = 0;
	 int centerz = -2000;
	 int eyez = 0;
	PImage img;
	
	public void setup() {
		size(640, 480,P3D);
		simpleOpenNI = new SimpleOpenNI(this);

		if (simpleOpenNI.isInit() == false) {
			System.out.println("Can't init SimpleOpenIN, maybe the camera is not connected!");
			exit();
			return;
		}

		smooth();
		noStroke();
		simpleOpenNI.setMirror(true);
		simpleOpenNI.enableDepth(); // needs to be enable for skeletons to work
									// right
		simpleOpenNI.enableUser();
		simpleOpenNI.enableRGB();
		simpleOpenNI.alternativeViewPointDepthToImage();

		resultImage = new PImage(width, height, RGB);
		shadowImage = new PImage(width, height, RGB);

		addFireflies();

		count = 0;

		loadForestImage();
		
		minim = new Minim(this);

	}

	public void onNewUser(SimpleOpenNI curContext, int userId) {
		System.out.println("new user:" + userId);
		userID = userId;
		tracking = true;
		simpleOpenNI.startTrackingSkeleton(userId);

		Vector<BodypartTrackingSprite> newTracks = new Vector<BodypartTrackingSprite>();

		right_hand = new BodypartTrackingSprite(this, userId,
				SimpleOpenNI.SKEL_RIGHT_HAND);
		addSprite(right_hand);
		newTracks.add(right_hand);

		left_hand = new BodypartTrackingSprite(this, userId,
				SimpleOpenNI.SKEL_LEFT_HAND);
		addSprite(left_hand);
		newTracks.add(left_hand);

		tracks.put(userId, newTracks);

	}

	public void onLostUser(SimpleOpenNI curContext, int userId) {
		System.out.println("lost user:" + userId);

		Vector<BodypartTrackingSprite> btsVec = tracks.get(userId);
		for (BodypartTrackingSprite bts : btsVec) {
			removeSprite(bts);

		}

	}

	public void draw() {

		simpleOpenNI.update();
		//PImage rgbImage = simpleOpenNI.rgbImage();
		//image(rgbImage, width, 0);
		//background(0);
		if (tracking) {
			
			// ask kinect for bitmap of user pixels
			loadPixels();
			userMap = simpleOpenNI.userMap();

			// captures still image after the user claps
			super.draw();
			//threedspace();
			//variables for clap motion
			float rightx = right_hand.getLocation().getX();
			float righty = right_hand.getLocation().getY();
			float leftx = left_hand.getLocation().getX();
			float lefty = left_hand.getLocation().getY();
			//detects if the two hands are in relatively close range to each other
			if (!clapped && rightx != 0f && righty != 0f && leftx != 0f
					&& lefty != 0f) {

				clapped = ((float) Math.abs(rightx - leftx) < 50f && 
						(float) Math.abs(righty - lefty) < 50f);
			}

			if (clapped) {

				count++;
				//a random chime for when the clap motion is detected
				if (count == 1) {
					String random_chime = soundSelection[randomize(0,soundSelection.length - 1)];
					
					playChime(random_chime, player);

				}
				//after some time after the clap the shadow of the user is shown in the back
				else if (count == 15) {

					for (int i = 0; i < userMap.length; i++) {
						if (userMap[i] != 0) {
							shadowImage.pixels[i] = color(red(forestImage.pixels[i]),
									green(forestImage.pixels[i]),
									blue(forestImage.pixels[i]));
						}
					}
					//reset counter and clapped boolean value
					count = 0;
					clapped = false;
				}
			}

			resultImage.updatePixels();
			image(resultImage, 0, 0);
			
			int fx, fy, inner_r, r, rRange,fz;

			//Draw fireflies
			for (int firefly_id = 0; firefly_id < n_fireflies; firefly_id++) {

			    //retrieves new x,y coordinates of firefly positions
				fx = fireflyXs.get(firefly_id);
				fy = fireflyYs.get(firefly_id);
				fz = fireflyZs.get(firefly_id);
				//Produces random size of fireflies for each frame to create blink effect
				rRange = randomize(10, 13);
				inner_r = randomize(rRange - 9, rRange - 6); //for inner circle of firefly
				r = randomize(rRange, rRange + 5); //for outer halo of firefly

				drawFirefly(firefly_id, fx, fy, r, inner_r);

				//if firefly touches shadow
				if (isTouchingShadow(fx, fy, r)) {

				    //add coordinate of this firefly to these arraylist
					touchedXs_shadow.add(fx);
					touchedYs_shadow.add(fy);

					// remove the touched firefly and put a new falling firefly on screen
					fx = randomize(0, width);
					fy = 0;
					fz = randomize(0, eyez);
				}

				//if firefly touches human body
				if (isTouchingBody(fx, fy, r)) {
					
				    //if firefly is embedded in body
					if (isInBody(fx, fy, r)) {
						fy -= 5; // float to the top
					}

				}

				// if firefly does not reach the end of screen yet
				else if (fy <= height) {

					fy += randomize(1, 5); // fall at a random speed

				}
				// if firefly reaches the end of screen
				else {
				    //reset coordinates to fall from top again
				    fx = randomize(0, width); 
				    fy = 0; 
				}

				//update coordinate values in arraylist
				fireflyXs.set(firefly_id, fx);
				fireflyYs.set(firefly_id, fy);
				fireflyZs.set(firefly_id, fz);
			}

		       	//Draw pixels on screen
			for (int i = 0; i < userMap.length; i++) {

			//Decay user shadow for each firefly that touches the shadow
				if (touchedXs_shadow.size() > 0) {
					decayOnePixelOfShadow(i);
				}

				paintEachPixel(i);
			}

			//to avoid memory overflow
			touchedXs_shadow.clear();
			touchedYs_shadow.clear();
			camera(width/2, height/2, (height/2) / tan(PI/6)+eyez, // eyeX, eyeY, eyeZ
					  width/2+centerx, height/2, centerz, // centerX, centerY, centerZ
				         0.0f, 1.0f, 0.0f); // upX, upY, upZ
				  System.out.println((height/2) / tan(PI/6)+eyez);
				  eyez += 1;
			 redraw();
		}
		
	}

	public void threedspace(){
		 lights();
		  
		  // box setup
		 
		  stroke(255);
		 
		  // background square
		 
		  line(0, 0, -2000, width, 0, -2000);
		  line(0, 0, -2000, 0, height, -2000);
		  line(0, height, -2000, width, height, -2000);
		  line(width, height, -2000, width, 0, -2000);
		 
		  // perspective lines
		 
		  line(0, 0, -2000, 0, 0, 0);
		  line(width, 0, -2000, width, 0, 0);
		  line(0, height, -2000, 0, height, 0);
		  line(width, height, -2000, width, height, 0);
		 
		 
		  // inital ball set up
		  //image(img, 0,0,10,10);
			
		  translate (width/2, height/2, (height/2) / -500);
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
		
		  
		 count2 ++;
			  camera(width/2, height/2, (height/2) / tan(PI/6)+eyez, // eyeX, eyeY, eyeZ
					  width/2+centerx, height/2, centerz, // centerX, centerY, centerZ
				         0.0f, 1.0f, 0.0f); // upX, upY, upZ
				  System.out.println((height/2) / tan(PI/6)+eyez);
				  eyez += 1;
	}
	/**Paint one pixel of screen
	 *
	 *@param i index of pixel in userMap to be painted
	 */
	public void paintEachPixel(int i) {
		
		// if the pixel is part of the user or shadow
		if (userMap[i] != 0
				|| shadowImage.pixels[i] != color(0,0,0)) {

			if (userMap[i] != 0) {
				//draw moving user
				resultImage.pixels[i] = color(255, 255, 255);
			} else {

				//draw shadow
				if (i < forestImage.pixels.length) {
					resultImage.pixels[i]=shadowImage.pixels[i] ;

				} else {
					resultImage.pixels[i] = color(0, 0, 0);
				}

			}

		}
		// if pixel is part of the background
		else {
			
			resultImage.pixels[i] = color(0, 0, 0); // backgroundImage.pixels[i];
		}
	}

	/**Create decay effect on shadow
	 *
	 *@param i index of shadow image to decay
	 */
	public void decayOnePixelOfShadow(int i) {
		
		//check if the coordinate of each touched firefly coincides with that of shadow pixel
		for (int firefly_id = 0; firefly_id < touchedXs_shadow.size(); firefly_id++) {

			int shadowX = getPoint(i)[0];
			int shadowY = getPoint(i)[1];

			int fireflyX = touchedXs_shadow.get(firefly_id);
			int fireflyY = touchedYs_shadow.get(firefly_id);

			int decay_r =randomize(800, 1000);

			//if the shadow pixel falls in the area to be decayed
			if ((shadowX - fireflyX) * (shadowX - fireflyX) + (shadowY - fireflyY)
					* (shadowY - fireflyY) < decay_r) {

				//change pixel color to background color
				shadowImage.pixels[getLoc(shadowX, shadowY)] = color(0, 0, 0);
			}
		}
	}

	/**Draw a firefly according to input location and size
	 *
	 *@param firefly_id Id of firefly to be drawn
	 *@param x   x value of firefly 
	 *@param y   y value of firefly
	 *@param r   radius of firefly halo
	 *@param inner_r  radius of inner circle of firefly
	 */
	public void drawFirefly(int firefly_id, int x, int y, int r, int inner_r) {

		noStroke();
		
		fill(0, 255, 255);
		ellipse(x, y, inner_r * 2f, inner_r * 2f);
		fill(0, 255, 255, 100);
		ellipse(x, y, (r * 2f), (r * 2f));
		
		//sphere(50);
	}

	/**Add random firefly locations to coordinate arraylists for setup*/
	public void addFireflies() {
		for (int firefly_id = 0; firefly_id < n_fireflies; firefly_id++) {
			fireflyXs.add(randomize(0, width));
			fireflyYs.add(randomize(0, height));
			fireflyZs.add(randomize(0,eyez));
		}
	}

	/**Load forest image for setup*/
	public void loadForestImage() {
		forestImage = loadImage("nightForest.jpg");
		loadPixels();
		forestImage.loadPixels();
	}

	/**Play chime soundtrack
	 *
	 *@param filename String value of chime sound file name 
	 */
	public void playChime(String filename,AudioPlayer player) {
		 player = minim.loadFile(filename);
		player.play();
	}

	/**Checks if firefly is touching shadows
	 *
	 *@param x  x value of firefly touching the shadow
	 *@param y  y value of firefly touching the shadow
	 *@param r  r value of firefly touching the shadow
	 *@return boolean value of whether this firefly is touching the shadow
	 */
	public boolean isTouchingShadow(int x, int y, int r) {
		int i = getLoc(x, y + r);

		if ((y + r) < height && x < width) {
			return (shadowImage.pixels[i] ==color(red(forestImage.pixels[i]),
					green(forestImage.pixels[i]),
					blue(forestImage.pixels[i])));
		}
		return false;
	}


	/**Checks if firefly is touching user body
	 *
	 *@param x  x value of firefly touching the user body
	 *@param y  y value of firefly touching the user body
	 *@param r  r value of firefly touching the user body
	 *@return boolean value of whether this firefly is touching the user body
	 */
	public boolean isTouchingBody(int x, int y, int r) {
		int i = getLoc(x, y + r);

		if ((y + r) < height && x < width) {
			return (userMap[i] != 0);
		}
		return false;
	}

	/**Checks if firefly is embedded in user body
	 *
	 *@param x  x value of firefly in the user body
	 *@param y  y value of firefly in the user body
	 *@param r  r value of firefly in the user body
	 *@return boolean value of whether this firefly is in the user body
	 */
	public boolean isInBody(int x, int y, int r) {
		if ((y - r) > 0) {
			return (userMap[getLoc(x, y - r)] != 0);
		}
		return false;
	}

	/**Converts x,y coordinate to index value of usermap array */
	public int getLoc(int x, int y) {
		int loc;

		loc = x + y * width;

		return loc;
	}

	/**Translate userMap array index value to x, y coordinate values*/
	public int[] getPoint(int loc) {

		int x = (loc % width);
		int y = (int) Math.ceil(loc / width);

		int[] arr = { x, y };
		return arr;
	}

	/**Produces random number of integers*/
	public int randomize(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}
	public void keyPressed() {
		  if(key==CODED){
		    if(      keyCode==LEFT){  centerx-=50;
		    } if(keyCode==RIGHT){ centerx+=50;
		    } if(keyCode==UP){    eyez-=50;
		    } if(keyCode==DOWN){  eyez+=50;
		    } 
		  redraw();
		}

}}