package edu.smith.csc.csc260.simpleOpenNI.skeleton;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PImage;
import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Point;

public class Pile extends SmithPApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean isTouching = false;
	boolean isInBody = false;
	boolean isOnLeftSlope1 = false;
	boolean isOnLeftSlope2 = false;

	boolean isOnRightSlope1 = false;
	boolean isOnRightSlope2 = false;

	int n_petals = 10;
	int fallingspeed = 7;
	/*
	 * int[] Xs = new int[n_petals]; int[] Ys = new int[n_petals];
	 */

	ArrayList<Integer> Xs = new ArrayList<Integer>();
	ArrayList<Integer> Ys = new ArrayList<Integer>();

	// Point[] Centers = new Point[2];

	ArrayList<Integer> touchedXs = new ArrayList<Integer>();
	ArrayList<Integer> touchedYs = new ArrayList<Integer>();

	int n_touchedPetals = 0;

	SimpleOpenNI kinect;
	// based on Greg's Book Making things see.
	boolean tracking = false;
	int userID;

	int[] userMap;

	// declare our images
	PImage backgroundImage;
	PImage resultImage;

	float timer = 0;

	public void setup() {
		super.setup();

		size(640 * 2, 480);
		kinect = new SimpleOpenNI(this);
		if (kinect.isInit() == false) {
			println("Can't init SimpleOpenNI, maybe the camera is not connected!");
			exit();
			return;
		}

		// enable depthMap generation
		kinect.enableDepth();

		// enable skeleton generation for all joints
		kinect.enableUser();
		// enable color image from the Kinect
		kinect.enableRGB();
		// enable the finding of users but dont' worry about skeletons

		// turn on depth/color alignment
		kinect.alternativeViewPointDepthToImage();
		// create a buffer image to work with instead of using sketch pixels
		resultImage = new PImage(640, 480, RGB);

		for (int petal_id = 0; petal_id < n_petals; petal_id++) {

			Xs.add(randomize(0, 640));
			Ys.add(0);

		}

		if (tracking) {

			n_touchedPetals = touchedXs.size();

			// //////////
			// ask kinect for bitmap of user pixels
			loadPixels();
			userMap = kinect.userMap();
		}

	}

	public void draw() {
		kinect.update();
		// get the Kinect color image
		PImage rgbImage = kinect.rgbImage();

		image(rgbImage, 640, 0);
		if (tracking) {

			n_touchedPetals = touchedXs.size();

			// //////////
			// ask kinect for bitmap of user pixels
			loadPixels();

			userMap = kinect.userMap();

			/*
			 * timer+=0.00001f; if(timer==0.0001f){
			 * 
			 * }
			 */

			// //////

			for (int i = 0; i < userMap.length; i++) {

				// if the pixel is part of the user
				if (userMap[i] != 0) {
					// set the pixel to the color pixel
					resultImage.pixels[i] = color(0, 0, 0); // rgbImage.pixels[i];

				} else {
					// set it to the background
					resultImage.pixels[i] = color(255, 255, 255); // backgroundImage.pixels[i];
				}

			}

			// update the pixel from the inner array to image
			resultImage.updatePixels();
			image(resultImage, 0, 0);

			ArrayList<Integer> touchedPetalIDs = new ArrayList<Integer>();
			for (int petal_id = 0; petal_id < n_petals; petal_id++) {

				/*
				 * int x = Xs[petal_id]; int y = Ys[petal_id];
				 */

				int x = Xs.get(petal_id);
				int y = Ys.get(petal_id);

				int r = 10;
				fill(255, 0, 0);
				ellipse(x, y, r * 2, r * 2);

				int i = getLoc(x, y);

				if ((y + r) < height && x < width / 2) {
					println((y + r) + " " + x);
					isTouching = (i <= userMap.length)
							&& (userMap[getLoc(x, y + r)] == 1);
				}

				if ((y - r) > 0) { // && (y-r) <= height

					// println ( (y - r) );

					isInBody = (userMap[getLoc(x, y - r)] == 1);

					
				}

				if (isTouching) {
				

					// float to the top
					if (isInBody) {
						y -= 1;
					}
					touchedXs.add(x);
					touchedYs.add(y);
				}

				else if (y <= height) {
					y += fallingspeed;
					// Centers[petal_id] = new Point(x, y);

				} else {
					// Centers[petal_id] = new Point(x, 0);
					x = randomize(0, width / 2);
					y = 0;
				}

				Xs.set(petal_id, x);
				Ys.set(petal_id, y);

			}

			/*
			 * for(Integer i: touchedPetalIDs){ Xs.remove(i); Ys.remove(i); }
			 */

			/*
			 * translate(width / 4, height / 2); drawLegs(); translate(-width /
			 * 4, -height / 2);
			 */

			/*
			 * int radius = 20;
			 * 
			 * for (int i = 0; i < userMap.length; i++) {
			 * 
			 * 
			 * for (int tpetal_id = 0; tpetal_id < n_touchedPetals; tpetal_id++)
			 * {
			 * 
			 * int bodyX = getPoint(i)[0]; int bodyY = getPoint(i)[1];
			 * 
			 * int petalX = touchedXs.get(tpetal_id); int petalY =
			 * touchedYs.get(tpetal_id);
			 * 
			 * if ((bodyX - petalX) * (bodyX - petalX) + (bodyY - petalY) *
			 * (bodyY - petalY) < radius* radius) { userMap[getLoc(bodyX,
			 * bodyY)] = 0; } }
			 * 
			 * // if the pixel is part of the user if (userMap[i] != 0) { // set
			 * the pixel to the color pixel resultImage.pixels[i] = color(0, 0,
			 * 0); // rgbImage.pixels[i];
			 * 
			 * } else { // set it to the background resultImage.pixels[i] =
			 * color(255, 255, 255); // backgroundImage.pixels[i]; }
			 * 
			 * }
			 */
			// EEERORR after balls reach to the end
			/*
			 * touchedXs.clear(); touchedYs.clear();
			 */
			n_petals = Xs.size();
		}

	}

	/** Draw randomly-spaced longer legs for spider */
	public void drawLegs() {
		float angle = 0f;
		float spiderRad = 0;// 100f;

		while (angle < 2f * PI) {
			strokeWeight(2f);
			float lenLine = 100f;
			angle += 0.01; // 0 to 45 deg
			line(spiderRad * cos(angle), spiderRad * sin(angle),
					(spiderRad + lenLine) * cos(angle), (spiderRad + lenLine)
							* sin(angle));
		}

	}

	public boolean intToBoolean(int i) {

		return (i == 1);
	}

	public int getNewLoc(int loc) {

		int w = width / 2;
		int h = height;

		int loc_new;

		int x = (loc % w);
		int y = (int) Math.ceil(loc / w);

		loc_new = x + y * w;

		return loc_new;
	}

	public int getLoc(int x, int y) {
		int loc_new;

		int w = width / 2;
		int h = height;

		loc_new = x + y * w;

		return loc_new;
	}

	public int[] getPoint(int loc) {

		int w = width / 2;
		int h = height;

		int loc_new;

		int x = (loc % w);
		int y = (int) Math.ceil(loc / w);

		int[] arr = { x, y };
		return arr;
	}

	public boolean isTouchingBody() {
		return true;
	}

	public void onNewUser(SimpleOpenNI curContext, int userId) {
		userID = userId;
		tracking = true;
		println("tracking - userId: " + userId);
		// curContext.startTrackingSkeleton(userId);
	}

	public void onLostUser(SimpleOpenNI curContext, int userId) {
		println("onLostUser - userId: " + userId);
	}

	public void onVisibleUser(SimpleOpenNI curContext, int userId) {
		// println("onVisibleUser - userId: " + userId);
	}

	public int randomize(int min, int max) {

		// return min + (float) Math.random() * max;
		return min + (int) (Math.random() * ((max - min) + 1));
	}
}
