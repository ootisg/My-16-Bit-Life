package npcs;

import cutsceens.Cutsceen;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import players.JeffreyTopDown;
import resources.Sprite;

public class NPC extends GameObject {
	Cutsceen attachedCutscene;
	boolean playing = false;
	boolean inzalized = false;
	public NPC () {
	}
	public String checkName (){
		try {
		return this.getVariantAttribute("Name");
		} catch (NullPointerException e) {
		return "unamed NPC";
		}
	}
	public String checkEntry () {
		try {
		return this.getVariantAttribute("Entry");
		} catch (NullPointerException e) {
			return "PLEASE someone tell me I am real and not just a default entery to handle a case that will never happen I AM REAL RIGHT";
		}
	}
	@Override 
	public void frameEvent () {
		if (!inzalized) {
			if (this.getVariantAttribute("attachedScene") != null && !this.getVariantAttribute("attachedScene").equals("nv")) {
				attachedCutscene = new Cutsceen (this.getVariantAttribute("attachedScene"));
			} else {
				attachedCutscene = new Cutsceen ("resources/cutsceenConfig/misconfiguredSceen.txt");
			}
			if (this.getVariantAttribute("sprite") != null && !this.getVariantAttribute("sprite").equals("nv")) {
				this.setSprite(new Sprite (this.getVariantAttribute("sprite")));
				this.getAnimationHandler().setFrameTime(100);
			} else {
				this.setSprite(new Sprite ("resources/sprites/config/point_guy.txt"));
				this.getAnimationHandler().setFrameTime(100);
			}
			this.setHitboxAttributes(0, 0, this.getSprite().getWidth(), this.getSprite().getHeight());
			inzalized = true;
		}
		try {
		if (keyDown (10) && !playing && Jeffrey.getActiveJeffrey().isColliding(this)) {
			Jeffrey.getInventory().addFreind(this);
			ObjectHandler.pause(true);
			playing = true;
		}
		} catch (NullPointerException e) {
			
		}
		try {
			if (keyDown (10) && !playing && JeffreyTopDown.getActiveJeffrey().isColliding(this)) {
				Jeffrey.getInventory().addFreind(this);
				ObjectHandler.pause(true);
				playing = true;
			}
			} catch (NullPointerException e) {
				
			}
	}
	@Override 
	public void pausedEvent () {
		System.out.println("debug");
		if (playing) {
			if (!attachedCutscene.play()) {
				ObjectHandler.pause(false);
				playing = false;
			}
		}
	}
}
