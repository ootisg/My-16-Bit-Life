package triggers;

import java.util.Iterator;

import gameObjects.CameraObject;
import main.GameObject;
import main.ObjectHandler;

public class CameraTrigger extends Trigger {
	CameraObject attachedCamera;
	public CameraTrigger () {
		this.setHitboxAttributes(0,0, 16, 16);
	}
	@Override 
	public void frameEvent () {
		if (attachedCamera == null) {
			Iterator <GameObject>  iter = ObjectHandler.getObjectsByName("CameraObject").iterator();
			while (iter.hasNext()) {
				CameraObject  working = (CameraObject) iter.next();
				if (working.getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner"))) {
					attachedCamera = working;
					
				}
			}
		}
	}
	@Override
	public void triggerEvent () {
		if (attachedCamera.inControl()) {
			attachedCamera.giveUpControl();
		} else {
			attachedCamera.takeControl();
		}
		this.eventFinished = true;
	}
	
}
