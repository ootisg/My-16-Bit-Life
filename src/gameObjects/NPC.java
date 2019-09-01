package gameObjects;

import items.FairUseKey;
import main.GameCode;
import main.GameObject;
import main.Textbox;
import resources.AnimationHandler;
import resources.Sprite;

public class NPC extends GameObject{
	//Don't have the sprites here either
	Textbox diolog;
	AnimationHandler animationHandler;
	Sprite door;
	boolean usefulBoolean = false;
	public NPC () {
		if (this.getVariantData().equals("pointGuy")) {
			this.setSprite(sprites.pointGuy);
			this.setHitboxAttributes(0, 0, 17, 22);
			animationHandler = new AnimationHandler (sprites.pointGuy);
			this.setAnimationHandler(animationHandler);
			animationHandler.setAnimationSpeed(0.1);
		}
		if (this.getVariantData().equals("tomatoDoor")){
			door = new Sprite("resources/sprites/gate.png");
			this.setSprite(door);
			this.setHitboxAttributes(0,0,5,32);
		}
	}
	// use the NPC name from the doc
	public NPC (String NPCInternalName) {
		if (NPCInternalName.equals("pointGuy")) {
			this.setSprite(sprites.pointGuy);
			this.setHitboxAttributes(0, 0, 17, 22);
			animationHandler = new AnimationHandler (sprites.pointGuy);
			this.setAnimationHandler(animationHandler);
			animationHandler.setAnimationSpeed(0.1);
		}
		if (NPCInternalName.equals("tomatoDoor")){
			door = new Sprite("resources/sprites/gate.png");
			this.setSprite(door);
			this.setHitboxAttributes(0,0,5,32);
		}
	}
	public void frameEvent () {
		if (this.getSprite().equals(sprites.pointGuy) && this.isColliding(GameCode.testJeffrey) && keyPressed (84) && (diolog == null || diolog.isDone == true)) {
			diolog = new Textbox ("resources/sprites/text.png", "???-HI JEFFREY ITS ME THE GUY THAT LIKES TO POINT AT STUFF YEAH THATS MY GIMIC I POINT AT STUFF DRAMATICLY COOL RIGHT.  JEFFREY - MAN I WISH I COULD DO THAT VERY BASIC THING THAT ALMOST ANYONE CAN DO.  POINT GUY - MY TECHNIQUE CAN NOT BE TAUGHT TO OTHERS, BUT YOU CAN HAVE MY LEFT ARM IF YOU WANT IT IS ONLY IMPLYED TO EXIST ANYWAY BECAUSE OF THE WAY IM DRAWN.  JEFFREY - UM OK       JEFFREY GOT THE LEFT ARM", 200, 100, (int)this.getX(), (int)this.getY() - 140);
			diolog.declare(0, 0);
		}
		if (this.getSprite().equals(this.door)) {
			FairUseKey key = new FairUseKey ();
			if (GameCode.testJeffrey.binded == false && usefulBoolean) {
				GameCode.testJeffrey.inventory.removeItem(key);
				this.forget();
			}
				if (this.isColliding(GameCode.testJeffrey)) {
				if (GameCode.testJeffrey.inventory.checkKey(key)) {
					if (usefulBoolean == false) {
					diolog = new Textbox ("resources/sprites/text.png", "JEFFREY USED THE FAIR USE KEY TO UNLOCK THE WAY FORWARD  JEFFREY - MAN THIS KEY IS USEFUL I SHOULD HOLD ONTO IT INCASE I ENCOUNTER ANYMORE OF THESE GATES LATER     NATHAN - WHAT NO YOU CAN NOT KEEP THE KEY THAT COMPLEATLY DEFEATES THE PURPOSE OF THE LEVEL DESIGN IF YOU ONLY HAVE TO FIND THE KEY ONCE  JEFFREY - OH OK   JEFFREY THREW AWAY THE FAIR USE KEY", 208, 100, (int)this.getX(), (int) this.getY() - 50);
					diolog.declare(0, 0);
					}
					usefulBoolean = true;
					this.setY(this.getY() + 1);
				} else {
					diolog = new Textbox ("resources/sprites/text.png", "THERE IS COPYWRITED MATERIAL PAST THIS GATE YOU MUST AQUIRE A FAIR USE KEY TO PROCEED", 200, 32, (int)this.getX(), (int) this.getY());
					diolog.declare(0, 0);
					if (this.getX()> GameCode.testJeffrey.getX()) {
						GameCode.testJeffrey.setX(GameCode.testJeffrey.getX() - 5);
					} else {
						GameCode.testJeffrey.setX(GameCode.testJeffrey.getX() + 5);
				}
				}
			}
		}
	}
}
