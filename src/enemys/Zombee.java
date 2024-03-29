package enemys;

import java.util.Random;

import gameObjects.DamageText;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.Sprite;

public class Zombee extends Enemy {
	private boolean pathDecided;
	int xToMove;
	Random decider;
	int yToMove;
	int timer;
	private final static Sprite NORMAL_SPRITE = new Sprite ("resources/sprites/config/zombee.txt") ;
	private final static Sprite WOOD_SPRITE = new Sprite ("resources/sprites/config/zombeeW.txt") ;
	private final static Sprite PETRIFIED_WOOD_SPRITE = new Sprite ("resources/sprites/ZombeePW.txt") ;
	public Zombee (){
		if (this.getVariantAttribute("Type") != null) {
			if (this.getVariantAttribute("Type").equals("Normal")) {
			this.setSprite(NORMAL_SPRITE);
			this.health = 150;
			this.defence = 0;
			}
			pathDecided = false;
			if (this.getVariantAttribute("Type").equals("Wood")) {
				this.health = 180;
				this.defence = 100;
				this.setSprite(WOOD_SPRITE);
			}
			if (this.getVariantAttribute("Type").equals("PetrifiedWood")) {
				this.health = 240;
				this.defence = 150;
				this.setSprite(PETRIFIED_WOOD_SPRITE);
			}
		} else {
			this.setSprite(NORMAL_SPRITE);
			this.health = 150;
			this.defence = 0;	
		}
		xToMove = 0;
		this.getAnimationHandler().setFrameTime(25);
		decider = new Random ();
		yToMove = 0;
		timer = 0;
		this.baseDamage = 20;
		this.setHitboxAttributes(0, 0, 16, 10);
		this.setGameLogicPriority(10);
	}
	public Zombee (String W){
        if (W.equals("N")) {
            this.setSprite(NORMAL_SPRITE);
        this.health = 150;
        this.defence = 0;
        }
        pathDecided = false;
        if (W.equals("W")) {
            this.health = 180;
            this.defence = 100;
            this.setSprite(WOOD_SPRITE);
        }
        if (W.equals("PW")) {
            this.health = 240;
            this.defence = 150;
            this.setSprite(PETRIFIED_WOOD_SPRITE);
        }
        xToMove = 0;
        decider = new Random ();
        yToMove = 0;
        timer = 0;
        this.baseDamage = 20;
        this.setHitboxAttributes(0, 0, 16, 10);
    }
	@Override 
	public String checkName () {
		return "ZOMBEE";
	}
	@Override
	public String checkEntry () {
		return "THE BEE POPULATION WAS DECLINEING INCREADBLY QUICKLY SO A INCREADBLY EXPERINCED TEAM OF NERDS LEAD BY A MAN NAMED DR. HYVE PERFORMED AND EXPERMENT THAT MADE BEES RISE FROM THE GRAVE BUT THEY BECAME INCREADBLY AGGRSIVE AND KILLED THE RESURCHES";
	}
	@Override
	public void enemyFrame () {
		if (((Player.getActivePlayer().getX() > this.getX()) && Player.getActivePlayer().getY() > this.getY()) && !pathDecided) {
			xToMove = decider.nextInt(3) + 1;
			yToMove = decider.nextInt(3) + 1;
			this.getAnimationHandler().setFlipHorizontal(false);
			pathDecided = true;
		}
if ((Player.getActivePlayer().getX() < this.getX()) && Player.getActivePlayer().getY() > this.getY()&& !pathDecided) {
	xToMove = decider.nextInt(3) + 1;
	xToMove = xToMove * -1;
	this.getAnimationHandler().setFlipHorizontal(true);
	yToMove = decider.nextInt(3) + 1;
	pathDecided = true;		
		}
if ((Player.getActivePlayer().getX() > this.getX()) && Player.getActivePlayer().getY() < this.getY()&& !pathDecided) {
	xToMove = decider.nextInt(3) + 1;
	yToMove = decider.nextInt(3) + 1;
	yToMove = yToMove * -1;
	this.getAnimationHandler().setFlipHorizontal(false);
	pathDecided = true;
}
if ((Player.getActivePlayer().getX() < this.getX()) && Player.getActivePlayer().getY() < this.getY()&& !pathDecided) {
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
if (Room.isColliding(this)) {
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
if (this.isColliding(Player.getActivePlayer())) {
	this.attackEvent();
}
	}
}
