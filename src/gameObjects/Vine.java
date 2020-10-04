package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.LoopableSprite;
import resources.Sprite;

public class Vine extends GameObject {
	Warning warn;
	LoopableSprite vineSprite;
	int desX = 0;
	int desY = 0;
	int tempY = 0;
	public Vine() {
		vineSprite = new LoopableSprite(new Sprite ("resources/sprites/config/vineSegment.txt"),0,45,0,0);
		vineSprite.addEndSprite(new Sprite ("resources/sprites/config/vineTop.txt"));
		this.setHitboxAttributes(0, -15, 15, 13);
		
	}
	@Override
	public void draw () {
		super.draw();
		if (warn.isWarned()) {
			if (desY <tempY) {
				tempY = tempY - 20;
				this.setHitboxAttributes(0, (int) (this.getHitboxYOffset()-20), 15, this.hitbox().height + 20);
			}
			vineSprite.setDestanation((int)this.getX() - Room.getViewX(), tempY);
			vineSprite.draw((int)this.getX() - Room.getViewX(), (int)this.getY() - Room.getViewY());
				if (this.isColliding(Jeffrey.getActiveJeffrey())) {
					Jeffrey.getActiveJeffrey().damage(10);
				}
		}
	}
	@Override
	public void onDeclare() {
		tempY = (int) this.getY();
		if (this.getVariantAttribute("warnTime") != null ){
			if (!this.getVariantAttribute("warnTime").equals("nv")){
				warn = new Warning (Integer.parseInt(this.getVariantAttribute("warnTime")),new Sprite ("resources/sprites/config/basicWarrning.txt"));
			} else {
				warn = new Warning (0,new Sprite ("resources/sprites/config/basicWarrning.txt"));
			}
		} else {
			warn = new Warning (0,new Sprite ("resources/sprites/config/basicWarrning.txt"));
		}
		warn.declare(this.getX(), this.getY());
		try {
			for (int i = 0; i < ObjectHandler.getObjectsByName("Point").size(); i++) {
				if (this.getVariantAttribute("partner").equals(ObjectHandler.getObjectsByName("Point").get(i).getVariantAttribute("PointNumber"))) {
					desX = (int)ObjectHandler.getObjectsByName("Point").get(i).getX();
					desY = (int)ObjectHandler.getObjectsByName("Point").get(i).getY();
				}
			}
		} catch (NullPointerException e) {
			
		}
	}

}
