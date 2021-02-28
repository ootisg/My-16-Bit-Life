package gameObjects;

public class QuickTimer {
	
	public double waitTime = 0;
	long startTime;
	boolean on = false;
	double timeElapsed;
	/**
	 * a class to check if a certain amount of time has gone by
	 * @param time time is seconds (duh ya stupid moron)
	 */
	public QuickTimer (double time) {
		waitTime = time * 1000;
		startTime = System.currentTimeMillis();
	}

	public boolean hasElapsed () {
		if (on) {
			return System.currentTimeMillis() > startTime + waitTime;
		} else {
			return timeElapsed > waitTime;
		}
	}
	public void turnOn () {
		if (!on) {
			on = true;
			startTime = System.currentTimeMillis();
			waitTime = waitTime - timeElapsed;
		}
	}
	public void turnOff() {
		if (on) {
			on = false;
			timeElapsed = System.currentTimeMillis() - startTime;
		}
	}
	public void setWaitTime (double time) {
		waitTime = time * 1000;
		timeElapsed = 0;
		startTime = System.currentTimeMillis();
	}
	public boolean isOn () {
		return on;
	}
}
