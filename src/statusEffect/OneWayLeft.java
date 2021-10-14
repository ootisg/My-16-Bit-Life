package statusEffect;

import players.Player;

public class OneWayLeft extends StatusType {
	public OneWayLeft () {
		
	}
	@Override
	public void onStatus () {
		Player.getActivePlayer().bindRight = true;	
	}
	public void onCure () {
		Player.getActivePlayer().bindRight = false;
	}
}
