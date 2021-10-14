package statusEffect;

import players.Jeffrey;

public class Poison2 extends StatusType{
	
	int timer = 15;
	
	public Poison2 () {
		
	}
	//TODO deal with Jeffreys sprites
	@Override
	public void onStatus () {
		
		if (timer == 0) {
			Jeffrey.getActivePlayer().damage(1);
			timer = 15;
		}
		
		timer = timer - 1;
	}
}
