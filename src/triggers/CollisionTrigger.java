package triggers;

import players.Player;

public class CollisionTrigger extends Trigger {

	@Override
	public boolean checkTriggerCondition() {
		return Player.getActivePlayer().isColliding(this);
	}
	
}
