package switches;

import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class Switch extends GameObject {
	private static final Sprite SWITCH_SPRITE = new Sprite ("resources/sprites/config/switch.txt");
	private boolean isActivated = false;
	private Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	public Switch () {
		this.setSprite(SWITCH_SPRITE);
		this.setHitboxAttributes(0, 0, 32, 20);
	}
	@Override
	public void frameEvent () {
		if (j.isColliding(this) && keyPressed(13)) {
			
		}
	}
	public boolean isActivated () {
		return isActivated;
	}
	
}
