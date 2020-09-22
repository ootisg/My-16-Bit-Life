package switches;

import resources.Sprite;

public class OneUseButton extends Switch {
	boolean weighted = false;
	public  OneUseButton () {
		this.makeSolid();	
		this.setSprite(new Sprite ("resources/sprites/button.png"));
		this.setHitboxAttributes(0, 0, 32, 12);
	}
	@Override 
	public void frameEvent () {
		super.frameEvent();
		this.setY(this.getY() - 4);
		this.isCollidingChildren("GameObject");
		boolean allGood = false;
		if (this.getCollisionInfo().collisionOccured()) {
			for (int i = 0; i < this.getCollisionInfo().getCollidingObjects().size(); i++) {
				if (this.getCollisionInfo().getCollidingObjects().get(i).getSprite() != null) {
					allGood = true;
					break;
				}
			}
		}
			if (!allGood) {
				this.onFlip();
				this.weighted = true;
		}
		this.setY(this.getY() + 4);
	}
	
}
