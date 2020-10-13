package gameObjects;

import players.Jeffrey;

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
		this.setX(Jeffrey.getActiveJeffrey().getX());
		this.setY(Jeffrey.getActiveJeffrey().getY());
	}
	@Override
	public void giveUpControl() {
		Jeffrey.setScroll(true);
		inControl = false;
	}
}
