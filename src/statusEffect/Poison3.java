package statusEffect;

import players.Jeffrey;

public class Poison3 extends StatusType{
	
	int timer = 5;
	
	public Poison3 () {
		
	}
	//TODO deal with Jeffreys sprites
	@Override
	public void onStatus () {
		
		if (timer == 0) {
			Jeffrey.getActivePlayer().damage(1);
			timer = 5;
		}
		
		timer = timer - 1;
	}
}
