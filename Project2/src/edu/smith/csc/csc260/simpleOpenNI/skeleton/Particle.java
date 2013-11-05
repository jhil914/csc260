package edu.smith.csc.csc260.simpleOpenNI.skeleton;

import edu.smith.csc.csc260.core.SmithPApplet;
import processing.core.PImage;
import processing.core.PVector;


public class Particle extends SmithPApplet{
  PVector location;
  PVector velocity;
  PVector acceleration;
  PImage petal;
  float lifespan;
   
  float mass = 1; // Let's do something better here!
 
  public Particle(PVector l) {
    acceleration = new PVector(0,2);
    velocity = new PVector(random(-2, 2),random(-2,0));
    location = l.get();
    lifespan = (float) 255.0;
  }
 
  public void run() {
    update();
    display();
  }
 
  void applyForce(PVector force) {
    PVector f = force.get();
    f.div(mass);  
    acceleration.add(f);
  }
 
  // Method to update location
  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    acceleration.mult(0);
    lifespan -= 1.0;
  }
 
  //particles made up of petal image - slows rendering a little
  void display() {
    petal = loadImage("petal.png");
    tint(255, lifespan);
    image(petal, location.x, location.y, 10, 11);
  }
 
  // Is the particle still useful?
  boolean isDead() {
    if (lifespan < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}

