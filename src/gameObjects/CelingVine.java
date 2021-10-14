package gameObjects;

import java.util.ArrayList;

import main.GameObject;
import main.ObjectHandler;
import mapObjects.CarryObject;
import mapObjects.MapObject;
import mapObjects.MoveingPlatform;
import players.Player;
import resources.Sprite;

public class CelingVine extends GameObject {
	boolean inzilized;
	static boolean grabbed;
	CarryObject attachedPlatform;
	public CelingVine () {
		this.setHitboxAttributes(0, 0, 16, 16);
		this.enablePixelCollisions();
		this.setSprite(new Sprite ("resources/sprites/VineTemp.png"));
	}
	@Override 
	public void frameEvent  () {
		if (!inzilized) {
			ArrayList <MapObject> working = new ArrayList <MapObject> ();
			ArrayList<ArrayList<GameObject>> fullList = ObjectHandler.getChildrenByName("MapObject");
			if (fullList != null) {
				for (int i = 0; i < fullList.size(); i++) {
					for (int j = 0; j <fullList.get(i).size(); j++) {
						try {
							CarryObject lame = (CarryObject) fullList.get(i).get(j);
							working.add((MapObject)fullList.get(i).get(j));
						} catch (ClassCastException e) {
							
						}
					}
				}
			}
			this.setY(this.getY() - 1);
			for (int i = 0; i < working.size(); i++) {
				if (this.isColliding(working.get(i))) {
					CarryObject platform;
					platform = (CarryObject) working.get(i);
					attachedPlatform = platform;
					while(true) {
						if (this.isColliding(working.get(i))) {
							this.setY(this.getY() + 1);
						} else {
							break;
						}
					}
					platform.addCarryObject(this);
					break;
				}
			}
			inzilized = true;
		}
		if (this.isColliding(Player.getActivePlayer()) && keyDown (32)) {
			Player.getActivePlayer().stopFall(true);	
			Player.getActivePlayer().binded = true;
			if (keyDown ('D')) {
				Player.getActivePlayer().setX(Player.getActivePlayer().getX() + 2);
			}
			
			if (keyDown ('A')) {
				Player.getActivePlayer().setX(Player.getActivePlayer().getX() - 2);
			}
			
			
			if (attachedPlatform != null) {
				ArrayList <MapObject> working = new ArrayList <MapObject> ();
				ArrayList<ArrayList<GameObject>> fullList = ObjectHandler.getChildrenByName("MapObject");
				if (fullList != null) {
					for (int i = 0; i < fullList.size(); i++) {
						for (int j = 0; j <fullList.get(i).size(); j++) {
							try {
								CarryObject lame = (CarryObject) fullList.get(i).get(j);
								working.add((MapObject)fullList.get(i).get(j));
							} catch (ClassCastException e) {
								
							}
						}
					}
				}
				for (int i = 0; i <working.size(); i++) {
					CarryObject platform = (CarryObject) working.get(i);
					if (platform.getCarryObjs().contains(Player.getActivePlayer())) {
						platform.removeCarryObject(Player.getActivePlayer());
					}
				}
				if (!attachedPlatform.getCarryObjs().contains(Player.getActivePlayer())) {
					attachedPlatform.addCarryObject(Player.getActivePlayer());
				}
			}
			if (!grabbed) {
				grabbed = true;
			}
		}
	}
	@Override
	public void staticLogic () {
		if (grabbed && (!Player.getActivePlayer().isColliding("CelingVine") || !keyDown(32))) {
			grabbed = false;
			Player.getActivePlayer().setVy(0);
			ArrayList <MapObject> working = new ArrayList <MapObject> ();
			ArrayList<ArrayList<GameObject>> fullList = ObjectHandler.getChildrenByName("MapObject");
			if (fullList != null) {
				for (int i = 0; i < fullList.size(); i++) {
					for (int j = 0; j <fullList.get(i).size(); j++) {
						try {
							CarryObject lame = (CarryObject) fullList.get(i).get(j);
							working.add((MapObject)fullList.get(i).get(j));
						} catch (ClassCastException e) {
							
						}
					}
				}
			}
			for (int i = 0; i <working.size(); i++) {
				CarryObject platform = (CarryObject) working.get(i);
				if (platform.getCarryObjs().contains(Player.getActivePlayer())) {
					platform.removeCarryObject(Player.getActivePlayer());
				}
			}
			Player.getActivePlayer().stopFall(false);
			Player.getActivePlayer().binded = false;
		}
	}
}
