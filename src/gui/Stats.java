package gui;

import java.awt.Color;
import java.awt.Graphics;

import main.GameCode;
import main.GameObject;
import main.RenderLoop;
import players.Jeffrey;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class Stats extends GameObject {
	//public int totalHearts = 10;
	Tbox charictarName;
	Tbox weaponName;
	Tbox ammoAmount;
	Sprite weaponSprite;
	public Stats () {
		charictarName = new Tbox (0,0,20,1,"JEFFREY", false);
		charictarName.declare(460,-6);
		charictarName.setScrollRate(0);
		weaponName = new Tbox (0,0,24,1, "REDBLACK PAINTBALL GUN", false);
		weaponName.declare(31, -6);
		weaponSprite = new Sprite("resources/sprites/blank.png");
		weaponName.setScrollRate(0);
		ammoAmount = new Tbox (0,0,24,1, "0", false);
		ammoAmount.declare(252,-6);
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
	
		if (GameCode.testJeffrey.witchCharictar == 0) {
		charictarName.setContent("JEFFREY");
		}
		if (GameCode.testJeffrey.witchCharictar == 1) {
		charictarName.setContent("SAM");
		}
		if (GameCode.testJeffrey.witchCharictar == 2) {
		charictarName.setContent("RYAN");
		}
		weaponName.setContent(GameCode.testJeffrey.getWeapon().checkName());
		weaponSprite = GameCode.testJeffrey.getWeapon().getUnrotatedSprite();
		ammoAmount.setContent(Integer.toString(GameCode.testJeffrey.getInventory().checkAmmoAmountOfWeapon(GameCode.testJeffrey.getWeapon())));
	}
	@Override 
	public void draw () {
		Graphics buffer = RenderLoop.window.getBufferGraphics ();
		buffer.setColor (new Color(0xFF0000));
		buffer.fillRect (300, 0, (int)(160 * (GameCode.testJeffrey.getHealth() / GameCode.testJeffrey.maxHealth)), 16);
		buffer.setColor (new Color(0x000000));
		buffer.drawRect (300, 0, 160, 16);
		buffer.setColor(new Color (0xFFFF00));
		buffer.fillRect(0, 0, GameCode.testJeffrey.switchTimer, 16);
		buffer.setColor(new Color (0x000000));
		buffer.drawRect(0, 0, 30, 16);
		weaponSprite.draw(260, 0);
	}
}
