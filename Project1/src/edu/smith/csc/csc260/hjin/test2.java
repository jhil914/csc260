/** @author Hee Jin
 * CSC 260
 * 3 Oct 2013
 * creates sprites and each sprite performs different actions*/
package edu.smith.csc.csc260.hjin;
import java.util.ArrayList;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.interpolation.easing.LinearEasingFunction;
import edu.smith.csc.csc260.interpolators.pointInterpolators.LinearPointInterpolator;
import edu.smith.csc.csc260.interpolators.scalarInterpolators.LinearScalarInterpolator;
import edu.smith.csc.csc260.spites.InterpolatedSprite;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class test2 extends SmithPApplet {
    private static final long serialVersionUID = 1L;
    /** canvas width*/
    public int windowWidth = 500;
    /** canvas height*/
    public int windowHeight = 400;
    /** sets up the canvas*/
    public ArrayList<Sprite> s;
    
    public Point startP;
    public Point endP;
    public void setup() {
      super.setup();
      
      size(windowWidth,windowHeight);//sets the size of the canvas
      frameRate(30);
      
      setBackgroundColor(new Color(0,0,0, 255));
      
      //first bouncing sprite
      BouncingSprite bs = new BouncingSprite(windowWidth, windowHeight,new Point(.09f, .1f,0),10);
     
      bs.setLocation(new Point(30,50));
      bs.setFill(new Color(255,0,0,200));
      addSprite(bs);
    //creates 30 bouncing sprites in random colors, size and velocity
   /**  for(int i= 0; i<50; i++){
			
			BouncingSprite bs2 = new BouncingSprite(windowWidth, windowHeight,new Point((float) Math.random()/5, (float) Math.random()/5,0),(int) (Math.random()*20));
			bs2.setLocation(new Point((float) (Math.random()*windowWidth),(float) (Math.random()*windowHeight)));
			bs2.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255),150));
			bs2.setNoStroke(true);
			addSprite(bs2);
			
		}*/
    
    }
    public void mouseClicked(){
    	
    		
    		BouncingSprite bs2 = new BouncingSprite(windowWidth, windowHeight,new Point((float) Math.random()/5, (float) Math.random()/5,0),(int) (Math.random()*20));
			bs2.setLocation(new Point(mouseX,mouseY));
			bs2.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255),150));
			bs2.setNoStroke(true);
			
			addSprite(bs2);
		//s.add(bs2);
    	
    	
    }
    public void mousePressed(){
    	if(mousePressed){
    		startP = new Point(mouseX, mouseY);
    	}
    }
    public void mouseReleased(){
    	endP = new Point (mouseX, mouseY);
    }
    public void mouseDragged(){
    	//int rand = (int) Math.random()*s.size();
    	InterpolatedSprite is = new InterpolatedSprite();
		is.setFill(new Color(0,255,0, 255));
		is.setLocationInterpolator(new LinearPointInterpolator(
				startP, 
				endP,
				new LinearEasingFunction(0, 20* 1000, 0)));

		

		addSprite(is);
    			
    			/*is.setLocationInterpolator(new LinearPointInterpolator(
    					new Point(20, 180), 
    					new Point(180,20),
    					new LinearEasingFunction(10*1000, 20* 1000, 3)));
*/
    }
    
}