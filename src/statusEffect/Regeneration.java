package statusEffect;

import gameObjects.DamageText;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;

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
				if (Jeffrey.jeffreyHealth < Jeffrey.maxJeffreyHealth) {
					Jeffrey.jeffreyHealth = Jeffrey.jeffreyHealth + 1;
				}
			}
				if (charictar == 1) {
					if (Jeffrey.samHealth < Jeffrey.maxSamHealth) {
						Jeffrey.samHealth = Jeffrey.samHealth + 1;
					}
				}
					if (charictar == 2) {
						if (Jeffrey.ryanHealth < Jeffrey.maxRyanHealth) {
							Jeffrey.ryanHealth = Jeffrey.ryanHealth + 1;
						}
					}
					DamageText text;
					text = new DamageText (1, Jeffrey.getActiveJeffrey().getX(), Jeffrey.getActiveJeffrey().getY(), 1);
					text.declare(0,0);
					timer = timer + 1;
		}
		if (Jeffrey.getActiveJeffrey().witchCharictar == charictar) {
		timer = timer + 1;
		}
		if (charictar == 0) {
			Jeffrey.status.statusAppliedOnJeffrey[7] = true;
		}
		if (charictar == 1) {
			Jeffrey.status.statusAppliedOnSam[7] = true;
		}
		if (charictar == 2) {
			Jeffrey.status.statusAppliedOnRyan[7] = true;
		}
		if (timer == time) {
			if (charictar == 0) {
				Jeffrey.status.statusAppliedOnJeffrey[7] = false;
			}
			if (charictar == 1) {
				Jeffrey.status.statusAppliedOnSam[7] = false;
			}
			if (charictar == 2) {
				Jeffrey.status.statusAppliedOnRyan[7] = false;
			}
			this.forget();
		}
	}
	public void setTime (int timeYouWant) {
		time = timeYouWant;
	}
}
