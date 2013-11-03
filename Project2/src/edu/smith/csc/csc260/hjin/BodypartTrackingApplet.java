package edu.smith.csc.csc260.hjin;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

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
	BouncingSprite bs2;
	ArrayList<BouncingSprite> s = new ArrayList<BouncingSprite>();
	boolean isit = false;
	float[] xpos1 = new float[50];
	float[] ypos1 = new float[50];
	public int dim;
	float[] SunLight_color = { 60f, 60f, 10f, 60f };
	int[] userMap;
	public void setup() {		// context = new SimpleOpenNI(this);
		simpleOpenNI = new SimpleOpenNI(this,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
		if(simpleOpenNI.isInit() == false) {
			System.out.println("Can't init SimpleOpenIN, maybe the camera is not connected!"); 
			exit();
			return;
		}
		
		  // enable skeleton generation for all joints
		  
		  background(0,0,0);

		  stroke(0,0,255);
		  strokeWeight(3);
		  smooth();
		  
		  
		simpleOpenNI.setMirror(false);
		simpleOpenNI.enableDepth(); // needs to be enable for skeletons to work right
		
		simpleOpenNI.enableUser();

		size(simpleOpenNI.depthWidth(), simpleOpenNI.depthHeight()); 
	}
	public void onNewUser(SimpleOpenNI curContext, int userId)
	{
		simpleOpenNI.startTrackingSkeleton(userId);
		
		Vector<BodypartTrackingSprite> newTracks = new Vector<BodypartTrackingSprite>();
		
		BodypartTrackingSprite bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_HEAD);
		addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_RIGHT_HAND);
		addSprite(bts);
		newTracks.add(bts);

		 hhh = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_LEFT_HAND);
		 isit = true;
		addSprite(hhh);
		newTracks.add(hhh);

		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_LEFT_SHOULDER);
		addSprite(bts);
		newTracks.add(bts);

		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER);
		addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_NECK);
		BouncingSprite bs2 = new BouncingSprite(700, 700,
				new Point((float) Math.random() / 5, (float) Math
						.random() / 5, 0),
				(int) (Math.random() * 20));
		bs2.setLocation(new Point(bts.getLocation().getX(), bts.getLocation().getY()));
		bs2.setFill(new Color((float) (Math.random() * 255),
				(float) (Math.random() * 255), (float) (Math
						.random() * 255), 150));

		bs2.setNoStroke(true);
		addSprite(bs2);
		s.add(bs2);
		
		newTracks.add(bts);
		//collide();
		
		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_RIGHT_FOOT);
		addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.GESTURE_WAVE);
		addSprite(bts);
		newTracks.add(bts);
		
		
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
		
		 image(simpleOpenNI.userImage(), 0, 0);
		
		super.draw();
		 
		if(xpos1.length==50){
		drawSunLight();
		}
	}
	public void collide() {

			for(int i = 0; i<s.size();i++){
			BouncingSprite bounce = s.get(i);
			float dx = hhh.getLocation().getX() - bounce.getLocation().getX();
			float dy = hhh.getLocation().getY() - bounce.getLocation().getY();
			float dis = (float) Math.sqrt(dy * dy + dx * dx);
			float vx = bounce.getvel().getX();
			float vy = bounce.getvel().getY();
			//during each collisions, the sprites are bounced off to random directions
			
				if (dis <= 50 + bounce.getRadius()) {
					System.out.println("collide");
					// going left

						bounce.setVel(vx, -vy);
					

					
					
				}
			
		}
}
	public void drawSunLight() {
		System.out.println("drawing sunlight");
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
	}}