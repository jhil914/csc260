package edu.smith.csc.csc260.hjin;


import edu.smith.csc.csc260.core.SmithPApplet;



public class BlurButInkNotCleared extends SmithPApplet{



private static final long serialVersionUID=1L; //to get rid of the warning


private static final int winWidth=600; //Window's width

private static final int winHeight=300; //Window's height

private static final int frame_v=30; //Frame rate



//****BUG:The first extra drop is always at 0,0

//The last drop should stop splattering



//****To Modify:Make more than one ink drop for each frame 

//and create new ink drop for every 3 frames

//randomize angle of lines radiating from drop



public void setup(){

super.setup();


size(winWidth,winHeight);

frameRate(frame_v);

smooth();

colorMode(RGB, 100,100,100,100);

background(100,100,100,100);

}


//Two options:

//blur fade by drawing transparent rect

//OR blur filter to keep it and spread


public void draw(){

super.draw();


/*//Draw semi-transparent white rectangle over the entire screen to create fade effect

fill(100, 10); 

rect(0, 0, width, height);*/

 

//Drag and draw. Blur the drawing

if(mousePressed){

float speed=abs((pmouseX-mouseX));

strokeWeight(speed/5);

//strokeWeight(5);

line(pmouseX,pmouseY,mouseX,mouseY);

filter(BLUR,1.2f);



}


//If mouse is not pressed, mouse acts like brush.

//Ink drops splatter under the mouse.


else{


fill(0); //black ink drop


//random radius

float dropRad=randomizeFloat(0,15f);



translate(pmouseX,pmouseY);

//draw ink drop and blur it

ellipse(0,0,dropRad*2f,dropRad*2f);

filter(BLUR,1.8f);



//draw lines radiating from ink drop 

for(float angle=0;angle<2*PI; angle+=PI/6 ){//angle+=randomizeFloat(0,PI/6f) ){// angle=angle+randomizeFloat(0,PI/3f) ){

strokeWeight(2f);


float lenLine=randomizeFloat(0,20f);

line(dropRad* cos(angle), dropRad* sin(angle), (dropRad+lenLine)* cos(angle), (dropRad+lenLine)* sin(angle) );

}

}

}


/**Return random float number between min and max*/

public float randomizeFloat(float min, float max){

return min+(float)Math.random()*max;

}


}


