package gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import main.GameObject;
import main.RenderLoop;
import map.Room;
import players.Jeffrey;

public class UseableJetpack extends GameObject {
	boolean check = false;
	boolean ready = false;
	boolean overheated = false;
	int fuel = 50;
	Graphics g = RenderLoop.window.getBufferGraphics();
	public UseableJetpack () {
		
	}
	public void frameEvent () {
		if (Jeffrey.getActiveJeffrey().vy != 0) {
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
			if (Jeffrey.getActiveJeffrey().vy > -5) {
				Jeffrey.getActiveJeffrey().vy = Jeffrey.getActiveJeffrey().vy - 1.1;
			}
			fuel = fuel - 1;
		} else {
			if (fuel < 50) {
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
		g.drawRect(0, 32, 16, 50);
		g.setColor(new Color (0xED9619));
		g.fillRect(0, 32 + (50 - fuel), 16, fuel);
	}
}
