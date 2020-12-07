package mapObjects;

import java.util.ArrayList;

import main.GameObject;

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
			objectsToCarry.get(i).goX(objectsToCarry.get(i).getX() + displacement);
		}
	}
	public void setY(double displacement,ArrayList <GameObject> objectsToCarry) {
		super.setY(this.getY() + displacement);
		for (int i = 0; i < objectsToCarry.size(); i++) {
			objectsToCarry.get(i).goY(objectsToCarry.get(i).getY() + displacement);
		}
	}
}
