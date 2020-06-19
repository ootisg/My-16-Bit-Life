
package triggers;

import java.util.ArrayList;
import java.util.Iterator;

import gameObjects.PairingObject;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class Trigger extends GameObject {
	boolean setHitbox;
	boolean Triggered;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	boolean eventFinished;
	int timer;
	boolean persistant;
	int eventWeAreOn;
	int eventSet;
	public ArrayList <ArrayList<Trigger>> RessesiveTriggerList = new ArrayList <ArrayList<Trigger>>();
	public Trigger () {
		setHitbox = true;
		eventSet = 0;
		Triggered = false;
		persistant = false;
		eventFinished = false;
		RessesiveTriggerList.add(new ArrayList<Trigger>());
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
		if (j.isColliding(this) && !Triggered && timer > 5 ) {
			this.Triggered = true;
		}
		if (setHitbox) {
			if ( this.getClass().getSimpleName().equals("Trigger")) {
				if (this.getVariantAttribute("persistant") != null && !this.getVariantAttribute("persistant").equals("no")) {
					persistant = true;
				}
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
			for (int i = 0; i != ObjectHandler.checkCollisionChildren("Trigger", this).getCollidingObjects().size(); i++) {
			
				if (!this.getCollisionInfo().getCollidingObjects().get(i).getClass().getSimpleName().equals("Trigger")) {
					working.add((Trigger) ObjectHandler.checkCollisionChildren("Trigger", this).getCollidingObjects().get(i));
				}
			}
			ArrayList<Trigger> sortedTriggerList = new ArrayList<Trigger>();
			while (sortedTriggerList.size() != working.size()) {
				int iCopy = 0;
				while (true) {
					if (!sortedTriggerList.contains(working.get(iCopy))) {
						break;
					}
					iCopy = iCopy + 1;
				}
				//sorts the ressive triggers into the order they should be set off
				for (int i = 0; i != working.size(); i++) {
					if (working.get(i).getY() <= working.get(iCopy).getY() && working.get(i).getX() <= working.get(iCopy).getX() && !sortedTriggerList.contains(working.get(i))) {
						iCopy = i;
					}
				}
				sortedTriggerList.add(working.get(iCopy));
			}
			int currentMeme = 0;
			for (int i = 0; i <sortedTriggerList.size(); i++) {
				if (sortedTriggerList.get(i).getClass().getSimpleName().equals("BreakTrigger")) {
					currentMeme = currentMeme + 1;
					RessesiveTriggerList.add(new ArrayList<Trigger> ());
				} else {
					RessesiveTriggerList.get(currentMeme).add(sortedTriggerList.get(i));
				}
			}
		}
	
	//code that is only run by the dominate trigger to activate the events of all the ressesive triggers
	if (Triggered){
		
		//if all triggers have been triggered forget the dominate trigger
		if (eventWeAreOn >= RessesiveTriggerList.get(eventSet).size()) {
			if (!persistant) {
				this.forget();
			} else {
				if (!this.isColliding(j)) {
					Triggered = false;
					eventWeAreOn = 0;
					eventSet = eventSet + 1;
					if (eventSet == RessesiveTriggerList.size()) {
						eventSet = 0;
					}
					for (int i = 0; i <RessesiveTriggerList.get(eventSet).size();i++) {
						RessesiveTriggerList.get(eventSet).get(i).declare();
						RessesiveTriggerList.get(eventSet).get(i).eventFinished = false;
						RessesiveTriggerList.get(eventSet).get(i).Triggered = false;
					}
				}
			}
		} else {
			if (RessesiveTriggerList.get(eventSet).get(eventWeAreOn).eventFinished) {
				RessesiveTriggerList.get(eventSet).get(eventWeAreOn).forget();
				eventWeAreOn = eventWeAreOn + 1;
			} else {
				RessesiveTriggerList.get(eventSet).get(eventWeAreOn).triggerEvent();
			}
		}
	}
	}
	@Override 
	public void pausedEvent () {
		if (Triggered){
			//if all triggers have been triggered forget the dominate trigger
			if (eventWeAreOn >= RessesiveTriggerList.get(eventSet).size()) {
				if (!persistant) {
					this.forget();
				} else {
					if (!this.isColliding(j)) {
						eventWeAreOn = 0;
						Triggered = false;
						eventSet = eventSet + 1;
						if (eventSet == RessesiveTriggerList.size()) {
							eventSet = 0;
						}
						for (int i = 0; i <RessesiveTriggerList.get(eventSet).size();i++) {
							RessesiveTriggerList.get(eventSet).get(i).declare();
							RessesiveTriggerList.get(eventSet).get(i).eventFinished = false;
							RessesiveTriggerList.get(eventSet).get(i).Triggered = false;
						}
					}
				}
			} else {
				if (RessesiveTriggerList.get(eventSet).get(eventWeAreOn).eventFinished) {
					RessesiveTriggerList.get(eventSet).get(eventWeAreOn).forget();
					eventWeAreOn = eventWeAreOn + 1;
				} else {
					RessesiveTriggerList.get(eventSet).get(eventWeAreOn).frameEvent();
					RessesiveTriggerList.get(eventSet).get(eventWeAreOn).triggerEvent();
				}
			}
		}
	}
	/**
	 * gets the object that was paired to this one via the map editor
	 * @return yeah that ya moron
	 */
	public PairingObject getPairedObject () {
		Iterator <GameObject> iter = ObjectHandler.getObjectsByName("PairingObject").iterator();
			while (iter.hasNext()) {
				PairingObject workin = (PairingObject)iter.next();
				if (workin.getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner"))) {
					return workin;
				}
			}
			return null;
	}
}