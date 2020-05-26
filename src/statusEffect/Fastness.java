package statusEffect;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;

public class Fastness extends GameObject {
	int charictar; 
	int timer = 0;
	int time = 900;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
	public Fastness (int charictarToModerate) {
		charictar = charictarToModerate;
	}
	@Override
	public void frameEvent () {
		if (j.witchCharictar == charictar) {
		timer = timer + 1;
		}
		if (charictar == 0) {
			Jeffrey.status.statusAppliedOnJeffrey[5] = true;
		}
		if (charictar == 1) {
			Jeffrey.status.statusAppliedOnSam[5] = true;
		}
		if (charictar == 2) {
			Jeffrey.status.statusAppliedOnRyan[5] = true;
		}
		if (timer == time) {
			if (charictar == 0) {
				Jeffrey.status.statusAppliedOnJeffrey[5] = false;
			}
			if (charictar == 1) {
				Jeffrey.status.statusAppliedOnSam[5] = false;
			}
			if (charictar == 2) {
				Jeffrey.status.statusAppliedOnRyan[5] = false;
			}
			this.forget();
		}
	}
	public void setTime (int timeYouWant) {
		time = timeYouWant;
	}
	}

