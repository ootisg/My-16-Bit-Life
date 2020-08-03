package statusEffect;

import main.GameCode;
import main.ObjectHandler;
import players.Jeffrey;

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
		Jeffrey.status.statusAppliedOnJeffrey [1] = true;
		Jeffrey.status.statusAppliedOnSam [1] = true;
		if (realDirection) {
			Jeffrey.getActiveJeffrey().bindRight = true;
		} else {
			Jeffrey.getActiveJeffrey().bindLeft = true;	
		}
		}
		if (timer) {
		this.forget();
		}
		timer = true;
	}
}
