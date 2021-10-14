package statusEffect;

import players.Player;

public class OneWayRight extends StatusType {
	public OneWayRight () {
		
	}
	@Override
	public void onStatus () {
		Player.getActivePlayer().bindLeft = true;	
	}
	public void onCure () {
		Player.getActivePlayer().bindLeft = false;
	}
}
