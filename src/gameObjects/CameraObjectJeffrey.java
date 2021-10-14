package gameObjects;

import players.Player;

public class CameraObjectJeffrey extends CameraObject {
	
	public CameraObjectJeffrey (int panTime) {
		super ();
		this.panTime = panTime;
	}
	@Override 
	public void frameEvent () {
		super.frameEvent();
		if (this.isActivated() && !this.panning) {
			this.giveUpControl();
		}
	}
	@Override
	public void onJeffreyPosChange () {
		this.setX(Player.getActivePlayer().getX());
		this.setY(Player.getActivePlayer().getY());
	}
	@Override
	public void giveUpControl() {
		Player.getActivePlayer().setScroll(true);
		inControl = false;
	}
}
