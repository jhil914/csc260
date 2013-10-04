package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.ConstantVelocitySprite;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;

public class BouncingSprite extends ConstantVelocitySprite{
	public int canvasHeight;
	public int canvasWidth;
	public int isEdge;
	public int currX;
	public int currY;
	public float radius;
	public String isit;
	public BouncingSprite(int W, int H,Point v){
		super(v, 0);
		canvasHeight = H;
		canvasWidth = W;
		isEdge = 1;
		radius = 10;
		
		isit = " ";
		
	}
	//@override
	public void render(SmithPApplet pApplet) {
		pApplet.ellipse(radius, radius, 2*radius, 2*radius);
	}
	
	public enum Wall { LEFT, RIGHT, TOP, BOTTOM, TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT, NONE }
	
	public Wall checkEdge(){
		Wall w = Wall.NONE;
		currX = (int)  Math.abs(getLocation().getX());
		currY = (int) Math.abs(getLocation().getY());

		if(currX - radius < 0) {
			if(currY - radius < 0) {
				w= Wall.TOPLEFT;
			} else if  (currY + radius > canvasHeight) {
				w= Wall.BOTTOMLEFT;
			} else {
				w= Wall.LEFT;
			}
		} 
		else if (currX + radius > canvasWidth) {
			if(currY - radius<0){
				w= Wall.TOPRIGHT;
			}
			else if( currY +radius >canvasHeight){
				w= Wall.BOTTOMRIGHT;
			}
			else {
				System.out.println("curr x: "+currX);
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
	}	/*	
		
		System.out.println(currX+" "+currY);
		if((currX <=radius || currX >=canvasWidth-radius)&&(currY <=radius || currY >=canvasHeight-radius)){
			isit = "xy";
		}
		
		else if(currX <=radius || currX >=canvasWidth-radius){
			
			isEdge*=-1;
			
			isit = "x";
			
		}
		else if(currY <=radius || currY >=canvasHeight-radius){
			isEdge *= -1;
			isit = "y";
		}
		return isEdge;
	}
	*/ 
	@Override
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		float vx = velocity.getX()*elapsedTime;
		float vy = velocity.getY()*elapsedTime;
		
		Wall w = checkEdge();
		if(w != Wall.NONE) {
			System.out.println("edge");
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
				if (vy >0){
					vy *=-1;
				}
			}
			else {
				System.out.println("bottom");
				if (vy >0){
					vy *=-20;
					System.out.println(vy);
				}
			}
			
	
			location.add(vx,
					vy,
					0);
			
		}
		else{
			System.out.println("it is not on the edge");
		location.add(
				velocity.getX() * elapsedTime,
				velocity.getY() * elapsedTime,
				velocity.getZ() * elapsedTime);
		
		
		}
		
		//System.out.println(velocity.getX()*elapsedTime+" "+velocity.getY()*elapsedTime);
	}
}
