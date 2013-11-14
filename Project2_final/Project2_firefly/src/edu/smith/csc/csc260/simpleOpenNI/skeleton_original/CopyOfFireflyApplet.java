package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PImage;
import SimpleOpenNI.SimpleOpenNI;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import edu.smith.csc.csc260.core.SmithPApplet;

public class CopyOfFireflyApplet extends SmithPApplet {
	private static final long serialVersionUID = 1L;

	public SimpleOpenNI simpleOpenNI;
	ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>>();

	BodypartTrackingSprite left_hand;
	BodypartTrackingSprite right_hand;

	int[] userMap;

	PImage backgroundImage, shadowImage, resultImage, forestImage;

	int userID, count;

	// rain parameters
	final int n_fireflies = 20;

	boolean tracking = false;
	boolean clapped = false;

	ArrayList<Integer> touchedXs_shadow = new ArrayList<Integer>();
	ArrayList<Integer> touchedYs_shadow = new ArrayList<Integer>();

	ArrayList<Integer> fireflyXs = new ArrayList<Integer>();
	ArrayList<Integer> fireflyYs = new ArrayList<Integer>();

	int r;

	final String[] soundSelection = { "Chimes1.mp3", "Chimes2.mp3", "Chimes3.mp3" };

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
		shadowImage = new PImage(width, height, RGB);

		addFireflies();

		count = 0;

		loadForestImage();

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
			super.draw();

			float rightx = right_hand.getLocation().getX();
			float righty = right_hand.getLocation().getY();
			float leftx = left_hand.getLocation().getX();
			float lefty = left_hand.getLocation().getY();

			if (!clapped && rightx != 0f && righty != 0f && leftx != 0f
					&& lefty != 0f) {

				clapped = ((float) Math.abs(rightx - leftx) < 50f && 
						(float) Math.abs(righty - lefty) < 50f);
			}

			if (clapped) {

				count++;

				if (count == 1) {
					String random_chime = soundSelection[randomize(0,soundSelection.length - 1)];
					playChime(random_chime);

				}

				else if (count == 15) {

					for (int i = 0; i < userMap.length; i++) {
						if (userMap[i] != 0) {
							shadowImage.pixels[i] = color(red(forestImage.pixels[i]),
									green(forestImage.pixels[i]),
									blue(forestImage.pixels[i]));
						}
					}
					count = 0;
					clapped = false;
				}
			}

			resultImage.updatePixels();
			image(resultImage, 0, 0);

			int fx, fy, inner_r, r, rRange;
			for (int firefly_id = 0; firefly_id < n_fireflies; firefly_id++) {

				fx = fireflyXs.get(firefly_id);
				fy = fireflyYs.get(firefly_id);

				rRange = randomize(10, 13);
				inner_r = randomize(rRange - 9, rRange - 6);
				r = randomize(rRange, rRange + 5);

				drawFirefly(firefly_id, fx, fy, r, inner_r);

				if (isTouchingShadow(fx, fy, r)) {

					touchedXs_shadow.add(fx);
					touchedYs_shadow.add(fy);

					// remove the touched firefly and put a new falling firefly
					fx = randomize(0, width);
					fy = 0;
				}

				if (isTouchingBody(fx, fy, r)) {
					
					if (isInBody(fx, fy, r)) {
						fy -= 5; // float to the top
					}

				}

				// if firefly does not reach the end of screen
				else if (fy <= height) {

					fy += randomize(1, 5); // fall at a random speed

				}
				// if firefly reaches the end of screen
				else {
					fx = randomize(0, width);
					fy = 0;
				}

				
				fireflyXs.set(firefly_id, fx);
				fireflyYs.set(firefly_id, fy);

			}

			for (int i = 0; i < userMap.length; i++) {

				if (touchedXs_shadow.size() > 0) {

					decayOnePixelOfShadow(i);
				}

				paintEachPixel(i);
			}

			touchedXs_shadow.clear();
			touchedYs_shadow.clear();

		}

	}

	/**Paint one pixel of screen*/
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

	/**Create decay effect on shadow*/
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

	/**Draw a firefly according to input location and size*/
	public void drawFirefly(int firefly_id, int x, int y, int r, int inner_r) {

		noStroke();

		fill(0, 255, 255);
		ellipse(x, y, inner_r * 2f, inner_r * 2f);
		fill(0, 255, 255, 100);
		ellipse(x, y, (r * 2f), (r * 2f));

	}

	/**Add fireflies to the screen for setup*/
	public void addFireflies() {
		for (int firefly_id = 0; firefly_id < n_fireflies; firefly_id++) {
			fireflyXs.add(randomize(0, width));
			fireflyYs.add(randomize(0, height));
		}
	}

	/**Load forest image*/
	public void loadForestImage() {
		forestImage = loadImage("nightForest.jpg");
		loadPixels();
		forestImage.loadPixels();
	}

	/**Play chime soundtrack*/
	public void playChime(String filename) {
		Minim minim = new Minim(this);
		AudioPlayer player = minim.loadFile(filename);
		player.play();
	}

	/**Checks if firefly is touching shadows*/
	public boolean isTouchingShadow(int x, int y, int r) {
		int i = getLoc(x, y + r);

		if ((y + r) < height && x < width) {
			return (shadowImage.pixels[i] ==color(red(forestImage.pixels[i]),
					green(forestImage.pixels[i]),
					blue(forestImage.pixels[i])));
		}
		return false;
	}

	/**Checks if firefly is touching user body*/
	public boolean isTouchingBody(int x, int y, int r) {
		int i = getLoc(x, y + r);

		if ((y + r) < height && x < width) {
			return (userMap[i] != 0);
		}
		return false;
	}

	/**Checks if firefly is embedded in user body*/
	public boolean isInBody(int x, int y, int r) {
		if ((y - r) > 0) {
			return (userMap[getLoc(x, y - r)] != 0);
		}
		return false;
	}

	/**Produces random number of integers*/
	public int getLoc(int x, int y) {
		int loc_new;

		loc_new = x + y * width;

		return loc_new;
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

}