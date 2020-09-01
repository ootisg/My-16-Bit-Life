package weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import items.Item;
import main.GameObject;
import main.RenderLoop;
import map.Room;
import projectiles.Projectile;
import resources.Sprite;

public class AimableWeapon extends Item {
	//This class is not yet commented
	protected BufferedImage img;
	protected Sprite src;
	protected double rotation;
	private double renderedRotation;
	public AimableWeapon (Sprite sprite) {
		this.src = sprite;
		this.rotation = 0;
		this.renderedRotation = -1;
		img = new BufferedImage (sprite.getFrame (0).getWidth (), sprite.getFrame (0).getHeight (), sprite.getFrame (0).getType ());
	}
	
	@Override
	public void draw () {
		if (this.visible) {
			if (rotation != renderedRotation) {
				BufferedImage startImg = src.getFrame (0);
				AffineTransform transform = new AffineTransform ();
				transform.rotate (rotation, 5, 12);
				AffineTransformOp operation = new AffineTransformOp (transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				img = new BufferedImage (this.src.getFrame (0).getWidth (), this.src.getFrame (0).getHeight (), this.src.getFrame (0).getType ());
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
			getSprite ().draw ((int) getX () - Room.getViewX () - 5, (int) getY () - Room.getViewY () - 12, getAnimationHandler ().flipHorizontal (), false, 0);
		}
	}	
	public void clearImage () {
		//BufferedImage usedImage = src.getImageArray ()[0];
	}
	public void setRotation (double rotation) {
		this.rotation = rotation;
	}
	public void changeSprite (Sprite newSprite) {
        src = newSprite;
    	BufferedImage startImg = src.getFrame (0);
		AffineTransform transform = new AffineTransform ();
		transform.rotate (rotation, 5, 12);
		AffineTransformOp operation = new AffineTransformOp (transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		img = new BufferedImage (this.src.getFrame (0).getWidth (), this.src.getFrame (0).getHeight (), this.src.getFrame (0).getType ());
		operation.filter (startImg, img);
		this.setSprite (new Sprite (img));
		renderedRotation = rotation;
    }
	public Sprite getUnrotatedSprite () {
		return this.getSprite();
	}
	public double getRotation () {
		return this.rotation;
	}
	public void setImg (BufferedImage imgbro) {
		img = imgbro;
	}
	public void shoot (Projectile projectile) {
	this.shoot(projectile, rotation);
	}
	public void shoot (Projectile projectile, double direction) {
		double ang = direction;
		double endX;
		double endY;
		if (getAnimationHandler ().flipHorizontal ()) {
			ang = Math.PI - ang;
		}
		if (getAnimationHandler ().flipHorizontal ()) {
			endX = this.getX () + Math.cos (ang + Math.PI /180 * 15) * 14 + 4;
			endY = this.getY () + Math.sin (ang + Math.PI / 180 * 15) * 14;
		} else {
			endX = this.getX () + Math.cos (ang - Math.PI / 180 * 15) * 14;
			endY = this.getY () + Math.sin (ang - Math.PI / 180 * 15) * 14;
		}
		projectile.declare();
		projectile.setAttributes (endX, endY, ang);
	}
	public double [] simulateShot () {
		double ang = this.getRotation();
		double endX;
		double endY;
		if (getAnimationHandler ().flipHorizontal ()) {
			ang = Math.PI - ang;
		}
		if (getAnimationHandler ().flipHorizontal ()) {
			endX = this.getX () + Math.cos (ang + Math.PI / 180 * 15) * 14 + 4;
			endY = this.getY () + Math.sin (ang + Math.PI / 180 * 15) * 14;
		} else {
			endX = this.getX () + Math.cos (ang - Math.PI / 180 * 15) * 14;
			endY = this.getY () + Math.sin (ang - Math.PI / 180 * 15) * 14;
		}
		double [] working = new double [] {endX,endY,ang};
		return working;
	}
	public Point getNextPos (double speed) {
		Point newPoint = new Point ();
		double [] working = this.simulateShot();
		newPoint.x = (int) (working[0] + Math.cos (working[2]) * speed); 
		newPoint.y = (int) (working[1] + Math.sin (working[2]) * speed);
		return newPoint;
	}
}