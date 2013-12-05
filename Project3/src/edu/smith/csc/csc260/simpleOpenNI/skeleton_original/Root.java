package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PImage;
import SimpleOpenNI.SimpleOpenNI;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import edu.smith.csc.csc260.core.SmithPApplet;


public class Root extends SmithPApplet{
	/*
	Roots
	 
	Date:         2009.01.09
	Author:       DrIan - Minor Modification of Origninl P.J. Onori Code
	 
	Description:  Modified (Minor Changes) from Processing sketch by P.J. Onori - Meander (http://www.openprocessing.org/visuals/?visualID=157)
	              This is P.J Onori Code modified to produce a differnet effect in this case growing 'roots' which tapper in size and grow darker as the 'root'
	              travels further from the central generation point. The Completed rendering has a woodcut quality to it
	               
	              All credit goes to P.J Onori as I don't code just modify and play around with the work of others to produce something different.            
	*/
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

    /*Array of file names for chime soundtrack*/
	final String[] soundSelection = { "Chimes1.mp3", "Chimes2.mp3", "Chimes3.mp3" };

	Minim minim;
	AudioPlayer player;
	
	int xSize = 500;
	int ySize = 500;
	 
	float xAnchor = 250;
	float yAnchor = 250;
	 
	Meanderer[] meanderers;
	 
	int total = (int)random(70, 100);
	int delayInterval = (int) random(3, 15);
	int pushCount = 0;
	 
	public void setup(){
		size(640, 480);
		simpleOpenNI = new SimpleOpenNI(this);
		if (simpleOpenNI.isInit() == false) {
			System.out.println("Can't init SimpleOpenIN, maybe the camera is not connected!");
			exit();
			return;
		}
	 // frameRate(60);
		simpleOpenNI.setMirror(true);
		simpleOpenNI.enableDepth(); // needs to be enable for skeletons to work
									// right
		simpleOpenNI.enableUser();
		simpleOpenNI.enableRGB();
		simpleOpenNI.alternativeViewPointDepthToImage();

		resultImage = new PImage(width, height, RGB);
		shadowImage = new PImage(width, height, RGB);

	  smooth();
	  noFill();
	 // background(255);
	  meanderers = new Meanderer[total];
	  stroke(0,0,0,25);
	  strokeWeight(5);
	  ellipse(250,250,495,495);
	 }
	
	public void draw()
	{
		
		
			

			super.draw();
			 if(frameCount%delayInterval==1&&pushCount!=total-1||frameCount==0)
			   {
			     meanderers[pushCount] = new Meanderer(xAnchor, yAnchor);
			     pushCount++;
			   }
			  for(int i=0; i<pushCount; i++) meanderers[i].update();

		  


	  
	}

	
	class Meanderer
	{
	  int count, seed, d1, toggle, baseAngle;
	  float travel, dx, dy, x, y, xSpeed, ySpeed, theta, angle, speed, d2, noiseScale, noiseCount, noiseSpeed, xCount, yCount, angleMultiplier, noiseVal, noiseCompoundX, noiseCompoundY;
	 
	  Meanderer (float xPos, float yPos)
	  {
	    toggle = (random(-1,1)<0) ?-1:1;
	    speed = random(0.6f, 1.5f)*toggle;
	    count=0;
	    seed = (int)random(200,1000);
	    d1 = (int)random(4,9);
	    d2 = random(.2f,.4f);
	    noiseScale=random(.01f,.03f);
	    noiseCount=0;
	    noiseSpeed=random(.001f, .01f);
	    xCount=(int)random(-10, 10);
	    yCount=(int)random(-10, 10);
	    angleMultiplier = random(.3f,.9f);
	    baseAngle = (int)random(2,4)*360;
	    noiseCompoundX=random(.1f,.3f);
	    noiseCompoundY=random(.1f,.3f);
	       
	    x = xPos;
	    y = yPos;
	  }
	 
	  void update()
	  {
	    noiseDetail(d1, d2);
	    noiseSeed(seed);
	    noiseVal=noise((x-xCount)*noiseScale, (y-yCount)*noiseScale, noiseCount);
	     
	    angle -= (angle - noiseVal*baseAngle)*angleMultiplier;
	    theta = -(angle * PI)/180;
	    xSpeed = cos(theta)*speed;
	    ySpeed = sin(theta)*speed;
	     
	    x -= xSpeed;
	    y -= ySpeed;
	     
	    if(x>xAnchor)dx=x-xAnchor;
	    if(y>yAnchor)dy=y-yAnchor;
	    if(x<xAnchor)dx=xAnchor-x;
	    if(y<yAnchor)dy=yAnchor-y;
	    travel = sqrt (dx*dx + dy*dy);
	         
	    float avgSpeed = (xSpeed+ySpeed)/2;
	    stroke(0,0,0,5*travel/10);
	    strokeWeight(1);
	    fill(255,255,255,55);
	    if(50-travel/5>1)
	    {
	    ellipse(x,y,50-travel/5,50-travel/5);
	    }
	    noiseCount+=noiseSpeed;
	    xCount+=noiseCompoundX;
	    yCount+=noiseCompoundY;
	    count++;
	  }
	 
	}

}
