package statusEffect;

import gameObjects.DamageText;
import main.GameCode;
import main.GameObject;

public class Regeneration extends GameObject {
	int charictar; 
	int timer = 0;
	int time = 300;
	public Regeneration ( int charictarToModerate) {
	charictar = charictarToModerate;
	}
	@Override
	public void frameEvent () {
		if (timer % 15 == 0) {
			if (charictar == 0) {
				if (GameCode.testJeffrey.jeffreyHealth != GameCode.testJeffrey.maxJeffreyHealth) {
					GameCode.testJeffrey.jeffreyHealth = GameCode.testJeffrey.jeffreyHealth + 1;
				}
			}
				if (charictar == 1) {
					if (GameCode.testJeffrey.samHealth != GameCode.testJeffrey.maxSamHealth) {
						GameCode.testJeffrey.samHealth = GameCode.testJeffrey.samHealth + 1;
					}
				}
					if (charictar == 0) {
						if (GameCode.testJeffrey.ryanHealth != GameCode.testJeffrey.maxRyanHealth) {
							GameCode.testJeffrey.ryanHealth = GameCode.testJeffrey.ryanHealth + 1;
						}
					}
					DamageText text;
					text = new DamageText (1, GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY(), true);
					text.declare(0,0);
					timer = timer + 1;
		}
		if (GameCode.testJeffrey.witchCharictar == charictar) {
		timer = timer + 1;
		}
		if (charictar == 0) {
			GameCode.testJeffrey.status.statusAppliedOnJeffrey[7] = true;
		}
		if (charictar == 1) {
			GameCode.testJeffrey.status.statusAppliedOnSam[7] = true;
		}
		if (charictar == 2) {
			GameCode.testJeffrey.status.statusAppliedOnRyan[7] = true;
		}
		if (timer == time) {
			if (charictar == 0) {
				GameCode.testJeffrey.status.statusAppliedOnJeffrey[7] = false;
			}
			if (charictar == 1) {
				GameCode.testJeffrey.status.statusAppliedOnSam[7] = false;
			}
			if (charictar == 2) {
				GameCode.testJeffrey.status.statusAppliedOnRyan[7] = false;
			}
			this.forget();
		}
	}
	public void setTime (int timeYouWant) {
		time = timeYouWant;
	}
}
