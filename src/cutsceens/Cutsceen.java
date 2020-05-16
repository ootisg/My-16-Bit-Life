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
	public Cutsceen (String filepath) {
		JSONObject sceneData = CutsceenLoader.getCutscene (filepath);
		JSONArray objsToUse = sceneData.getJSONArray ("usedObjects");
		JSONArray events = sceneData.getJSONArray ("events");
		
		//Get/add the objects to use
		objectsInScene = new ArrayList<CutsceneObject> ();
		ArrayList<Object> epicContents = events.getContents ();
		for (int i = 0; i < epicContents.size (); i++) {
			objectsInScene.add (generateCutsceneObject ((JSONObject)epicContents.get (i)));
		}
		
		//Load in the cutscene events
		ArrayList<Object> eventsList = events.getContents ();
		for (int i = 0; i < eventsList.size (); i++) {
			JSONObject event = (JSONObject)eventsList.get (i);
			//Switch statement babyyyyyyy
			switch (event.getString("type")) {
			case "moveSlow":
				//Get guarenteed params
				GameObject objToMove = searchByName (event.getString("name")).obj;
				int desX = event.getInt ("destinationX");
				int desY = event.getInt ("destinationY");
				double middleVelocity = event.getDouble ("middleVelocity");
				//Set defaults for optional params
				double startVelocity = 0;
				double endVelocity = 0;
				double startAcceleration = Double.POSITIVE_INFINITY;
				double endAcceleration = Double.NEGATIVE_INFINITY;
				//Set optional params if present
				if (event.get("startVelocity") != null) {
					startVelocity = event.getDouble("startVelocity");
				}
				if (event.get("endtVelocity") != null) {
					endVelocity = event.getDouble("endVelocity");
				}
				if (event.get("startAcceleration") != null) {
					startVelocity = event.getDouble("startAcceleration");
				}
				if (event.get("endAcceleration") != null) {
					startVelocity = event.getDouble("endAcceleration");
				}
				//Do the thing
				moveSlowly (objToMove, desX, desY, startVelocity, middleVelocity, endVelocity, startAcceleration, endAcceleration);
				break;
			case "sound":
				//get filepath
				String path = event.getString("path");
				//do the thing
				playSound (path);
				break;
			case "music":
				//get filepath
				String file = event.getString("path");
				//do the thing
				playMusic (file);
				break;
			case "text":
				//get filepath
				String text = event.getString("text");
				//do the thing
				makeTextBox (text);
				break;
			case "playScene":
				//get filepath
				String coolPath = event.getString("path");
				//do the thing
				makeTextBox (coolPath);
				break;
			case "animation":
				//get filepath and craft sprite
				String spritePath = event.getString("path");
				Sprite workingSprite = new Sprite (spritePath);
				
				// get gameObject
				GameObject workingObject = searchByName (event.getString("name")).obj;
				//do the thing
				playAnimation (workingSprite,workingObject);
				break;
			case "sprite":
				//get filepath and craft sprite
				String sPath = event.getString("path");
				Sprite wSprite = new Sprite (sPath);
				
				// get gameObject
				GameObject wObject = searchByName (event.getString("name")).obj;
				//do the thing
				changeSprite (wSprite,wObject);
				break;
			case "custom":
				//yeet yeet
				customCode(CutsceneEvent.makeCutsceneEvent(event));
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
	public void moveSlowly(GameObject objectToMove, int desX, int desY, double startVelocity, double middleVelocity, double endVelocity, double startAcceleration, double endAcceleration) {
		objectsToHandle.add(searchByGameObject (objectToMove));
		comands.add("moveSlow");
		comands.add(Integer.toString(desX));
		comands.add(Integer.toString(desY));
		comands.add(Double.toString(startVelocity));
		comands.add(Double.toString(middleVelocity));
		comands.add(Double.toString(endVelocity));
		comands.add(Double.toString(startAcceleration));
		comands.add(Double.toString(endAcceleration));
	/*=*/}
	/**
	 * plays a sound effect
	 * @param soundPath the filepath to the sound effect
	 */
	public void playSound (String soundPath) {
		comands.add("sound");
		comands.add(soundPath);
	}
	/**
	 * plays music
	 * @param soundPath the filepath of the song
	 */
	public void playMusic (String soundPath) {
		comands.add("music");
		comands.add(soundPath);
	}
	/**
	 * writes text to the screen by using a textbox
	 * @param text the message to write to the scren
	 */
	public void makeTextBox (String text) {
		comands.add("text");
		comands.add(text);
	}
	/**
	 * plays a diffrent cutsceen
	 * @param cutsceen
	 */
	public void playSceen (String cutsceen) {
		comands.add("playScene");
		cutsceensToHandle.add(new Cutsceen (cutsceen));
	}
	/**
	 * plays through an animation once before switching back to the old sprite
	 * @param animaiton the animation to play
	 * @param onWhat the gameObject that plays that animation
	 */
	public void playAnimation (Sprite animaiton, GameObject onWhat) {
		comands.add("animation");
		spritesToHandle.add(onWhat.getSprite());
		spritesToHandle.add(animaiton);
		objectsToHandle.add(searchByGameObject (onWhat));
	}
	/**
	 * changes the sprite of an object to a new sprite
	 * @param newSprite the new sprite of the object
	 * @param onWhat the object to change the sprite of
	 */
	public void changeSprite (Sprite newSprite, GameObject onWhat) {
		comands.add("sprite");
		spritesToHandle.add(newSprite);
		objectsToHandle.add(searchByGameObject (onWhat));
	}
	public void customCode (CutsceneEvent sceen) {
		comands.add("custom");
		customEvents.add(sceen);
	}
	/**
	 * returns true if the cutsceen is still playing
	 * and plays the cutsceen
	 * @return
	 */
	public boolean play () {
		if (!comands.isEmpty()) {
			switch (comands.get(0)) {
			case "moveSlow":
				this.runMoveSlowCode();
				break;
			case "sound":
				this.runSoundCode();
				break;
			case "music":
				this.runMusicCode();
				break;
			case "text":
				this.runTextCode();
				break;
			case "playScene":
				this.runCutsceenCode();
				break;
			case "animation":
				this.runAniamtionCode();
				break;
			case "sprite":
				this.runSpriteCode();
				break;
			case "custom":
				this.runCustomCode();
				break;
			}
			return true;
		} else {
			return false;
		}
	}
	public void runMoveSlowCode() {
		if (event == null) {
		event = new MoveSlowEvent (objectsToHandle.get(0).obj, Integer.parseInt(comands.get(1)), Integer.parseInt(comands.get(2)), Double.parseDouble(comands.get(3)), Double.parseDouble(comands.get(4)), Double.parseDouble(comands.get(5)), Double.parseDouble(comands.get(6)), Double.parseDouble(comands.get(7)));
		}
		if (event.runEvent()) {
			event = null;
			comands.remove(0);
			comands.remove(0);
			comands.remove(0);
			comands.remove(0);
			comands.remove(0);
			comands.remove(0);
			comands.remove(0);
			comands.remove(0);
			objectsToHandle.remove(0);
		}
	}
	public void runSoundCode() {
		if (sound == null) {
			sound = new Playsound ();
			}
		if (sound.playSound(6F, comands.get(1))) {
			sound = null;
			comands.remove(0);
			comands.remove(0);	
		}
		
	}
	public void runMusicCode() {
		GameCode.player.play(comands.get(1), 6F);
		comands.remove(0);
		comands.remove(0);
	}
	public void runTextCode() {
		if (MakeText.makeText(comands.get(1))) {
			comands.remove(0);
			comands.remove(0);
		}
	}
	public void runCutsceenCode() {
		if (!cutsceensToHandle.get(0).play()) {
			comands.remove(0);
			cutsceensToHandle.remove(0);
		}
	}
	public void runAniamtionCode() {
		if (!objectsToHandle.get(0).obj.getSprite().equals(spritesToHandle.get(1))) {
			objectsToHandle.get(0).obj.setSprite(spritesToHandle.get(1));
		} else {
			if (objectsToHandle.get(0).obj.getAnimationHandler().getFrame() == objectsToHandle.get(0).obj.getAnimationHandler().getImage().getFrameCount()) {
				objectsToHandle.get(0).obj.setSprite(spritesToHandle.get(0));
				spritesToHandle.remove(0);
				spritesToHandle.remove(0);
				objectsToHandle.remove(0);
				comands.remove(0);
			}
		}
	}
	public void runSpriteCode() {
		objectsToHandle.get(0).obj.setSprite(spritesToHandle.get(0));
		objectsToHandle.remove(0);
		spritesToHandle.remove(0);
		comands.remove(0);
	}
	public void runCustomCode() {
	
		if (this.customEvents.get(0).runEvent()) {
			customEvents.remove(0);
			comands.remove(0);
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
