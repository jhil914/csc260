package edu.smith.csc.csc260.core;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import processing.core.PApplet;
import processing.core.PGraphics;
import edu.smith.csc.csc260.simpleOpenNI.skeleton_original.Sprite;
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

	public PGraphics getPicker() {
		//		return pickBuffer;
		return null;
	}
	

	public void init() {
		super.init();
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

	/****
	 * This section of code replicates the functionality of listeners so Sprites and listen for events like one would with a normal gui program
	 */

	Vector<KeyListener> keyListeners = new Vector<KeyListener>();
	Vector<MouseListener> mouseListeners = new Vector<MouseListener>();
	Vector<MouseMotionListener> mouseMotionListerns = new Vector<MouseMotionListener>();
	Vector<MouseWheelListener> mouseWheelListerns = new Vector<MouseWheelListener>();
	Vector<FocusListener> focusListeners = new Vector<FocusListener>();

	@Override
	public void addKeyListener(KeyListener l) {
		super.addKeyListener(l);
		if(l != this) keyListeners.add(l);
	}

	@Override
	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		if(l != this) mouseListeners.add(l);
	}

	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		super.addMouseMotionListener(l);
		if(l != this) mouseMotionListerns.add(l);
	}

	@Override
	public void addMouseWheelListener(MouseWheelListener l) {
		super.addMouseWheelListener(l);
		if(l != this) mouseWheelListerns.add(l);
	}

	@Override
	public void addFocusListener(FocusListener l) {
		super.addFocusListener(l);
		if(l != this) focusListeners.add(l);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		super.mouseWheelMoved(arg0);
		for(MouseWheelListener l : mouseWheelListerns) {
			l.mouseWheelMoved(arg0);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		super.mouseDragged(arg0);
		for(MouseMotionListener l : mouseMotionListerns) {
			l.mouseDragged(arg0);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		super.mouseMoved(arg0);
		for(MouseMotionListener l : mouseMotionListerns) {
			l.mouseMoved(arg0);
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		super.mouseClicked(arg0);
		for(MouseListener l : mouseListeners) {
			l.mouseClicked(arg0);
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		super.mouseEntered(arg0);
		for(MouseListener l : mouseListeners) {
			l.mouseEntered(arg0);
		}

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		super.mouseExited(arg0);
		for(MouseListener l : mouseListeners) {
			l.mouseExited(arg0);
		}

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		super.mousePressed(arg0);
		for(MouseListener l : mouseListeners) {
			l.mousePressed(arg0);
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		super.mouseReleased(arg0);
		for(MouseListener l : mouseListeners) {
			l.mouseReleased(arg0);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		for(KeyListener l : keyListeners) {
			l.keyPressed(e);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		for(KeyListener l : keyListeners) {
			l.keyReleased(e);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		super.keyTyped(e);
		for(KeyListener l : keyListeners) {
			l.keyTyped(e);
		}

	}

	@Override
	public void focusGained(FocusEvent arg0) {
		super.focusGained(arg0);
		for(FocusListener l : focusListeners) {
			l.focusGained(arg0);
		}

	}

	@Override
	public void focusLost(FocusEvent arg0) {
		super.focusGained(arg0);
		for(FocusListener l : focusListeners) {
			l.focusGained(arg0);
		}

	}
	public  void makeFullscreen() {
		makeFullscreen(-1,-1);
	}

	public  void makeFullscreen(int width, int height) {
		Frame frame = new Frame();
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.setUndecorated( true );  

		GraphicsDevice myDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice  ();  
		if(myDevice.isFullScreenSupported()) {
			myDevice.setFullScreenWindow( frame );
			if ((width > 0) && myDevice.isDisplayChangeSupported()) 
			{  
				try {
				DisplayMode newDisplayMode =  new DisplayMode(  width, height, 32, DisplayMode.REFRESH_RATE_UNKNOWN);		   
				myDevice.setDisplayMode(newDisplayMode);
				} catch (Exception e) {
					System.out.println("display mode or resolution not supported");
				}
			}	  
		} else {
			System.out.println("fullscreen not supported");
		}

		this.init();



	}

}
