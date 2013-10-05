package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.interpolation.easing.LinearEasingFunction;
import edu.smith.csc.csc260.interpolators.scalarInterpolators.LinearScalarInterpolator;
import edu.smith.csc.csc260.spites.InterpolatedSprite;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;





public class test extends SmithPApplet{
	
	public int windowWidth = 300;
	public int windowHeight = 400;
	public int dim;
	public void setup(){
		super.setup();
		
		size(windowWidth,windowHeight);//sets the size of the canvas
	      frameRate(30);
	      
	    //  setBackgroundColor(new Color(255,255,255, 255));
		
	      
	      dim = width/2;
	      background(0);
	      colorMode(HSB, 360, 100, 100);
	      noStroke();
	      ellipseMode(RADIUS);
	      
	      drawGradient(5, height/2);
	}
	public void mouseClicked(){
		
		CircleInterpolatedSprite eis = new CircleInterpolatedSprite(6);
		eis.setSize(0);
		eis.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255), 100));
		eis.setLocation(new Point(mouseX,mouseY));
		eis.setNoStroke(true);
		eis.setSizeInterpolator(new LinearScalarInterpolator(
				0, 
				(float)(40 * Math.PI),
				new LinearEasingFunction(0, 50* 1000, 0)));
		
		System.out.println("size: "+eis.getSize());
		addSprite(eis);
}
	

	public	void drawGradient(float x, float y) {
		  int radius = dim/2;
		  float h = random(0, 360);
		  float alpha = 90;
		  for (int r = radius; r > 0; --r) {
			  
		    fill(90, 90, 90,alpha);
		    ellipse(x, y, r, r);
		    h = (h + 1) % 360;
		   if(alpha >=3){
			   alpha-=3;
		   }
		    System.out.println(alpha);
		  }
}
}