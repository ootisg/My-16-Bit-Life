package gameObjects;

import main.GameObject;
import map.Room;
import players.Player;
import players.JeffreyTopDown;
import resources.Sprite;

public class LevelEntrence extends GameObject {
	String levelPath;
	String levelName;
	boolean displayName = true;
	boolean inzialized = false;
	int dificulty;
	int unlock;
	public LevelEntrence () {
	}
	@Override
	public void frameEvent () {
		if (!inzialized) {
			levelName = this.getVariantAttribute("name");
			levelPath = this.getVariantAttribute("path");
			if (this.getVariantAttribute("dificulty") != null) {
				if (!this.getVariantAttribute("dificulty").equals("nv")) {
					dificulty = Integer.parseInt(this.getVariantAttribute("dificulty"));
				}
			}
			
			if (levelName == null) {
				displayName = false;
			} else if (levelName.equals("nv")) {
				displayName = false;
			}
			if (levelPath == null) {
				System.out.println("Bruh you need a path");
			} else if (levelPath.equals("nv")) {
				System.out.println("Bruh you need a path");
			}
			if (this.getVariantAttribute("sprite") == null) {
				this.setSprite(new Sprite ("resources/sprites/wall.png"));
				this.setHitboxAttributes(0, 0, 16, 16);
			} else if (this.getVariantAttribute("sprite").equals("nv")){
				this.setSprite(new Sprite ("resources/sprites/wall.png"));
				this.setHitboxAttributes(0, 0, 16, 16);
				}else {
					this.setSprite (new Sprite (this.getVariantAttribute("sprite")));
					this.setHitboxAttributes(0,0,this.getSprite().getFrame(0).getWidth(), this.getSprite().getFrame(0).getHeight());
			}
			inzialized = true;
		}
		try { 
			if (this.isColliding(Player.getActivePlayer()) && keyPressed (10)) {
				Room.loadRoom(levelPath);
			} 
		} catch (NullPointerException e) {
			
		}
		try { 
			if (this.isColliding(JeffreyTopDown.getActiveJeffrey()) && keyPressed (10)) {
				Room.loadRoom(levelPath);
			} 
		} catch (NullPointerException e) {
			
		}
	}
}
