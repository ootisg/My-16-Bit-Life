package gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import main.GameObject;
import main.RenderLoop;
import map.Room;
import players.Jeffrey;

public class UseableCarpet extends GameObject {
	boolean check = false;
	boolean ready = false;
	boolean overheated = false;
	boolean velocitySet = true;
	int fuel = 100;
	Graphics g = RenderLoop.window.getBufferGraphics();
	public UseableCarpet () {
		
	}
	public void frameEvent () {
		if (Jeffrey.getActiveJeffrey().getVy() != 0) {
			check = true;
		} else {
			overheated = false;
			check = false;
			ready = false;
		}
		if ((!keyDown(32) && check) ) {
			ready = true;
		} 
		if (ready && keyDown (32) && fuel > 0 && !overheated) {
			Jeffrey.getActiveJeffrey().stopFall(true);
			fuel = fuel - 1;
			velocitySet = false;
		} else {
			if (!velocitySet) {
				velocitySet = true;
				Jeffrey.getActiveJeffrey().setVy(0);
			}
			Jeffrey.getActiveJeffrey().stopFall(false);
			if (fuel < 100) {
				fuel = fuel + 1;
			}
		}
		if (fuel <= 0) {
			overheated = true;
		}
	}
	@Override
	public void draw () {
		g.setColor(new Color (0x000000));
		g.drawRect(0, 32, 16, 100);
		g.setColor(new Color (0xFF0000));
		g.fillRect(0, 32 + (100 - fuel), 16, fuel);
	}
}
