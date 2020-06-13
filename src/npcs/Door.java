package npcs;

import items.FairUseKey;
import resources.Sprite;

public class Door extends LegacyNPC{
	Sprite door;
	public Door (){
		door = new Sprite ("resources/sprites/Door.png");
		this.setSprite(door);
		this.changeTriggering();
		this.setHitboxAttributes(0,0,5,32);
		FairUseKey key = new FairUseKey ();
		this.takeItem(key, 1);
		this.setAmountOfItemMessages(1);
		this.disapear();
		this.changeMessage("THERE IS COPYWRITED MATERIAL PAST THIS GATE YOU MUST AQUIRE A FAIR USE KEY TO PROCEED");
		this.itemMessages[0] = "JEFFREY USED THE FAIR USE KEY TO UNLOCK THE WAY FORWARD JEFFREY - MAN THIS KEY IS USEFUL I SHOULD HOLD ONTO IT INCASE I ENCOUNTER ANYMORE OF THESE GATES LATER     NATHAN - WHAT NO YOU CAN NOT KEEP THE KEY THAT COMPLEATLY DEFEATES THE PURPOSE OF THE LEVEL DESIGN IF YOU ONLY HAVE TO FIND THE KEY ONCE  JEFFREY - OH OK   JEFFREY THREW AWAY THE FAIR USE KEY";
	}
	@Override 
	public String checkName () {
		return "DOOR";
	}
	@Override 
	public String checkEntry () {
		return "THIS IS MY BUDDY DOOR FROM COLLAGE OH THE TIMES WE HAD";
	}
}
