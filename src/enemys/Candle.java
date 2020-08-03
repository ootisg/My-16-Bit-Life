package enemys;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameObjects.DamageText;
import gameObjects.DarkOverlay;
import gameObjects.Extinguisable;
import gameObjects.LightSource;
import main.GameCode;
import main.ObjectHandler;
import players.Jeffrey;
import projectiles.DirectionBullet;
import projectiles.PokaDot;
import resources.Sprite;


public class Candle extends Enemy implements LightSource, Extinguisable {
	PokaDot fireball;
	int timer = 0;
	int flameHealth = 30;
	boolean extinguesed = false;
	public Candle () {
	this.setHealth(90);
	this.setHitboxAttributes(0, 0, 16, 16);
	this.setSprite(new Sprite ("resources/sprites/candle.png"));	
	this.adjustHitboxBorders();
	DarkOverlay.addLightSources(this);
	}
	@Override
	public void enemyFrame () {
	timer = timer + 1;
	if (timer == 30 && !extinguesed) {
		DirectionBullet bullet = new DirectionBullet (this.getX(), this.getY());
		fireball = new PokaDot(bullet.findDirection(Jeffrey.getActiveJeffrey()));
		fireball.declare(this.getX(),this.getY());
		timer = 0;
		}
	if (extinguesed) {
		this.setSprite(new Sprite ("resources/sprites/extinutesedCandle.png"));
	}
	
	}
	public void damage (int amount) {
		if (Jeffrey.getActiveJeffrey().checkIfPowerful()) {
			amount = (int) ((amount * 1.2) - defence);
			if(amount <= 0){
				amount = 1;
			}
			text = new DamageText (amount * 1.2, this.getX(), this.getY(), false );	
		} else {
			amount = amount - defence;
			if(amount <= 0){
				amount = 1;
			}
		text = new DamageText (amount, this.getX(), this.getY(), false);
		}
		text.declare(this.getX(), this.getY());
		this.health = health - amount;
	
		if (this.isColliding("Paintball") || this.isColliding("PaintballWeak") || this.isColliding("SlimeSword")) {
			this.flameHealth = this.flameHealth - amount;
		}
		if (this.flameHealth <= 0) {
			extinguesed = true;
			}
	}
	@Override
	public void forget () {
		DarkOverlay.removeLightSources(this);
		super.forget();
	}
	@Override
	public Rectangle getVeiwport() {
		return new Rectangle ((int)this.getX() - 10, (int)this.getY() -10, 36,  36);
	}
	@Override
	public boolean getLit() {
		return true;
	}
	@Override
	public BufferedImage getOverlay() {
		return new Sprite ("resources/sprites/overlays/candleOverlay.png").getFrame(0);
	}
	@Override
	public void extinguse() {
		this.extinguesed = true;	
	}
}
