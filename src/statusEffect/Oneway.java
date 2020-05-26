package statusEffect;

import main.GameCode;
import main.ObjectHandler;
import players.Jeffrey;

public class Oneway extends Status {
	boolean timer;
	boolean realDirection;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
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
		j.bindRight = true;
		} else {
		j.bindLeft = true;	
		}
		}
		if (timer) {
		this.forget();
		}
		timer = true;
	}
}
