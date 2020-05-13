package cutsceens;

import java.util.ArrayList;

import actions.MakeText;
import json.JSONException;
import json.JSONObject;
import main.GameCode;
import main.GameObject;
import resources.Sprite;

public class Cutsceen extends GameObject {
	ArrayList <GameObject> objectsToHandle = new ArrayList <GameObject> ();
	ArrayList <Cutsceen> cutsceensToHandle = new ArrayList <Cutsceen> ();
	ArrayList <Sprite> spritesToHandle = new ArrayList <Sprite> ();
	ArrayList <CustomCutsceenEvent> customEvents = new ArrayList <CustomCutsceenEvent> ();
	ArrayList <String> comands = new ArrayList <String> ();
	public Cutsceen () {
	}
	/**
	 * moves an object to a destanation without just teleporting them there
	 * @param objectToMove the object to move
	 * @param time the time it takes to move them there (in frames)
	 * @param desX the x coordinate of the destanation
	 * @param desY the y coordinate of the destaination
	 */
	public void moveSlowly(GameObject objectToMove, int time, int desX, int desY) {
		objectsToHandle.add(objectToMove);
		comands.add("moveSlow");
		comands.add(Integer.toString(time));
		comands.add(Integer.toString(desX));
		comands.add(Integer.toString(desY));
		}
	/**
	 * plays a sound effect
	 * @param soundPath the filepath to the sound effect
	 */
	public void playSound (String soundPath) {
		comands.add("Sound");
		comands.add(soundPath);
	}
	/**
	 * plays music
	 * @param soundPath the filepath of the song
	 */
	public void playMusic (String soundPath) {
		comands.add("Music");
		comands.add(soundPath);
	}
	/**
	 * writes text to the screen by using a textbox
	 * @param text the message to write to the scren
	 */
	public void makeTextBox (String text) {
		comands.add("Text");
		comands.add(text);
	}
	/**
	 * plays a diffrent cutsceen
	 * @param cutsceen
	 */
	public void playSceen (Cutsceen cutsceen) {
		comands.add("play");
		cutsceensToHandle.add(cutsceen);
	}
	/**
	 * plays through an animation once before switching back to the old sprite
	 * @param animaiton the animation to play
	 * @param onWhat the gameObject that plays that animation
	 */
	public void playAnimation (Sprite animaiton, GameObject onWhat) {
		comands.add("animation");
		spritesToHandle.add(onWhat.getSprite());
		spritesToHandle.add(animaiton);
		objectsToHandle.add(onWhat);
	}
	/**
	 * changes the sprite of an object to a new sprite
	 * @param newSprite the new sprite of the object
	 * @param onWhat the object to change the sprite of
	 */
	public void changeSprite (Sprite newSprite, GameObject onWhat) {
		comands.add("sprite");
		spritesToHandle.add(newSprite);
		objectsToHandle.add(onWhat);
	}
	public void customCode (CustomCutsceenEvent sceen, JSONObject object) {
		comands.add("Custom");
		comands.add(object.toString());
		customEvents.add(sceen);
	}
	/**
	 * returns true if the cutsceen is still playing
	 * and plays the cutsceen
	 * @return
	 */
	public boolean play () {
		if (!comands.isEmpty()) {
			switch (comands.get(0)) {
			case "moveSlow":
				this.runMoveSlowCode();
				break;
			case "Sound":
				this.runSoundCode();
				break;
			case "Music":
				this.runMusicCode();
				break;
			case "Text":
				this.runTextCode();
				break;
			case "play":
				this.runCutsceenCode();
				break;
			case "animation":
				this.runAniamtionCode();
				break;
			case "sprite":
				this.runSpriteCode();
				break;
			case "Custom":
				this.runCustomCode();
			}
			return true;
		} else {
			return false;
		}
	}
	public void runMoveSlowCode() {
		
	}
	public void runSoundCode() {
		if (!GameCode.player.clip2.isOpen()) {
		GameCode.player.playSoundEffect(6F, comands.get(1));
		} else {
			
		}
		
	}
	public void runMusicCode() {
		GameCode.player.play(comands.get(1), 6F);
		comands.remove(0);
		comands.remove(0);
	}
	public void runTextCode() {
		if (MakeText.makeText(comands.get(1))) {
			comands.remove(0);
			comands.remove(0);
		}
	}
	public void runCutsceenCode() {
		if (!cutsceensToHandle.get(0).play()) {
			comands.remove(0);
			cutsceensToHandle.remove(0);
		}
	}
	public void runAniamtionCode() {
		if (!objectsToHandle.get(0).getSprite().equals(spritesToHandle.get(1))) {
			objectsToHandle.get(0).setSprite(spritesToHandle.get(1));
		} else {
			if (objectsToHandle.get(0).getAnimationHandler().getFrame() == objectsToHandle.get(0).getAnimationHandler().getImage().getFrameCount()) {
				objectsToHandle.get(0).setSprite(spritesToHandle.get(0));
				spritesToHandle.remove(0);
				spritesToHandle.remove(0);
				objectsToHandle.remove(0);
				comands.remove(0);
			}
		}
	}
	public void runSpriteCode() {
		objectsToHandle.get(0).setSprite(spritesToHandle.get(0));
		objectsToHandle.remove(0);
		spritesToHandle.remove(0);
		comands.remove(0);
	}
	public void runCustomCode() {
		try {
			this.customEvents.get(0).runEvent(this,new JSONObject (this.comands.get(1)));
		} catch (JSONException e) {
			this.customEvents.remove(0);
			this.comands.remove(0);
			this.comands.remove(0);
		}
		
	}
	public void addObjectToScene (GameObject obj) {
		objectsToHandle.add (obj);
	}
}
