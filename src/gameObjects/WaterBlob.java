package gameObjects;

import java.util.Random;

import items.Item;
import main.GameCode;
import main.ObjectHandler;
import gui.Tbox;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class WaterBlob extends Enemy {
	//And what do you know, I don't have the water blob sprites either
	private Spritesheet blobSheet = new Spritesheet ("resources/sprites/Water Blob.png");
	private Sprite leftJumpSprite = new Sprite (blobSheet, new int [] {0}, new int [] {100}, 80,60, 40, 32);
	private Sprite rightJumpSprite = new Sprite (blobSheet, new int [] {80}, new int [] {20}, 80,60, 40,32);
	private Sprite idleSprite = new Sprite (blobSheet, new int [] {80}, new int [] {100}, 80,60,40,32);
	private Sprite dropletSprite = new Sprite (blobSheet, new int [] {80,160,0,80,160}, new int [] {100, 100,160, 160, 160}, 80,80,40,32);
	private int timer;
	private Random RNG;
	private boolean dirction;
	private boolean jumping;
	private boolean falling;
	private int oldTimer;
	public WaterBlob () {
		jumping = false;
		this.setSprite (idleSprite);
		setHitboxAttributes (0, 0, 40, 32);
		this.health = 80;
		this.defence = 0;
		timer = 0;
		RNG = new Random ();
		getAnimationHandler ().setFrameTime (333.33);
		dirction = true;
		falling = false;
		oldTimer = 0;
	}
	public void attackEvent() {
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
		if (oldTimer == 0) {
			if (timer != 0) {
				oldTimer = timer;
				if (jeffrey.inventory.amountOfConsumables() == 0) {
					Tbox box;
					box = new Tbox (this.getX(),this.getY() - 45, 12, 4, new String ("YOU HAVE NO ITEMS WHAT A MORRON"), true);
				} else {
				Item itemToRemove = jeffrey.inventory.findConsumableAtIndex(RNG.nextInt(jeffrey.inventory.amountOfConsumables()));
				Tbox box;
				box = new Tbox (this.getX(),this.getY() - 45, 12, 4, new String ("A " + itemToRemove.checkName() + " WAS RUINED"), true);
				jeffrey.inventory.removeItem(itemToRemove);
				}
			} else {
				oldTimer = 1;
				if (jeffrey.inventory.amountOfConsumables() == 0) {
					Tbox box;
					box = new Tbox (this.getX(),this.getY() - 45, 12, 4, new String ("YOU HAVE NO ITEMS WHAT A MORRON"), true);
				} else {
				Item itemToRemove = jeffrey.inventory.findConsumableAtIndex(RNG.nextInt(jeffrey.inventory.amountOfConsumables()));
				Tbox box;
				box = new Tbox (this.getX(),this.getY() - 45, 12, 4, new String ("A " + itemToRemove.checkName() + " WAS RUINED"), true);
				jeffrey.inventory.removeItem(itemToRemove);
				}
			}
		}
	}
	@Override
	public void enemyFrame () {
		if (timer - 60 > oldTimer ) {
			oldTimer = 0;
		}
		if (RNG.nextInt(900) == 666 && !jumping && !falling) {
			jumping = true;
			timer = 0;
		}
		if (jumping) {
			if (dirction) {
				if (!this.getSprite().equals(rightJumpSprite)){
				this.setSprite(rightJumpSprite);
				}
			} else {
				if (!this.getSprite().equals(leftJumpSprite)){
					this.setSprite(leftJumpSprite);
			}
		}
			this.setY(this.getY() -4);
			if (Room.isColliding(this.hitbox())) {
				this.setY(this.getY() + 4);
				timer = 30;
			}
			if (timer == 30) {
				falling = true;
				if (!this.getSprite().equals(dropletSprite)) {
					this.setSprite(dropletSprite);
				}
				timer = 0;
				jumping = false;
			}
		}
		if (!falling) {
		if (dirction) {
			this.setX(this.getX() + 1);
		} else {
			this.setX(this.getX() - 1);
		}
		}
		if (timer >=180 && !jumping) {
			timer = RNG.nextInt(2);
			System.out.println(timer);
			if (timer == 0) {
				dirction = false;
			} else {
				dirction = true;
			}
		}
		timer = timer + 1;
		if (Room.isColliding(this.hitbox()) && !falling && !jumping) {
			dirction = !dirction;
		}
		this.setY(this.getY() + 5);
		if (!Room.isColliding(this.hitbox()) && !falling && !jumping) {
			jumping =true;
			timer = 0;
		}
		this.setY(this.getY() - 5);
		if (falling) {
			if (timer == 38 && (getAnimationHandler ().getFrameTime() != 0)) {
				timer = 0;
				this.setHitboxAttributes(0, 0, 40, 32);
				getAnimationHandler ().setFrameTime(0);
			}
			this.setY(this.getY() + 1);
			if (Room.isColliding(this.hitbox())) {
				this.setY(this.getY()- 1);
				if (!Room.isColliding(this.hitbox())) {
					this.setSprite(idleSprite);
					
					while(Room.isColliding(this.hitbox())) {
						this.setY(this.getY() + 1);
						
					}
					setHitboxAttributes (0, 0, 40, 32);
					getAnimationHandler().setFrameTime(150);
					falling = false;
				}
			}
		}
	}
}
