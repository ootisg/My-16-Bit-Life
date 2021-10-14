package cutsceens;

import json.JSONObject;
import main.GameCode;
import main.GameObject;

public class PlaySceneEvent extends CutsceneEvent {
	
	Cutsceen toPlay;
	
	public PlaySceneEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		
		String path;
		
		GameObject [] passedObjects;
		
		path = info.getString("path");
		
		passedObjects = (GameObject []) info.getJSONArray ("passedObjects").getContents().toArray();
		
		toPlay = new Cutsceen(path,passedObjects);
	}
	
	@Override
	public boolean runEvent () {
		return toPlay.play(); 
	}

}
