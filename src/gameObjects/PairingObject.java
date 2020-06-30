package gameObjects;

import java.util.ArrayList;
import java.util.LinkedList;

import main.GameObject;
import main.ObjectHandler;

public class PairingObject extends GameObject {
	ArrayList <GameObject> pairedObject;
	public PairingObject () {
		this.setHitboxAttributes (0,0,16,16);
		this.setGameLogicPriority(-1);
	
	}
	@Override 
	public void frameEvent () {
		if (pairedObject == null) {
			if (ObjectHandler.checkCollisionChildren("GameObject", this).collisionOccured()) {
				pairedObject = ObjectHandler.checkCollisionChildren("GameObject", this).getCollidingObjects();
				for (int i = 0; i < pairedObject.size(); i++) {
					if (pairedObject.get(i).getClass().getSimpleName().equals("Trigger")) {
						pairedObject.remove(i);
					}
				}
			}
		}
	}
	public ArrayList <GameObject> getPairedObjects (){
		return pairedObject;
	}
	public ArrayList <PairingObject> getPairedParingObjects() {
		ArrayList <GameObject> working= ObjectHandler.getObjectsByName("PairingObject");
		ArrayList <PairingObject> pairedObjects = new ArrayList <PairingObject>();
		for (int i = 0; i < working.size(); i++) {
			PairingObject workingPairing = (PairingObject) working.get(i);
			if (workingPairing.getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner")) && !workingPairing.equals(this)) {
				pairedObjects.add(workingPairing);
				}
			}
		return pairedObjects;
		}
	public ArrayList <GameObject> getPairedPairedObjects (){
		ArrayList <GameObject> working = new ArrayList<GameObject> ();
		ArrayList <PairingObject> pairedObjects = this.getPairedParingObjects();
		for (int i =0; i < pairedObjects.size(); i++) {
			working.addAll(pairedObjects.get(i).getPairedObjects());
			}
		return working;
		}
	}