package npcs;

import resources.AnimationHandler;

public class PointGuy extends NPC {
AnimationHandler animationHandler;
	public PointGuy () {
		this.setSprite(sprites.pointGuy);
		this.createHitbox(0, 0, 17, 22);
		animationHandler = new AnimationHandler (sprites.pointGuy);
		this.setAnimationHandler(animationHandler);
		animationHandler.setAnimationSpeed(0.1);
	this.changeMessage("???-HI JEFFREY ITS ME THE GUY THAT LIKES TO POINT AT STUFF YEAH THATS MY GIMIC I POINT AT STUFF DRAMATICLY COOL RIGHT.  JEFFREY - MAN I WISH I COULD DO THAT VERY BASIC THING THAT ALMOST ANYONE CAN DO.  POINT GUY - MY TECHNIQUE CAN NOT BE TAUGHT TO OTHERS, BUT YOU CAN HAVE MY LEFT ARM IF YOU WANT IT IS ONLY IMPLYED TO EXIST ANYWAY BECAUSE OF THE WAY IM DRAWN.  JEFFREY - UM OK       JEFFREY GOT THE LEFT ARM");	
	}
}
