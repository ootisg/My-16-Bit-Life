package triggers;

import java.util.ArrayList;

import gameObjects.PairingObject;
import main.GameObject;
import main.ObjectHandler;
import switches.Activateable;
import switches.Switch;

public class SwitchTrigger extends Trigger implements Activateable {
	ArrayList <GameObject> switchesToActivate = new ArrayList<GameObject>();
	boolean active = false;
	@Override
	public void frameEvent () {
		super.frameEvent();
		if (!this.setHitbox) {
			ArrayList <GameObject> paringObjects = ObjectHandler.getObjectsByName("PairingObject");
			int width = (int) this.hitbox().getWidth();
			int height = (int) this.hitbox().getHeight();
			this.setHitboxAttributes(0, 0, 16, 16);
			for (int i = 0; i < paringObjects.size(); i++) {
				if (paringObjects.get(i).isColliding(this)) {
					PairingObject obj = (PairingObject) paringObjects.get(i);
					ArrayList <Object> pairedList = obj.getPairedPairedObjects();
					for (int j = 0; j < pairedList.size(); j++) {
						switchesToActivate.add((GameObject)pairedList.get(i));
					}
					break;
				}
			}
			this.setHitboxAttributes(0,0, width, height);
		}
	}
	@Override
	public boolean checkTriggerCondition () {
		return active;
	}
	@Override
	public void activate() {
		active = true;
	}
	@Override
	public void deactivate() {
		active = false;
	}
	@Override
	public boolean isActivated() {
		return active;
	}
	@Override
	public void pair() {
		
	}
}
