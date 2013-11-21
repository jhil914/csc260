package edu.smith.csc.csc260.util;

public class Color {
	float r;
	float g;
	float b;
	float a;
	
	
	public Color() {
		this(0,0,0,0);
	}
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(Color c) {
		this(c.r, c.g, c.b, c.a);
	}

	public float getR() { return r; }
	public float getG() { return g; }
	public float getB() { return b; }
	public float getA() { return a; }
	
	public void setR(float r) { this.r = r; }
	public void setG(float g) { this.g = g; }
	public void setB(float b) { this.b = b; }
	public void setA(float a) { this.a = a; }

	public void set(Color c) {
		r = c.r;
		g = c.g;
		b = c.b;
		a = c.a;
	}
}
