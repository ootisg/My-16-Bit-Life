package gameObjects;

import main.GameObject;
import players.Jeffrey;
import resources.Sprite;

public class UsablePogo extends GameObject {
	boolean pogoing = false;
	double previosVy = 0;
	Sprite jeffreyBounce = new Sprite ("resources/sprites/config/jeffrey_pogoing.txt");
	Sprite samBounce = new Sprite ("resources/sprites/config/sam_pogoing.txt");
	Sprite ryanBounce = new Sprite ("resources/sprites/config/ryan_pogoing.txt");
	public UsablePogo () {
		
	}
	public void frameEvent () {
		if (Jeffrey.getActiveJeffrey().vy != 0 && keyDown ('S')) {
			switch (Jeffrey.getActiveJeffrey().witchCharictar) {
			case 0:
				Jeffrey.getActiveJeffrey().setSprite (jeffreyBounce);
				break;
			case 1:
				Jeffrey.getActiveJeffrey().setSprite (samBounce);
				break;
			case 2:
				Jeffrey.getActiveJeffrey().setSprite (ryanBounce);
				break;
			default:
				Jeffrey.getActiveJeffrey().setSprite (jeffreyBounce);
				break;
			}
			if (Jeffrey.getActiveJeffrey().witchCharictar == 1 && !Jeffrey.getActiveJeffrey().getSprite().equals(samBounce) || !Jeffrey.getActiveJeffrey().getSprite().equals(jeffreyBounce) || !Jeffrey.getActiveJeffrey().getSprite().equals(ryanBounce)) {
				Jeffrey.getActiveJeffrey().changeSprite(false);
				Jeffrey.getActiveJeffrey().getAnimationHandler().setFrameTime(50);
			}
			if (Jeffrey.getActiveJeffrey().getAnimationHandler().getFrame() == 5 || Jeffrey.getActiveJeffrey().getAnimationHandler().getFrame() == 6) {
				Jeffrey.getActiveJeffrey().getAnimationHandler().setFrameTime(0);
			} else {
				Jeffrey.getActiveJeffrey().getAnimationHandler().setFrameTime(50);
			}
			pogoing = true;
			previosVy = Jeffrey.getActiveJeffrey().vy;
		} else {
			if (keyDown ('S') && pogoing) {
				Jeffrey.getActiveJeffrey().vy = -previosVy - 8;
			} else {
				pogoing = false;
				Jeffrey.getActiveJeffrey().changeSprite(true);
			}
		}
	}
}
