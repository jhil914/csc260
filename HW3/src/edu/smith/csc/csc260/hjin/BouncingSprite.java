package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.ConstantVelocitySprite;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;

public class BouncingSprite extends ConstantVelocitySprite{
	public int canvasHeight;
	public int canvasWidth;
	public boolean isEdge;
	public float currX;
	public float currY;
	public float radius;
	public BouncingSprite(int W, int H,Point v, float av){
		super(v, av);
		canvasHeight = H;
		canvasWidth = W;
		isEdge = false;
		radius = 10;
		
	}
	//@override
	public void render(SmithPApplet pApplet) {
		pApplet.ellipse(radius, radius, 2*radius, 2*radius);
	}
	
	public boolean checkEdge(){
		
		currX = location.getX();
		currY = location.getY();
		System.out.println(currX+" "+currY);
		if(currX <=radius || currX >=canvasWidth-radius){
			System.out.println(currX+"is smaller than "+radius);
			System.out.println(canvasWidth-radius);
			isEdge = true;
			
		}
		else if(currY <=radius || currY >=canvasHeight-radius){
			isEdge = true;
			
		}
		return isEdge;
	}
	@Override
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		
		if(checkEdge()){
			System.out.println("update ");
			location.add(-velocity.getX() ,
					-velocity.getY() ,
					-velocity.getZ() );
		}
		else{
		location.add(
				velocity.getX() * elapsedTime,
				velocity.getY() * elapsedTime,
				velocity.getZ() * elapsedTime);
		
		angle+=angularVelocity * elapsedTime;
		}
		//System.out.println(velocity.getX()*elapsedTime+" "+velocity.getY()*elapsedTime);
	}
}
