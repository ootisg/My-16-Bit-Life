package gameObjects;

import main.GameObject;
import players.Player;

public class GrabbableObject extends GameObject {
	
	boolean grabbedOn = false;

	public void onRelease() {
		
	}
	public void onGrab() {
		
	}
	public void whileGrabbed() {
		
	}
	public void whileNotGrabbed() {
		
	}
	@Override
	public void frameEvent () {
		Player j = Player.getActivePlayer();  //TODO make this work with swappable Jeffreys
		
		if (this.isColliding(j) && keyDown (' ') && !grabbedOn) {
			grabbedOn = true;
			j.stopFall(true);
			j.bindLeft = true;
			j.bindRight = true;
			j.vx = 0;
			this.onGrab();
			
		}
		if (grabbedOn && !keyDown(' ')) {
			grabbedOn = false;
			j.bindLeft = false;
			j.bindRight = false;
			j.stopFall(false); 
			this.onRelease();
		}
		if (grabbedOn) {
			whileGrabbed();
		} else {
			whileNotGrabbed();
		}
	}
	
}
