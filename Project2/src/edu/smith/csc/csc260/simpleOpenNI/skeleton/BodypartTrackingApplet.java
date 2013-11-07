package edu.smith.csc.csc260.simpleOpenNI.skeleton;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PImage;
import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class BodypartTrackingApplet extends SmithPApplet {
	private static final long serialVersionUID = 1L;
	public SimpleOpenNI  simpleOpenNI;
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
	boolean tracking = false;
	int userID;
	
	Circles[] c;

	float y = -5;
	float y1 = -5;
	float y2 = -5;
	float y3 = -5;
	float y4 = -5;
	float r = random (20,40);
	float r1 = random (20,40);
	float r2 = random (20,40);
	float r3 = random (20,40);
	float r4 = random (20,40);
	float x = random (10,590);
	float x1 = random (10,590);
	float x2 = random (10,590);
	float x3 = random (10,590);
	float x4 = random (10,590);
	float w = 397-y;

	
	public void setup() {	
		size(640 * 2, 480);// context = new SimpleOpenNI(this);
		simpleOpenNI = new SimpleOpenNI(this);
		if(simpleOpenNI.isInit() == false) {
			System.out.println("Can't init SimpleOpenIN, maybe the camera is not connected!"); 
			exit();
			return;
		}
		
		  // enable skeleton generation for all joints
		  c = new Circles [510];
		  for (int i=0; i<510; i++)
		  {
		    c[i] = new Circles();
		    c[i].x = random(500);
		    c[i].y = random(60);
		    c[i].w = random(5, 20);
		    c[i].h = random(5, 20);
		  }

		/**background(0,0,0);

		  stroke(0,0,255);
		  strokeWeight(3);
		  smooth();
		 */
		  smooth();
		  noStroke();
		simpleOpenNI.setMirror(false);
		
		simpleOpenNI.enableDepth(); // needs to be enable for skeletons to work right
		
		simpleOpenNI.enableUser();
		simpleOpenNI.enableRGB();
		simpleOpenNI.alternativeViewPointDepthToImage();
resultImage = new PImage(640,480, RGB);

bs2 = new BouncingSprite(width/2, height,
		new Point((float) Math.random() / 5, (float) Math
				.random() / 5, 0),
		20);
bs2.setLocation(new Point(0, 0));
bs2.setFill(new Color(255,0,0,255));

bs2.setNoStroke(true);
addSprite(bs2);
		
	}
	public void onNewUser(SimpleOpenNI curContext, int userId)
	{
		userID = userId;
		tracking = true;
		simpleOpenNI.startTrackingSkeleton(userId);
		
		Vector<BodypartTrackingSprite> newTracks = new Vector<BodypartTrackingSprite>();
		
		/**	BodypartTrackingSprite bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_HEAD);
		addSprite(bts);
		newTracks.add(bts);
		
		

		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_LEFT_SHOULDER);
		addSprite(bts);
		newTracks.add(bts);

		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER);
		addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_NECK);
		
		addSprite(bts);
		
		
		newTracks.add(bts);
		//collide();
		
		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_RIGHT_FOOT);
		addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.GESTURE_WAVE);
		addSprite(bts);
		newTracks.add(bts);
		*/
		rrr = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_RIGHT_HAND);
		addSprite(rrr);
		newTracks.add(rrr);

		 hhh = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_LEFT_HAND);
		 isit = true;
		addSprite(hhh);
		newTracks.add(hhh);
		
		tracks.put(userId, newTracks);
		
	}

	public void onLostUser(SimpleOpenNI curContext, int userId)
	{

		Vector<BodypartTrackingSprite> btsVec = tracks.get(userId);
		for(BodypartTrackingSprite bts : btsVec) {
			removeSprite(bts);
			
		}

	}
	public void draw()
	{
		simpleOpenNI.update();
		PImage rgbImage = simpleOpenNI.rgbImage();
		// image(simpleOpenNI.userImage(), 0, 0);
		 image(rgbImage, 640, 0);
		//super.draw();
		 if (tracking) {
				// ask kinect for bitmap of user pixels
				loadPixels();
				userMap =simpleOpenNI.userMap();
				for (int i = 0; i < userMap.length; i++) {
					// if the pixel is part of the user
					if (userMap[i] != 0) {
						// set the pixel to the color pixel
						resultImage.pixels[i] =color(0,0,0); //rgbImage.pixels[i];
					} else {
						// set it to the background
						resultImage.pixels[i] = color(255,255,255); //backgroundImage.pixels[i];
					}
				}

				// update the pixel from the inner array to image
				resultImage.updatePixels();
				image(resultImage, 0, 0);
				if(rrr.getLocation().getX()<=500){
				for (int i=0; i<rrr.getLocation().getX(); i++)
				  {
				    c[i].show();
				    c[i].move();
				  }
				 
				/**  for (int i=0; i<rrr.getLocation().getX()+10; i++)
				  {
				    c[i].howred = 255;
				    c[i].howgreen = random(20, 50);
				    c[i].howblue = random(20, 50);
				  }*/
				 /** if (c[(int) (rrr.getLocation().getX()+10)].y > 500);
				  {
				    c[(int) (rrr.getLocation().getX()+10)].y = random(60);
				  }*/
				
				w = 397 - y;
				fill(255,20,147,150);
				  ellipse(x,y,r,r);
				  w = 397 - y1;
				  fill(255,20,147,150);
				  ellipse(x1,y1,r1,r1);
				  w = 397 - y2;
				  fill(255,20,147,150);
				  ellipse(x2,y2,r2,r2);
				  w = 397 - y3;
				  fill(255,20,147,150);
				  ellipse(x3,y3,r3,r3);
				  w = 397 - y4;
				  fill(255,20,147,150);
				  ellipse(x4,y4,r4,r4);
				 y += 2;
				 y1 += 3;
				 y2 += 6;
				 y3 += 4;
				 y4 += 7;
				 if (y > 397) {
				   y = -5;
				   x = random(10,590);
				 }
				 if (y1 > 397) {
				   y1 = -5;
				   x1 = random(10,590);
				 }
				 if (y2 > 397) {
				   y2 = -5;
				   x2 = random(10,590);
				 }
				 if (y3 > 397) {
				   y3 = -5;
				   x3 = random(10,590);
				 }
				 if (y4 > 397) {
				   y4 = -5;
				   x4 = random(10,590);
				 }

				}
				if(hhh.getLocation().getX()!=0){
					 collide();
				 }
			}
		 super.draw();
		 if(xpos1.length==50){
				drawSunLight();
				}
		 
		 

		
	}
	public void collide() {

			//System.out.println(hhh.getLocation().getX());
			float dx = hhh.getLocation().getX() - bs2.getLocation().getX();
			float dy = hhh.getLocation().getY() - bs2.getLocation().getY();
			float dis = (float) Math.sqrt(dy * dy + dx * dx);
			float vx = bs2.getvel().getX();
			float vy = bs2.getvel().getY();
			//during each collisions, the sprites are bounced off to random directions
			//System.out.println(dis);
				if (dis <= 10 + bs2.getRadius()) {
					System.out.println("collide");
					// going left

						//bs2.setVel(vx, -vy);
					
bs2.setFill(new Color(0,0,255,255));
					bs2.setLocation(hhh.getLocation());
					System.out.println(xpos1[49]-xpos1[48]);
				if(Math.abs(xpos1[49]-xpos1[48])>7){
					bs2.setFill(new Color((float) (Math.random() * 255),
							(float) (Math.random() * 255), (float) (Math
									.random() * 255), 150));
					
				}
			
		}
}
	public void drawSunLight() {
		//System.out.println("drawing sunlight");
		float r = 0;
		float theta = 0;
		// Shift array values
		if(isit){
		for (int i = 0; i < xpos1.length - 1; i++) {
			// Shift all elements down one spot.
			// xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at the
			// second to last element.
			xpos1[i] = xpos1[i + 1];
			ypos1[i] = ypos1[i + 1];
		}

		// New location
		xpos1[xpos1.length - 1] = hhh.getLocation().getX(); // Update the last spot in the array
											// with the mouse location.
		ypos1[ypos1.length - 1] = hhh.getLocation().getY();

		
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

		}}
	}
	class Circles
	{
	  float x, y;
	  float w, h;
	  float howred, howgreen, howblue;
	 
	  void show()
	  {
	    fill(255,20,147,150);
	    ellipse(x, y, w, h);
	  }
	 
	  void move()
	  {
	    x+= random (-7, 7);
	    y+= random(4, 6);
	  }
	}}