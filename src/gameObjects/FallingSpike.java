package gameObjects;

import cutsceens.MoveSlowEvent;
import cutsceens.SlowMover;
import main.GameObject;
import map.Room;
import players.Player;
import resources.Sprite;

public class FallingSpike extends GameObject  {
	SlowMover event;
	boolean falling = false;
	public FallingSpike () {
		this.setSprite(new Sprite ("resources/sprites/SpikeDown.png"));
		this.setHitboxAttributes(0, 0, 15, 16);
		this.adjustHitboxBorders();
	}
	@Override
	public void frameEvent () {
		if (isColliding (Player.getActivePlayer())) {
			attackEvent ();
		}
		if ((Player.getActivePlayer().getX() > this.getX () - 8&& Player.getActivePlayer().getX() < this.getX() + 8 || falling)) {
			falling = true;
			if (event == null) {
				int origioalPosiotn = (int)this.getY();
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
				this.forget();
			}
		}
	}
	public void attackEvent () {
		Player.getActivePlayer().damage(10);
	}

}
