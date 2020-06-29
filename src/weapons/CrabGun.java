package weapons;

import main.ObjectHandler;
import players.Jeffrey;
import projectiles.DirectionBullet;
import projectiles.PokaDot;
import resources.Sprite;

public class CrabGun extends AimableWeapon {
	int timer = 0;
	public static final Sprite crabGunBullet = new Sprite ("resources/sprites/config/Cyclops_Crab_Gun_Bullet.txt");
	public static final Sprite crabGunFireing = new Sprite ("resources/sprites/config/Cyclops_Crab_Gun_Firing.txt");
	Sprite idleSprite;
	private static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	public CrabGun (Sprite spriteToUse) {
		super(spriteToUse);
		idleSprite = spriteToUse;
		this.getAnimationHandler().setFrameTime(45);
	}
	@Override
	public void frameEvent () {
		timer = timer + 1;
		if (timer > 5) {
			timer = 0;
			
			DirectionBullet bullet;
			bullet = new DirectionBullet(this.getX(),this.getY());
			double suggestedDirection = bullet.findDirection(player);
			if (!this.getAnimationHandler().flipHorizontal()) {
				if (suggestedDirection > 5 || (suggestedDirection % 6.28 + 6.28) < 7.28 ) {
					this.setRotation(suggestedDirection);
					PokaDot dot = new PokaDot (this.rotation,crabGunBullet);
					dot.setSpeed(3);
					/*if (!this.getSprite().equals(crabGunFireing)) {
						this.setSprite(crabGunFireing);
						System.out.println(this.getSprite().equals(crabGunFireing));
					}*/
					dot.declare(this.getX(), this.getY() - 5);
				} else {
					//this.setSprite(idleSprite);
				}
			} else {
				if (suggestedDirection > 2 && suggestedDirection < 4.28 ) {
					this.setRotation((suggestedDirection* -1) - 3.3);
					PokaDot dot = new PokaDot ((this.rotation* -1) + 3.14 ,crabGunBullet);
					dot.setSpeed(3);
					/*if (!this.getSprite().equals(crabGunFireing)) {
						this.setSprite(crabGunFireing);
						System.out.println(this.getSprite().equals(crabGunFireing));
					}*/
					dot.declare(this.getX(), this.getY() - 5);
				} else {
					//this.setSprite(idleSprite);
				}
			}
		}
	}
}
