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
		this.setGameLogicPriority(-1);
	}
	public void frameEvent () {
		if (Jeffrey.getActiveJeffrey().getVy() != 0 && keyDown ('S')) {
			if ( !Jeffrey.getActiveJeffrey().getSprite().equals(samBounce) && !Jeffrey.getActiveJeffrey().getSprite().equals(jeffreyBounce) && !Jeffrey.getActiveJeffrey().getSprite().equals(ryanBounce)) {
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
				}
				Jeffrey.getActiveJeffrey().getAnimationHandler().playToo(5);
				Jeffrey.getActiveJeffrey().allowSpriteEdits(false);
			}
			pogoing = true;
			previosVy = Jeffrey.getActiveJeffrey().getVy();
			if (Jeffrey.getActiveJeffrey().getAnimationHandler().getFrame() == 6) {
				Jeffrey.getActiveJeffrey().getAnimationHandler().setAnimationFrame(5);
			}
		} else {
			if (keyDown ('S') && pogoing) {
				Jeffrey.getActiveJeffrey().getAnimationHandler().playFrom(5,6);
				Jeffrey.getActiveJeffrey().setVy(-previosVy - 8);
			} else {
				if (pogoing) {
					Jeffrey.getActiveJeffrey().getAnimationHandler().playInfintely();
					Jeffrey.getActiveJeffrey().allowSpriteEdits(true);
					switch (Jeffrey.getActiveJeffrey().witchCharictar) {
					case 0:
						Jeffrey.getActiveJeffrey().setSprite (Jeffrey.JEFFREY_WALKING_POGO);
						break;
					case 1:
						Jeffrey.getActiveJeffrey().setSprite (Jeffrey.SAM_WALKING_POGO);
						break;
					case 2:
						Jeffrey.getActiveJeffrey().setSprite (Jeffrey.RYAN_WALKING_POGO);
						break;
					}
				}
				pogoing = false;
			}
		}
	}
	public boolean isPogoing () {
		return pogoing;
	}
}
