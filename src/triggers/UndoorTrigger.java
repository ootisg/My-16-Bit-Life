package triggers;

import gameObjects.TemporaryWall;
import main.ObjectHandler;

public class UndoorTrigger extends Trigger {
	TemporaryWall wallTomanage;
	public UndoorTrigger () {
		 this.setHitboxAttributes(0, 0, 16, 16);
	}
	public void triggerEvent () {
		for (int i = 0; i < ObjectHandler.getObjectsByName("TemporaryWall").size(); i++) {
			if (ObjectHandler.getObjectsByName("TemporaryWall").get(i).getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner"))) {
				wallTomanage =((TemporaryWall) ObjectHandler.getObjectsByName("TemporaryWall").get(i));
				wallTomanage.activated = false;
			}
			}
		this.eventFinished = true;
	}
}
