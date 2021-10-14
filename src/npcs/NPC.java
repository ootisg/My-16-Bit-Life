package npcs;

import cutsceens.Cutsceen;
import main.GameObject;
import main.ObjectHandler;
import players.Player;
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
			if (this.getVariantAttribute("Sprite") != null && !this.getVariantAttribute("Sprite").equals("nv")) {
				this.setSprite(new Sprite (this.getVariantAttribute("Sprite")));
				this.getAnimationHandler().setFrameTime(100);
			} else {
				this.setSprite(new Sprite ("resources/sprites/config/point_guy.txt"));
				this.getAnimationHandler().setFrameTime(100);
			}
			this.setHitboxAttributes(-2, 0, this.getSprite().getWidth() + 4, this.getSprite().getHeight());
			inzalized = true;
		}
		try {
		if (keyDown (10) && !playing && Player.getActivePlayer().isColliding(this)) {
			Player.getInventory().addFreind(this);
			ObjectHandler.pause(true);
			playing = true;
		}
		} catch (NullPointerException e) {
			
		}
		try {
			if (keyDown (10) && !playing && JeffreyTopDown.getActiveJeffrey().isColliding(this)) {
				Player.getInventory().addFreind(this);
				ObjectHandler.pause(true);
				playing = true;
			}
			} catch (NullPointerException e) {
				
			}
	}
	@Override
	public Object clone () {
		NPC newPC = new NPC ();
		newPC.setX(this.getX());
		newPC.setY(this.getY());
		newPC.setVariantAttribute("Name", this.getVariantAttribute("Name"));
		newPC.setVariantAttribute("Entry", this.getVariantAttribute("Entry"));
		newPC.setVariantAttribute("attachedScene", this.getVariantAttribute("attachedScene"));
		newPC.setVariantAttribute("Sprite",this.getVariantAttribute("Sprite"));
		//might need to do something to check if this is a subclass but I don't really feel like it
		
		return newPC;
	}
	@Override 
	public void pausedEvent () {
		if (playing) {
			if (!attachedCutscene.play()) {
				ObjectHandler.pause(false);
				playing = false;
			}
		}
	}
}
