package gameObjects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		System.out.println ("ANIME IS BEING TESTED");
		if (this.mouseButtonDown(0)) {
			mx = getCursorX ();
			my = getCursorY ();
		}
		Graphics2D g = (Graphics2D)GameAPI.getWindow ().getBufferGraphics ();
		g.setColor(new Color(0x0000FF));
		g.setStroke (new BasicStroke (2));
		int toX1 = getCursorX ();
		int toY1 = getCursorY ();
		int xprev = 0;
		int yprev = 0;
		int startx = 0;
		int starty = 0;
		int xcur = 0;
		int ycur = 0;
		for (double theta = 0; theta <= Math.PI * 2; theta += Math.PI / 180) {
			xcur = (int)(mx + Math.cos (theta) * 64);
			ycur = (int)(my + Math.sin (theta) * 64);
			VectorCollisionInfo cia = Room.getCollisionInfo (mx, my, xcur, ycur);
			if (cia != null) {
				xcur = (int)cia.getCollisionX ();
				ycur = (int)cia.getCollisionY ();
			}
			if (theta != 0) {
				g.drawLine (xprev, yprev, xcur, ycur);
			} else {
				startx = xcur;
				starty = ycur;
			}
			xprev = xcur;
			yprev = ycur;
		}
		g.drawLine (xcur, ycur, startx, starty);
		//VectorCollisionInfo cia = Room.getCollisionInfo (mx, my, toX1, toY1);
		/*if (cia != null) {
			g.drawLine(mx, my, (int)cia.getCollisionX (), (int)cia.getCollisionY ());
		} else {
			g.drawLine (mx, my, toX1, toY1);
		}
		//j.damage(1);*/
	}
	
	
}
