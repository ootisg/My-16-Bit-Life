package gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import main.GameObject;
import main.RenderLoop;
import map.Room;
import players.Player;

public class UseableFan extends GameObject {
	public boolean used = false;
	public boolean ready = false;
	public UseableFan () {
		
	}
	public void frameEvent () {
		if (Player.getActivePlayer().getVy() != 0) {
			if (!keyDown (32)) {
				ready = true;
			}
			if (keyDown (32) && !used && ready){
				used = true;
				if (Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
					Player.getActivePlayer().vx = -6;
				} else {
					Player.getActivePlayer().vx = 6;
				}
			}
		} else {
			used = false;
			ready = false;
		}
	}
}
