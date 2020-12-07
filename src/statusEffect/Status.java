package statusEffect;

import java.util.Arrays;

import enemys.Enemy;
import main.GameObject;
import players.Jeffrey;

public class Status extends GameObject {
	public Boolean [] statusAppliedOnJeffrey;
	public Boolean [] statusAppliedOnSam;
	public Boolean [] statusAppliedOnRyan;
	public Status(){
		statusAppliedOnJeffrey = new Boolean [10];
		statusAppliedOnSam = new Boolean [10];
		statusAppliedOnRyan = new Boolean [10];
		Arrays.fill(statusAppliedOnJeffrey, false);
		Arrays.fill(statusAppliedOnSam, false);
		Arrays.fill(statusAppliedOnRyan, false);
	}
	//indexes 
	// 0 = poison
	// 1 = one way
	// 2 = lemony
	// 3 = slowness
	// 4 = brittle
	// 5 = fast
	// 6 = powerful
	// 7 = regenaration
	// 8 = slippery
	// when makieng new status be sure to add a thing that makes this true when its aplied and false when it is not
	public boolean checkStatus (int index, int charictar) {
	if (charictar == 0) {
	return statusAppliedOnJeffrey [index];
	}
	if (charictar == 1) {
	return statusAppliedOnSam [index];
	}
	if (charictar == 2) {
	return statusAppliedOnRyan[index];	
	}
	return false;
	}
	/**
	 * use to a apply a status duh
	 * @param index the numerical index corrisponding to the correct staus as explained earlier in this file
	 * @param charictar the index for the charictar that receves the status as allways Jeffrey is 0 Sam is 1 and Ryan is 2
	 */
	public void applyStatus (int index, int charictar) {
		if (charictar == 0) {
			statusAppliedOnJeffrey [index] = true;
		}
		if (charictar == 1) {
			 statusAppliedOnSam [index] = true;
		}
		if (charictar == 2) {
			statusAppliedOnRyan[index] = true;	
		}
	}
	// use to cure status
	// in the class for your status make it so if this ever becomes false the status gets forgoten
	public void CureStatus(int index, int charictar){
	if (charictar == 0) {
		statusAppliedOnJeffrey [index] = false;
	}
	if (charictar == 1) {
		statusAppliedOnSam [index] = false;
	}
	if (charictar == 2) {
		statusAppliedOnRyan [index] = false;
	}
	}
}
