package cutsceens;

import json.JSONObject;
import main.GameCode;
import main.GameObject;
import resources.Sprite;

public class AnimationEvent extends CutsceneEvent {

	Sprite savedSprite;
	GameObject savedObject;
	
	public AnimationEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		//get filepath and craft sprite
		savedSprite = new Sprite (info.getString("path"));
		
		// get gameObject
		savedObject = partOf.searchByName (info.getString("name")).obj;
		
	}
	
	@Override
	public boolean runEvent () {
		if (!savedObject.getSprite().equals(savedSprite)) {
			savedObject.setSprite(savedSprite);
		} else {
			if (savedObject.getAnimationHandler().getFrame() == savedObject.getAnimationHandler().getImage().getFrameCount() - 1) {
				savedObject.setSprite(savedSprite);
				return false;
			}
		}
		return true;
	}

}
