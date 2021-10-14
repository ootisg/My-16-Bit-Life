package gameObjects;

import main.GameObject;
import players.Player;
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
		if (Player.getActivePlayer().getVy() != 0 && keyDown ('S')) {
			if ( !Player.getActivePlayer().getSprite().equals(samBounce) && !Player.getActivePlayer().getSprite().equals(jeffreyBounce) && !Player.getActivePlayer().getSprite().equals(ryanBounce)) {
				//TODO get the sprites to work
				
//				switch (Player.getActivePlayer().witchCharictar) {
//				case 0:
//					Player.getActivePlayer().setSprite (jeffreyBounce);
//					break;
//				case 1:
//					Player.getActivePlayer().setSprite (samBounce);
//					break;
//				case 2:
//					Player.getActivePlayer().setSprite (ryanBounce);
//					break;
//				}
//				Player.getActivePlayer().getAnimationHandler().playToo(5);
//				Player.getActivePlayer().allowSpriteEdits(false);
			}
			pogoing = true;
			previosVy = Player.getActivePlayer().getVy();
//			if (Player.getActivePlayer().getAnimationHandler().getFrame() == 6) {
//				Player.getActivePlayer().getAnimationHandler().setAnimationFrame(5);
//			}
		} else {
			if (keyDown ('S') && pogoing) {
//				Player.getActivePlayer().getAnimationHandler().playFrom(5,6);
				Player.getActivePlayer().setVy(-previosVy - 8);
			} else {
//				if (pogoing) {
//					Player.getActivePlayer().getAnimationHandler().playInfintely();
//					Player.getActivePlayer().allowSpriteEdits(true);
//					switch (Player.getActivePlayer().witchCharictar) {
//					case 0:
//						Player.getActivePlayer().setSprite (Player.JEFFREY_WALKING_POGO);
//						break;
//					case 1:
//						Player.getActivePlayer().setSprite (Player.SAM_WALKING_POGO);
//						break;
//					case 2:
//						Player.getActivePlayer().setSprite (Player.RYAN_WALKING_POGO);
//						break;
//					}
			//	}
				pogoing = false;
			}
		}
	}
	public boolean isPogoing () {
		return pogoing;
	}
}
