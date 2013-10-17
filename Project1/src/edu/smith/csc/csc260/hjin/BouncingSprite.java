/**@author Hee Jin
 * CSC 260
 * 4 Oct 2013
 * The class makes the sprite bounce off the walls.
 *  
 *  */
package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.ConstantVelocitySprite;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;

public class BouncingSprite extends ConstantVelocitySprite{
	/** the height of the canvas */
	public int canvasHeight;
	/** the width of the canvas */
	public int canvasWidth;	
	/** Current x position of the sprite */
	public float currX;
	/** Current Y position of the sprite */
	public float currY;
	/** The sprite's radius */
	public float radius;
	public float vx = 0;
	 public float vy = 0;
	public boolean isit;
	/** Constructor for bouncingsprite*/
	public BouncingSprite(int W, int H,Point v,int r){
		super(v, 0);
		canvasHeight = H;
		canvasWidth = W;	
		radius = r;		
		isit = false;
	}
	public Wall bounceFrom(Point p, int r){
		Wall w = null;
		float dx = p.getX()-getLocation().getX();
		float dy = p.getY()-getLocation().getY();
		float dis = (float) Math.sqrt(dy*dy+dx*dx);
		float angle = (float) Math.atan2(dy, dx);
		if(dis<=r+radius){
			isit = true;
			if(angle<45 && angle <315){
				w= Wall.RIGHT;
				System.out.println("should bounce");
			}
			
		        
		}
		
		
		
		
		return w;
	}
	
	
	
	//@override
	public void render(SmithPApplet pApplet) {
		pApplet.ellipse(radius, radius, 2*radius, 2*radius);
	}
	/**Enum for the location of where the ball hits on the walls */
	public enum Wall { LEFT, RIGHT, TOP, BOTTOM, TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT, NONE }
	
	/**Checks if the ball is hitting the wall at an instance 
	 * and returns which part of the wall it is hitting 
	 * */
	public Wall checkEdge(){
		Wall w = Wall.NONE;
		currX =  Math.abs(getLocation().getX()); 
		currY = Math.abs(getLocation().getY());

		if(currX - radius <= 0) {
			if(currY - radius < 0) {
				w= Wall.TOPLEFT;
			} else if  (currY + radius > canvasHeight) {
				w= Wall.BOTTOMLEFT;
			} else {
				w= Wall.LEFT;
			}
		} 
		else if (currX + radius >= canvasWidth) {
			if(currY - radius<0){
				w= Wall.TOPRIGHT;
			}
			else if( currY +radius >canvasHeight){
				w= Wall.BOTTOMRIGHT;
			}
			else {
				w= Wall.RIGHT;
			}
		}
		else if(currY <radius){
			w= Wall.TOP;
		}
		else if(currY +radius > canvasHeight){			
			w= Wall.BOTTOM;			
		}
		
		return w;
	}	
	@Override 
	/** Checks if the sprite is on the edge and returns the opposite velocity*/
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		float vx = velocity.getX();
		float vy = velocity.getY();
		
		Wall w = checkEdge();
		if(w != Wall.NONE) {
			if (w == Wall.LEFT || w == Wall.TOPLEFT || w == Wall.BOTTOMLEFT) {
				if(vx < 0) {
					vx *= -1;
				}				
			}
			else if (w ==Wall.RIGHT || w ==Wall.TOPRIGHT || w == Wall.BOTTOMRIGHT){
				if (vx>0){
					vx *=-1;
				}
			}
			else if (w == Wall.TOP){
				if (vy <0){
					vy *=-1;
				}
			}
			else {
				
				if (vy >0){
					vy *=-1;
				
				}
			}
			velocity.set(vx,vy,0);
			
			location.add(vx*elapsedTime,
					vy*elapsedTime,
					0);			
		}
		else{
			
		location.add(
				velocity.getX() * elapsedTime,
				velocity.getY() * elapsedTime,
				velocity.getZ() * elapsedTime);		
		
		}
		
	}
}
