package edu.smith.csc.csc260.spites;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class Sprite {
	protected float angle;
	protected Point location = new Point();
	protected Point offset = new Point();
	protected Color fill = new Color(255,255,255,255);
	protected Color stroke = new Color(0,0,0,255);
	protected float size=1; //0 to 1
	
	public Sprite() {
		
	}

	public void setNoStroke(boolean b) {
		if(b) {
			stroke = null;
		} else if (stroke == null) {
			stroke = new Color(0,0,0,255);
		} // else it already had stroke
	}
	public boolean getNoStroke() {
		return stroke == null;
	}
	
	public void setNoFill(boolean b) {
		if(b) {
			fill = null;
		} else if (fill == null) {
			fill = new Color(255,255,255,255);
		} // else it already had stroke
	}
	
	public boolean getNoFill() {
		return fill == null;
	}
	
	public void setStroke(Color c) {
		stroke = c;
	}
	public void setFill(Color c) {
		fill = c;
	}
	public void setLocation(Point loc) {
		location = loc;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public float getAngle() {
		return angle;
	}
	public void setAngle(float a) {
		angle = a;
	}
	//for project1
	public float getSize() {
		return size;
	}
	public void setSize(float s) {
		size = s;
	}
	
	
	/**
	 * Override this in subclasses if you want sprite to move.
	 * @param curTime - the current system time (in millis)
	 * @param lastTime - the system time of the last frame
	 * @param elapsedTime - the difference between the current time and the last time
	 */
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		
	}
	
	
	
	/**
	 * Handles translating the sprite to x,y.  Calls updatePosition (to change x and y) and draw to draw the sprite.
	 * 
	 * @param pApplet
	 * @param curTime
	 * @param lastTime
	 * @param elapsedTime
	 */
	public void display(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {

		updatePosition(pApplet, curTime, lastTime, elapsedTime);
		draw(pApplet);
	}
	
	/**
	 * Override this in subclasses if you want sprite to be visible.
	 * @param pApplet
	 */
	public void render(SmithPApplet pApplet) {
		
	}
	
	/**
	 * Override this in a subclass if you need to setup your sprite with a reference to the SmithPApplet or 
	 * in a "valid opengl context." Gets called inside PApplet's draw method before the Sprite's display, draw, or render methods get called
	 * @param pApplet
	 */
	public void setup(SmithPApplet pApplet) {
		
	}
	
	public void draw(SmithPApplet pApplet) {
		pApplet.pushStyle();
		pApplet.pushMatrix();
		
		if(fill == null) {
			pApplet.noFill();
		} else {
			pApplet.fill(fill.getR(), fill.getG(), fill.getB(), fill.getA());			
		}
		if(stroke == null) {
			pApplet.noStroke();
		} else {
			pApplet.stroke(stroke.getR(), stroke.getG(), stroke.getB(), stroke.getA());			
		}

		pApplet.translate(location.getX(), location.getY());
		pApplet.rotate(angle);
		pApplet.translate(offset.getX(), offset.getY());
		pApplet.scale(size); 
		
		render(pApplet);
		
		pApplet.popMatrix();
		pApplet.popStyle();
		
	}
	
}

