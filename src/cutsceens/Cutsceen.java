package cutsceens;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;


import gameObjects.BreakableObject;
import gameObjects.Shard;
import gui.ListTbox;
import items.Item;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.Sprite;

public class Cutsceen extends GameObject {
	ArrayList <CutsceneObject> objectsInScene = new ArrayList <CutsceneObject>();
	
	ArrayList <CutsceneEvent> events = new ArrayList <CutsceneEvent>();
	
	ListTbox box = null;
	GameObject [] passedObjects;
	boolean chaining = false;
	String reconsturctionPath;
	GameObject [] linkedObjects;
	public Cutsceen (String filepath) {
		passedObjects = null;
		this.consturcterCode(filepath);
	}
	
	public Cutsceen (String filepath, GameObject [] passedObjects) {
		this.passedObjects = passedObjects;
		this.consturcterCode(filepath);
	}
	private void consturcterCode (String filepath) {
		JSONObject sceneData = CutsceenLoader.getCutscene (filepath);
		if (sceneData.getString("linkedScene") != null) {
			reconsturctionPath = sceneData.getString("linkedScene"); 
		} else {
			reconsturctionPath = filepath;
		}
		
		if (sceneData.getJSONArray("linkedObjects") != null) {
			linkedObjects = (GameObject []) sceneData.getJSONArray("linkedObjects").getContents().toArray();
		} else {
			linkedObjects = passedObjects;
		}
		JSONArray objsToUse = sceneData.getJSONArray ("usedObjects");
		JSONArray events = sceneData.getJSONArray ("events");
		//Get/add the objects to use
		objectsInScene = new ArrayList<CutsceneObject> ();
		ArrayList<Object> epicContents = objsToUse.getContents ();
		for (int i = 0; i < epicContents.size (); i++) {
			objectsInScene.add (generateCutsceneObject ((JSONObject)epicContents.get (i)));
		}
		
		//Load in the cutscene events
		ArrayList<Object> eventsList = events.getContents ();
		for (int i = 0; i < eventsList.size (); i++) {
			JSONObject event = (JSONObject)eventsList.get (i);
			
			
			Class<?> c;
			CutsceneEvent curr = null;
			try {
				String type = event.getString("type");
				type = type.substring(0,1).toUpperCase() + type.substring(1);
				c = Class.forName("cutsceens." + type + "Event");
				curr = (CutsceneEvent) c.getConstructors()[0].newInstance(this,event);
				this.events.add(curr);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | SecurityException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			//get chaining info
			
			if (event.get("chainInfo") != null) {
				if (event.get("chainInfo").equals("start")) {
					curr.chainStart();
				} else {
					curr.chainEnd();
				}
			}
		}	
	}

	

	/**
	 * returns true if the cutsceen is still playing
	 * and plays the cutsceen
	 * @return wheather or not the cutsceen is done playing
	 */
	public boolean play () {
		return this.play(0);
	}
	private boolean play (int eventNumber) {
			if (!events.isEmpty()) {
				boolean stop = events.get(eventNumber).runEvent();
					if (stop) {
						//checks if it is the end of the chain
						if (events.get(eventNumber).isChainEnd()){
							//checks if it is the last event in the chain to conclude
							if (eventNumber == 0) {
								chaining = false;
							} else {
								//sets the new last event in the chian to the end of the chain if this concludes before anther event
								 events.get(eventNumber - 1).chainEnd();
								 
							}
						}
						events.remove(eventNumber);
						return true;
					}
					//starts chaining if this is the start of a chain
					if (events.get(eventNumber).isChainStart()) {
						chaining = true;
					}
					//plays the next element in the chain if it is chaining
					if (chaining) {
						this.play(eventNumber + 1);
					}
				return true;
			} else {
				this.passedObjects = this.linkedObjects;
				this.consturcterCode(reconsturctionPath);
				return false;
			}
	}


	private CutsceneObject generateCutsceneObject (JSONObject objData) {
		CutsceneObject obj = null;
		String genMethod = objData.getString ("genMethod");
		if (genMethod.equals ("create")) {
			obj = new CutsceneObject (ObjectHandler.getInstance (objData.getString ("type")));
			double x = 0;
			double y = 0;
			try {
				x = objData.getDouble ("x");
			} catch (NumberFormatException | NullPointerException e) {
				
			}
			try {
				y = objData.getDouble ("y");
			} catch (NumberFormatException | NullPointerException e) {
				
			}
			if (objData.getString("declare") == null) {
				obj.obj.declare (x,y);
			} else {
				if (!objData.getString("declare").equals("no")) {
				obj.obj.declare (x,y);
				}
			}
		} 
		if (genMethod.equals ("hijack")) {
			if (!objData.getString ("type").equals ("Player")){
				obj = new CutsceneObject (ObjectHandler.getObjectsByName (objData.getString ("type")).get (0));
			} else {
				obj = new CutsceneObject (Player.getActivePlayer());
			}
		} 
		if (genMethod.equals("pass")) {
			for (int i = 0; i <passedObjects.length; i++) {
				if (passedObjects[i].getClass().getSimpleName().equals(objData.getString("type"))){
					obj = new CutsceneObject(passedObjects[i]);
				}
			}
		} 
		if (genMethod.equals("getJ")) {
			obj = new CutsceneObject (GameCode.getPartyManager().getPlayer(0));
		} 
		if (genMethod.equals("getS")) {
			obj = new CutsceneObject (GameCode.getPartyManager().getPlayer(1));
		} 
		if (genMethod.equals("getR")){
			obj = new CutsceneObject (GameCode.getPartyManager().getPlayer(2));
		}
		if (obj == null) {
			return null;
		}
		
		//Set name/id field
		obj.id = objData.getString ("name");
		
		//Set persistence
		if (objData.get ("persistent") != null) {
			if (objData.getString ("persistent").equals ("true")) {
				obj.persistent = true;
			} else {
				obj.persistent = false;
			}
		} else {
			obj.persistent = false;
		}	
		if (genMethod.equals ("hijack")) {
			obj.persistent = true;
		} else {
			obj.persistent = false;
		}
		//Return the final object
		return obj;
	}
	public CutsceneObject searchByGameObject (GameObject obj) {
		for (int i = 0; i < objectsInScene.size (); i++) {
			CutsceneObject cobj = objectsInScene.get (i);
			if (obj == cobj.obj) {
				return cobj;
			}
		}
		return null;
	}
	public CutsceneObject searchByName (String name) {
		for (int i = 0; i < objectsInScene.size (); i++) {
			CutsceneObject cobj = objectsInScene.get (i);
			if (cobj.id.equals (name)) {
				return cobj;
			}
		}
		return null;
	}
	
	public static class CutsceneObject {
		
		public GameObject obj;
		public String id = "null";
		public boolean persistent = false;
		
		public CutsceneObject (GameObject obj) {
			this.obj = obj;
		}
		public boolean isPersistant () {
			return persistent;
		}
	}
	
}
