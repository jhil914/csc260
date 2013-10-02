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
	
	public int checkEdge(){
		
		currX = Math.abs(getLocation().getX());
		currY = Math.abs(getLocation().getY());
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
	@Override
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		float vx = velocity.getX()*elapsedTime;
		float vy = velocity.getY()*elapsedTime;
		if(checkEdge()==-1){
			
			if(isit =="x"){
				
			vx = -vx;
			
			if(vy<0){
				vy*=-1;
			}
			}
			else if(isit =="y"){
				
				vy = -vy;
			if(vx<0){
				vx*=-1;
			}
			
			
			}
			else if(isit =="xy"){
				if(vx>0){
					vx = -vx;
				}
				if(vy>0){
				vy = -vy;
				}
			}
			
			System.out.println(vx+" "+vy);
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
