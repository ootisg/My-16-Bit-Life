package cutsceens;

import java.util.ArrayList;

import gameObjects.BreakableObject;
import gameObjects.Shard;
import json.JSONObject;
import resources.Sprite;

public class BreakEvent extends CutsceneEvent{
	String path;

	
	ArrayList <Sprite> workingBEEEE;
	
	double minSpeed = 1;
	double maxSpeed = 3;
	double minDirection = 0;
	double maxDirection = 3.14;
	int amountOfShards;

	BreakableObject BEEEObject;
	
	public BreakEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		//get filepath and craft sprite
		ArrayList <Object> spritePaths = info.getJSONArray("paths").getContents();
		workingBEEEE = new ArrayList <Sprite>();
		amountOfShards = info.getInt("shardNum");
		
		if (info.get("minSpeed") != null) {
			minSpeed = info.getDouble("minSpeed");
		}
		if (info.get("maxSpeed") != null) {
			maxSpeed = info.getDouble("maxSpeed");
		}
		if (info.get("minDirection") != null) {
			minDirection = info.getDouble("minDirection");
		}
		if (info.get("maxDirecion") != null) {
			maxDirection = info.getDouble("maxDirection");
		}
		for (int b = 0; b < spritePaths.size(); b++) {
			JSONObject workingThePrequil = (JSONObject) (spritePaths.get(b));
			String working = workingThePrequil.getString("path");
			workingBEEEE.add(new Sprite ("resources/sprites/" + working + ".png"));
		}
		
		// get gameObject
		BEEEObject = (BreakableObject) partOf.searchByName (info.getString("name")).obj;
		
	}
	
	@Override
	public boolean runEvent () {
		BEEEObject.setSprite(workingBEEEE.get(0));
		
		Shard [] usableArray = new Shard [workingBEEEE.size()];
		for (int i = 0; i < usableArray.length; i++) {
			usableArray[i] = new Shard (workingBEEEE.get(i));
		}
		BEEEObject.Break(usableArray, amountOfShards,minSpeed,maxSpeed,minDirection,maxDirection);
		return false;
	}

}
