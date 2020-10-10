package triggers;

import players.Jeffrey;

public class CollisionTrigger extends Trigger {

	@Override
	public boolean checkTriggerCondition() {
		return Jeffrey.getActiveJeffrey().isColliding(this);
	}
	
}
