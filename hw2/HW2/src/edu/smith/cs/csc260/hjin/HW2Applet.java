package edu.smith.cs.csc260.hjin;


import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.RectangleSprite;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

/**@author Hee Jin
 * CSC 260
 * 22 Sep 2013
 * 
 *  Application for the Bezier curve
 *  draws the curve on the applet
 * */
public class HW2Applet extends SmithPApplet{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HW2Applet(){
		
	}
	/** sets up the bezier curve object*/
	public void setup(){
		
		size(100,100);
		
		
		Point a = new Point(3,3);
		Point b = new Point(4,4);
		Point c = new Point (5,5);
		BezierSprite bS = new BezierSprite (a,b,c,4);
		
		addSprite(bS);
		
		
	}
	/** draws it out */
	public void draw(){
	
		background(200,20,100);
		
		super.draw();
	}
}
