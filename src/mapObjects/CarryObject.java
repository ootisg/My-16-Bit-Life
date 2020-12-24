package mapObjects;

import java.util.ArrayList;

import gameObjects.StickyObject;
import main.GameObject;
import map.Room;

public class CarryObject extends MapObject {
	ArrayList<GameObject> objectsToCarry = new ArrayList<GameObject> ();
	public void addCarryObject (GameObject obj) {
		if (!objectsToCarry.contains(obj)) {
			objectsToCarry.add(obj);
		}
	}
	public void removeCarryObject (GameObject obj) {
		objectsToCarry.remove(obj);
	}
	public ArrayList <GameObject> getExternalCarryObjects (){
		return objectsToCarry;
	}
	
	public ArrayList<GameObject> getCarryObjs () {
		this.setHitbox(-1, -1, this.hitbox().width + 1, this.hitbox().height + 1);
		this.isCollidingChildren("GameObject");
		ArrayList<GameObject> carryObjs = new ArrayList <GameObject> ();
		ArrayList <GameObject> objList = this.getCollisionInfo().getCollidingObjects();
		objList.removeAll(objectsToCarry);
		for (int i =0; i < objList.size(); i++) {
			try {
				StickyObject working = (StickyObject) objList.get(i);
				carryObjs.add(objList.get(i));
			} catch (ClassCastException e) {
				
			}
		}
		this.setHitbox(0, -3, this.hitbox().width - 1, this.hitbox().height - 1);
		this.isCollidingChildren("GameObject");
		objList = this.getCollisionInfo().getCollidingObjects();
		for (int i =0; i < objList.size(); i++) {
			if (!carryObjs.contains(objList.get(i))) {
				carryObjs.add(objList.get(i));
			}
		}
		for (int i = 0; i < objectsToCarry.size(); i++) {
			if (!carryObjs.contains(objectsToCarry.get(i))) {
				carryObjs.add(objectsToCarry.get(i));
			}
		}
		return carryObjs;
	}
	public void setX(double displacement) {
		super.setX(this.getX() + displacement);
		ArrayList <GameObject> objTotal = this.getCarryObjs();
		for (int i = 0; i < objTotal.size(); i++) {
			if (this.isColliding(objTotal.get(i))) {
				objTotal.get(i).setX(objTotal.get(i).getX() + displacement);
			} else {
				objTotal.get(i).goX(objTotal.get(i).getX() + displacement);
			}
		}
	}
	public void setY(double displacement) {
		super.setY(this.getY() + displacement);
		ArrayList <GameObject> objTotal = this.getCarryObjs();
		for (int i = 0; i < objTotal.size(); i++) {
			if (this.isColliding(objTotal.get(i))) {
				objTotal.get(i).setY(objTotal.get(i).getY() + displacement);
			} else {
				objTotal.get(i).goY(objTotal.get(i).getY() + displacement);
			}
		}
	}
	public boolean goX(double displacement) {
		boolean answer = super.goX(this.getX() + displacement);
		ArrayList <GameObject> objTotal = this.getCarryObjs();
		if (answer) {
			for (int i = 0; i < objTotal.size(); i++) {
				if (this.isColliding(objTotal.get(i))) {
					objTotal.get(i).setX(objTotal.get(i).getX() + displacement);
				} else {
					objTotal.get(i).goX(objTotal.get(i).getX() + displacement);
				}
			}
		}
		return answer;
	}
	public boolean goY(double displacement) {
		boolean answer = super.goY(this.getY() + displacement);
		ArrayList <GameObject> objTotal = this.getCarryObjs();
		if (answer) {
			for (int i = 0; i < objTotal.size(); i++) {
				if (this.isColliding(objTotal.get(i))) {
					objTotal.get(i).setY(objTotal.get(i).getY() + displacement);
				} else {
					objTotal.get(i).goY(objTotal.get(i).getY() + displacement);
				}
			}
		}
		return answer;
	}
	public void setX(double displacement,ArrayList <GameObject> objectsToCarry) {
		super.setX(this.getX() + displacement);
		ArrayList <GameObject> objTotal = this.getCarryObjs();
		for (int i = 0; i < objectsToCarry.size(); i++) {
			if (!objTotal.contains(objectsToCarry.get(i))) {
				objTotal.add(objectsToCarry.get(i));
			}
		}
		for (int i = 0; i < objTotal.size(); i++) {
			if (this.isColliding(objTotal.get(i))) {
				objTotal.get(i).setX(objTotal.get(i).getX() + displacement);
			} else {
				objTotal.get(i).goX(objTotal.get(i).getX() + displacement);
			}
		}
	}
	public void setY(double displacement,ArrayList <GameObject> objectsToCarry) {
		super.setY(this.getY() + displacement);
		ArrayList <GameObject> objTotal = this.getCarryObjs();
		for (int i = 0; i < objectsToCarry.size(); i++) {
			if (!objTotal.contains(objectsToCarry.get(i))) {
				objTotal.add(objectsToCarry.get(i));
			}
		}
		for (int i = 0; i < objTotal.size(); i++) {
			if (this.isColliding(objTotal.get(i))) {
				objTotal.get(i).setY(objTotal.get(i).getY() + displacement);
			} else {
				objTotal.get(i).goY(objTotal.get(i).getY() + displacement);
			}
		}
	}
	public boolean goX(double displacement, ArrayList <GameObject> objectsToCarry) {
		boolean answer = super.goX(this.getX() + displacement);
		ArrayList <GameObject> objTotal = this.getCarryObjs();
		for (int i = 0; i < objectsToCarry.size(); i++) {
			if (!objTotal.contains(objectsToCarry.get(i))) {
				objTotal.add(objectsToCarry.get(i));
			}
		}
		if (answer) {
			for (int i = 0; i < objTotal.size(); i++) {
				if (this.isColliding(objTotal.get(i))) {
					objTotal.get(i).setX(objTotal.get(i).getX() + displacement);
				} else {
					objTotal.get(i).goX(objTotal.get(i).getX() + displacement);
				}
			}
		}
		return answer;
	}
	public boolean goY(double displacement, ArrayList <GameObject> objectsToCarry) {
		boolean answer = super.goY(this.getY() + displacement);
		ArrayList <GameObject> objTotal = this.getCarryObjs();
		for (int i = 0; i < objectsToCarry.size(); i++) {
			if (!objTotal.contains(objectsToCarry.get(i))) {
				objTotal.add(objectsToCarry.get(i));
			}
		}
		if (answer) {
			for (int i = 0; i < objTotal.size(); i++) {
				if (this.isColliding(objTotal.get(i))) {
					objTotal.get(i).setY(objTotal.get(i).getY() + displacement);
				} else {
					objTotal.get(i).goY(objTotal.get(i).getY() + displacement);
				}
			}
		}
		return answer;
	}
}
