package cutsceens;

import java.lang.reflect.InvocationTargetException;

import json.JSONArray;
import json.JSONObject;

public class CutsceneEvent {
	
	boolean chainStart = false;
	boolean chainEnd = false;

	public CutsceneEvent (Cutsceen partOf, JSONObject info) {
		
	}
	
	/**
	 * runs the event (make sure to adjust the comands when you are done with these lines 
	 * Jeffrey, scene != seen 
	 * 	cutsceen.customEvents.remove(0);
			cutsceen.comands.remove(0);
			cutseen.comands.remove(0);
	 * @param cutsceen figure it out dumbass
	 * @return true if the event has finished; false otherwise
	 */
	public boolean runEvent () {
		return false;
	}
	
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
	public boolean isChainStart() {
		return chainStart;
	}

	public void chainStart() {
		this.chainStart = true;
	}

	public boolean isChainEnd() {
		return chainEnd;
	}

	public void chainEnd() {
		this.chainEnd = true;
	}
}
