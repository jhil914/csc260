package edu.smith.csc.csc260.hjin;

import SimpleOpenNI.SimpleOpenNI;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Color;

public class BodypartTrackingSprite extends Sprite {
	BodypartTrackingApplet handView;
	int id;
	int bodyPart;
	float conf = 0;
PShape s,h,b;
	public BodypartTrackingSprite(BodypartTrackingApplet handView, int id, int bodyPart) {
		super();
		this.handView = handView;
		this.id = id;
		this.setFill(new Color((float)Math.random() * 255f, (float)Math.random() * 255f, (float) Math.random() * 255f, 200f));
		this.bodyPart = bodyPart;
	}

	@Override
	public void render(SmithPApplet pApplet) {
		if(conf > 0)
			if(bodyPart == SimpleOpenNI.SKEL_LEFT_HAND){
				pApplet.rect(0, 0, 30, 30);
			}
			/**else if(bodyPart == SimpleOpenNI.SKEL_HEAD){
				s=pApplet.createShape(pApplet.GROUP);
				
				h = pApplet.createShape(pApplet.ELLIPSE, -25, 0, 50, 50);
				  h.setFill(pApplet.color(255));
				  b = pApplet.createShape(pApplet.RECT, -25, 45, 50, 40);
				  b.setFill(pApplet.color(0));

				  // Add the two "child" shapes to the parent group
				  s.addChild(b);
				  s.addChild(h);
				  pApplet.translate(50, 15);
				pApplet.shape(s);
				
			}*/
			else{
			pApplet.ellipse(0, 0, 30, 30);
	}
	}


	PVector pvec = new PVector();
	PVector pvec2d = new PVector();
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		conf = handView.simpleOpenNI.getJointPositionSkeleton(id, bodyPart, pvec);

		if (conf != 0) {
			handView.simpleOpenNI.convertRealWorldToProjective(pvec,pvec2d);
			this.location.set(pvec2d.x, pvec2d.y, 0);

		}
	}
	
	

}
