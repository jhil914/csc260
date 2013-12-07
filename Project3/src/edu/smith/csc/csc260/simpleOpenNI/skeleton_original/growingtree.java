package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Color;

public class growingtree extends SmithPApplet{
	//Watch the tree continue to grow.  If mousePressed you can stop the growth. 
	 
	int num =10;
	 
	Leaves[] myLeaves = new Leaves [num]; // creating my object
	Color[] myColors = new Color[2]; //creating 2 differnt colors
	 
	 //more organic and 3d
	public void setup() {
	  size(640,480,P3D);
	 
	  myColors[0] = new Color(150, 255, 46, 95);  //allows me to set colors for my color indices
	  myColors[1] = new Color(12, 191, 159, 80);
	 
	  background(0);
	  noStroke();
	  fill(250, 255, 8,80);
	  ellipse(350, 40, 50, 50);
	  
	  smooth();
	 
	  for (int i=0; i<myLeaves.length; i++) {
	    myLeaves[i] = new Leaves();
	  }
	}
	 
	 
	public void draw() {
		background(0);
		fill(100);
		  line(0, 0, -2000, width, 0, -2000);
		  line(0, 0, -2000, 0, height, -2000);
		  line(0, height, -2000, width, height, -2000);
		  line(width, height, -2000, width, 0, -2000);
		 
		  // perspective lines
		 
		  line(0, 0, -2000, 0, 0, 0);
		  line(width, 0, -2000, width, 0, 0);
		  line(0, height, -2000, 0, height, 0);
		  line(width, height, -2000, width, height, 0);
		
	  for (int i=0; i < myLeaves.length; i++) {
	    myLeaves[i].update();
	    myLeaves[i].draw();
	  }
	}
	public void mousePressed() {
		  setup();
		}
	public void keyPressed() {
	//	System.out.println(true);
		  for (int i=0; i < myLeaves.length; i++) {
			  myLeaves[i].update();
			   myLeaves[i].change();
			   myLeaves[i].draw();
			  }
		}
	class Leaves {
		int initialx = 50;
		int initialy = 400;
	  int x =initialx;//starts the leaves towards the center of the screen
	  int y = initialy;
	  Color c = myColors[floor(random(0, 2))];
	  float noiseScale=0.02f;
	  float times = 0;
	  float n;
	  int div;
	  int changex ;
		 int changey;
		 float a;
		  float b;
	  Leaves() {
	  }
	 
	  void update() {
		  changex = mouseX-x;
			 changey = mouseY-y;
	    x =  x+(int)(random (-2,2)); //this updates the position of the point and makes it move across the screen
	    y = y + (int)(random(-2,2));
	    float dis = (float) Math.sqrt(changex*changex+changey*changey);
	   times+= 0.01f;
	   if(changey!=0&&changex!=0){
	   div = changey/changex;
		  
		  if(div>=1){
			  
			  a = div;
			  b =1;
			  System.out.println("a");
		  }
		  else if(div!=0){
			  System.out.println("b");
			  a = 1;
			  b = 1/div;
		  }
	   }
	   else{
		   System.out.println("c");
		   a=2;
		   b=2;
	   }
	  }
	
	 public void change(){
		 System.out.println(div);
		  n = (float) (noise(times)*Math.sqrt(changex*changex+changey*changey));
		 // System.out.println(mouseX+" "+x+" "+changex+" "+changey);
		 if(changex>0){
			 if(changey>0){
				// System.out.println("1");
				 x+= random(0,a);
				 y+= random(0,b);
			 }
			 else{
				// System.out.println("here");
				 x+= random(0,a);
				 y-= random(0,b);
			 }
		 }
		 else{
			 if(changey>0){
				// System.out.println("there");
				 x-= random(0,a);
				 y+= random(0,b);
			 }
			 else{
				// System.out.println("2");
				 x-= random(0,a);
				 y-= random(0,b);
			 }
		 }
	 }
	  public void draw() {
		  
		 
			 
			  
	  if (random(20)>18) {
	      fill(100);
	    }
	    else {
	      strokeWeight(5);
	      fill(100 );
	      stroke(0,10);
	      ellipse(x, y, 5, 5);
	     // ellipse(width/2, height/2, n,n);
	    }
	  }
	 /*void tree(float x, float y, float w, float l) {
	    fill(152, 95, 60, 80);
	    rect(height/2, width/2, 10, height/2);*/
	  }

}
