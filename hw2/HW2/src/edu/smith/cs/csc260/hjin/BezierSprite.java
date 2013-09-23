package edu.smith.cs.csc260.hjin;


import processing.opengl.*;
import processing.core.PShape;
import processing.opengl.PShapeOpenGL;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;
/**
 * 
 * @author Alice Jin
 * 
 * xt= (1-t)2x0 +2t(1-t)x1 + t2x2 
yt= (1-t)2y0 +2t(1-t)y1 + t2y2 

 *
 */
public class BezierSprite extends Sprite{
	PShape bezier ;

	/**
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param sub
	 */
	double sub;
	Point p1,p2,p3;
	public BezierSprite(Point p1, Point p2, Point p3, double d){
		this.sub = d;
		this.p1 = p1;
		this.p2 = p2;
		this.p3=p3;
	}



	public void render(SmithPApplet pApplet){
	
		if(bezier == null) {
			
			bezier= pApplet.createShape(); 
			
			bezier.beginShape();
			
			for(float t = 0; t <= 1.0; t+= 1.0/sub) {
				//		for(float i=p1.getX();i<=p3.getX();i++){
				///			for(float j=p1.getY();i<=p3.getY();j++){

				int xt= (int) ((1-t)*2*p1.getX() +2*t*(1-t)*p2.getX() + 2*t*p3.getX()); 
				int yt= (int) ((1-t)*2*p1.getY() +2*t*(1-t)*p2.getY() + 2*t*p3.getY());
				
				bezier.vertex(xt, yt);
				
			}
			
			bezier.endShape(pApplet.CLOSE);
			
		}
		pApplet.shape(bezier);
	}
}
