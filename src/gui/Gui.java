package gui;


import main.GameObject;
import main.ObjectHandler;
import map.Room;


public class Gui extends GameObject {
	Stats stats;
	Menu menu;
	public Gui () {
		stats = new Stats ();
		stats.declare(0, 0);
		this.declare (0, 0);
	}
	@Override
	public void frameEvent () {
		stats.frameEvent();
		if (keyPressed ('E')) {
			menu = new Menu ();
			menu.declare(0 + Room.getViewX(), 0);
			ObjectHandler.pause (true);
		}
	}
	@Override
	public void pausedEvent () {
	stats.frameEvent();	
	}
	@Override
	public void forget () {
		
	}
}