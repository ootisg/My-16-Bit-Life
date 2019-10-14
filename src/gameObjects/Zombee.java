package gameObjects;

import java.util.Random;

import main.GameCode;
import map.Room;
import resources.Sprite;

public class Zombee extends Enemy {
	private boolean pathDecided;
	int xToMove;
	Sprite normalSprite;
	Sprite woodSprite;
	Sprite petrifiedWoodSprite;
	Random decider;
	int yToMove;
	int timer;
	public Zombee (){
		if (this.getVariantAttribute("Type").equals("Normal")) {
			normalSprite = new Sprite ("resources/sprites/config/zombee.txt");
		this.setSprite(normalSprite);
		this.health = 150;
		this.defence = 0;
		}
		pathDecided = false;
		if (this.getVariantAttribute("Type").equals("Wood")) {
			this.health = 180;
			this.defence = 100;
			woodSprite = new Sprite ("resources/sprites/config/zombeeW.txt");
			this.setSprite(woodSprite);
		}
		if (this.getVariantAttribute("Type").equals("Petrified Wood")) {
			this.health = 240;
			this.defence = 150;
			petrifiedWoodSprite = new Sprite ("resources/sprites/ZombeePW.txt");
			this.setSprite(petrifiedWoodSprite);
		}
		xToMove = 0;
		this.getAnimationHandler().setFrameTime(25);
		decider = new Random ();
		yToMove = 0;
		timer = 0;
		this.baseDamage = 20;
		this.setHitboxAttributes(0, 0, 16, 10);
	}
	public Zombee (String W){
		if (W.equals("N")) {
			normalSprite = new Sprite ("resources/sprites/config/zombee.txt");
			this.setSprite(normalSprite);
		this.health = 150;
		this.defence = 0;
		}
		pathDecided = false;
		if (W.equals("W")) {
			this.health = 180;
			this.defence = 100;
			woodSprite = new Sprite ("resources/sprites/config/zombeeW.txt");
			this.setSprite(woodSprite);
		}
		if (W.equals("PW")) {
			this.health = 240;
			this.defence = 150;
			petrifiedWoodSprite = new Sprite ("resources/sprites/ZombeePW.txt");
			this.setSprite(petrifiedWoodSprite);
		}
		xToMove = 0;
		decider = new Random ();
		yToMove = 0;
		timer = 0;
		this.baseDamage = 20;
		this.setHitboxAttributes(0, 0, 16, 10);
	}
	@Override
	public void frameEvent () {
		if (timer%4 == 0) {
			this.setHitboxAttributes(0, 0, 128, 128);
			if (this.isColliding("gameObjects.ZombeeTree")) {
				this.health = this.health + 1;
				DamageText text;
				text = new DamageText (1,this.getX(),this.getY());
				text.declare(this.getX(), this.getY());
			}
			this.setHitboxAttributes(0, 0, 16, 10);
		}
		if (((GameCode.testJeffrey.getX() > this.getX()) && GameCode.testJeffrey.getY() > this.getY()) && !pathDecided) {
			xToMove = decider.nextInt(3) + 1;
			yToMove = decider.nextInt(3) + 1;
			this.getAnimationHandler().setFlipHorizontal(false);
			pathDecided = true;
		}
if ((GameCode.testJeffrey.getX() < this.getX()) && GameCode.testJeffrey.getY() > this.getY()&& !pathDecided) {
	xToMove = decider.nextInt(3) + 1;
	xToMove = xToMove * -1;
	this.getAnimationHandler().setFlipHorizontal(true);
	yToMove = decider.nextInt(3) + 1;
	pathDecided = true;		
		}
if ((GameCode.testJeffrey.getX() > this.getX()) && GameCode.testJeffrey.getY() < this.getY()&& !pathDecided) {
	xToMove = decider.nextInt(3) + 1;
	yToMove = decider.nextInt(3) + 1;
	yToMove = yToMove * -1;
	this.getAnimationHandler().setFlipHorizontal(false);
	pathDecided = true;
}
if ((GameCode.testJeffrey.getX() < this.getX()) && GameCode.testJeffrey.getY() < this.getY()&& !pathDecided) {
	xToMove = decider.nextInt(3) + 1;
	yToMove = decider.nextInt(3) + 1;
	yToMove = yToMove * -1;
	this.getAnimationHandler().setFlipHorizontal(true);
	xToMove = xToMove * -1;
	pathDecided = true;
}
this.setX(this.getX() + xToMove);
this.setY(this.getY() + yToMove);
timer = timer + 1;
if (timer == 20) {
	pathDecided = false;
}
if (Room.isColliding(this.hitbox())) {
	timer = 0;
	this.setX(this.getX() - xToMove);
	this.setY(this.getY() - yToMove);
	xToMove = decider.nextInt(3) + 1;
	yToMove = decider.nextInt(3) + 1;
	if (decider.nextBoolean()) {
	yToMove = yToMove * -1;
	}
	if (decider.nextBoolean()) {
	this.getAnimationHandler().setFlipHorizontal(true);
	xToMove = xToMove * -1;
	} else {
		this.getAnimationHandler().setFlipHorizontal(false);
	}
}
if (this.isColliding(GameCode.testJeffrey)) {
	this.attackEvent();
}
	}
}
