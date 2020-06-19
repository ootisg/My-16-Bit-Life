package enemys;

import map.Room;
import projectiles.PokaDot;
import resources.Sprite;

public class SpearThrower extends Enemy {
	int coolDownTimer = 0;
	public SpearThrower () {
		this.lockedRight = true;
		this.setSprite(new Sprite ("resources/sprites/SpearGuy.png"));
		this.setHitboxAttributes(0,0, 30, 40);
		this.setFalls(true);
		this.changeJumpTiemMultiplyer(0.7);
		this.health = 120;
		this.getAnimationHandler().setFrameTime(12);
		this.setMomentum(6);
		this.adjustHitboxBorders();
	}
	@Override 
	public void enemyFrame () {
		this.jump(3,8);
		this.setY(this.getY () - 6);
		if (this.lockedRight) {
			if (!checkX(this.getX() + 5)) {
				this.lockedRight = false;
				this.getAnimationHandler().setFlipHorizontal(false);
			}
		} else {
			if (!checkX(this.getX() - 5)) {
				this.lockedRight = true;
				this.getAnimationHandler().setFlipHorizontal(true);
			}
		}
		this.setY(this.getY () + 6);
		coolDownTimer = coolDownTimer + 1;
		if (this.isNearPlayerX(0, 300, 0, 0) && this.lockedRight && coolDownTimer > 30) {
			coolDownTimer = 0;
			PokaDot spear = new PokaDot (0,new Sprite ("resources/sprites/Spear.png"));
			spear.setSpeed(8);
			spear.getAnimationHandler().setFlipHorizontal(true);
			spear.declare(this.getX() + 16, this.getY() + 30);
		}
		if (this.isNearPlayerX(0, 0, 0, 300) && !this.lockedRight && coolDownTimer > 30) {
			coolDownTimer = 0;
			PokaDot spear = new PokaDot (Math.PI,new Sprite ("resources/sprites/Spear.png"));
			spear.setSpeed(8);
			spear.declare(this.getX() - 16, this.getY() + 30);
		}
	}

}
