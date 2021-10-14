
package statusEffect;

import players.Player;

public class Lemony extends StatusType {
	
	public Lemony () {
	}
	
	@Override
	public void onCure () {
		Player.getActivePlayer().status.applyStatus("Slowness", 4500);
	}
	
	
	
}