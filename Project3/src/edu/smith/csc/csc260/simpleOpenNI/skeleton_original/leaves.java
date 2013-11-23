package edu.smith.csc.csc260.simpleOpenNI.skeleton_original;

import processing.core.PImage;
import edu.smith.csc.csc260.core.SmithPApplet;

public class leaves extends SmithPApplet{
	final boolean IN_BROWSER=true;
	//ifnot IN_BROWSER_then_uncomment_import processing.opengl.*;//*/
	 
	float mod(float v, float m){ v=v/m; return (v-floor(v))*m; }
	float mix(float p,float a,float b){ return (float) (a*(1.0-p)+p*b); }
	float gauss(float x){ return exp((float) (-x*x/2.0)) / sqrt(2*PI); }
	float gaussI(float z){ // integrated gauss [0..1]
	  if(z<-8.0) return 0.0f;
	  if(z> 8.0) return 1.0f;
	  float sum=0.0f, term=z;
	  for (int i=3; sum+term!=sum; i+=2){
	    sum =sum+term;
	    term=term*z*z/i;
	  }
	  return (float) (0.5+sum*gauss(z));
	}
	float gaussE(float z){ return gaussI(z)*2-1; }// gauss error func==> [-1..0..1]
	float fade01trig(float v){ return (float) (sin((float) (2.0*PI*v+PI))/(2.0*PI)+v); }
	float fadealpha01(float p){ return (float) (sin((float) (PI*p-PI/2.0))/2.0+0.5); }
	float randomGauss(){
	 float x=0,y=0,r,c;
	  do{ x=random(-1.0f,1.0f);
	      y=random(-1.0f,1.0f);
	      r=x*x+y*y;
	  }while(r==0 || r>1);
	  c=sqrt((float) (-2.0*log(r)/r));
	  return x*c; //return [x*c, y*c];
	}
	float randomGaussIn(float L, float H, float mul){ return constrain( (float) (randomGauss()*(H-L)*mul + (L+H)/2.0)  ,L,H);  }
	float randomGaussAt(float L, float H, float mul){ return (float) (randomGauss()*(H-L)*mul + (L+H)/2.0);  }
	float sin1(float v){ return sin(2*PI*v); }
	float cos1(float v){ return cos(2*PI*v); }
	//////////////////////////////////////////////////////////////////////////////
	float roadWidth=300.0f;          //on x, half width
	float roadHWidth=roadWidth/2.0f;
	float roadLength=1000.0f;         //on y
	float picH=25.0f;                 //on z
	float picW=picH;                 //on z
	float perspectiveMul=1.0f; //=H*old perspectiveMul
	float roadWidth0Mul=5.0f;
	float roadWidth1Mul=10.0f;
	float picH1Mul=0.3f;
	float maxPicH=picH*1.0f;
	 
	class Swirl{
	  float dx,dy;
	  void calc(float x,float y){
	    dx=sin1(x)*sin1(y);
	    dy=cos1(x)*cos1(y);
	  }
	}
	 
	class Bar{
	  int id;
	  float zoom;
	  float px,py;
	  float colr,colg,colb,cola;
	  Bar(int eid, float percent){ id=eid;
	    py=random(0.0f,roadLength);
	    py=roadLength*(1.0f-percent);
	    //px=random(-roadHWidth,roadHWidth);
	    px=randomGaussAt(-roadHWidth,roadHWidth,(float) 0.5);
	    zoom=exp(randomGaussAt(-0.5f,0.5f,0.5f));
	    cola=noise(-3,id);
	    colr=constrain(  (noise(-4,id)-0.5f)*512.0f+128.0f   ,0.0f,256.0f);
	    colg=constrain(  (noise(-5,id)-0.5f)*512.0f+128.0f   ,0.0f,256.0f);
	    colb=constrain(  (noise(-6,id)-0.5f)*512.0f+128.0f   ,0.0f,256.0f);
	    float L=min(colr,min(colg,colb));
	    float H=max(colr,max(colg,colb));
	    colr=map(colr  ,L,H  ,30.0f,256.0f);
	    colg=map(colg  ,L,H  ,30.0f,256.0f);
	    colb=map(colb  ,L,H  ,30.0f,256.0f);
	  }
	  float x,y,z,y0,y1,x0,x1,z0,z1,czoom;
	  void calc(float ex, float ey, float ez){
	    ////road+leaf motion
	    //calc road px,py here
	    float vxs=1.5f/roadWidth;
	    float vys=1.5f/roadWidth;
	    float vx=py*vxs;
	    float vy=px*vys;
	    Swirl s1=new Swirl();
	    Swirl s2=new Swirl();
	    Swirl s3=new Swirl();
	    Swirl s4=new Swirl();
	    for(int i=0;i<1;i++){
	      //Runge-Kutta//
	      float h=0.005f;
	      //h=h*(noise(t/100.0,vx,vy)*2.0-0.7);
	      //float vspeed=1.0+1.0/(0.2+  1.0-sq(8.0*sin1(vx)*cos1(vy))  );
	      //h=h*vspeed;
	      s1.calc(vx,vy);
	      s2.calc(vx+h*s1.dx/2.0f,vy+h*s1.dy/2.0f);
	      s3.calc(vx+h*s2.dx/2.0f,vy+h*s2.dy/2.0f);
	      s4.calc(vx+h*s3.dx,vy+h*s3.dy);
	      vx+=h*(s1.dx+s4.dx + 2.0*(s2.dx+s3.dx))/6.0;
	      vy+=h*(s1.dy+s4.dy + 2.0*(s2.dy+s3.dy))/6.0;
	    }
	    py=vx/vxs;
	    px=vy/vys;
	 
	    y0=perspectiveMul*(roadWidth/roadWidth0Mul)/W;  //sx=W/2=perspectiveMul*roadWidth/2/y0;  // road full screen width if ez=0
	    z0=(H/2.0f*0.0f)*y0/perspectiveMul +ez;          //sy=H/2.0=perspectiveMul*z0/y0;         // road touch screen bottom
	    if(IN_BROWSER){ z0=(H/2.0f*0.5f)*y0/perspectiveMul +ez;  }
	     
	    y1=y0+roadLength;
	    z1=-H/2.0f*y1/perspectiveMul+maxPicH;         //sy=-H/2.0=perspectiveMul*(z1+maxPicH)/y1;
	 
	    y=mod(py+ey,roadLength);
	 
	    /// more leafs near
	    y=y/roadLength;
	    y=mix(0.0f,y,sq(y));
	    y=y*roadLength;
	 
	    y=y+y0;
	    czoom=map(y,y0,y1,zoom,zoom*picH1Mul);
	 
	    //sx=W/2=perspectiveMul*(x1max+roadWidth/2)/y1  //road max x position at y1
	    float curRoadWidthMul=map(y,y0,y1,1.0f,roadWidth1Mul);
	    float x1max=(y1/perspectiveMul*W-roadWidth)/2.0f;
//	     x=map(y,y0,y1,px*curRoadWidthMul-ex,
//	         (noise((y+ey)/roadLength/2.0)*2.0-1.0) *x1max  // road "snakiness"
//	       );
	    x=map(y,y0,y1,px*curRoadWidthMul-ex,
	        (sin((y+ey)/roadLength/2.0f)) *x1max  // road "snakiness"
	      );
	 
	    z=map(y,y0,y1,z0,z1);
	 
	    z+=(picH*y0/6.0)/(y-y0);  // near leafs dive down
	  }
	 
	  void draw(int mode){
	    float sx,sy;
	    sx=        perspectiveMul/y* x;
	    sy=        perspectiveMul/y* z;
	    float boxS=perspectiveMul/y* czoom*picH;
	    //tint(255,200);
	    //tint(255,map(y,0.0,1.0, -16.0+64.0*noise(-3,id),255.0*0.8));
	    float alpha=map( (y-y0)/roadLength*4.0f,    0.0f,1.0f,    1.0f+64.0f*cola, 255.0f*0.9f );
	    //final float maxBoxS=100.0;  if(boxS>maxBoxS) boxS=((sqrt(boxS/maxBoxS)))*maxBoxS;
	    //alpha=alpha*0.7;
	    tint(colr,colg,colb, alpha);
	    image(img,CX+sx-boxS/2.0f,(H/2.0f+sy)-boxS/2.0f,boxS,boxS);
	  }
	}
	 
	 
	float cirad=40.0f;
	float defx=10.0f;
	float  dx, dy, dz;
	float ddx,ddy,ddz;
	float  ex, ey, ez;
	int n=400;
	Bar[] a;
	int Wi,Hi;
	float W,H;
	float CX,CY;
	PImage img;
	float t=0.0f;
	 
	public void setup(){
	  if(IN_BROWSER){ Wi=900; Hi=400;
	           }else{ Wi=width; Hi=height-80; }
	  W=Wi; H=Hi;
	  //size(Wi,Hi, P2D);
	  if(IN_BROWSER){ size(Wi, Hi, P3D);
	           }else{ size(Wi, Hi, OPENGL); }
	  perspectiveMul=perspectiveMul*H;
	  CX=W/2.0f;
	  CY=H/2.0f;
	  background(0);
	  noStroke();
	  //noSmooth();
	  a=new Bar[n];
	  for(int i=0;i<n;i++){ a[i]=new Bar(i,(float)i/n ); }
	   dx=0.0f;  dy=0.0f;  dz=0.0f;
	  ddx=0.0f; ddy=0.0f; ddz=0.0f;
	   ex=0.0f;  ey=0.0f;  ez=0.0f;
	  dy=-10.0f;
	  //img=loadImage("kytka.gif");
	  img=loadImage("stars.png");
	 
	}
	 
	public void draw(){
	  dx+=-ddx;
	  dy+=-ddy;
	  dz+=+ddz;
	  if(ddx==0.0){ dx*=0.99; }
	  if(ddy==0.0){ dy*=0.99; }
	  if(ddz==0.0){ dz*=0.99; }
	  ex+=dx*0.3;
	  ey+=dy*0.3;
	  ez+=dz*0.2;
	  background(0);
	  for(int i=0;i<n;i++){ a[i].calc(ex,ey,ez+sq(dy)/100.0f); }
	  for(int i=0;i<n;i++){ a[i].draw(0); }
	  t++;
	}
	 
	public void keyPressed() {
	  if(key==CODED){
	    if(      keyCode==LEFT){  ddx=+1;
	    }else if(keyCode==RIGHT){ ddx=-1;
	    }else if(keyCode==UP){    ddy=+1;
	    }else if(keyCode==DOWN){  ddy=-1;
	    }
	  }else if(key==','){ ddz=-1;
	  }else if(key=='.'){ ddz=+1;
	  }
	  redraw();
	}
	public void keyReleased() {
	  if(key==CODED){
	    if(      keyCode==LEFT){  ddx=0;
	    }else if(keyCode==RIGHT){ ddx=0;
	    }else if(keyCode==UP){    ddy=0;
	    }else if(keyCode==DOWN){  ddy=0;
	    }
	  }else if(key==','){ ddz=0;
	  }else if(key=='.'){ ddz=0;
	  }
	  redraw();
	}

}
