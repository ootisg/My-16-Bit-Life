package cutsceens;

import json.JSONObject;
import main.GameObject;

public class MoveSlowEvent extends CutsceneEvent {

	SlowMover slow;
	
	public MoveSlowEvent (Cutsceen partOf, JSONObject info) {
		super (partOf, info);
		//Get guarenteed params
		GameObject objToMove = partOf.searchByName (info.getString("name")).obj;
		int desX;
		int desY;
		if (info.get("destinationX") != null) {
			desX = info.getInt ("destinationX");
		} else {
			desX = (int) (info.getInt("offsetX") + objToMove.getX());
		}
		if (info.get("destinationY") != null) {
			desY = info.getInt ("destinationY");
		} else {
			desY = (int) (info.getInt("offsetY") + objToMove.getY());
		}
		double middleVelocity = info.getDouble ("middleVelocity");
		//Set defaults for optional params
		double startVelocity = 0;
		double endVelocity = 0;
		double startAcceleration = 2000000000;
		double endAcceleration = 1000000000;
	
		//Set optional params if present
		if (info.get("startVelocity") != null) {
			startVelocity = info.getDouble("startVelocity");
		}
		if (info.get("endVelocity") != null) {
			endVelocity = info.getDouble("endVelocity");
		}
		if (info.get("startAcceleration") != null) {
			startAcceleration = info.getDouble("startAcceleration");
		}
		if (info.get("endAcceleration") != null) {
			endAcceleration = info.getDouble("endAcceleration");
		}
		
		slow = new SlowMover (objToMove,desX,desY,startVelocity,middleVelocity,endVelocity,startAcceleration,endAcceleration);		
	}

	
	@Override
	public boolean runEvent () {
		return slow.runEvent();
	}
	

}
