package triggers;

import java.util.ArrayList;
import gameObjects.PairingObject;
import main.GameObject;
import main.ObjectHandler;

public class DeclareTrigger extends RessesiveTrigger {
	ArrayList <GameObject> objectsToSpawn = new ArrayList <GameObject>();
	double [] xCoordinates;
	double [] yCoordinates;
	int timer;
	 public DeclareTrigger() {
		 this.setHitboxAttributes(0, 0, 16, 16);
		 xCoordinates = new double [200];
		 yCoordinates = new double [200];
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
							xCoordinates [x] = workingList.get(i).getX();
							yCoordinates [x] = workingList.get(i).getY();
							objectsToSpawn.add(workingList.get(i));
							workingList.get(i).forget();
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
			objectsToSpawn.get(i).declare(xCoordinates[i], yCoordinates[i]);
		}
		this.eventFinished = true;
	}
}
