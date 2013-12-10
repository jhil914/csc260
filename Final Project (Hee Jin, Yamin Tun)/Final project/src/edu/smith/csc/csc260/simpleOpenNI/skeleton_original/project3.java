package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class project3 extends SmithPApplet {

	private static final long serialVersionUID = 1L;

	public SimpleOpenNI simpleOpenNI;

	ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>>();

	BodypartTrackingSprite left_hand, right_hand;

	int[] userMap;

	PImage backgroundImage, shadowImage, resultImage, forestImage;

	int userID, count;

	boolean tracking = false;

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

	// int count = 0;

	boolean[] gather = new boolean[myLeavestotal];

	Point[] gatherp = new Point[myLeavestotal];

	public void setup() {

		size(640, 480);

		simpleOpenNI = new SimpleOpenNI(this);

		if (simpleOpenNI.isInit() == false) {

			System.out

					.println("Can't init SimpleOpenIN, maybe the camera is not connected!");

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

		for (int i = 0; i < xpos.length; i++) {

			xpos[i] = 0;

			ypos[i] = 0;

		}

		// makes the leaves

		for (int i = 0; i < myLeaves.length; i++) {

			myLeaves[i][0] = new Leaves(width / 2, height / 2);

			myLeaves[i][0].setID(0);

		}

		for (int i = 0; i < myLeaves.length; i++) {

			myLeaves[i][1] = new Leaves(100, 100);

			myLeaves[i][1].setID(1);

		}

		for (int i = 0; i < myLeaves.length; i++) {

			myLeaves[i][2] = new Leaves(400, 400);

			myLeaves[i][2].setID(2);

		}

		for (int i = 0; i < gather.length; i++) {

			gather[i] = false;

			gatherp[i] = new Point(300, 300);

		}

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

		PImage rgbImage = simpleOpenNI.rgbImage();

		image(rgbImage, width, 0);

		if (tracking) {

			// ask kinect for bitmap of user pixels

			loadPixels();

			userMap = simpleOpenNI.userMap();

			// captures still image after the user claps

			// super.draw();

			// variables for clap motion

			super.draw();

			resultImage.updatePixels();

			image(resultImage, 0, 0);

			float lx = left_hand.getLocation().getX();

			float ly = left_hand.getLocation().getY();

			float rx = right_hand.getLocation().getX();

			float ry = right_hand.getLocation().getY();

			println(lx + " " + ly + " " + rx + " " + ry);

			

			for (int j = 0; j < myLeavestotal; j++) {

				for (int i = 1; i < myLeaves.length; i++) {
					myLeaves[i][j].mousenear(10, rx, ry, lx, ly);
					if (myLeaves[i][j].inRange == true) {

						// System.out.println(j+" "+gather[j]);

						// if (myLeaves[i][j].inRange == true) {

						// System.out.println(j);

						if (myLeaves[i][j].colorstatus().equals("green")) {

							

							if (gather[j] == true) {

								for (int k = 0; k < gather.length; k++) {

									gather[k] = false;

									// gatherp[i] = new Point(300,300);

								}

							}

							// Draw an ellipse for each element in the arrays.

							// Color and size are tied to the loop's counter: i.
							myLeaves[i][j].storeloc(myLeaves[i][j].x,
									myLeaves[i][j].y);
							if(myLeaves[i][j].LinRange){
							myLeaves[i][j].follow2(new Point(left_hand.getLocation().getX(), left_hand.getLocation().getY()));
							}if(myLeaves[i][j].RinRange){
								myLeaves[i][j].follow2(new Point(right_hand.getLocation().getX(), right_hand.getLocation().getY()));
							}
							//myLeaves[i][j].setinitial();
							//myLeaves[i][j].update();
							myLeaves[i][j].draw();

						

							// System.out.println(myLeaves[i][j].x);

						} else {

							myLeaves[i][j].outsidewindow();

							// System.out.println(myLeaves[i][j].outside);

							if (myLeaves[i][j].outside) {

								// store the j so that with that j every i gets

								// the same random point

								// System.out.println("outside");

								myLeaves[i][j].storeloc(myLeaves[i][j].x,

								myLeaves[i][j].y);

								if (gather[j] == false) {

									// System.out.println(j);

									gather[j] = true;

									gatherp[j] = new Point(random(10, width-10),random(10, height-10));

								}

								myLeaves[i][j].gather(gatherp[j]);

								// System.out.println(j+" "+myLeaves[i][j].count+" ");

								myLeaves[i][j].circlenear(10, gatherp[j]);

								if(myLeaves[i][j].cirinRange){
									myLeaves[i][j].mousenearfalse();
									myLeaves[i][j].outside = false;
								//	gather[j] = false;
									myLeaves[i][j].count = 0;
									myLeaves[i][j].count2 = 0;
									myLeaves[i][j].setinitial();
									System.out.println("inhere");
								}

							} else {

								myLeaves[i][j].explode();

							}

							myLeaves[i][j].draw();

						}

					} else {

						following = false;

						myLeaves[i][j].update();

						myLeaves[i][j].draw();

					}

				}

				/**
				 * 
				 * for (int i=0; i < myLeaves.length; i++) {
				 * 
				 * myLeaves[i].explode(); myLeaves[i].draw(); }
				 */

			}

			// Draw pixels on screen

			for (int i = 0; i < userMap.length; i++) {

				paintEachPixel(i);

			}

		}

	}

	public PVector getHandPoint(Leaves leaves) {

		PVector handpt = new PVector(0, 0);

		if (leaves.LinRange) {

			float lx = left_hand.getLocation().getX();

			float ly = left_hand.getLocation().getY();

			handpt.set(lx, ly);

		} else if (leaves.RinRange) {

			float rx = right_hand.getLocation().getX();

			float ry = right_hand.getLocation().getY();

			handpt.set(rx, ry);

		}

		return (handpt);

	}

	/**
	 * 
	 * Paint one pixel of screen
	 * 
	 * 
	 * 
	 * @param i
	 * 
	 *            index of pixel in userMap to be painted
	 */

	public void paintEachPixel(int i) {

		// if the pixel is part of the user or shadow

		if (userMap[i] != 0) {

			// draw moving user

			resultImage.pixels[i] = color(255, 255, 255);

		} else {

			// draw shadow

			resultImage.pixels[i] = color(0, 0, 0);

		}

		// if pixel is part of the background

	}

	public void reset() {

		for (int j = 0; j < myLeavestotal; j++) {

			for (int i = 0; i < myLeaves.length; i++) {

				// myLeaves[i][j].restore();

				myLeaves[i][j].mousenearfalse();

				myLeaves[i][j].outside = false;

				myLeaves[i][j].setinitial();

			}

		}

	}

	public void mouseClicked() {

		for (int j = 0; j < myLeavestotal; j++) {

			for (int i = 0; i < myLeaves.length; i++) {

				myLeaves[i][j].restore();

				myLeaves[i][j].mousenearfalse();

			}

		}

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

		boolean outside = false;

		int range = -1;

		float R = 200;

		float G = 0;

		int colorchange = -1;

		float accelX, accelY;

		float springing = .0009f, damping = .98f;

		boolean LinRange, RinRange = false;
		boolean inRange = false;
		int initx;

		int inity;

		int storedx;

		int storedy;

		PVector startP;

		PVector endP;

		Point randompoint;

		int ID;

		int count;
		int count2;

		boolean cirinRange = false;

		Leaves(int firstx, int firsty) {

			initialx = firstx;

			initialy = firsty;

			x = initialx;// starts the leaves towards the center of the screen

			y = initialy;

			initx = (int) (initialx);

			inity = (int) (initialy);

			count = 0;
			count2 = 0;
			// System.out.println(initialx + " " + initialy);

		}

		void update() {

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

		}
		public void follow2(Point g){
			startP = new PVector(storedx, storedy);
			endP = new PVector(g.getX(), g.getY());
			if (count2 < 50) {
				count2++;
			} else {
				count2 = 0;
			}
			float progress = map(count2, 0, width, 0, 1);

			float px = lerp(startP.x, endP.x, progress); // we can calculate x
															// and y separately
			float py = lerp(startP.y, endP.y, progress);
			// System.out.println(count);

			x = (int) (px+random(-8,8));
			y = (int) (py +random(-8,8));
			System.out.println(ID+" "+x+" "+y);
		}
		public boolean mousenear(int r, float rightx, float righty,

		float leftx, float lefty) {

			if (Math.abs(leftx - initialx) <= r

			&& Math.abs(lefty - initialy) <= r) {

				LinRange = true;
				inRange = true;
				println("left");

			} else if (Math.abs(rightx - initialx) <= r

			&& Math.abs(righty - initialy) <= r) {

				RinRange = true;
				inRange = true;
				println("right");

			}

			return (LinRange || RinRange);

		}

		public void setinitial() {

			initialx = x;

			initialy = y;

		}

		public void circlenear(int r, Point p) {

			cirinRange = false;

			if (Math.abs(p.getX() - x) <= r && Math.abs(p.getY() - y) <= r) {

				cirinRange = true;

			}

		}

		public void setID(int id) {

			ID = id;

		}

		public void mousenearfalse() {

			inRange = false;
			LinRange = false;
			RinRange = false;
		}

		public void follow() {

			float noiseval = noise(x * noisescale, y * noisescale);

			for (int i = 0; i < xpos.length; i++) {

				// Draw an ellipse for each element in the arrays.

				// Color and size are tied to the loop's counter: i.

				initialx = (int) (xpos[i] + noiseval * 5);

				initialy = (int) (ypos[i] + noiseval * 5);

			}

		}

		public boolean outsidewindow() {

			if (x < 0 || x > width || y < 0 || y > height) {

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

		public void storeloc(int locx, int locy) {

			storedx = locx;

			storedy = locy;

		}

		public void gather(Point g) {

			startP = new PVector(storedx, storedy);

			endP = new PVector(g.getX(), g.getY());

			if (count < width) {

				count++;

			} else {

				count = 0;

			}

			float progress = map(count, 0, width, 0, 1);

			float px = lerp(startP.x, endP.x, progress); // we can calculate x

			// and y separately

			float py = lerp(startP.y, endP.y, progress);

			// System.out.println(count);

			x = (int) px;

			y = (int) py;

			System.out.println(ID + " " + x + " " + y);

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

			x = initx;

			y = inity;

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

	}

}
