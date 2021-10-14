package statusEffect;

import gameObjects.DamageText;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import players.Player;

public class Regeneration1 extends StatusType{
	
	int timer = 30;
	
	public Regeneration1 () {
		
	}
	//TODO deal with Jeffreys sprites
	@Override
	public void onStatus () {
		
		if (timer == 0) {
			Jeffrey.getActivePlayer().heal(1);
			timer = 30;
		}
		
		timer = timer - 1;
	}
}
