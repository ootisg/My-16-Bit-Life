package cutsceens;

import java.util.ArrayList;

import main.GameCode;
import main.GameObject;
import resources.Sprite;

public class Cutsceen extends GameObject {
	ArrayList <GameObject> objectsToHandle = new ArrayList <GameObject> ();
	ArrayList <Cutsceen> cutsceensToHandle = new ArrayList <Cutsceen> ();
	ArrayList <Sprite> spritesToHandle = new ArrayList <Sprite> ();
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
	public void customCode () {
		comands.add("Custom");
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
		
	}
	public void runTextCode() {
		
	}
	public void runCutsceenCode() {
		
	}
	public void runAniamtionCode() {
		
	}
	public void runSpriteCode() {
		
	}
	public void runCustomCode() {
		
	}
}
