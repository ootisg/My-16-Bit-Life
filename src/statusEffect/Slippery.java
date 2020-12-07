package statusEffect;

import gameObjects.DamageText;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;

public class Slippery extends GameObject {
	int charictar; 
	int timer = 0;
	int time = 300;
	public Slippery ( int charictarToModerate) {
	charictar = charictarToModerate;
	}
	@Override
	public void frameEvent () {
		if (Jeffrey.getActiveJeffrey().witchCharictar == charictar) {
		timer = timer + 1;
		}
		if (charictar == 0) {
			Jeffrey.status.statusAppliedOnJeffrey[8] = true;
		}
		if (charictar == 1) {
			Jeffrey.status.statusAppliedOnSam[8] = true;
		}
		if (charictar == 2) {
			Jeffrey.status.statusAppliedOnRyan[8] = true;
		}
		if (timer == time) {
			if (charictar == 0) {
				Jeffrey.status.statusAppliedOnJeffrey[8] = false;
			}
			if (charictar == 1) {
				Jeffrey.status.statusAppliedOnSam[8] = false;
			}
			if (charictar == 2) {
				Jeffrey.status.statusAppliedOnRyan[8] = false;
			}
			this.forget();
		}
	}
	public void setTime (int timeYouWant) {
		time = timeYouWant;
	}
}
