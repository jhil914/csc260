package edu.smith.csc.csc260.interpolation.easing;

public class LinearEasingFunction implements EasingFunction  {
	
	private long startTime;
	private long endTime;
	private long duration;
	private float invDuration;
	private int loopCnt;
	
	private boolean isDone = false;
	private boolean hasStarted = false;
	
	protected float t;

	public LinearEasingFunction(long startTime, long endTime) {
		this(startTime, endTime, 1);
	}

	public LinearEasingFunction(long startTime, long endTime, int loopCnt) {
		setTimes(startTime, endTime);
		this.loopCnt = loopCnt;
	}
	/**
	 * 
	 * @param i - number of times to loop interpolation. <=0 implies forever, 1 is default (noloop)
	 */
	public void setLoopCnt(int i) {
		loopCnt = i;
	}
	/**
	 * 
	 * @return number of loops left (negative is infinite)
	 */
	public int getLoopCnt() {
		return loopCnt;
	}
	
	public void setTimes(long startTime, long endTime) {
		this.startTime = startTime;
		this.endTime = endTime;		
		duration = endTime - startTime;
		invDuration = 1.0f /duration;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getEndTime() {
		return endTime;
	}
	
	public void setStartTime(long startTime) {
		setTimes(startTime, this.endTime);
	}

	public void setEndTime(long endTime) {
		setTimes(this.startTime, endTime);
	}

	/**
	 * setT allows you to manually set the T value use by the Interpolator (for special cases when it may have already be calculated by a different object).
	 * @param t
	 */
	public void setT(float t) {
		this.t =t;
	}
	public float calcT(long curTime) {
		
		if(isDone) return 1.0f;
		
		if(curTime <= startTime) {
			t =  0;
		} else if (curTime > endTime) {
			if(loopCnt == 1) {
				isDone = true;
				t = 1.0f;
			} else {
				//update times for next loop and recompute
				startTime = endTime;
				endTime = startTime + duration;
				loopCnt--;
				calcT(curTime);
			}
		} else {
			t =  (curTime - startTime) * invDuration;
		}
		return t;
	}
	
	public float getT() {
		return t;
	}

	/**
	 * @return  isDone interpolating
	 */
	public boolean isDone() {
		return isDone;
	}


	/**
	 * @return hasStarted interpolating
	 */
	public boolean hasStarted() {
		return hasStarted;
	}

	

}
