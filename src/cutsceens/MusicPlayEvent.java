package cutsceens;

import json.JSONObject;
import main.GameCode;

public class MusicPlayEvent extends CutsceneEvent {

	String path;

	
	public MusicPlayEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		path = info.getString("path");
		
	}
	
	@Override
	public boolean runEvent () {
		GameCode.player.play(path, 6F);
		return false;
	}

}
