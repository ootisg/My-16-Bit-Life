
package triggers;

import java.util.ArrayList;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import resources.Sprite;

public class Trigger extends GameObject {
	Boolean setHitbox;
	Boolean Triggered;
	Boolean eventFinished;
	int timer;
	int eventWeAreOn;
	public ArrayList <Trigger> RessesiveTriggerList = new ArrayList <Trigger>();
	public Trigger () {
		setHitbox = true;
		Triggered = false;
		eventFinished = false;
		this.adjustHitboxBorders();
		eventWeAreOn = 0;
		timer = 0;
	}
	public boolean Triggered () {
		return (Triggered);
	}
	public void triggerEvent() {
		
	}
	
	public void frameEvent () {
		//sets the hitbox to work with the bottom bound
		if (GameCode.testJeffrey.isColliding(this) && !Triggered && timer > 5 ) {
			this.Triggered = true;
		}
		if (setHitbox) {
			if ( this.getClass().getSimpleName().equals("Trigger")) {
		int uppyThing = 0;
			while (uppyThing < ObjectHandler.getObjectsByName("HitboxRightBottomBound").size()) {
			if(ObjectHandler.getObjectsByName("HitboxRightBottomBound").get(uppyThing).getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner"))){
				this.setHitboxAttributes(0, 0, (int) (ObjectHandler.getObjectsByName("HitboxRightBottomBound").get(uppyThing).getX()- this.getX()), (int) (ObjectHandler.getObjectsByName("HitboxRightBottomBound").get(uppyThing).getY()- this.getY()));
				break;
			}
			uppyThing = uppyThing + 1;
		}
		setHitbox = false;
		} else {
			this.setHitboxAttributes(0, -1, 5, 5);
			setHitbox = false;
			}
		}
		timer = timer +1;
		
		if (timer == 5 && this.getClass().getSimpleName().equals("Trigger")) {
		
			this.isCollidingChildren("Trigger");
			ArrayList<Trigger> working = new ArrayList <Trigger> ();
			System.out.println ( ObjectHandler.checkCollisionChildren("Trigger", this).getCollidingObjects());
			for (int i = 0; i != ObjectHandler.checkCollisionChildren("Trigger", this).getCollidingObjects().size(); i++) {
			
				if (!this.getCollisionInfo().getCollidingObjects().get(i).getClass().getSimpleName().equals("Trigger")) {
					working.add((Trigger) ObjectHandler.checkCollisionChildren("Trigger", this).getCollidingObjects().get(i));
				}
			}
			while (RessesiveTriggerList.size() != working.size()) {
				int iCopy = 0;
				while (true) {
					if (!RessesiveTriggerList.contains(working.get(iCopy))) {
						break;
					}
					iCopy = iCopy + 1;
				}
				//sorts the ressive triggers into the order they should be set off
				for (int i = 0; i != working.size(); i++) {
					if (working.get(i).getY() <= working.get(iCopy).getY() && working.get(i).getX() < working.get(iCopy).getX() && !RessesiveTriggerList.contains(working.get(i))) {
					iCopy = i;
					}
				}
				RessesiveTriggerList.add(working.get(iCopy));
			}
		}
	
	//code that is only run by the dominate trigger to activate the events of all the ressesive triggers
	if (Triggered){
		
		//if all triggers have been triggered forget the dominate trigger
		if (eventWeAreOn >= RessesiveTriggerList.size()) {
			this.forget();
		} else {
			if (RessesiveTriggerList.get(eventWeAreOn).eventFinished) {
				RessesiveTriggerList.get(eventWeAreOn).forget();
				eventWeAreOn = eventWeAreOn + 1;
			} else {
				RessesiveTriggerList.get(eventWeAreOn).triggerEvent();
			}
		}
	}
	}
}