package gameObjects;

import main.GameObject;
import resources.Sprite;

public class Rail extends GameObject {
	private int direction = 0;
	private boolean inzialized = false;
	public Rail () {
		this.setHitboxAttributes(0, 0, 16, 16);
		this.enablePixelCollisions();
		//this.adjustHitboxBorders();
		this.setSprite(new Sprite ("resources/sprites/railStrate.png"));
		
	}
	@Override 
	public void frameEvent () {
		if (!inzialized) {
		
			if (this.getVariantAttribute("Direction") != null) {
				switch (this.getVariantAttribute("Direction")) {
				case "Strate":
					this.direction = 0;
					this.setHitboxAttributes(0, 0, 16, 16);
					this.setSprite(new Sprite ("resources/sprites/railStrate.png"));
					break;
				case "UpLeft":
					this.direction = 1;
					this.setSprite(new Sprite ("resources/sprites/railDownRight.png"));
					break;
				case "UpRight":
					this.direction = 2;
					this.setSprite(new Sprite ("resources/sprites/railUpRight.png"));
					break;
				}
			} else {
				this.direction = 0;
				this.setHitboxAttributes(0, 0, 16, 16);
				this.setSprite(new Sprite ("resources/sprites/railStrate.png"));
			}
			inzialized  = true;
			this.getAnimationHandler().scale(16, 16);
		}
		
		if (this.isColliding("Minecart")) {
		switch (direction) {
		case 0:
			for (int i = 0; i < this.getCollisionInfo().getCollidingObjects().size(); i++) {
				Minecart sweetCart = (Minecart)this.getCollisionInfo().getCollidingObjects().get(i);
				if (!sweetCart.taken) {
					sweetCart.setFall(false);
					if (sweetCart.getAnimationHandler().getRotation() != 0) {
						sweetCart.rotateSprite(0);
						sweetCart.taken = true;
						sweetCart.direction = 0;
						while (this.isColliding(sweetCart)) {
							sweetCart.setY(sweetCart.getY() - 1);
						}
					}
					if (sweetCart.vx > 0) {
						sweetCart.vx = sweetCart.vx - 0.1;
						if (sweetCart.vx < 0) {
							sweetCart.vx = 0;
						}
					} else {
						if (sweetCart.vx != 0) {
							sweetCart.vx = sweetCart.vx + 0.1;
							if (sweetCart.vx > 0) {
								sweetCart.vx = 0;
							}
						} 
					}
				}
			}
			break;
		case 1:
			for (int i = 0; i < this.getCollisionInfo().getCollidingObjects().size(); i++) {
			Minecart epicCart = (Minecart)this.getCollisionInfo().getCollidingObjects().get(i);
			if (!epicCart.taken) {
				epicCart.setFall(true);
				if (epicCart.getAnimationHandler().getRotation() != 50) {
					epicCart.rotateSprite(50);
					epicCart.taken = true;
					epicCart.direction = 1;
					while (this.isColliding(epicCart)) {
						epicCart.setY(epicCart.getY() - 1);
					}
				}
				epicCart.vx = epicCart.vx + 1;
			}
		}
			break;
		case 2:
			for (int i = 0; i < this.getCollisionInfo().getCollidingObjects().size(); i++) {
			Minecart currentCart = (Minecart)this.getCollisionInfo().getCollidingObjects().get(i);
			if (!currentCart.taken) {
				currentCart.setFall(true);
				if (currentCart.getAnimationHandler().getRotation() != 310) {
					currentCart.rotateSprite(310);
					currentCart.taken = true;
					currentCart.direction = 2;
					while (this.isColliding(currentCart)) {
						currentCart.setY(currentCart.getY() - 1);
					}
					}
				currentCart.vx = currentCart.vx - 1;
				}
			}
			break;
		}
	}
}
	/** zero indicates a strate rail
	* one indicates a rail that goes up and to the left
	* two indicates a rail that goes up and to the right
	* 
	* @return the proper direction
	 */
	public int getDirection () {
		return direction;
	}
}
