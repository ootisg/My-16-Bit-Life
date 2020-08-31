package gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import main.GameObject;
import main.RenderLoop;
import map.Room;
import players.Jeffrey;

public class UseableFan extends GameObject {
	public boolean used = false;
	public boolean ready = false;
	public UseableFan () {
		
	}
	public void frameEvent () {
		if (Jeffrey.getActiveJeffrey().vy != 0) {
			if (!keyDown (32)) {
				ready = true;
			}
			if (keyDown (32) && !used && ready){
				used = true;
				if (Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
					Jeffrey.getActiveJeffrey().vx = -6;
				} else {
					Jeffrey.getActiveJeffrey().vx = 6;
				}
			}
		} else {
			used = false;
			ready = false;
		}
	}
}
