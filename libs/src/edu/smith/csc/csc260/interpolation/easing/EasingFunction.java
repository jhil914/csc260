package edu.smith.csc.csc260.interpolation.easing;

public interface EasingFunction {
	public void setStartTime(long startTime);
	public long getStartTime();
	
	public void setEndTime(long endTime);
	public long getEndTime();
	
	/**
	 * Calculates T and stores the value t internally.
	 * T values should range between 0 and 1
	 * @param curTime
	 * @return returns t value for current time
	 */
	public float calcT(long curTime);
	
	/**
	 * Returns stored t value calculated by calcT(long curTime)
	 * 
	 * @return
	 */
	public float getT();
	
	public boolean isDone();
	public boolean hasStarted();

}
