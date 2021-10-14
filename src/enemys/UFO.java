package enemys;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import main.ObjectHandler;
import main.RenderLoop;
import map.Room;
import players.Player;
import resources.LoopableSprite;
import resources.Sprite;

public class UFO extends Enemy {
	boolean chaseing = false;
	
	boolean direction = true;
	
	boolean laserOn;
	
	int timer;
	
	int yTo;
	
	LoopableSprite laser;
	
	public static final Color LAZER_COLOR = new Color (0xE00000);
	public static final int LAZER_OFFSET = 10;
	public UFO () {
		this.setSprite(new Sprite ("resources/sprites/UFO.png"));
		this.setHitboxAttributes(0, 0, 32, 25);
		this.setHealth(120);
		this.defence = 20;
		laser = new LoopableSprite (new Sprite ("resources/sprites/config/ufo_laser_a.txt"),0,16,0,0);
		laser.addSprite(new Sprite ("resources/sprites/config/ufo_laser_b.txt"));
		laser.addEndSprite(new Sprite ("resources/sprites/config/ufo_laser_c.txt"));
		
	}
	@Override 
	public String checkName () {
		return "MARSH-AN UFO";
	}
	@Override
	public String checkEntry () {
		return "YIKES DUDE";
	}
	@Override 
	public void enemyFrame () {
		timer = timer + 1;
		if (this.isNearPlayerX(0, 400, 0, 400)) {
			if (this.getX() + LAZER_OFFSET > Player.getActivePlayer().getX()) {
				this.goX(this.getX() - 3);
				if (this.getX() + LAZER_OFFSET < Player.getActivePlayer().getX()) {		
					this.goX(Player.getActivePlayer().getX() - LAZER_OFFSET);	
				}
			} else {
				this.goX(this.getX() + 3);
				if (this.getX() + LAZER_OFFSET > Player.getActivePlayer().getX()) {		
					this.goX(Player.getActivePlayer().getX() - LAZER_OFFSET);	
				}
			
			}
		}
		if (timer > 50) {
			laserOn = true;
			if (timer > 55) {
				timer = 0;
				laserOn = false;
			}
		}
		if (laserOn) {
			yTo = 0;
			while (true) {
				this.setHitboxAttributes(15, 0, 1, 25 + yTo);
				yTo = yTo + 1;
				if (Room.isColliding(this) || yTo > Room.getViewY() + RenderLoop.window.getResolution()[1] || this.isColliding(Player.getActivePlayer())) {
					if (this.isColliding(Player.getActivePlayer())) {
						Player.getActivePlayer().damage(12);
					}
					break;
				}
			}
			this.setHitboxAttributes(0, 0, 32, 25);
		}
	}
	@Override 
	public void draw () {
		super.draw();
		if (laserOn) {
			laser.setDestanation((int)this.getX() - Room.getViewX() +8, (int)(yTo + 18 + this.getY()));
			laser.draw((int)this.getX() - Room.getViewX() + 8, (int)this.getY() - Room.getViewY() + 14);
		}
	}
}
