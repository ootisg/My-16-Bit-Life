package mapObjects;

import resources.Sprite;

public class MoveablePlatform extends MapObject {
	public MoveablePlatform () {
		this.setHitboxAttributes(0, 0, 20, 16);
	}
	
	@Override
	public void onDeclare () {
		if (this.getVariantAttribute("spritePath") != null) {
			if (!this.getVariantAttribute("spritePath").equals("nv")) {
				this.setSprite(new Sprite ("resources/sprites/" +this.getVariantAttribute("spritePath")));
				this.setHitboxAttributes(0, 0, this.getSprite().getFrame(0).getWidth(), this.getSprite().getFrame(0).getHeight());
			} else {
				this.setSprite(new Sprite ("resources/sprites/default_platform.png"));	
			}
		} else {
			this.setSprite(new Sprite ("resources/sprites/default_platform.png"));
		}
	}
}
