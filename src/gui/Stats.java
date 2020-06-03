package gui;

import java.awt.Color;
import java.awt.Graphics;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import main.RenderLoop;
import players.Jeffrey;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class Stats extends GameObject {
	//public int totalHearts = 10;
	Tbox charictarName;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	Tbox weaponName;
	Tbox ammoAmount;
	Sprite weaponSprite;
	public static final Sprite HEALTH_BORDER_SPRITE = new Sprite ("resources/sprites/Health_Border.png");
	public static final Sprite HEALTH_SPRITE = new Sprite ("resources/sprites/Health_Bar.png");
	public static final Sprite JEFFREY_BAR = new Sprite ("resources/sprites/Character_Border_Jeffrey.png");
	public static final Sprite SAM_BAR = new Sprite ("resources/sprites/Character_Border_Sam.png");
	public static final Sprite RYAN_BAR = new Sprite ("resources/sprites/Character_Border_Ryan.png");
	public static final Sprite EMPTY_BAR = new Sprite ("resources/sprites/Character_Border_Empty.png");
	public static final Sprite CHARICTAR_SPRITE = new Sprite ("resources/sprites/charictar_bar.png");
	public int nextCharacter;
	int currentBar = 0;
	
	public Stats () {
		charictarName = new Tbox (0,0,20,1,"JEFFREY", false);
		charictarName.declare(75,-6);
		charictarName.setScrollRate(0);
		weaponName = new Tbox (460,0,24,1, "REDBLACK PAINTBALL GUN", false);
		weaponName.declare(400, -6);
		weaponSprite = new Sprite("resources/sprites/blank.png");
		weaponName.setScrollRate(0);
		ammoAmount = new Tbox (0,0,24,1, "0", false);
		ammoAmount.declare(340,-6);
		ammoAmount.setScrollRate(0);
		charictarName.keepOpen(true);
		weaponName.keepOpen(true);
		ammoAmount.keepOpen(true);
	}
	@Override
	public void frameEvent () {
		/*int numHearts = (int) Math.ceil((player.getHealth () / player.maxHealth) * totalHearts);
		for (int i = 0; i < numHearts - 1; i ++) {
			sprites.hearts.setFrame (0);
			sprites.hearts.draw (i * 16, 0);
		}
		int finalHeart;
		if (player.getHealth () - (player.maxHealth / totalHearts) * (numHearts - 1) >= .01) { 
			finalHeart = (int)((player.getHealth () - (player.maxHealth / totalHearts) * (numHearts - 1) - .01) / (player.maxHealth / (totalHearts * 4)));
		} else {
			finalHeart = -1;
		}
		if (finalHeart > 3) {
			finalHeart = 3;
		}
		if (finalHeart >= 0) {
			sprites.hearts.setFrame (3 - finalHeart);
			sprites.hearts.draw ((numHearts - 1) * 16, 0);
		}*/
	
		if (j.witchCharictar == 0) {
		charictarName.setContent("JEFFREY");
		}
		if (j.witchCharictar == 1) {
		charictarName.setContent("SAM");
		}
		if (j.witchCharictar == 2) {
		charictarName.setContent("RYAN");
		}
		weaponName.setContent(j.getWeapon().checkName());
		weaponSprite = j.getWeapon().getUnrotatedSprite();
		ammoAmount.setContent(Integer.toString(j.getInventory().checkAmmoAmountOfWeapon(j.getWeapon())));
	}
	@Override 
	public void draw () {
		Graphics buffer = RenderLoop.window.getBufferGraphics ();
		buffer.setColor (new Color(0xFF0000));
		HEALTH_BORDER_SPRITE.draw(160,0);
		
		currentBar = j.nextCharacter;
		switch (currentBar) {
			case 0:
				if (j.jeffreyHealth > 0) {
					JEFFREY_BAR.draw(0, 0);	
				}
				if (j.jeffreyHealth <= 0) {
					j.nextCharacter++;
				}
				if (j.jeffreyHealth <= 0 && (j.samHealth <= 0 || j.ryanHealth <= 0)) {
					EMPTY_BAR.draw(0, 0);
				}
				break;
			case 1:
				if (j.samHealth > 0) {
					SAM_BAR.draw(0, 0);	
				}
				if (j.samHealth <= 0) {
					j.nextCharacter++;
				}
				if (j.samHealth <= 0 && (j.jeffreyHealth <= 0 || j.ryanHealth <= 0)) {
					EMPTY_BAR.draw(0, 0);
				}
				break;
			case 2:
				if (j.ryanHealth > 0) {
					RYAN_BAR.draw(0, 0);	
				}
				if (j.ryanHealth <= 0) {
					j.nextCharacter++;
				}
				if (j.ryanHealth <= 0 && (j.jeffreyHealth <= 0 || j.samHealth <= 0)) {
					EMPTY_BAR.draw(0, 0);
				}
				break;
			default:
				if (j.ryanHealth > 0) {
					RYAN_BAR.draw(0, 0);	
				}
				if (j.ryanHealth <= 0 && j.jeffreyHealth > 0) {
					JEFFREY_BAR.draw(0, 0);
				}
				if (j.ryanHealth <= 0 && j.jeffreyHealth <= 0) {
					SAM_BAR.draw(0, 0);
				}
				break;
		}
		//Sam's doesn't work properly and I'm lazy rn so this fixes his character switch bar when J + R are dead.
		if (j.witchCharictar == 1 && j.jeffreyHealth <= 0 && j.ryanHealth <= 0) {
			EMPTY_BAR.draw(0,0);
		}
		
		/* if (j.witchCharictar == 0) {
			if (j.samHealth > 0) {
			SAM_BAR.draw(0, 0);
			} else if(j.ryanHealth > 0) {
				RYAN_BAR.draw(0,0);
			} else {
				EMPTY_BAR.draw(0, 0);
			}
			}
			if (j.witchCharictar == 1) {
				if (j.ryanHealth > 0) {
					RYAN_BAR.draw(0, 0);
					} else if(j.jeffreyHealth > 0) {
						JEFFREY_BAR.draw(0,0);
					} else {
						EMPTY_BAR.draw(0, 0);
					}
			}
			if (j.witchCharictar == 2) {
				if (j.jeffreyHealth > 0) {
					JEFFREY_BAR.draw(0, 0);
					} else if(j.samHealth > 0) {
						SAM_BAR.draw(0,0);
					} else {
						EMPTY_BAR.draw(0, 0);
					}
			} */
		if (j.witchCharictar ==0 ) {
			if (j.getHealth() > 0) {
			HEALTH_SPRITE.draw(160,0,0,(int)(Math.ceil((170 * (j.getHealth() / j.maxJeffreyHealth)))), 24);
			}
		}
		if (j.witchCharictar ==1 ) {
			if (j.getHealth() > 0) {				
			HEALTH_SPRITE.draw(160,0,0,(int)(Math.ceil((170 * (j.getHealth() / j.maxSamHealth)))), 24);
			}
			}
		if (j.witchCharictar ==2 ) {
			if (j.getHealth() > 0) {
			HEALTH_SPRITE.draw(160,0,0,(int)(Math.ceil((170 * (j.getHealth() / j.maxRyanHealth)))), 24);
			}
			}
		buffer.setColor (new Color(0x000000));
		buffer.setColor(new Color (0xFFFF00));
		buffer.setColor(new Color (0x000000));
		if (j.switchTimer != 0) {
		CHARICTAR_SPRITE.draw(0, 0, 0, (int) (Math.ceil(45*j.switchTimer/30.0)), 24);
		}
		weaponSprite.draw(144, 0);
	}
}
