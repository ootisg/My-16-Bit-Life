package gameObjects;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import main.GameObject;
import main.RenderLoop;
import map.Room;
import projectiles.Projectile;
import resources.Sprite;

public class Shard extends Projectile {
	double direction;
	double speed;
	int timer = 0;
	boolean unaturally;
	protected BufferedImage img;
	private double rotation;
	private double renderedRotation;
	public Shard (Sprite sprite) {
		this.setHitboxAttributes(0, 0, 8, 8);
		img = new BufferedImage (sprite.getFrame (0).getWidth (), sprite.getFrame (0).getHeight (), sprite.getFrame (0).getType ());
	}
	@Override
	public void projectileFrame () {
		if (this.getDirection() % 6.28 > 1.57 && this.getDirection() %6.28 < 4.7) {
			this.rotation = this.rotation - (this.getSpeed()/1800.0 + 0.001);
		} else {
			this.rotation = this.rotation + (this.getSpeed()/1800.0 + 0.002);
		}
		if (!checkIfGoingIntoWall()) {
		if (this.getSpeed() != 0) {
		if (this.getDirection() % 6.28 > 0 && this.getDirection() %6.28 < 3.14 && this.speed < 15) {
			this.setSpeed(this.getSpeed() + 0.4);
		} else {
			if (this.getSpeed() < 15) {
				this.setSpeed(this.getSpeed() -0.3);
				if (this.getSpeed() < 0) {
					this.setSpeed(0);
				}
				if (this.getDirection() > 4.7) {
					this.setDirection(this.getDirection() - 0.01);
				} else {
					this.setDirection(this.getDirection() + 0.01);
				}
			}
		}
		} else {
			if (!(this.getDirection() % 6.28 > 0 && this.getDirection() %6.28 < 3.14)) {
				this.setDirection(1.57);
				this.setSpeed(this.getSpeed() + 3);
			}
		}
		}else {
			if (timer == 0) {
			this.setSpeed(this.getSpeed()/4);
			}
			this.setSpeed(this.getSpeed() - 0.1);
			if (this.getDirection() == 1.57) {
				this.forget();
			}
			
			timer = timer + 1;
			if (timer == 30) {
				this.forget();
			}
			this.goY( this.getY() + 3);
		}
	}
	@Override
	public void draw () {
		if (rotation != renderedRotation) {
			
			BufferedImage startImg = this.getSprite().getFrame (0);
			AffineTransform transform = new AffineTransform ();
			transform.rotate (rotation, 4, 4);
			AffineTransformOp operation = new AffineTransformOp (transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			img = new BufferedImage (this.getSprite().getFrame (0).getWidth (), this.getSprite().getFrame (0).getHeight (), this.getSprite().getFrame (0).getType ());
			operation.filter (startImg, img);
			this.setSprite (new Sprite (img));
			renderedRotation = rotation;
		}
		if (this.hiboxBorders) {
			if (this.hitbox() != null) {
				Graphics g = RenderLoop.window.getBufferGraphics();
				g.setColor(new Color(0xFFFFFF));
				g.drawRect((int)(this.getX() + this.getHitboxXOffset() - Room.getViewX()),(int) (this.getY() + this.getHitboxYOffset() - Room.getViewY()), this.hitbox().width, this.hitbox().height);
				}
			}
		getSprite ().draw ((int) getX () - Room.getViewX (), (int) getY () - Room.getViewY (), getAnimationHandler ().flipHorizontal (), false, 0);
	}
}
