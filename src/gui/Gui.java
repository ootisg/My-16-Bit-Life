package gui;


import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Player;


public class Gui extends GameObject {
	Stats stats;
	boolean prepared = true;
	public Menu menu;
	public Gui () {
		stats = new Stats ();
		stats.declare(0, 0);
		this.declare (0, 0);
	}
	@Override
	public void frameEvent () {
		if (keyDown('E')&& prepared  && Player.getActivePlayer() != null) {
			menu = new Menu ();
			menu.declare(0 + Room.getViewX(), 0 + Room.getViewY());
			ObjectHandler.pause (true);
		}
		if (!keyDown ('E')) {
			prepared = true;
		}
	}
	@Override
	public void pausedEvent () {
	stats.frameEvent();	
	prepared = false;
	}
	@Override
	public void forget () {
		
	}
	public static Gui getGui() {
		return (Gui)ObjectHandler.getObjectsByName("Gui").get(0);
	}
}