package cutsceens;

import json.JSONObject;
import main.GameCode;
import main.GameObject;
import resources.Sprite;

public class ChangeSpriteEvent extends CutsceneEvent {

	Sprite savedSprite;
	GameObject savedObject;
	
	public ChangeSpriteEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		//get filepath and craft sprite
		savedSprite = new Sprite (info.getString("path"));
		
		// get gameObject
		savedObject = partOf.searchByName (info.getString("name")).obj;
		
	}
	
	@Override
	public boolean runEvent () {
		savedObject.setSprite(savedSprite);
		return false;
	}

}
