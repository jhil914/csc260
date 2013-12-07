package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import edu.smith.csc.csc260.core.SmithPApplet;

public class snake extends SmithPApplet{
	snaked[] snakeds = new snaked[0];
	public void setup() {
	  background(255);
	  size(500, 500);
	  stroke(0, 0, 0, 10);
	  smooth();
	}
	public void draw() {
	  for(int i = 0; i < snakeds.length; i++) {
	    if(!snakeds[i].done())
	    snakeds[i].go();
	  }
	  if(mousePressed) {
	    snakeds = (snaked[]) append(snakeds, new snaked(mouseX, mouseY, 15, random(100), 1));
	  }
	}
	public void mousePressed() {
	  fill(255, 255, 255, 150);
	  rect(0, 0, width, height);
	  for(int i = 0; i < snakeds.length; i++) {
	    snakeds[i].tm = 1;
	  }
	}
	class snaked {
	  float X;
	  float Y;
	  float rot;
	  float V;
	  float tm;
	  int fm;
	  snaked(int tX, int tY, float tfm, float trot, float tV) {
	    X = tX;
	    Y = tY;
	    rot = trot;
	    tm = tfm;
	    V = tV;
	  }
	  void go() {
	    V += random(-0.03f, 0.03f);
	    tm /= 1.01;
	    strokeWeight(tm);
	    rot += random(-0.2f, 0.2f);
	    line(X, Y, X + V*sin(rot), Y + V*cos(rot));
	    line(X, Y, X + V*sin(rot), Y + V*cos(rot));
	    X += V*sin(rot);
	    Y += V*cos(rot);
	    fm++;
	    if(random(400) > 398.5-(fm/20)) {
	      snakeds = (snaked[]) append(snakeds, new snaked((int)(X), (int)(Y), tm, rot + random(-0.2f, 0.2f), V));
	    }
	  }
	  boolean done() {
	    if(tm < 1.01) {
	      return true;
	    }else{
	      return false;
	    }
	  }
	}

}
