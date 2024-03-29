package enemys;

import cutsceens.MoveSlowEvent;
import cutsceens.SlowMover;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.Sprite;

public class FallingChandleer extends GameObject {
	boolean falling = false;
	SlowMover event;
	boolean rising = false;
	int origioalPosiotn;
	public FallingChandleer () {
		this.setSprite(new Sprite ("resources/sprites/chandleer.png"));
		this.setHitboxAttributes(0, 0, 30, 16);
	}
	@Override 
	public void frameEvent () {
		if (isColliding (Player.getActivePlayer())) {
			attackEvent ();
		}
		
		if ((Player.getActivePlayer().getX() > this.getX () + 8 && Player.getActivePlayer().getX() < this.getX() +22 || falling) && !rising) {
			falling = true;
			if (event == null) {
				origioalPosiotn = (int)this.getY();
				int workingPosition = (int)this.getY();
				while (true) {
					this.setY(workingPosition);
					if (Room.isColliding(this) || workingPosition == 1000+this.getY()){
						break;
					}
					workingPosition = workingPosition + 1;
				}
				this.setY(origioalPosiotn);
				
		
				event =  new SlowMover (this, (int)this.getX(), workingPosition , 170, 240, 0, 10, 100000000);
			}
			if (event.runEvent()) {
				falling = false;
				rising = true;
				event = null;
			}
		}
		if (rising) {
			if (event == null) {
				event =  new SlowMover (this, (int)this.getX(),origioalPosiotn, 0, 100, 0, 200, 100000000);
			}
			if (event.runEvent()) {
				rising = false;
				event = null;
			}
		}
	}
	public void attackEvent () {
		Player.getActivePlayer().damage (5);
	}
}
