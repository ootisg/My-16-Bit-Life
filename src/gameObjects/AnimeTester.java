package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GameAPI;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import map.VectorCollisionInfo;
import players.Jeffrey;
import resources.Sprite;

public class AnimeTester extends GameObject {

//	static BufferedImage miku = Sprite.getImage("resources/sprites/lulwut.png");
	
	static int mx = -1;
	static int my = -1;
	
	public AnimeTester () {
		//this.setSprite(new Sprite (miku));
	}
	//291 143
	//338 132
	@Override 
	public void draw () {
		//super.draw();
		if (this.mouseButtonPressed(0)) {
			mx = getCursorX ();
			my = getCursorY ();
		}
		Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
		Graphics g = GameAPI.getWindow ().getBufferGraphics ();
		g.setColor(new Color(0xFF0000));
		int toX1 = getCursorX ();
		int toY1 = getCursorY ();
		//VectorCollisionInfo cia = Room.getCollisionInfo (mx, my, toX1, toY1);
		/*if (cia != null) {
			g.drawLine(mx, my, (int)cia.getCollisionX (), (int)cia.getCollisionY ());
		} else {
			g.drawLine (mx, my, toX1, toY1);
		}
		//j.damage(1);*/
	}
	
	
}
