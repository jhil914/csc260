package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.ConstantVelocitySprite;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;

public class swirl extends ConstantVelocitySprite{
	public Sprite bs;
	public swirl(Sprite s,Point v){
		
		super(v,0);
		bs = s;
		
	}
	
	
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		
    	float vx = velocity.getX()*2;
		float vy = velocity.getY();
		
		
			velocity.set(vx,vy,0);
			
			location.add(vx*elapsedTime,
					vy*elapsedTime,
					0);			
    }
}
