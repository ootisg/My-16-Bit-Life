package triggers;

import java.util.ArrayList;
import gameObjects.PairingObject;
import main.GameObject;
import main.ObjectHandler;

public class ForgetTrigger extends RessesiveTrigger {
	ArrayList <GameObject> objectsToSpawn = new ArrayList <GameObject>();
	int timer;
	 public ForgetTrigger() {
		 this.setHitboxAttributes(0, 0, 16, 16);
		 timer = 0;
	}
	@Override
	public void frameEvent () {
		if (timer == 0) {
				int uppyThing = 0;
				int x = 0;
				try {
				while (uppyThing < ObjectHandler.getObjectsByName("PairingObject").size()) {
					try {
					if(ObjectHandler.getObjectsByName("PairingObject").get(uppyThing).getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner"))){
						ArrayList <GameObject> workingList = ((PairingObject) ObjectHandler.getObjectsByName("PairingObject").get(uppyThing)).getPairedObjects();
						for (int i = 0; i < workingList.size(); i++) {
							objectsToSpawn.add(workingList.get(i));
							x = x + 1;
					}
				}
					uppyThing = uppyThing + 1;
					} catch (NullPointerException e) {
						timer = -1;
						break;
					}
			}
				} catch (NullPointerException e) {
					
				}
		timer = timer + 1;
	}
}
	public void triggerEvent () {
		for (int i = 0; i != objectsToSpawn.size(); i++) {
			objectsToSpawn.get(i).forget();
		}
		this.eventFinished = true;
	}
}
