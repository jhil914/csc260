package edu.smith.csc.csc260.simpleOpenNI.skeleton;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PImage;
import processing.core.PShape;
import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class BodypartTrackingApplet extends SmithPApplet {
	private static final long serialVersionUID = 1L;
	public SimpleOpenNI simpleOpenNI;
	ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>>();
	BodypartTrackingSprite bbb;
	BodypartTrackingSprite hhh;
	BodypartTrackingSprite rrr;
	BouncingSprite bs2;
	ArrayList<BouncingSprite> s = new ArrayList<BouncingSprite>();
	boolean isit = false;
	float[] xpos1 = new float[50];
	float[] ypos1 = new float[50];
	public int dim;
	float[] SunLight_color = { 60f, 60f, 10f, 60f };
	int[] userMap;
	PImage backgroundImage;
	PImage resultImage;
	PImage body;
	boolean tracking = false;
	int userID;
	PImage anotherImage;
	PImage um;
	float umx;
	float umy;
	boolean stop = false;
	int[] userMapcapture;
	int count;
	// Circles[] c;
	boolean isTouching = false;
	int n_petals = 10;
	boolean isInBody = false;
	boolean isOnLeftSlope1 = false;
	boolean isOnLeftSlope2 = false;

	boolean isOnRightSlope1 = false;
	boolean isOnRightSlope2 = false;

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

	ArrayList<Petals> e = new ArrayList<Petals>();
	float y = -5;

	float r = random(5, 20);

	float rr = random(5, 20);

	float x = random(500);

	float w = 397 - y;

	public void setup() {
		size(640 * 2, 480);// context = new SimpleOpenNI(this);
		simpleOpenNI = new SimpleOpenNI(this);
		if (simpleOpenNI.isInit() == false) {
			System.out
					.println("Can't init SimpleOpenIN, maybe the camera is not connected!");
			exit();
			return;
		}
		um = loadImage("umbrella.png");
		/**
		 * for(int i=0; i<50; i++){ e.add(new Petals());
		 * 
		 * e.get(i).x= random(500); e.get(i).y = random(0,-5);
		 * e.get(i).r=random(5,20); e.get(i).rr=random(5,20); } // enable
		 * skeleton generation for all joints /** c = new Circles [510]; for
		 * (int i=0; i<510; i++) { c[i] = new Circles(); c[i].x = random(500);
		 * c[i].y = random(60); c[i].w = random(5, 20); c[i].h = random(5, 20);
		 * }
		 * 
		 * background(0,0,0);
		 * 
		 * stroke(0,0,255); strokeWeight(3); smooth();
		 */
		smooth();
		noStroke();
		simpleOpenNI.setMirror(false);
		count = 0;
		simpleOpenNI.enableDepth(); // needs to be enable for skeletons to work
									// right

		simpleOpenNI.enableUser();
		simpleOpenNI.enableRGB();
		simpleOpenNI.alternativeViewPointDepthToImage();
		resultImage = new PImage(640, 480, RGB);
		anotherImage = new PImage(640, 480, RGB);
		body = new PImage(640, 480, RGB);
		for (int petal_id = 0; petal_id < n_petals; petal_id++) {

			Xs.add(randomize(0, 640));
			Ys.add(0);

		}

	}

	public void onNewUser(SimpleOpenNI curContext, int userId) {
		userID = userId;
		tracking = true;
		simpleOpenNI.startTrackingSkeleton(userId);

		Vector<BodypartTrackingSprite> newTracks = new Vector<BodypartTrackingSprite>();

		
		rrr = new BodypartTrackingSprite(this, userId,
				SimpleOpenNI.SKEL_RIGHT_HAND);
		addSprite(rrr);
		newTracks.add(rrr);

		hhh = new BodypartTrackingSprite(this, userId,
				SimpleOpenNI.SKEL_LEFT_HAND);
		isit = true;
		addSprite(hhh);
		newTracks.add(hhh);

		tracks.put(userId, newTracks);

	}

	public void onLostUser(SimpleOpenNI curContext, int userId) {

		Vector<BodypartTrackingSprite> btsVec = tracks.get(userId);
		for (BodypartTrackingSprite bts : btsVec) {
			removeSprite(bts);

		}

	}

	public void draw() {

		simpleOpenNI.update();
		PImage rgbImage = simpleOpenNI.rgbImage();
		// image(simpleOpenNI.userImage(), 0, 0);
		image(rgbImage, 640, 0);
		// super.draw();
		if (tracking) {
			// ask kinect for bitmap of user pixels
			n_touchedPetals = touchedXs.size();
			loadPixels();
			userMap = simpleOpenNI.userMap();
//captures still image when the user stops
			if (xpos1.length == 50) {
				if (Math.abs(xpos1[49] - xpos1[48]) < 1
						&& Math.abs(ypos1[49] - ypos1[48]) < 1) {
					if (count < 30){
						fill(0, 0, 255, 255);
						ellipse(50, 50, 20, 20);
						System.out.println("stop");
						stop = true;
						// loadPixels();
						// userMapcapture = simpleOpenNI.userMap();
						for (int i = 0; i < userMap.length; i++) {
							if (userMap[i] != 0) {
								anotherImage.pixels[i] = color(0, 0, 0);
							} 
						}
					}
				}
			}
			count++;
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

		int radius = 20;

		for (int i = 0; i < userMap.length; i++) {

			for (int tpetal_id = 0; tpetal_id < n_touchedPetals; tpetal_id++) {

				int bodyX = getPoint(i)[0];
				int bodyY = getPoint(i)[1];

				int petalX = touchedXs.get(tpetal_id);
				int petalY = touchedYs.get(tpetal_id);

				if ((bodyX - petalX) * (bodyX - petalX) + (bodyY - petalY)
						* (bodyY - petalY) < radius * radius) {
					anotherImage.pixels[getLoc(bodyX, bodyY)] = color(255, 255,
							255);
				}
			}
			// if the pixel is part of the user

			if (userMap[i] != 0 || anotherImage.pixels[i] == color(0, 0, 0)) {
				// set the pixel to the color pixel
				resultImage.pixels[i] = color(0, 0, 0); // rgbImage.pixels[i];
				// anotherImage.pixels[i] = color(0,0,0);
			

			} else {
				// set it to the background
				resultImage.pixels[i] = color(255, 255, 255); // backgroundImage.pixels[i];
				// anotherImage.pixels[i] = color(255,255,255);
			}
		}
		n_petals = Xs.size();
		// update the pixel from the inner array to image

		
//umbrella code
		umx = hhh.getLocation().getX();
		umy = hhh.getLocation().getY();

		image(um, umx - width / 10, umy - height / 3, width / 5, height / 3);
		
		}
		
		
	}

	public void collide() {

		// System.out.println(hhh.getLocation().getX());
		

		for (int i = 0; i < e.size(); i++) {
			float petalx = e.get(i).x;
			float petaly = e.get(i).y;
			if (petalx < umx + width / 10 && petalx > umx - width / 10
					&& petaly > umy - height / 3) {
				e.get(i).y = random(-5, 0);
				e.get(i).x = random(10, 590);
			}
		}
	}

	public void drawSunLight() {
		// System.out.println("drawing sunlight");
		float r = 0;
		float theta = 0;
		// Shift array values
		if (isit) {
			for (int i = 0; i < xpos1.length - 1; i++) {
				// Shift all elements down one spot.
				// xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at
				// the
				// second to last element.
				xpos1[i] = xpos1[i + 1];
				ypos1[i] = ypos1[i + 1];
			}

			// New location
			xpos1[xpos1.length - 1] = hhh.getLocation().getX(); // Update the
																// last spot in
																// the array
			// with the mouse location.
			ypos1[ypos1.length - 1] = hhh.getLocation().getY();

			for (int i = 1; i < xpos1.length; i = i * 2) {

				for (int j = 1; j < xpos1.length; j++) {

					float x = cos(theta);// equations that creates the parabolic
											// movement of the sprites

					float y = 4 * sin(theta);

					noStroke();

					fill((int) SunLight_color[0] - j * 100 / xpos1.length,
							(int) SunLight_color[1] - j * 100 / xpos1.length,
							(int) SunLight_color[2] - j * 100 / xpos1.length,
							60);
					// Draw an ellipse for each element in the arrays.
					ellipse(xpos1[j] * x, ypos1[j] * y, i * 80 / xpos1.length,
							i * 80 / xpos1.length);
					theta += j * 3;

					r = (float) (r + 0.05 * j);
				}

			}
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

	public int randomize(int min, int max) {

		// return min + (float) Math.random() * max;
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	class Petals {
		float x, y;
		float r, rr;

		void show() {
			fill(255, 20, 147, 150);
			ellipse(x, y, r, rr);
		}

		void move() {
			x += random(-7, 7);
			y += random(4, 6);
		}
	}
}