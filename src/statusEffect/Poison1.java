package statusEffect;

import players.Jeffrey;

public class Poison1 extends StatusType{
	
	int timer = 30;
	
	public Poison1 () {
		
	}
	//TODO deal with Jeffreys sprites
	@Override
	public void onStatus () {
		
		if (timer == 0) {
			Jeffrey.getActivePlayer().damage(1);
			timer = 30;
		}
		
		timer = timer - 1;
	}
}
