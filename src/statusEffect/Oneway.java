package statusEffect;

import main.GameCode;

public class Oneway extends Status {
	boolean timer;
	boolean realDirection;
	public Oneway (boolean direction) {
		timer = false;
		realDirection = direction;
	}
	@Override
	public void frameEvent () {
		if (!timer) {
		GameCode.testJeffrey.status.statusAppliedOnJeffrey [1] = true;
		GameCode.testJeffrey.status.statusAppliedOnSam [1] = true;
		if (realDirection) {
		GameCode.testJeffrey.bindRight = true;
		} else {
		GameCode.testJeffrey.bindLeft = true;	
		}
		}
		if (timer) {
		this.forget();
		}
		timer = true;
	}
}
