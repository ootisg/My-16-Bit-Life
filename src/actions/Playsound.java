package actions;

import main.GameCode;

/**
 * plays a sound in its entirity then returns true
 * @author Jeffrey Marsh
 *
 */
public class Playsound {
	private boolean wasOpen = false;
	
	public boolean playSound(float volume, String soundPath) {
		if (GameCode.player.getClip(soundPath) == null && wasOpen) {
			return true;
		} else {
			if (wasOpen) {
				return false;
			} else {
				wasOpen = true;
				GameCode.player.playSoundEffect(volume, soundPath);
				return false;
			}
		}
	}
}
