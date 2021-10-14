package gameObjects;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import players.Player;
import resources.Sprite;

public class LaunchPad extends GameObject {
	
	public static final Sprite ladder = new Sprite ("resources/sprites/Ladder.png");
	Player jeffrey = (Player) ObjectHandler.getObjectsByName ("Player").get (0);
	boolean onTop;
	public LaunchPad () {
		this.setSprite(ladder);
		this.setHitboxAttributes(0, 0, 8, 8);
		onTop = false;
	}
	
	public  void frameEvent () {
		if (this.isColliding(jeffrey) && keyPressed('W')) {
			jeffrey.onLadder = true;
			jeffrey.setVy(0);
			jeffrey.vx = 0;
			jeffrey.setX(this.getX());
		}
		if (jeffrey.getY() <= this.getY()  && this.isColliding(jeffrey)) {
			jeffrey.onLadder = false;
			jeffrey.standingOnPlatform = true;
		}
		if (jeffrey.standingOnPlatform && (keyPressed (32))) {
			jeffrey.standingOnPlatform = false;
		}
		if (jeffrey.onLadder && keyDown ('W') && this.isColliding (jeffrey)) {
			jeffrey.setY(jeffrey.getY() -3);
		}
		if (jeffrey.onLadder && keyDown ('S') && this.getY() >= jeffrey.getY()) {
			jeffrey.setY(jeffrey.getY() + 3);
		}
		if (jeffrey.onLadder && (keyPressed (32))){
			jeffrey.onLadder = false;
		}
	}
}
