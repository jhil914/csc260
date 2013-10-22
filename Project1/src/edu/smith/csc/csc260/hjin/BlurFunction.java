package edu.smith.csc.csc260.hjin;


import java.util.ArrayList;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.interpolators.scalarInterpolators.LinearScalarInterpolator;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class BlurFunction extends SmithPApplet{
	private static final long serialVersionUID=1L; //to get rid of the warning
	
	float dropRad=10;
	int timer=0;
	boolean isDark=false;
	boolean inTransition=false;
	float speed=1;
	boolean isinBigTree=false;
	boolean isinSmallTree=false;
	
	float bigTreeX=150f;
	float smallTreeX=500f;
	float alpha = 0;
	boolean itis;
	int count;
	//Night
			public ArrayList<BouncingSprite> s;
			  
		    float[] xpos = new float[50]; 
			float[] ypos = new float[50];
		    public Point startP;
		    public Point endP;
		    
	//Sunlight
		// Declare two arrays with 50 elements.
			float[] xpos1 = new float[50]; 
			float[] ypos1 = new float[50];
			public int canvasH = 500;
			public int canvasW = 500;
			public int dim;

			float[] halo_color={0,0,0,60f};
	//BUG: fading during make day-night transition
	//add sunlight to daytime
	//add Hee Jin's code
	public void setup(){
		super.setup();
		
		size(600,300);
		frameRate(30);
		smooth();
		colorMode(RGB, 100,100,100,100);
		background(100,100,95); //yellowish //************
	
		fill(0);
		
		/*for(int m=0;m<10;m++){
			addSprite(createSquare());
		}*/
		 s=new ArrayList<BouncingSprite>();
		itis = false;
		count = 0;
	}

	
	public void draw(){
		
		super.draw();

		strokeWeight(2f);

		speed=abs( (pmouseX-mouseX)+(pmouseY-mouseY));
		println(timer);

		//wait till user swipe mouse for 6 frames
		if(speed>150 && timer<6){
			timer++;
		}
		
		/**TO NIGHT*/
		//no matter current speed, if user swipes mouse for more than 6 frames, transform to night
		if (timer>=6){
			if(!isDark){
				if(alpha<100){
					println("STARTS DARKNESS "+alpha);
					inTransition=true;
					fill(0,0,0, alpha);
					rect(0, 0, width, height);
					alpha+=5; //0.1;
					stroke(100-alpha);

				}
				//now is night time
				else{
					isDark=true;
					inTransition=false;
					background(0,0,0);
					timer=0;//reset timer for day transition
					alpha=0;
					isinSmallTree=false;
					isinBigTree=false;
					for(int i=0;i<halo_color.length;i++){
						halo_color[i]=randomizeFloat(80f,100f);
					}
				}
			}
			
			/**TO DAY*/
			//no matter current speed, if user swipes mouse for more than 6 frames, transform to day
			else{
				if(alpha<100){
					println("STARTS BRIGHTNESS "+alpha);
					inTransition=true;
					fill(100,100,95, alpha);
					rect(0, 0, width, height);
					alpha+=5; //0.1;
					stroke(alpha);
				}
				//now is day time
				else{
					isDark=false;
					inTransition=false;
					background(100,100,95);
					timer=0;//reset timer for day transition
					alpha=0;
					isinSmallTree=false;
					isinBigTree=false;
					
					//randomize halo color
					for(int i=0;i<halo_color.length;i++){
						halo_color[i]=randomizeFloat(0,100f);
					}
					
				}
			}
		}
		
		
		//for slow mouse motion, day time
		if(isDark){
			println("NIGHT "+alpha);
			
			if (!inTransition){
				stroke(100);
				if(!isinSmallTree){
					clearPrintsNight();
				}
			}
			
			drawDaytime();
			
		}
		
		//for fast mouse motion, night time
		else{
			println("DAY "+alpha);
			
			if (!inTransition){
				stroke(0);
				if(!isinSmallTree){
					clearPrintsDay();
				}
			}
			
			drawDaytime(); 

		}
	}
	public void mousePressed(){
		if(mousePressed){
			count++;
			if(count%2!=0){
			 setBackgroundColor(new Color(0,0,0, 255));
			itis =true;
			}
			else{
				itis = false;
			}
		}
	}
	
	public void mouseMoved(){
    	
    	if(itis){
  	collide();
  	  snake();
  	if(Math.abs(mouseX-pmouseX)==7){
  		
  if(s.size()<100){
  			BouncingSprite bs2 = new BouncingSprite(width, height,new Point((float) Math.random()/5, (float) Math.random()/5,0),(int) (Math.random()*20));
  			bs2.setLocation(new Point(mouseX,mouseY));
  			bs2.setFill(new Color((float) (Math.random()*255),(float) (Math.random()*255),(float) (Math.random()*255),150));
  			
  			bs2.setNoStroke(true);
  			addSprite(bs2);
  		s.add(bs2);
  	}
		
    }
  	 
    	}
    }
	public void collide(){
		   
		   for(int i = 0; i<s.size();i++){
	   		BouncingSprite bounce = s.get(i);
	   		float dx =mouseX-bounce.getLocation().getX();
	   		float dy = mouseY-bounce.getLocation().getY();
	   		float dis = (float) Math.sqrt(dy*dy+dx*dx);
	   		float vx = bounce.getvel().getX();
	   		float vy = bounce.getvel().getY();
	   	

				
			
	   		if(Math.abs(mouseX-pmouseX)>=8){
	   		if(dis<=50+bounce.getRadius()){
	   			System.out.println("collide");
	   			if(mouseX-pmouseX<0){//going left
	   				
	   					bounce.setVel(vx,-vy);
	   				
	   				
	   			}
	   			
	   			else{
	   				
	   					bounce.setVel(vx,-vy);
	   			
	   				

	   			}
	   		}
	   		}
	   		}
	   }
	 public void snake(){
		 System.out.println("snake");
    	 for (int i = 0; i < xpos.length-1; i ++ ) {
 	  	    // Shift all elements down one spot. 
 	  	    // xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at the second to last element.
 	  	    xpos[i] = xpos[i+1];
 	  	    ypos[i] = ypos[i+1];
 	  	  }
 	  	  
 	  	  // New location
 	  	  xpos[xpos.length-1] = mouseX; // Update the last spot in the array with the mouse location.
 	  	  ypos[ypos.length-1] = mouseY;
 	  	 
 	  	  
 	  	for (int i = 0; i < xpos.length; i++) {
 		     // Draw an ellipse for each element in the arrays. 
 		     // Color and size are tied to the loop's counter: i.
 		    noStroke();
 		    fill((int)255-i*255/xpos.length);
 		    ellipse(xpos[i],ypos[i],i*50/xpos.length,i*50/xpos.length);
 		    
 		  }
    }
	public void clearPrintsNight(){
		//Draw semi-transparent white rectangle over the entire screen to create fade effect
		fill(0,0,0,10); 
		rect(0, 0, width, height);
	}
	
	public void clearPrintsDay(){
		//Draw semi-transparent white rectangle over the entire screen to create fade effect
		fill(100,100,95, 20); 
		rect(0, 0, width, height);
	}
	
	public void drawDaytime(){
		
		filter(BLUR,1f);
		
		//draw plant
		translate(bigTreeX,height); 
		drawBranch_r((height)/4f , PI/4,(height)/4f , PI/4, 0.3f, 0.3f, 0.4f, 0.5f ); //k*PI/4);
		translate(-bigTreeX, -height);
		
		translate(smallTreeX,height); 
		drawBranch_r((height)/8f , PI/4,(height)/8f , PI/4, 0.3f, 0.5f, 0.1f, 0.2f);
		translate(-smallTreeX,-height); 


		//if(pmouseX>=0){ ///////////
			dropRad=randomizeFloat(1f,15f);
			
			if(isinBigTree){
				translate(  (bigTreeX-50f) +pmouseX*100f/width , (height/2f+50f) +pmouseY*(height/2f-100f)/height ); 
				drawLongerLegs();
				println("Aggressive");


			}
			else if(isinSmallTree){
				translate(smallTreeX-25f+pmouseX*50f/width,height-50f+pmouseY*50f/height); 
				drawConstantLegs();
				println("Peaceful");
								
			}
			else{
				
				translate(pmouseX,pmouseY);
				

				//if spider touches aggressive tree
				if(pmouseX> bigTreeX-50f && pmouseX<bigTreeX+50f &&
						pmouseY>height/2f+50f && pmouseY<=height-50f ){
					isinBigTree=true;
				}
				
				//if spider touches peaceful tree
				else if(pmouseX>smallTreeX-25f && pmouseX<smallTreeX+25f &&
						pmouseY>height-50f && pmouseY<=height ){
					isinSmallTree=true;
				}
			}

			fill(0); //black ink drop
			ellipse(0,0,dropRad*2f,dropRad*2f);
			drawConstantLegs();
			translate(-pmouseX,-pmouseY);
			
			drawSunLight();
			
		//}
	}
	
	public void drawConstantLegs(){
		//draw ink drop and blur it
		
		//draw lines radiating from ink drop 
		for(float angle=0;angle<2*PI; angle+=PI/6 ){//angle+=randomizeFloat(0,PI/6f) ){// angle=angle+randomizeFloat(0,PI/3f) ){
			strokeWeight(2f);
			
			float lenLine=randomizeFloat(0,20f);
			line(dropRad* cos(angle), dropRad* sin(angle), (dropRad+lenLine)* cos(angle), (dropRad+lenLine)* sin(angle) );
		}
	}
	
	public void drawLongerLegs(){
				
		//draw lines radiating from ink drop 
		//for(float angle=0;angle<2f*PI; angle++ ){//angle+=randomizeFloat(0,PI/6f) ){// angle=angle+randomizeFloat(0,PI/3f) ){
		float angle=0f;
		while( angle<2f*PI){	
			strokeWeight(2f);
			
			float lenLine=randomizeFloat(0,50f);

			angle+=randomizeFloat(0,PI/8); //0 to 45 deg
			line(dropRad* cos(angle), dropRad* sin(angle), (dropRad+lenLine)* cos(angle), (dropRad+lenLine)* sin(angle) );
		}
		
	}
	
	/**Return random float number between min and max*/
	public float randomizeFloat(float min, float max){
		return min+(float)Math.random()*max;
	}
	
	public long randomizeDuration(long min, long max){
		return min+(long)(Math.random()*max);
	}
	
	public void drawBranch_r(float h, float angle,float h1, float angle1, float h_i, float angle_i, float h_range, float angle_range){
		
		h*=randomizeFloat(h_i,h_i+h_range);
		angle*=randomizeFloat(angle_i,angle_i+angle_range);//0.7f);
		
		h1*=randomizeFloat(h_i, h_i+h_range);//0.5f);
		angle1*=randomizeFloat(angle_i,angle_i+angle_range);
		
		if(h>2){
			pushMatrix();
			rotate(angle);
			line(0,0,0,-h);
			translate(0,-h); //move to end of branch
			drawBranch_r(h, angle,h1, angle1, h_i, angle_i, h_range, angle_range);
			popMatrix();
			
			
			pushMatrix();
			rotate(-angle1);
			line(0,0,0,-h1);
			translate(0,-h1); //move to end of branch
			drawBranch_r(h, angle,h1, angle1, h_i, angle_i, h_range, angle_range);
			popMatrix();
			
		}
		else{
			//ellipse(0,0,10f,10f);
			
		}
		
	}
	
	
	public void drawSunLight(){
		 float r = 0;
		  float theta = 0;
		  // Shift array values
		  for (int i = 0; i < xpos1.length-1; i ++ ) {
		    // Shift all elements down one spot. 
		    // xpos[0] = xpos[1], xpos[1] = xpos = [2], and so on. Stop at the second to last element.
		    xpos1[i] = xpos1[i+1];
		    ypos1[i] = ypos1[i+1];
		  }
		  
		  // New location
		  xpos1[xpos1.length-1] = mouseX; // Update the last spot in the array with the mouse location.
		  ypos1[ypos1.length-1] = mouseY;
		  
		  // Draw everything
		  for (int i = 1; i < xpos1.length; i = i*2) {
		     // Draw an ellipse for each element in the arrays. 
		     // Color and size are tied to the loop's counter: i.
		   
		    for(int j =1; j<xpos1.length; j++){
		    	
		    	float x =  cos(theta);

		    	float y =  4*sin(theta);

		    	noStroke();

		    	fill((int)halo_color[0]-j*100/xpos1.length,(int)halo_color[1]-j*100/xpos1.length,(int)halo_color[2]-j*100/xpos1.length,60);
		    	
		    	ellipse(xpos1[j]*x , ypos1[j]*y, i*60/xpos1.length,i*60/xpos1.length);
		    	//ellipse((float) ((float)xpos[j]*x*Math.random()) , (float) ((float)ypos[j]*y*Math.random()), 16, 16);
		    	theta += j*3;

		    	r=(float) (r+0.05*j);
		   // ellipse(xpos[j]*j/xpos.length,ypos[j]*j/xpos.length,j*100/xpos.length,j*100/xpos.length);
		    }
		  
		  
		  }
	}
	
}
