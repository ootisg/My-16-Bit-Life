package statusEffect;

import main.GameCode;
import main.GameObject;

public class Slowness extends GameObject {
	int charictar; 
	int timer = 0;
	int time = 900;
	public Slowness ( int charictarToModerate) {
	charictar = charictarToModerate;
	}
	@Override
	public void frameEvent () {
		if (GameCode.testJeffrey.witchCharictar == charictar) {
		timer = timer + 1;
		}
		if (charictar == 0) {
			GameCode.testJeffrey.status.statusAppliedOnJeffrey[3] = true;
		}
		if (charictar == 1) {
			GameCode.testJeffrey.status.statusAppliedOnSam[3] = true;
		}
		if (charictar == 2) {
			GameCode.testJeffrey.status.statusAppliedOnRyan[3] = true;
		}
		if (timer == time) {
			if (charictar == 0) {
				GameCode.testJeffrey.status.statusAppliedOnJeffrey[3] = false;
			}
			if (charictar == 1) {
				GameCode.testJeffrey.status.statusAppliedOnSam[3] = false;
			}
			if (charictar == 2) {
				GameCode.testJeffrey.status.statusAppliedOnRyan[3] = false;
			}
			this.forget();
		}
	}
	public void setTime (int timeYouWant) {
		time = timeYouWant;
	}
}