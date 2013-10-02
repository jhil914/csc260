package edu.smith.csc.csc260.hjin;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.interpolation.easing.LinearEasingFunction;
import edu.smith.csc.csc260.interpolators.pointInterpolators.LinearPointInterpolator;
import edu.smith.csc.csc260.interpolators.scalarInterpolators.LinearScalarInterpolator;
import edu.smith.csc.csc260.spites.ConstantVelocitySprite;
import edu.smith.csc.csc260.spites.InterpolatedSprite;
import edu.smith.csc.csc260.spites.PShapeSprite;
import edu.smith.csc.csc260.spites.RectangleSprite;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;


	public class hw3 extends SmithPApplet {
		private static final long serialVersionUID = 1L;
		public int windowWidth = 200;
		public int windowHeight = 100;
		public void setup() {
			super.setup();
			
			size(windowWidth,windowHeight);
			frameRate(30);
			setBackgroundColor(new Color(70,50,70, 255));
			
			CircleSprite s = new CircleSprite(20);
			s.setLocation(new Point(5,15));
			s.setFill(new Color(255,0,0,200));
			
			BouncingSprite bs = new BouncingSprite(windowWidth, windowHeight,new Point(.09f, .1f,0));
			bs.setLocation(new Point(30,50));
			bs.setFill(new Color(255,0,0,200));
			addSprite(bs);
			
		/**	RectangleSprite r = new RectangleSprite(10, 30);
			r.setLocation(new Point(5, 15));
			r.setFill(new Color(255,0,0,255));
			addSprite(r);
			
			r = new RectangleSprite(10, 30);
			r.setLocation(new Point(5, 15));
			r.setNoStroke(true);
			r.setAngle((float)Math.PI * 45.0f/180.0f);
			r.setFill(new Color(255,255,0,100));
			addSprite(r);
			
			PShapeSprite ps = new PShapeSprite();
			ps.setLocation(new Point(100, 100));
			addSprite(ps);
			*/
			/**ConstantVelocitySprite cvs = new ConstantVelocitySprite(new Point(.01f, 0,0),0); 
			addSprite(cvs);
			cvs = new ConstantVelocitySprite(new Point(0, .01f, 0),0); 
			addSprite(cvs);
			//cvs = new ConstantVelocitySprite(new Point(.01f, .01f,0), .01f); 
			//addSprite(cvs);
			
			InterpolatedSprite is = new InterpolatedSprite();
			is.setFill(new Color(0,200,255, 150));
			is.setLocationInterpolator(new LinearPointInterpolator(
					new Point(20, 180), 
					new Point(180,20),
					new LinearEasingFunction(10*1000, 20* 1000, 3)));

			is.setAngleInterpolator(new LinearScalarInterpolator(
					0, 
					(float)(2 * Math.PI),
					new LinearEasingFunction(10*1000, 15* 1000, 0)));

			addSprite(is);
			*/
			InterpolatedSprite eis = new InterpolatedSprite();
			eis.setFill(new Color(0,200,200,100));
			eis.setLocationInterpolator(new Rectinterpolate(
					new Point(100,150),
					40.0f,
					new LinearEasingFunction(5*1000, 10*1000,0)));
			addSprite(eis);
		}
		


	}


