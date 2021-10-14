package cutsceens;

import json.JSONObject;
import main.GameCode;
import map.Room;

public class ChangeMapEvent extends CutsceneEvent {

	String path;

	
	public ChangeMapEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		path = info.getString("path");
		
	}
	
	@Override
	public boolean runEvent () {
		Room.loadRoom(path);
		return false;
	}

}
