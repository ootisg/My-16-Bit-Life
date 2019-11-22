package gameObjects;

import java.util.ArrayList;

import main.ObjectHandler;

public class DoorTrigger extends Trigger {
	TemporaryWall wallTomanage;
	public DoorTrigger () {
	}
	public void triggerEvent () {
		for (int i = 0; i < ObjectHandler.getObjectsByName("TemporaryWall").size(); i++) {
			if (ObjectHandler.getObjectsByName("TemporaryWall").get(i).getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner"))) {
				wallTomanage =((TemporaryWall) ObjectHandler.getObjectsByName("TemporaryWall").get(i));
				wallTomanage.activated = true;
			}
			}
		this.eventFinished = true;
	}
}
