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
		  if(Math.abs(mouseX-width/2)<30&&Math.abs(mouseY-height/2)<30){
			  System.out.println(true);
			  for (int i=0; i < myLeaves.length; i++) {
				   myLeaves[i].change();
				   myLeaves[i].draw();
				  }
		  }
	  for (int i=0; i < myLeaves.length; i++) {
	    myLeaves[i].update();
	    myLeaves[i].draw();
	  }
	}
	public void mousePressed() {
		  setup();
		}
	 
	class Leaves {
		int initialx = 50;
		int initialy = 400;
	  int x =initialx;//starts the leaves towards the center of the screen
	  int y = initialy;
	  Color c = myColors[floor(random(0, 2))];
	  float noiseScale=0.02f;

	  Leaves() {
	  }
	 
	  void update() {
	 
	    x =  x+(int)(random (-2,2)); //this updates the position of the point and makes it move across the screen
	    y = y + (int)(random(-2,2));
	   
	  }
	
	 public void change(){
		 int changex = mouseX-x;
		 int changey = mouseY-y;
		 if(changex>0){
			 if(changey>0){
				 x+= random(0,2);
				 y+= random(0,2);
			 }
			 else{
				 x+= random(0,2);
				 y-= random(0,2);
			 }
		 }
		 else{
			 if(changey>0){
				 x-= random(0,2);
				 y+= random(0,2);
			 }
			 else{
				 x-= random(0,2);
				 y-= random(0,2);
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
	    }
	  }
	 /*void tree(float x, float y, float w, float l) {
	    fill(152, 95, 60, 80);
	    rect(height/2, width/2, 10, height/2);*/
	  }

}
