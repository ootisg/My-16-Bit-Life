package gameObjects;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class LaunchPad extends GameObject {
	
	public static final Sprite ladder = new Sprite ("resources/sprites/Ladder.png");
	
	boolean onTop;
	public LaunchPad () {
		this.setSprite(ladder);
		this.setHitboxAttributes(0, 0, 8, 8);
		onTop = false;
	}
	
	public  void frameEvent () {
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
		if (this.isColliding(jeffrey) && keyPressed('W')) {
			Jeffrey.onLadder = true;
			Jeffrey.vy = 0;
			Jeffrey.vx = 0;
			jeffrey.setX(this.getX());
		}
		if (jeffrey.getY() <= this.getY()  && this.isColliding(jeffrey)) {
			Jeffrey.onLadder = false;
			Jeffrey.standingOnPlatform = true;
		}
		if (Jeffrey.standingOnPlatform && (keyPressed (32))) {
			Jeffrey.standingOnPlatform = false;
		}
		if (Jeffrey.onLadder && keyDown ('W') && this.isColliding (jeffrey)) {
			jeffrey.setY(jeffrey.getY() -3);
		}
		if (Jeffrey.onLadder && keyDown ('S') && this.getY() >= jeffrey.getY()) {
			jeffrey.setY(jeffrey.getY() + 3);
		}
		if (Jeffrey.onLadder && (keyPressed (32))){
			Jeffrey.onLadder = false;
		}
	}
}
