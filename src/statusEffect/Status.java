package statusEffect;

import gameObjects.Enemy;
import main.GameObject;
import players.Jeffrey;

public class Status extends GameObject {
	public Boolean [] statusAppliedOnJeffrey;
	public Boolean [] statusAppliedOnSam;
	public Status(){
		statusAppliedOnJeffrey = new Boolean [2];
		statusAppliedOnSam = new Boolean [2];
		statusAppliedOnJeffrey [0] = false;
		statusAppliedOnSam [0] = false;
		statusAppliedOnJeffrey[1] = false;
		statusAppliedOnSam [1] = false;
	}
	//indexes 
	// 0 = poison
	// 1 = one way
	// when makieng new status be sure to add a thing that makes this true when its aplied and false when it is not
	public boolean checkStatus (int index, int charictar) {
	if (charictar == 0) {
	return statusAppliedOnJeffrey [index];
	}
	if (charictar == 1) {
	return statusAppliedOnSam [index];
	}
	return false;
	}
	// use to cure status
	// in the class for your status make it so if this ever becomes false the status gets forgoten
	public void CureStatus(int index, int charictar){
	if (charictar == 0) {
		statusAppliedOnJeffrey [index] = false;
	}
	if (charictar == 1) {
		statusAppliedOnJeffrey [index] = false;
	}
	}
}
