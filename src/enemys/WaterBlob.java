package enemys;

import java.util.Random;

import items.Item;
import main.GameCode;
import main.ObjectHandler;
import gui.Tbox;
import map.Room;
import players.Player;
import resources.Sprite;

public class WaterBlob extends Enemy {
	//And what do you know, I don't have the water blob sprites either
	private int timer;
	private Random RNG;
	private boolean dirction;
	private boolean jumping;
	private boolean falling;
	private int oldTimer;
	private static final Sprite LEFT_JUMP_SPRITE = new Sprite ("resources/sprites/config/waterBlobLeftJump.txt");
	private static final Sprite RIGHT_JUMP_SPRITE = new Sprite ("resources/sprites/config/waterBlobRightJump.txt");
	private static final Sprite IDLE_SPRITE = new Sprite ("resources/sprites/config/waterBlobIdle.txt");
	private static final Sprite DROPLET_SPRITE = new Sprite ("resources/sprites/config/waterBlobDroplet.txt");
	public WaterBlob () {
		jumping = false;
		this.setSprite (IDLE_SPRITE);
		setHitboxAttributes (0, 0, 40, 32);
		this.enablePixelCollisions();
		this.health = 80;
		this.defence = 0;
		timer = 0;
		RNG = new Random ();
		this.getAnimationHandler().keepScale();
		getAnimationHandler ().setFrameTime (150);
		dirction = true;
		falling = false;
		oldTimer = 0;
		this.getAnimationHandler().scale(40, 32);
	}
	public void attackEvent() {
		super.attackEvent();
		if (oldTimer == 0) {
			if (timer != 0) {
				oldTimer = timer;
				if (Player.getInventory().amountOfConsumables() == 0) {
					Tbox box;
					box = new Tbox (this.getX(),this.getY() - 45, 12, 4, new String ("YOU HAVE NO ITEMS WHAT A MORRON"), true);
				} else {
					Item itemToRemove = Player.getInventory().getConsumableItems().get(RNG.nextInt(Player.getInventory().amountOfConsumables()));
					Tbox box;
					box = new Tbox (this.getX(),this.getY() - 45, 12, 4, new String ("A " + itemToRemove.checkName() + " WAS RUINED"), true);
					Player.getInventory().removeItem(itemToRemove);
				}
			} else {
				oldTimer = 1;
				if (Player.getInventory().amountOfConsumables() == 0) {
					Tbox box;
					box = new Tbox (this.getX(),this.getY() - 45, 12, 4, new String ("YOU HAVE NO ITEMS WHAT A MORRON"), true);
				} else {
					Item itemToRemove = Player.getInventory().getConsumableItems().get(RNG.nextInt(Player.getInventory().amountOfConsumables()));
					Tbox box;
					box = new Tbox (this.getX(),this.getY() - 45, 12, 4, new String ("A " + itemToRemove.checkName() + " WAS RUINED"), true);
					Player.getInventory().removeItem(itemToRemove);
				}
			}
		}
	}
	@Override
	public void enemyFrame () {
		if (timer - 60 > oldTimer ) {
			oldTimer = 0;
		}
		if (this.isNearPlayerX(10, 100, 10, 100) && !jumping && !falling) {
			if (Player.getActivePlayer().getX()>this.getX()) {
				dirction = true;
			} else {
				dirction = false;
			}
			jumping = true;
			timer = 0;
		}
		if (jumping) {
			if (dirction) {
				if (!this.getSprite().equals(RIGHT_JUMP_SPRITE)){
				this.setSprite(RIGHT_JUMP_SPRITE);
				this.getAnimationHandler().setRepeat(false);
				}
			} else {
				if (!this.getSprite().equals(LEFT_JUMP_SPRITE)){
					this.setSprite(LEFT_JUMP_SPRITE);
					this.getAnimationHandler().setRepeat(false);
			}
		}
			this.setY(this.getY() -4);
			if (Room.isColliding(this)) {
				this.setY(this.getY() + 4);
				timer = 60;
			}
			if ( timer == 60|| Player.getActivePlayer().getX() > this.getX() && Player.getActivePlayer().getX() < this.getX() + this.hitbox().getWidth()) {
				falling = true;
				if (!this.getSprite().equals(DROPLET_SPRITE)) {
					this.setSprite(DROPLET_SPRITE);
					this.getAnimationHandler().setRepeat(true);
					this.getAnimationHandler().enableAlternate();
				}
				timer = 0;
				jumping = false;
			}
		}
		if (!falling) {
		if (dirction) {
			if (!this.goX(this.getX() + 2)) {
			dirction = false;	
			}
		} else {
			if (!this.goX(this.getX() - 2)) {
			dirction = true;
			}
		}
		}
		timer = timer + 1;
		this.setY(this.getY() + 5);
		if (!Room.isColliding(this) && !falling && !jumping) {
			jumping =true;
			timer = 0;
		}
		this.setY(this.getY() - 5);
		if (falling) {
			this.setY(this.getY() + 5);
			if (Room.isColliding(this)) {
				this.setY(this.getY()- 5);
				if (!Room.isColliding(this)) {
					this.setSprite(IDLE_SPRITE);
					this.getAnimationHandler().disableAlternate();
					while(Room.isColliding(this)) {
						this.setY(this.getY() + 1);
						
					}
					setHitboxAttributes (0, 0, 40, 32);
					falling = false;
				}
			}
		}
	}
}
