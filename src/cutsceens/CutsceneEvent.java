package cutsceens;

import java.lang.reflect.InvocationTargetException;

import json.JSONArray;
import json.JSONObject;

public interface CutsceneEvent {

	/**
	 * runs the event (make sure to adjust the comands when you are done with these lines 
	 * Jeffrey, scene != seen
	 * 	cutsceen.customEvents.remove(0);
			cutsceen.comands.remove(0);
			cutseen.comands.remove(0);
	 * @param cutsceen figure it out dumbass
	 * @return true if the event has finished; false otherwise
	 */
	public boolean runEvent ();
	
	public static CutsceneEvent makeCutsceneEvent (JSONObject object) {
		String customEventType = object.getString ("customEventType");
		String cutsceneTypename = "cutsceens." + customEventType;
		CutsceneEvent event = null;
		try {
			event = (CutsceneEvent) Class.forName (cutsceneTypename).getConstructor (JSONObject.class).newInstance(object);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event;
	}
}
