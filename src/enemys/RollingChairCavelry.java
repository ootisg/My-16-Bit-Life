package enemys;

import java.util.Random;

import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.Sprite;

public class RollingChairCavelry extends Enemy{
	int direction;
	Random RNG;
	int turrning;
	boolean realDirection;
	int timer;
	
	boolean inzialized = false;
	
	private static final Sprite IDLE_RIGHT_SPRITE = new Sprite ("resources/sprites/config/RollingChairRightIdle.txt");
	private static final Sprite LEFT_SPRITE= new Sprite ("resources/sprites/config/RollingChairLeft.txt");
	private static final Sprite RIGHT_SPRITE= new Sprite ("resources/sprites/config/RollingChairRight.txt");
	private static final Sprite TURNING_SPRITE= new Sprite ("resources/sprites/config/RollingChairTurrning.txt");
	private static final Sprite IDLE_LEFT_SPRITE= new Sprite ("resources/sprites/config/RollingChairLeftIdle.txt");
public RollingChairCavelry () {
	this.setFalls(true);
	this.setHitboxAttributes(10, 0, 43, 24);
	this.health = 75;
	RNG = new Random ();
	this.defence = 0;
	turrning = 0;
	timer = 0;
	this.enablePixelCollisions();
	direction = 0;
}
	@Override 
public void enemyFrame () {
		if (!inzialized) {
			try {
				if (this.getVariantAttribute("startLeft").equals("yes")) {
					this.setSprite(IDLE_LEFT_SPRITE);
					realDirection = false;
				} else {
					this.setSprite(IDLE_RIGHT_SPRITE);
					realDirection = true;
				}
				}catch (NullPointerException e) {
					this.setSprite(IDLE_RIGHT_SPRITE);
					realDirection = true;
				}
			inzialized = true;
		}
		if (realDirection && Player.getActivePlayer().getX() < this.getX()) {
			this.setSprite(TURNING_SPRITE);
			turrning = 1;
			realDirection = false;
		}
		if (!realDirection && Player.getActivePlayer().getX() > this.getX()) {
			this.setSprite(TURNING_SPRITE);
			turrning = 1;
			this.getAnimationHandler().setFlipHorizontal(true);
			realDirection = true;
		}
		if (turrning > 0) {
			turrning = turrning + 1;
			if (turrning > 8) {
				if (realDirection) {
					this.getAnimationHandler().setFlipHorizontal(false);
					turrning = 0;
					this.setSprite(RIGHT_SPRITE);
				} else {
					turrning = 0;
					this.setSprite(LEFT_SPRITE);
				}
			}
		}
		if (realDirection) {
			if (direction < 0 ) {
				direction = direction + 5;
				if (direction >= 0) {
					direction = 0;
					timer = 0;
				}
		} else {
			if (direction < 8) {
			timer = timer + 1;
			if (timer == 8) {
				timer = 0;
				if (direction == 0) {
					if (!this.getSprite().equals(IDLE_RIGHT_SPRITE)) {
					direction = 1;
				}
				} else {
				direction = direction + direction;
				}
			}
		}
		}
		} else {
			if (direction > 0 ) {
				direction = direction - 5;
				if (direction <= 0) {
					direction = 0;
					timer = 0;
				}
		} else {
			if (direction > -8) {
			timer = timer + 1;
			if (timer == 8) {
				timer = 0;
				if (direction == 0) {
					if (!this.getSprite().equals(IDLE_LEFT_SPRITE)) {
						direction = -1;
					}
				} else {
				direction = direction + direction;
				}	
			}
		}
		}
		}
		int i;
		if (direction > 0) {
		for (i = 0; i < direction; i ++ ) {
			this.setX(this.getX() + i);
			if (Room.isColliding(this)) {
				direction = 0;
				this.setSprite(IDLE_RIGHT_SPRITE);
			}
			this.setX(this.getX() - i);
			
			}
		}
		if (direction < 0) {
			for (i = 0; i > direction; i = i - 1 ) {
				this.setX(this.getX() + i);
				if (Room.isColliding(this)) {
					direction = 0;
					this.setSprite(IDLE_LEFT_SPRITE);
				}
				this.setX(this.getX() - i);
			}
		}
		this.setX(this.getX() + direction);
		if (this.isColliding(Player.getActivePlayer())) {
			if (direction != 0) {
				Player.getActivePlayer().damage(RNG.nextInt(Math.abs(direction)) * 5);
			} else {
				Player.getActivePlayer().damage(RNG.nextInt(10));	
			}
		}
		
	}
}
