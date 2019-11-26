package npcs;

import gameObjects.NPC;
import resources.Sprite;

public class PointGuy extends NPC {
	public PointGuy () {
		this.setSprite(new Sprite ("resources/sprites/config/point_guy.txt"));
		this.setHitboxAttributes(0, 0, 17, 22);
		this.getAnimationHandler().setFrameTime(700);
	this.changeMessage("???-HI JEFFREY ITS ME THE GUY THAT LIKES TO POINT AT STUFF YEAH THATS MY GIMIC I POINT AT STUFF DRAMATICLY COOL RIGHT.  JEFFREY - MAN I WISH I COULD DO THAT VERY BASIC THING THAT ALMOST ANYONE CAN DO.  POINT GUY - MY TECHNIQUE CAN NOT BE TAUGHT TO OTHERS, BUT YOU CAN HAVE MY LEFT ARM IF YOU WANT IT IS ONLY IMPLYED TO EXIST ANYWAY BECAUSE OF THE WAY IM DRAWN.  JEFFREY - UM OK       JEFFREY GOT THE LEFT ARM");	
	}
	@Override
	public String checkName () {
		return "POINT AT YOU GUY";
	}
	@Override
	public String checkEntry() {
		return "THIS GUY LIKES TO POINT DRAMATICLY AT THINGS ISEN'T HE COOL";
	}
}
