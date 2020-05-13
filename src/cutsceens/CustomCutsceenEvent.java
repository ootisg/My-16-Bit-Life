package cutsceens;

import json.JSONObject;

public interface CustomCutsceenEvent {
	/**
	 * runs the event (make sure to adjust the comands when you are done with these lines 
	 * 	cutsceen.customEvents.remove(0);
			cutsceen.comands.remove(0);
			cutseen.comands.remove(0);
	 * @param cutsceen
	 * @param object
	 */
	public void runEvent (Cutsceen cutsceen, JSONObject object);
}
