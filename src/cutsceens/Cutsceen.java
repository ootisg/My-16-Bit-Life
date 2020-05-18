package cutsceens;

import java.util.ArrayList;
import java.util.HashMap;

import actions.MakeText;
import actions.Playsound;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import resources.Sprite;

public class Cutsceen extends GameObject {
	ArrayList <CutsceneObject> objectsToHandle = new ArrayList <CutsceneObject> ();
	ArrayList <CutsceneObject> objectsInScene = new ArrayList<CutsceneObject> ();
	ArrayList <Cutsceen> cutsceensToHandle = new ArrayList <Cutsceen> ();
	ArrayList <Sprite> spritesToHandle = new ArrayList <Sprite> ();
	ArrayList <CutsceneEvent> customEvents = new ArrayList <CutsceneEvent> ();
	ArrayList <String> comands = new ArrayList <String> ();
	MoveSlowEvent event;
	Playsound sound;
	boolean chaining = false;
	public Cutsceen (String filepath) {
		JSONObject sceneData = CutsceenLoader.getCutscene (filepath);
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
			//get chaining info
			boolean startChain = false;
			boolean endChain = false;
			if (event.get("chainInfo") != null) {
				if (event.get("chainInfo").equals("start")) {
					startChain = true;
				} else {
					endChain = true;
				}
			}
			//Switch statement babyyyyyyy
			switch (event.getString("type")) {
			case "moveSlow":
				//Get guarenteed params
				GameObject objToMove = searchByName (event.getString("name")).obj;
				int desX;
				int desY;
				if (event.get("destinationX") != null) {
				desX = event.getInt ("destinationX");
				} else {
				desX = (int) (event.getInt("offsetX") + objToMove.getX());
				}
				if (event.get("destinationY") != null) {
				desY = event.getInt ("destinationY");
				} else {
				desY = (int) (event.getInt("offsetY") + objToMove.getY());
				}
				double middleVelocity = event.getDouble ("middleVelocity");
				//Set defaults for optional params
				double startVelocity = 0;
				double endVelocity = 0;
				double startAcceleration = 2000000000;
				double endAcceleration = 1000000000;
			
				//Set optional params if present
				if (event.get("startVelocity") != null) {
					startVelocity = event.getDouble("startVelocity");
				}
				if (event.get("endVelocity") != null) {
					endVelocity = event.getDouble("endVelocity");
				}
				if (event.get("startAcceleration") != null) {
					startVelocity = event.getDouble("startAcceleration");
				}
				if (event.get("endAcceleration") != null) {
					startVelocity = event.getDouble("endAcceleration");
				}
				//Do the thing
				moveSlowly (objToMove, desX, desY, startVelocity, middleVelocity, endVelocity, startAcceleration, endAcceleration,startChain,endChain);
				break;
			case "sound":
				//get filepath
				String path = event.getString("path");
				
				//do the thing
				playSound (path,startChain,endChain);
				break;
			case "music":
				//get filepath
				String file = event.getString("path");
				//do the thing
				playMusic (file,startChain,endChain);
				break;
			case "text":
				//get filepath
				String text = event.getString("text");
				//do the thing
				makeTextBox (text,startChain,endChain);
				break;
			case "playScene":
				//get filepath
				String coolPath = event.getString("path");
				//do the thing
				makeTextBox (coolPath,startChain,endChain);
				break;
			case "animation":
				//get filepath and craft sprite
				String spritePath = event.getString("path");
				Sprite workingSprite = new Sprite (spritePath);
				
				// get gameObject
				GameObject workingObject = searchByName (event.getString("name")).obj;
				//do the thing
				playAnimation (workingSprite,workingObject,startChain,endChain);
				break;
			case "sprite":
				//get filepath and craft sprite
				String sPath = event.getString("path");
				Sprite wSprite = new Sprite (sPath);
				
				// get gameObject
				GameObject wObject = searchByName (event.getString("name")).obj;
				//do the thing
				changeSprite (wSprite,wObject,startChain,endChain);
				break;
			case "custom":
				//yeet yeet
				customCode(CutsceneEvent.makeCutsceneEvent(event),startChain,endChain);
				break;
			}
		}
	}
	/**
	 * moves an object to a destanation without just teleporting them there
	 * @param objectToMove the object to move
	 * @param desX the x coordinate of the destanation
	 * @param desY the y coordinate of the destaination
	 * @param startVelocity the velocity the object starts at
	 * @param middleVelocity the "terminal" velocity of the object
	 * @param endVelocity the velocity the object ends at
	 * @param startAcceleration the acceleration to use to speed up the object at the start of this event
	 * @param endAcceleration the acceleration to use to slow down the object at the end of this event
	 */
	public void moveSlowly(GameObject objectToMove, int desX, int desY, double startVelocity, double middleVelocity, double endVelocity, double startAcceleration, double endAcceleration, boolean startChain, boolean endChain) {
		objectsToHandle.add(searchByGameObject (objectToMove));
		comands.add("moveSlow");
		comands.add(Integer.toString(desX));
		comands.add(Integer.toString(desY));
		comands.add(Double.toString(startVelocity));
		comands.add(Double.toString(middleVelocity));
		comands.add(Double.toString(endVelocity));
		comands.add(Double.toString(startAcceleration));
		comands.add(Double.toString(endAcceleration));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	/*=*/}
	/**
	 * plays a sound effect
	 * @param soundPath the filepath to the sound effect
	 */
	public void playSound (String soundPath, boolean startChain, boolean endChain) {
		comands.add("sound");
		comands.add(soundPath);
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	/**
	 * plays music
	 * @param soundPath the filepath of the song
	 */
	public void playMusic (String soundPath, boolean startChain, boolean endChain) {
		comands.add("music");
		comands.add(soundPath);
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	/**
	 * writes text to the screen by using a textbox
	 * @param text the message to write to the scren
	 */
	public void makeTextBox (String text, boolean startChain, boolean endChain) {
		comands.add("text");
		comands.add(text);
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	/**
	 * plays a diffrent cutsceen
	 * @param cutsceen
	 */
	public void playSceen (String cutsceen, boolean startChain, boolean endChain) {
		comands.add("playScene");
		cutsceensToHandle.add(new Cutsceen (cutsceen));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	/**
	 * plays through an animation once before switching back to the old sprite
	 * @param animaiton the animation to play
	 * @param onWhat the gameObject that plays that animation
	 */
	public void playAnimation (Sprite animaiton, GameObject onWhat, boolean startChain, boolean endChain) {
		comands.add("animation");
		spritesToHandle.add(onWhat.getSprite());
		spritesToHandle.add(animaiton);
		objectsToHandle.add(searchByGameObject (onWhat));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	/**
	 * changes the sprite of an object to a new sprite
	 * @param newSprite the new sprite of the object
	 * @param onWhat the object to change the sprite of
	 */
	public void changeSprite (Sprite newSprite, GameObject onWhat, boolean startChain, boolean endChain) {
		comands.add("sprite");
		spritesToHandle.add(newSprite);
		objectsToHandle.add(searchByGameObject (onWhat));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	public void customCode (CutsceneEvent sceen, boolean startChain, boolean endChain) {
		comands.add("custom");
		customEvents.add(sceen);
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	/**
	 * returns true if the cutsceen is still playing
	 * and plays the cutsceen
	 * @return wheather or not the cutsceen is done playing
	 */
	public boolean play () {
		return this.play(0,0,0,0,0);
	}
	private boolean play (int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber) {
		if (!comands.isEmpty()) {
			switch (comands.get(commandNumber)) {
			case "moveSlow":
				this.runMoveSlowCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber);
				break;
			case "sound":
				this.runSoundCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber);
				break;
			case "music":
				this.runMusicCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber);
				break;
			case "text":
				this.runTextCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber);
				break;
			case "playScene":
				this.runCutsceenCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber);
				break;
			case "animation":
				this.runAniamtionCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber);
				break;
			case "sprite":
				this.runSpriteCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber);
				break;
			case "custom":
				this.runCustomCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber);
				break;
			}
			return true;
		} else {
			return false;
		}
	}
	public void runMoveSlowCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber) {
		if (event == null) {
		event = new MoveSlowEvent (objectsToHandle.get(objectNumber).obj, Integer.parseInt(comands.get(commandNumber + 1)), Integer.parseInt(comands.get(commandNumber + 2)), Double.parseDouble(comands.get(commandNumber + 3)), Double.parseDouble(comands.get(commandNumber + 4)), Double.parseDouble(comands.get(commandNumber + 5)), Double.parseDouble(comands.get(commandNumber + 6)), Double.parseDouble(comands.get(commandNumber + 7)));
		}
		if (event.runEvent()) {
			//checks if it is the end of the chain
			if (Boolean.parseBoolean(comands.get(commandNumber + 9))){
				//checks if it is the last event in the chain to conclude
				if (commandNumber == 0) {
					chaining = false;
				} else {
					//sets the new last event in the chian to the end of the chain if this concludes before anther event
					comands.set(commandNumber - 1, "true");
				}
			}
			//plays the event chained to this one one last time
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 9))) {
				this.play(commandNumber + 10, objectNumber + 1, spriteNumber, cutsceenNumber, eventNumber);
			}
			event = null;
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			objectsToHandle.remove(objectNumber);
			return;
		}
		//starts chaining if this is the start of a chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 8))) {
			chaining = true;
		}
		//plays the next element in the chain if it is chaining
		if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 9))) {
			this.play(commandNumber + 10, objectNumber + 1, spriteNumber, cutsceenNumber, eventNumber);
		}
	}
	public void runSoundCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber) {
		if (sound == null) {
			sound = new Playsound ();
			}
	
		if (sound.playSound(6F, comands.get(commandNumber + 1))) {
			sound = null;
			//checks if it is the end of the chain
			if (Boolean.parseBoolean(comands.get(commandNumber + 3))){
				//checks if it is the last event in the chain to conclude
				if (commandNumber == 0) {
					chaining = false;
				} else {
					comands.set(commandNumber - 1, "true");
				}
			}
			//plays the event chained to this one one last time
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 3))) {
				this.play(commandNumber + 4, objectNumber, spriteNumber, cutsceenNumber, eventNumber);
			}
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			return;
		}
		//starts chaining if this is the start of a chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 2))) {
			chaining = true;
		}
		//plays the next element in the chain if it is chaining
		if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 3))) {
			this.play(commandNumber + 4, objectNumber, spriteNumber, cutsceenNumber, eventNumber);
		}
		
	}
	public void runMusicCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber) {
		//plays the song
		GameCode.player.play(comands.get(commandNumber + 1), 6F);
		//checks if it is the end of the chain
			if (Boolean.parseBoolean(comands.get(commandNumber + 3))){
				//checks if it is the last event in the chain to conclude
				if (commandNumber == 0) {
					chaining = false;
				} else {
					comands.set(commandNumber - 1, "true");
				}
			}
			//starts chaining if this is the start of a chain
			if (Boolean.parseBoolean(comands.get(commandNumber + 2))) {
				chaining = true;
			}
			//plays the next element in the chain if it is chaining
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 3))) {
				this.play(commandNumber + 4, objectNumber, spriteNumber, cutsceenNumber, eventNumber);
			} 
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
		
		
		
	}
	public void runTextCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber) {
		if (MakeText.makeText(comands.get(commandNumber + 1))) {
			//checks if it is the last event in the chain to conclude
			if (Boolean.parseBoolean(comands.get(commandNumber + 3))){
				if (commandNumber == 0) {
					chaining = false;
				} else {
					comands.set(commandNumber - 1, "true");
				}
			}
			//plays the event chained to this one one last time
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 3))) {
				this.play(commandNumber + 4, objectNumber, spriteNumber, cutsceenNumber, eventNumber);
			}
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			return;
		}
			//starts chaining if this is the start of a chain
			if (Boolean.parseBoolean(comands.get(commandNumber + 2))) {			
				chaining = true;
			}
			//plays the next element in the chain if it is chaining
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 3))) {
				this.play(commandNumber + 4, objectNumber, spriteNumber, cutsceenNumber, eventNumber);
			}
	}
	public void runCutsceenCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber) {
		if (!cutsceensToHandle.get(cutsceenNumber).play()) {
			//checks if it is the last event in the chain to conclude
			if (Boolean.parseBoolean(comands.get(commandNumber + 2))){
				if (commandNumber == 0) {
					chaining = false;
				} else {
					comands.set(commandNumber - 1, "true");
				}
			}
			//plays the event chained to this one one last time
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
				this.play(commandNumber + 3, objectNumber, spriteNumber, cutsceenNumber, eventNumber);
			}
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			cutsceensToHandle.remove(cutsceenNumber);
			return;
		}
		//starts chaining if this is the start of a chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 1))) {			
			chaining = true;
		}
		//plays the next element in the chain if it is chaining
		if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
			this.play(commandNumber + 3, objectNumber, spriteNumber, cutsceenNumber + 1, eventNumber);
		}
	}
	public void runAniamtionCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber) {
		if (!objectsToHandle.get(objectNumber).obj.getSprite().equals(spritesToHandle.get(spriteNumber + 1))) {
			objectsToHandle.get(objectNumber).obj.setSprite(spritesToHandle.get(spriteNumber + 1));
		} else {
			if (objectsToHandle.get(objectNumber).obj.getAnimationHandler().getFrame() == objectsToHandle.get(objectNumber).obj.getAnimationHandler().getImage().getFrameCount()) {
				objectsToHandle.get(objectNumber).obj.setSprite(spritesToHandle.get(spriteNumber));
				//checks if it is the last event in the chain to conclude
				if (Boolean.parseBoolean(comands.get(commandNumber + 2))){
					if (commandNumber == 0) {
						chaining = false;
					} else {
						comands.set(commandNumber - 1, "true");
					}
				}
				//plays the event chained to this one one last time
				if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
					this.play(commandNumber + 3, objectNumber + 1, spriteNumber + 2, cutsceenNumber, eventNumber);
				}
				spritesToHandle.remove(spriteNumber);
				spritesToHandle.remove(spriteNumber);
				objectsToHandle.remove(objectNumber);
				comands.remove(commandNumber);
				comands.remove(commandNumber);
				comands.remove(commandNumber);
				return;
			}
		}
			//starts chaining if this is the start of a chain
			if (Boolean.parseBoolean(comands.get(commandNumber + 1))) {			
				chaining = true;
			}
			//plays the next element in the chain if it is chaining
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
				this.play(commandNumber + 3, objectNumber + 1, spriteNumber + 2, cutsceenNumber, eventNumber);
			}
	}
	public void runSpriteCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber) {
		objectsToHandle.get(objectNumber).obj.setSprite(spritesToHandle.get(spriteNumber));
		//checks if it is the end of the chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 2))){
			//checks if it is the last event in the chain to conclude
			if (commandNumber == 0) {
				chaining = false;
			} else {
				comands.set(commandNumber - 1, "true");
			}
		}
		//starts chaining if this is the start of a chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 1))) {
			chaining = true;
		}
		//plays the next element in the chain if it is chaining
		if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
			this.play(commandNumber + 3, objectNumber + 1, spriteNumber + 1, cutsceenNumber, eventNumber);
		} 
		comands.remove(commandNumber);
		comands.remove(commandNumber);
		comands.remove(commandNumber);
		objectsToHandle.remove(objectNumber);
		spritesToHandle.remove(spriteNumber);
	}
	public void runCustomCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber) {
	
		if (this.customEvents.get(eventNumber).runEvent()) {
				//checks if it is the last event in the chain to conclude
				if (Boolean.parseBoolean(comands.get(commandNumber + 2))){
					if (commandNumber == 0) {
						chaining = false;
					} else {
						comands.set(commandNumber - 1, "true");
					}
				}
				//plays the event chained to this one one last time
				if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
					this.play(commandNumber + 3, objectNumber, spriteNumber, cutsceenNumber, eventNumber + 1);
				}
			customEvents.remove(eventNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			return;
		}
		//starts chaining if this is the start of a chain
				if (Boolean.parseBoolean(comands.get(commandNumber + 1))) {			
					chaining = true;
				}
				//plays the next element in the chain if it is chaining
				if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
					this.play(commandNumber + 3, objectNumber, spriteNumber, cutsceenNumber, eventNumber + 1);
				}
		
		
	}
	private CutsceneObject generateCutsceneObject (JSONObject objData) {
		CutsceneObject obj = null;
		String genMethod = objData.getString ("genMethod");
		if (genMethod.equals ("create")) {
			obj = new CutsceneObject (ObjectHandler.getInstance (objData.getString ("type")));
			obj.obj.declare (objData.getInt ("x"), objData.getInt ("y"));
		} else if (genMethod.equals ("hijack")) {
			obj = new CutsceneObject (ObjectHandler.getObjectsByName (objData.getString ("type")).get (0));
		}
		if (obj == null) {
			return null;
		}
		
		//Set name/id field
		obj.id = objData.getString ("name");
		
		//Set persistence
		if (genMethod.equals ("hijack")) {
			obj.persistent = true;
		} else {
			obj.persistent = false;
		}
		if (objData.get ("persistent") != null) {
			if (objData.getString ("persistent").equals ("true")) {
				obj.persistent = true;
			} else {
				obj.persistent = false;
			}
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
	private static class CutsceneObject {
		
		public GameObject obj;
		public String id = "null";
		public boolean persistent = false;
		
		public CutsceneObject (GameObject obj) {
			this.obj = obj;
		}
		
	}
}
