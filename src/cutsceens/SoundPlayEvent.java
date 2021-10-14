package cutsceens;

import json.JSONObject;
import main.GameCode;

public class SoundPlayEvent extends CutsceneEvent {

	String path;
	private boolean wasOpen = false;
	
	public SoundPlayEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		path = info.getString("path");
		
	}
	
	@Override
	public boolean runEvent () {
		if (GameCode.player.getClip(path) == null && wasOpen) {
			return true;
		} else {
			if (wasOpen) {
				return false;
			} else {
				wasOpen = true;
				GameCode.player.playSoundEffect(6F, path);
				return false;
			}
		}
	}

}
