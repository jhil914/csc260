/** @author Hee Jin
 * CSC 260
 * 3 Oct 2013
 * creates sprites and each sprite performs different actions*/
package edu.smith.csc.csc260.hjin;
import java.util.ArrayList;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.hjin.BouncingSprite.Wall;
import edu.smith.csc.csc260.interpolation.easing.LinearEasingFunction;
import edu.smith.csc.csc260.interpolators.pointInterpolators.LinearPointInterpolator;
import edu.smith.csc.csc260.interpolators.scalarInterpolators.LinearScalarInterpolator;
import edu.smith.csc.csc260.spites.ConstantVelocitySprite;
import edu.smith.csc.csc260.spites.InterpolatedSprite;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class test2 extends SmithPApplet {
    private static final long serialVersionUID = 1L;
    /** canvas width*/
    public int windowWidth = 800;
    /** canvas height*/
    public int windowHeight = 800;
    /** sets up the canvas*/
    public ArrayList<BouncingSprite> s;
  //  public ArrayList<Float> xpos;
  //  public ArrayList<Float> ypos;
    float[] xpos = new float[50]; 
	float[] ypos = new float[50];
    public Point startP;
    public Point endP;
    public void setup() {
      super.setup();
      s=new ArrayList<BouncingSprite>();
      size(windowWidth,windowHeight);//sets the size of the canvas
      frameRate(30);
      
      
      setBackgroundColor(new Color(0,0,0, 255));
     // setBackgroundColor(new Color(255,255,255,255));
      //first bouncing sprite
      BouncingSprite bs = new BouncingSprite(windowWidth, windowHeight,new Point(.09f, .1f,0),10,new Point(mouseX,mouseY));
     
      bs.setLocation(new Point(30,50));
      bs.setFill(new Color(255,0,0,200));
      bs.bounceFrom(new Point(mouseX, mouseY), 5);
      s.add(bs);
      addSprite(bs);
    //creates 30 bouncing sprites in random colors, size and velocity
      
      
    }
    public void mouseDragged(){
    	
    	if(s.size()==100){
      	  setBackgroundColor(new Color(255,255,255,255));
        }
    	 for (int i = 0; i < xpos.length-1; i ++ ) {
    	  	    // Shift all elements down one spot. 
    	  	    // xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at the second to last element.
    	  	    xpos[i] = xpos[i+1];
    	  	    ypos[i] = ypos[i+1];
    	  	  }
    	  	  
    	  	  // New location
    	  	  xpos[xpos.length-1] = mouseX; // Update the last spot in the array with the mouse location.
    	  	  ypos[ypos.length-1] = mouseY;
    	  	 
    	  	  
    	  	for (int i = 0; i < xpos.length; i++) {
    		     // Draw an ellipse for each element in the arrays. 
    		     // Color and size are tied to the loop's counter: i.
    		    noStroke();
    		    fill((int)255-i*255/xpos.length);
    		    ellipse(xpos[i],ypos[i],i*50/xpos.length,i*50/xpos.length);
    		    
    		  }
    	  //	System.out.println(s.get(0).getLocation().getX());
    	  	float dx =mouseX-s.get(0).getLocation().getX();
    		float dy = mouseY-s.get(0).getLocation().getY();
    		float dis = (float) Math.sqrt(dy*dy+dx*dx);
    		float angle = (float) Math.atan2(dy, dx);
    		//System.out.println(angle);
    		//System.out.println(mouse.getX());
    		
    		if(dis<=25+s.get(0).getRadius()){
    			System.out.println("collide");
    			
    		}
    	  	
    	  	
    	  	
    	  	/**if(Math.abs(mouseX-pmouseX)==7){
    	  		if(mousePressed){
    	  			if(s.size()<100){
    	    	BouncingSprite bs2 = new BouncingSprite(windowWidth, windowHeight,new Point((float) Math.random()/5, (float) Math.random()/5,0),(int) (Math.random()*20));
    			bs2.setLocation(new Point(mouseX,mouseY));
    			//bs2.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255),150));
    			bs2.setFill(new Color(255f,255f,255f,(float) (Math.random()*255)));
    			bs2.setNoStroke(true);
    			addSprite(bs2);
    			bs2.bounceFrom(new Point(mouseX, mouseY), 5);
    		s.add(bs2);
    	  		}
    	  	}
    	  	}
    	*/
    	
    }
    
    public void mouseMoved(){
    	
    	float r = 0;
  	  float theta = 0;
  	  // Shift array values
  	if(s.size()==100){
  		System.out.println("100");
  	  setBackgroundColor(new Color(255,255,255,255));
    }
  	  for (int i = 0; i < xpos.length-1; i ++ ) {
  	    // Shift all elements down one spot. 
  	    // xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at the second to last element.
  	    xpos[i] = xpos[i+1];
  	    ypos[i] = ypos[i+1];
  	  }
  	  
  	  // New location
  	  xpos[xpos.length-1] = mouseX; // Update the last spot in the array with the mouse location.
  	  ypos[ypos.length-1] = mouseY;
  	 
  	  
  	for (int i = 0; i < xpos.length; i++) {
	     // Draw an ellipse for each element in the arrays. 
	     // Color and size are tied to the loop's counter: i.
	    noStroke();
	    fill((int)255-i*255/xpos.length);
	    ellipse(xpos[i],ypos[i],i*50/xpos.length,i*50/xpos.length);
	    
	  }
  	if(Math.abs(mouseX-pmouseX)==7){
  		
  /**	if(s.size()<100){
  			BouncingSprite bs2 = new BouncingSprite(windowWidth, windowHeight,new Point((float) Math.random()/5, (float) Math.random()/5,0),(int) (Math.random()*20));
  			bs2.setLocation(new Point(mouseX,mouseY));
  			bs2.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255),150));
  			//bs2.setFill(new Color(255f,255f,255f,(float) (Math.random()*255)));
  			bs2.setNoStroke(true);
  			addSprite(bs2);
  		s.add(bs2);
  	}
		*/
    }
  	  // Draw everything
  	/*if(s.size()>0){
  	    for(int j =0; j<s.size(); j++){
  	    	
  	    	
  	    	float x = cos(theta);
  	    	float y =  4*sin(theta);
  	    	Point loc = new Point(s.get(j).getLocation());
  	    
  	    	loc.add((float)(x*xpos[j]),(float) (y*ypos[j]), 0);
  	    	//ellipse(xpos[j]*x , ypos[j]*y, i*150/xpos.length,i*150/xpos.length);
  	    	//ellipse((float) ((float)xpos[j]*x*Math.random()) , (float) ((float)ypos[j]*y*Math.random()), 16, 16);
  	    	theta += j*3;

  	    	  	   // ellipse(xpos[j]*j/xpos.length,ypos[j]*j/xpos.length,j*100/xpos.length,j*100/xpos.length);
  	    }
  	    }	*/  
  	  
  	      
    
    }
    
}