package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.ConstantVelocitySprite;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;

public class BouncingSprite extends ConstantVelocitySprite{
	public int canvasHeight;
	public int canvasWidth;
	public int isEdge;
	public float currX;
	public float currY;
	public float radius;
	public boolean isX;
	public boolean isY;
	public String isit;
	public BouncingSprite(int W, int H,Point v){
		super(v, 0);
		canvasHeight = H;
		canvasWidth = W;
		isEdge = 1;
		radius = 10;
		isX = false;
		isit = " ";
		
	}
	//@override
	public void render(SmithPApplet pApplet) {
		pApplet.ellipse(radius, radius, 2*radius, 2*radius);
	}
	
	public int checkEdge(){
		
		currX = Math.abs(getLocation().getX());
		currY = Math.abs(getLocation().getY());
		System.out.println(currX+" "+currY);
		if(currX <=radius || currX >=canvasWidth-radius){
			System.out.println(currX+"is smaller than "+radius);
			System.out.println(canvasWidth-radius);
			isEdge*=-1;
			isX =true;
			isit = "x";
			
		}
		 if(currY <=radius || currY >=canvasHeight-radius){
			isEdge *= -1;
			isY = true;
			isit = "y";
		}
		return isEdge;
	}
	@Override
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		
		if(checkEdge()==-1){
			if(isit =="x"){
			location.add(-velocity.getX()*elapsedTime,
					-velocity.getY()*elapsedTime ,
					0);
			//Maybe I should not seperate location add... may be I should combine
			//it in some way... wtf..
			
			}
			else if(isit =="y"){
				
				location.add(velocity.getX()*elapsedTime,
						-velocity.getY()*elapsedTime ,
						0);
			
			
			}
			System.out.println("isit: "+isit);
		}
		else{
		location.add(
				velocity.getX() * elapsedTime,
				velocity.getY() * elapsedTime,
				velocity.getZ() * elapsedTime);
		
		
		}
		
		//System.out.println(velocity.getX()*elapsedTime+" "+velocity.getY()*elapsedTime);
	}
}
