package edu.smith.cs.csc260.hjin;


import processing.opengl.*;
import processing.core.PShape;
import processing.opengl.PShapeOpenGL;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;
/**
 * 
@author Hee Jin
CSC 260 
22 Sep 2013

initializes the bezier curve
 *
 */
public class BezierSprite extends Sprite{
	// initialize bezier as pshape
	PShape bezier ; 
	/** initilize sub*/
	double sub;
	/** initilize the points*/
	Point p1,p2,p3;
	/**
	 * Constructor for the Bezier curve
	 
	 */
	
	public BezierSprite(Point p1, Point p2, Point p3, double d){
		this.sub = d;
		this.p1 = p1;
		this.p2 = p2;
		this.p3=p3;
	}


/** renders the bezier curve with the formula*/
	public void render(SmithPApplet pApplet){
	
		if(bezier == null) {
			
			bezier= pApplet.createShape(); // for some reason this syntax doesn't work...
			
			bezier.beginShape();//gives me a nullpointererror
			
			for(float t = 0; t <= 1.0; t+= 1.0/sub) {
				
				int xt= (int) ((1-t)*2*p1.getX() +2*t*(1-t)*p2.getX() + 2*t*p3.getX()); 
				int yt= (int) ((1-t)*2*p1.getY() +2*t*(1-t)*p2.getY() + 2*t*p3.getY());
				
				bezier.vertex(xt, yt);
				
			}
			
			bezier.endShape(pApplet.CLOSE);
			
		}
		pApplet.shape(bezier);
	}
}
