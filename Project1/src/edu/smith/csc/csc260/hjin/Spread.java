package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.spites.ConstantVelocitySprite;
import edu.smith.csc.csc260.spites.InterpolatedSprite;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;

public class Spread extends InterpolatedSprite{

	public Sprite sprite;
	
	public Spread(Point velocity, Sprite s) {
		
		sprite = s;
	}
	
	public void increase(){
		
	}
	

}
