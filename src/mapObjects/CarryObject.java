package mapObjects;

import java.util.ArrayList;

import main.GameObject;
import map.Room;

public class CarryObject extends MapObject {
	ArrayList<GameObject> objectsToCarry = new ArrayList<GameObject> ();
	public void addCarryObject (GameObject obj) {
		objectsToCarry.add(obj);
	}
	public void removeCarryObject (GameObject obj) {
		objectsToCarry.remove(obj);
	}
	public ArrayList <GameObject> getCarryObjects (){
		return objectsToCarry;
	}
	public void setX(double displacement,ArrayList <GameObject> objectsToCarry) {
		super.setX(this.getX() + displacement);
		for (int i = 0; i < objectsToCarry.size(); i++) {
			if (this.isColliding(objectsToCarry.get(i))) {
				objectsToCarry.get(i).setX(objectsToCarry.get(i).getX() + displacement);
			} else {
				objectsToCarry.get(i).goX(objectsToCarry.get(i).getX() + displacement);
			}
		}
	}
	public void setY(double displacement,ArrayList <GameObject> objectsToCarry) {
		super.setY(this.getY() + displacement);
		for (int i = 0; i < objectsToCarry.size(); i++) {
			if (this.isColliding(objectsToCarry.get(i))) {
				objectsToCarry.get(i).setY(objectsToCarry.get(i).getY() + displacement);
			} else {
				objectsToCarry.get(i).goY(objectsToCarry.get(i).getY() + displacement);
			}
		}
	}
	public boolean goX(double displacement, ArrayList <GameObject> objectsToCarry) {
		boolean answer = super.goX(this.getX() + displacement);
		if (answer) {
			for (int i = 0; i < objectsToCarry.size(); i++) {
				if (this.isColliding(objectsToCarry.get(i))) {
					objectsToCarry.get(i).setX(objectsToCarry.get(i).getX() + displacement);
				} else {
					objectsToCarry.get(i).goX(objectsToCarry.get(i).getX() + displacement);
				}
			}
		}
		return answer;
	}
	public boolean goY(double displacement, ArrayList <GameObject> objectsToCarry) {
		boolean answer = super.goY(this.getY() + displacement);
		if (answer) {
			for (int i = 0; i < objectsToCarry.size(); i++) {
				if (this.isColliding(objectsToCarry.get(i))) {
					objectsToCarry.get(i).setY(objectsToCarry.get(i).getY() + displacement);
				} else {
					objectsToCarry.get(i).goY(objectsToCarry.get(i).getY() + displacement);
				}
			}
		}
		return answer;
	}
}
