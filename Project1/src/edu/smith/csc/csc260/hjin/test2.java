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
    
      BouncingSprite bs = new BouncingSprite(windowWidth, windowHeight,new Point(.09f, .1f,0),10);
     
      bs.setLocation(new Point(30,50));
      bs.setFill(new Color(255,0,0,200));
     
      s.add(bs);
      addSprite(bs);
 
    }
    public void snake(){
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
    }
    public void mouseDragged(){
    	
    	
    		collide();
    	  	snake();
    	  	if(Math.abs(mouseX-pmouseX)==7){
    	  		if(mousePressed){
    	  			if(s.size()<100){
    	    	BouncingSprite bs2 = new BouncingSprite(windowWidth, windowHeight,new Point((float) Math.random()/3, (float) Math.random()/3,0),(int) (Math.random()*20));
    			bs2.setLocation(new Point(mouseX,mouseY));
    			//bs2.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255),150));
    			bs2.setFill(new Color(255f,255f,255f,(float) (Math.random()*255)));
    			bs2.setNoStroke(true);
    			addSprite(bs2);
    			
    		s.add(bs2);
    	  		}
    	  	}
    	  	}
    
    	
    }
    
   public void collide(){
	   
	   for(int i = 0; i<s.size();i++){
   		BouncingSprite bounce = s.get(i);
   		float dx =mouseX-bounce.getLocation().getX();
   		float dy = mouseY-bounce.getLocation().getY();
   		float dis = (float) Math.sqrt(dy*dy+dx*dx);
   		float vx = bounce.getvel().getX();
   		float vy = bounce.getvel().getY();
   	

			
		
   		if(Math.abs(mouseX-pmouseX)>=8){
   		if(dis<=50+bounce.getRadius()){
   			System.out.println("collide");
   			if(mouseX-pmouseX<0){//going left
   				
   					bounce.setVel(vx,-vy);
   				
   				
   			}
   			
   			else{
   				
   					bounce.setVel(vx,-vy);
   			
   				

   			}
   		}
   		}
   		}
   }
    
    public void mouseMoved(){
    	
    	float r = 0;
  	  float theta = 0;
  	  // Shift array values
  	
  	collide();
  	  snake();
  	if(Math.abs(mouseX-pmouseX)==7){
  		
  if(s.size()<100){
  			BouncingSprite bs2 = new BouncingSprite(windowWidth, windowHeight,new Point((float) Math.random()/5, (float) Math.random()/5,0),(int) (Math.random()*20));
  			bs2.setLocation(new Point(mouseX,mouseY));
  			bs2.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255),150));
  			
  			bs2.setNoStroke(true);
  			addSprite(bs2);
  		s.add(bs2);
  	}
		
    }
  	 
    
    }
    
}