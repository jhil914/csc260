package edu.smith.csc.csc260.simpleOpenNI.skeleton;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PImage;
import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;

import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class BodypartTrackingApplet extends SmithPApplet {
	private static final long serialVersionUID = 1L;

	public SimpleOpenNI simpleOpenNI;
	ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>>();

	BodypartTrackingSprite left_hand;
	BodypartTrackingSprite right_hand;
	BouncingSprite bs;
	float[] SunLight_color = { 60f, 60f, 10f, 60f };// ????
	float[] xpos1 = new float[50];// ????
	float[] ypos1 = new float[50];// ????
	int[] userMap;

	PImage backgroundImage, anotherImage, resultImage, body, um;

	int userID, count;
	
	// rain parameters
	public final int n_petals = 10;
	int fallingspeed=randomize(3,9);

	boolean tracking = false;
	boolean isit = false; // ????
	boolean clapped = false;
	boolean isTouchingShadow = false;
	boolean isTouchingBody = false;
	boolean isInBody = false;

	ArrayList<Integer> touchedXs_shadow = new ArrayList<Integer>();
	ArrayList<Integer> touchedYs_shadow = new ArrayList<Integer>();

	ArrayList<Integer> petalXs = new ArrayList<Integer>();
	ArrayList<Integer> petalYs = new ArrayList<Integer>();

	
	int r;

	/*
	 * float y = -5;//???? float r = random(5, 20);//???? float rr = random(5,
	 * 20);//???? float x = random(500);//???? float w = 397 - y;//????
	 */
	public void setup() {
		size(640 , 480);
		simpleOpenNI = new SimpleOpenNI(this);

		if (simpleOpenNI.isInit() == false) {
			System.out
					.println("Can't init SimpleOpenIN, maybe the camera is not connected!");
			exit();
			return;
		}

		

		count = 0;

		smooth();
		noStroke();
		simpleOpenNI.setMirror(true);
		simpleOpenNI.enableDepth(); // needs to be enable for skeletons to work
									// right
		simpleOpenNI.enableUser();
		simpleOpenNI.enableRGB();
		simpleOpenNI.alternativeViewPointDepthToImage();

		resultImage = new PImage(width,height , RGB);
		anotherImage = new PImage(width, height, RGB);
		
		for (int petal_id = 0; petal_id < n_petals; petal_id++) {
			petalXs.add(randomize(0, 640));
			petalYs.add(randomize(0, height));
		}
		
		println(userID);
		
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
		isit = true;
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
		//image(rgbImage, 640, 0);
		if (tracking) {

			// ask kinect for bitmap of user pixels
			loadPixels();
			userMap = simpleOpenNI.userMap();

			// captures still image after the user claps
			super.draw();
			

			float rightx = right_hand.getLocation().getX();
			float righty = right_hand.getLocation().getY();
			float leftx = left_hand.getLocation().getX();
			float lefty = left_hand.getLocation().getY();

			//fill(255,0,0);
			///ellipse(leftx,lefty,40f,40f);
		//	println(rightx+" " +leftx+" "+(float)Math.abs(rightx - leftx));

			if (!clapped && rightx != 0f && righty != 0f && leftx != 0f
					&& lefty != 0f) {

				clapped = ( (float)Math.abs(rightx - leftx) < 50f && (float)Math.abs(righty
						- lefty) < 50f);
			}

			if (clapped) {

				count++;
				// System.out.println("stop");

				if (count == 15) {
					// fill(0, 0, 255, 255);
					// ellipse(50, 50, 20, 20);

					// println("Catched!");

					for (int i = 0; i < userMap.length; i++) {
						if (userMap[i] != 0) {
							anotherImage.pixels[i] = color(0, 255, 0);
						}
					}
					count = 0;
					clapped = false;
				} else if (count % 3 == 0) {
					java.awt.Toolkit.getDefaultToolkit().beep();
				}

			}

			resultImage.updatePixels();

			image(resultImage, 0, 0);

			int x, y;
			for (int petal_id = 0; petal_id < n_petals; petal_id++) {

				x = petalXs.get(petal_id);
				y = petalYs.get(petal_id);

				noStroke();
				// int range=randomize();

				// draw spider body
				int inner_r = randomize(3, 6);
				int r = randomize(10, 15);

				fill(0, 255, 255);
				ellipse(x, y, inner_r * 2f, inner_r * 2f);
				fill(0, 255, 255, 100);
				ellipse(x, y, (r * 2f), (r * 2f));

				
				int i = getLoc(x, y + r);

				if ((y + r) < height && x < width ) {
					isTouchingBody = (userMap[i] !=0);

					// when circle touches both body and shadow
					// if(!isTouchingBody){
					isTouchingShadow = (anotherImage.pixels[i] == color(0, 255,
							0));
					// }
				}

				if ((y - r) > 0) {
					isInBody = (userMap[getLoc(x, y - r)] !=0);
				}

				if (isTouchingShadow) {

					touchedXs_shadow.add(x);
					touchedYs_shadow.add(y);

					// removing the touched petal and putting new petal
					x = randomize(0, width );
					y = 0;
				}
				
				if (isTouchingBody) {

					// float to the top
					if (isInBody) {
						y -= 5;
					}

				}

				//if firefly does not reach the end of screen
				else if (y <= height) {
					y += fallingspeed;

				} 
				//if firefly reaches the end of screen
				else {
					x = randomize(0, width);
					y = 0;
				}

				petalXs.set(petal_id, x);
				petalYs.set(petal_id, y);

			}


			for (int i = 0; i < userMap.length; i++) {

				if (touchedXs_shadow.size() > 0) {

					for (int tpetal_id = 0; tpetal_id < touchedXs_shadow.size(); tpetal_id++) {

						int bodyX = getPoint(i)[0];
						int bodyY = getPoint(i)[1];

						int petalX = touchedXs_shadow.get(tpetal_id);
						int petalY = touchedYs_shadow.get(tpetal_id);

						if ((bodyX - petalX) * (bodyX - petalX)
								+ (bodyY - petalY) * (bodyY - petalY) < random(300,400)) {

							anotherImage.pixels[getLoc(bodyX, bodyY)] = color(
									0, 0, 0);
						}
					}
				}
				
				// if the pixel is part of the user
				if (userMap[i] != 0 || anotherImage.pixels[i] == color(0, 255, 0)) {
					if (userMap[i] != 0) {

						// set the pixel to the color pixel
						resultImage.pixels[i] = color(255, 255, 255); // rgbImage.pixels[i];
					} else {
						resultImage.pixels[i] = color(0, 255, 0);
					}

				} else {
					// set it to the background
					resultImage.pixels[i] = color(0, 0, 0); // backgroundImage.pixels[i];
				}
			}

			touchedXs_shadow.clear();
			touchedYs_shadow.clear();
			
		}
		
	}

	
	public int getLoc(int x, int y) {
		int loc_new;

		

		loc_new = x + y * width;

		return loc_new;
	}

	public int[] getPoint(int loc) {

		

		int x = (loc % width);
		int y = (int) Math.ceil(loc / width);

		int[] arr = { x, y };
		return arr;
	}

	public int randomize(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

}