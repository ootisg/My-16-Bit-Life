package cutsceens;

import json.JSONException;
import json.JSONObject;
import json.JSONUtil;

public class CutsceenLoader {
	/**
	 * {
	"usedObjects": [
		{
		everything in currly brace represents one object the usedObjects array contains all of those
			"name": "objectName"
			"type": "objectType" (refers to what kind of object it is ie CreepyButterfly or Zombee)
			"genMethod": "clone"
			"persistent":"true" (whether or not the object sticks around after the cutscene)
		}

			... (for the sake of the example there is only one object)		
	],
	"events": [
		{
			"type": action
			"otherStuff": "varies" (each action requires diffrent information)
		}
			... (list the actions in order to make them happen in that order)
	]	
}

	 * @param filepath the path to the JSON formated file
	 * @return the cutsceen 
	 */
	public static JSONObject getCutscene (String filepath) {
		try {
			return JSONUtil.loadJSONFile(filepath);
		} catch (JSONException e) {
			return null;
		}
	}
	
}
