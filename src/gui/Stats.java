package gui;

import java.awt.Color;
import java.awt.Graphics;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import main.RenderLoop;
import players.Player;
import resources.AfterRenderDrawer;
import resources.Sprite;
import triggers.Checkpoint;

public class Stats extends GameObject {
	//public int totalHearts = 10;
	Tbox charictarName;
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
	
	public Stats () {
		charictarName = new Tbox (0,0,20,1,"JEFFREY", false);
		charictarName.declare(75,-6);
		charictarName.setScrollRate(0);
		charictarName.setScaleing(false);
		charictarName.setPersistence(true);
		
		
		weaponName = new Tbox (460,0,24,1, "REDBLACK PAINTBALL GUN", false);
		weaponName.declare(400, -6);
		weaponSprite = new Sprite("resources/sprites/blank.png");
		weaponName.setScrollRate(0);
		weaponName.setScaleing(false);
		weaponName.setPersistence(true);
		
		ammoAmount = new Tbox (0,0,24,1, "0", false);
		ammoAmount.declare(340,-6);
		ammoAmount.setScrollRate(0);
		ammoAmount.setScaleing(false);
		ammoAmount.setPersistence(true);
		
		this.setRenderPriority(420);
		charictarName.keepOpen(true);
		weaponName.keepOpen(true);
		ammoAmount.keepOpen(true);
		ammoAmount.setPlace();
		charictarName.setPlace();
		weaponName.setPlace();
		CHARICTAR_SPRITE.setScale(false);
		EMPTY_BAR.setScale(false);
		RYAN_BAR.setScale(false);
		SAM_BAR.setScale(false);
		JEFFREY_BAR.setScale(false);
		HEALTH_SPRITE.setScale(false);
		HEALTH_BORDER_SPRITE.setScale(false);
		this.setPersistence(true);
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
		} */
		try {
			charictarName.setContent(Player.getActivePlayer().getClass().getSimpleName().toUpperCase());
			weaponName.setContent(Player.getActivePlayer().getWeapon().checkName());
			weaponSprite = Player.getActivePlayer().getWeapon().getUnrotatedSprite();
			weaponSprite.setScale(false);
			ammoAmount.setContent(Double.toString(Player.getActivePlayer().getWeapon().getAmmoCount()));
		} catch (NullPointerException e ) {
		}
	}
	@Override 
	public void draw () {
		try {
		HEALTH_BORDER_SPRITE.draw(160,0);
		int currentBar;
		if (GameCode.getPartyManager().checkSwitch()) {
			currentBar = GameCode.getPartyManager().getNextPlayer().getPlayerNum();
		} else {
			currentBar = 69;
		}
		switch (currentBar) {
			case 0:
				JEFFREY_BAR.draw(0, 0);	
				break;
			case 1:
				SAM_BAR.draw(0, 0);	
				break;
			case 2:
				RYAN_BAR.draw(0, 0);
				break;
			default:
				EMPTY_BAR.draw(0, 0);
				break;
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
		if (Player.getActivePlayer().getHealth() > 0) {
			HEALTH_SPRITE.draw(160,0,0,(int)(Math.ceil((170 * (Player.getActivePlayer().getHealth() / Player.getActivePlayer().maxHealth)))), 24);
		}
		
		if (GameCode.getPartyManager().getSwitchTimer() != 0) {
			CHARICTAR_SPRITE.draw(0, 0, 0, (int) (Math.ceil(45*GameCode.getPartyManager().getSwitchTimer()/30.0)), 24);
		}
		weaponSprite.draw(144, 0);
		} catch (NullPointerException e) {
			
		}
	}
}
