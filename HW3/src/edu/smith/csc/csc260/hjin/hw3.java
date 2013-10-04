/** @author Hee Jin
 * CSC 260
 * 3 Oct 2013
 * creates sprites and each sprite performs different actions*/
package edu.smith.csc.csc260.hjin;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.interpolation.easing.LinearEasingFunction;
import edu.smith.csc.csc260.spites.InterpolatedSprite;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class hw3 extends SmithPApplet {
    private static final long serialVersionUID = 1L;
    /** canvas width*/
    public int windowWidth = 500;
    /** canvas height*/
    public int windowHeight = 400;
    /** sets up the canvas*/
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
     for(int i= 0; i<50; i++){
			
			BouncingSprite bs2 = new BouncingSprite(windowWidth, windowHeight,new Point((float) Math.random()/5, (float) Math.random()/5,0),(int) (Math.random()*20));
			bs2.setLocation(new Point((float) (Math.random()*windowWidth),(float) (Math.random()*windowHeight)));
			bs2.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255),150));
			bs2.setNoStroke(true);
			addSprite(bs2);
			
		}
     //creates 20 different ellipse interpolating sprites
      for(int i =0; i<20; i++){
    	  int v = (int) (Math.random()*10+1);
    	  InterpolatedSprite is = new InterpolatedSprite();
			is.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255),150));
			is.setNoStroke(true);
			is.setLocationInterpolator(new Ellipseinterpolate(
					new Point((float) (Math.random()*windowWidth),(float) (Math.random()*windowHeight)),
					40.0f,
					new LinearEasingFunction(0, v*1000,0)));
			addSprite(is);
      }
      // creates an ellipse interpolating sprite that interpolates after about 5 sec
      InterpolatedSprite eis = new InterpolatedSprite();

      eis.setFill(new Color(250,0,0,255));
      eis.setLocationInterpolator(new Ellipseinterpolate(
          new Point(100,150),      
          40.0f,
          new LinearEasingFunction(5*1000, 10*1000,0)));
      addSprite(eis);
    }
}