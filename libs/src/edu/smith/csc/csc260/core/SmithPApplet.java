package edu.smith.csc.csc260.core;

import java.util.concurrent.ConcurrentLinkedQueue;

import processing.core.PApplet;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Color;

public class SmithPApplet extends PApplet {
	
	private static final long serialVersionUID = 1L; // added to avoid warning message
	
	private long startTime;
	protected long lastTime;
	protected long curTime;
	protected long elapsedTime;
	
	ConcurrentLinkedQueue<Sprite> renderList = new ConcurrentLinkedQueue<Sprite>(); // list of sprites to draw
	ConcurrentLinkedQueue<Sprite> newSpriteList = new ConcurrentLinkedQueue<Sprite>(); // list of sprites to draw
	
	Color bgColor = null;
	
	public void setup() {
		startTime = System.currentTimeMillis();
		lastTime = 0;
	}
	
	public void draw() {
		render();
		// there will be other stuff here in the future
	}
	
	public void setBackgroundColor(Color color) {
		this.bgColor = color;
	}
	public Color getBackgroundColor() {
		return bgColor;
	}
	
	public void setNoBackgroundColor(boolean b) {
		if(b) {
			bgColor = null;
		} else {
			if (bgColor == null) {
				bgColor = new Color(0,0,0,255);				
			}
		}
	}
	public boolean getNoBackgroundColor() {
		return bgColor == null;
	}
	public void size(int w, int h) {
		super.size(w, h, P2D);
	}
	
	public void render() {
		 curTime = System.currentTimeMillis() - startTime;
		 elapsedTime = curTime - lastTime;

		if(bgColor != null) {
			background(bgColor.getR(), bgColor.getG(), bgColor.getB(), bgColor.getA());
		}
		for(Sprite sprite : newSpriteList) {
			sprite.setup(this);
			renderList.add(sprite);
		}
		newSpriteList.clear();
		
		for(Sprite sprite : renderList) {
			sprite.display(this, curTime, lastTime, elapsedTime);
		}
		lastTime = curTime;
	}
	
	public void addSprite(Sprite sprite) {
		newSpriteList.add(sprite);
	}
	public void removeSprite(Sprite sprite) {
		newSpriteList.remove(sprite);
		renderList.remove(sprite);
	}

	/**
	 * @return the lastTime
	 */
	public long getLastTime() {
		return lastTime;
	}

	/**
	 * @return the curTime
	 */
	public long getCurTime() {
		return curTime;
	}

	/**
	 * @return the elapsedTime
	 */
	public long getElapsedTime() {
		return elapsedTime;
	}
}
