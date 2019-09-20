package gui;

import java.awt.Color;
import java.awt.Graphics;

import main.GameObject;
import players.Jeffrey;
import resources.Sprite;

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
			menu = new Menu();
			menu.mainMenu = new ListTbox (0, 0, new String[]{"WEAPONS", "POPTART HOLDER", "EXIT"});
			// TODO mainLoop.pause replacement
			//MainLoop.pause ();
		}
	}
	//@Override
	//      TODO mainLoop.pause replacement
	//public void pausedEvent () {
	//stats.frameEvent();	
	///}
	@Override
	public void forget () {
		
	}
}