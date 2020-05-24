package cutsceens;

import java.util.ArrayList;
import java.util.HashMap;

import actions.MakeText;
import actions.Playsound;
import gameObjects.BreakableObject;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import resources.Sprite;

public class Cutsceen extends GameObject {
	ArrayList <CutsceneObject> objectsToHandle = new ArrayList <CutsceneObject> ();
	ArrayList <CutsceneObject> objectsInScene = new ArrayList<CutsceneObject> ();
	ArrayList <Cutsceen> cutsceensToHandle = new ArrayList <Cutsceen> ();
	ArrayList <Sprite> spritesToHandle = new ArrayList <Sprite> ();
	ArrayList <CutsceneEvent> customEvents = new ArrayList <CutsceneEvent> ();
	ArrayList <String> comands = new ArrayList <String> ();
	ArrayList <MoveSlowEvent> event = new ArrayList <MoveSlowEvent> ();
	ArrayList <Playsound> sound = new ArrayList <Playsound>();
	ArrayList <MakeText> text = new ArrayList <MakeText> ();
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
			case "changeMap":
				//get filepath
				String neatPath = event.getString("path");
				//do the thing
				changeMap (neatPath,startChain,endChain);
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
			case "break":
				//get filepath and craft sprite
				ArrayList <Object> spritePaths = event.getJSONArray("paths").getContents();
				ArrayList <Sprite> workingBEEEE = new ArrayList <Sprite>();
				int amountOfShards = event.getInt("shardNum");
				double minSpeed = 1;
				double maxSpeed = 3;
				double minDirection = 0;
				double maxDirection = 3.14;
				if (event.get("minSpeed") != null) {
					minSpeed = event.getDouble("minSpeed");
				}
				if (event.get("maxSpeed") != null) {
					maxSpeed = event.getDouble("maxSpeed");
				}
				if (event.get("minDirection") != null) {
					minDirection = event.getDouble("minDirection");
				}
				if (event.get("maxDirecion") != null) {
					maxDirection = event.getDouble("maxDirection");
				}
				for (int b = 0; b < spritePaths.size(); b++) {
					JSONObject workingThePrequil = (JSONObject) (spritePaths.get(b));
					String working = workingThePrequil.getString("path");
					workingBEEEE.add(new Sprite ("resources/sprites/" + working + ".png"));
				}
				
				
				// get gameObject
				BreakableObject BEEEObject = (BreakableObject) searchByName (event.getString("name")).obj;
				//do the thing
				breakObject (workingBEEEE,BEEEObject,amountOfShards,minSpeed,maxSpeed,minDirection,maxDirection,startChain,endChain);
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
	 * breaks an object into shards (only works if you object extends breakable object)
	 * @param posibleShards the sprites that the shards can be
	 * @param objectToBreak the object to break
	 * @param amountOfShards the amount of shards the object breaks into
	 * @param minSpeed the mininum speed of the shards
	 * @param maxSpeed the maximum speed of the shards
	 * @param minDirection the minimum direction of the object (in radians)
	 * @param maxDirection the maximum direction of the object (in radians)
	 */
	public void breakObject (ArrayList <Sprite> posibleShards,BreakableObject objectToBreak, int amountOfShards,double minSpeed, double maxSpeed, double minDirection, double maxDirection,boolean startChain,boolean endChain) {
		comands.add("break");
		comands.add(Integer.toString(amountOfShards));
		comands.add(Double.toString(minSpeed));
		comands.add(Double.toString(maxSpeed));
		comands.add(Double.toString(minDirection));
		comands.add(Double.toString(maxDirection));
		for (int i  = 0; i < posibleShards.size(); i++) {
		spritesToHandle.add(posibleShards.get(i));
		}
		objectsToHandle.add(searchByGameObject (objectToBreak));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
		spritesToHandle.add(null);
	}
	/**
	 * changes the map
	 * @param mapPath the path to the new Map
	 */
	public void changeMap (String mapPath, boolean startChain, boolean endChain) {
		comands.add("changeMap");
		comands.add(mapPath);
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
		return this.play(0,0,0,0,0,0,0,0);
	}
	private boolean play (int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber,  int slowEventNumber, int soundNumber,int textNumber) {
		if (!comands.isEmpty()) {
			switch (comands.get(commandNumber)) {
			case "moveSlow":
				this.runMoveSlowCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			case "sound":
				this.runSoundCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			case "music":
				this.runMusicCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			case "text":
				this.runTextCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			case "playScene":
				this.runCutsceenCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			case "animation":
				this.runAniamtionCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			case "sprite":
				this.runSpriteCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			case "custom":
				this.runCustomCode(commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			case "changeMap":
				this.runChangeMapCode (commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			case "break":
				this.runBreakCode (commandNumber,objectNumber,spriteNumber,cutsceenNumber,eventNumber,slowEventNumber,soundNumber,textNumber);
				break;
			}
			return true;
		} else {
			return false;
		}
	}
	public void runMoveSlowCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
		if (slowEventNumber == event.size()) {
		event.add(new MoveSlowEvent (objectsToHandle.get(objectNumber).obj, Integer.parseInt(comands.get(commandNumber + 1)), Integer.parseInt(comands.get(commandNumber + 2)), Double.parseDouble(comands.get(commandNumber + 3)), Double.parseDouble(comands.get(commandNumber + 4)), Double.parseDouble(comands.get(commandNumber + 5)), Double.parseDouble(comands.get(commandNumber + 6)), Double.parseDouble(comands.get(commandNumber + 7))));
		}
		if (event.get(slowEventNumber).runEvent()) {
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
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);	
			event.remove(slowEventNumber);
			objectsToHandle.remove(objectNumber);
			//plays the event chained to this one one last time
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
				comands.remove(commandNumber);
				this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
			} else {
				comands.remove(commandNumber);
			}
			return;
		}
		//starts chaining if this is the start of a chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 8))) {
			chaining = true;
		}
		//plays the next element in the chain if it is chaining
		if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 9))) {
			this.play(commandNumber + 10, objectNumber + 1, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber + 1,soundNumber,textNumber);
		}
	}
	public void runSoundCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
		if (soundNumber == sound.size()) {
			sound.add(new Playsound ());
			}
	
		if (sound.get(soundNumber).playSound(6F, comands.get(commandNumber + 1))) {
			//checks if it is the end of the chain
			if (Boolean.parseBoolean(comands.get(commandNumber + 3))){
				//checks if it is the last event in the chain to conclude
				if (commandNumber == 0) {
					chaining = false;
				} else {
					comands.set(commandNumber - 1, "true");
				}
			}
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			sound.remove(soundNumber);
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
				comands.remove(commandNumber);
				this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
			} else {
				comands.remove(commandNumber);
			}
			return;
		}
		//starts chaining if this is the start of a chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 2))) {
			chaining = true;
		}
		//plays the next element in the chain if it is chaining
		if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 3))) {
			this.play(commandNumber + 4, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber + 1,textNumber);
		}
		
	}
	public void runMusicCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
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
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
				comands.remove(commandNumber);
				this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
			} else {
				comands.remove(commandNumber);
			}
	}
	public void runChangeMapCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
		//changes the map
		Room.loadRoom(comands.get(commandNumber + 1));
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
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
				comands.remove(commandNumber);
				this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
			} else {
				comands.remove(commandNumber);
			}		
	}
	public void runTextCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
		if (textNumber == text.size()) {
			text.add(new MakeText ());
			}
		if (text.get(textNumber).makeText(comands.get(commandNumber + 1))) {
			//checks if it is the last event in the chain to conclude
			if (Boolean.parseBoolean(comands.get(commandNumber + 3))){
				if (commandNumber == 0) {
					chaining = false;
				} else {
					comands.set(commandNumber - 1, "true");
				}
			}
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			text.remove(textNumber);
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
				comands.remove(commandNumber);
				this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
			} else {
				comands.remove(commandNumber);
			}
			return;
		}
			//starts chaining if this is the start of a chain
			if (Boolean.parseBoolean(comands.get(commandNumber + 2))) {			
				chaining = true;
			}
			//plays the next element in the chain if it is chaining
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 3))) {
				this.play(commandNumber + 4, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber + 1);
			}
	}
	public void runCutsceenCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
		if (!cutsceensToHandle.get(cutsceenNumber).play()) {
			//checks if it is the last event in the chain to conclude
			if (Boolean.parseBoolean(comands.get(commandNumber + 2))){
				if (commandNumber == 0) {
					chaining = false;
				} else {
					comands.set(commandNumber - 1, "true");
				}
			}
			
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			cutsceensToHandle.remove(cutsceenNumber);
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
				comands.remove(commandNumber);
				this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
			} else {
				comands.remove(commandNumber);
			}
			return;
		}
		//starts chaining if this is the start of a chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 1))) {			
			chaining = true;
		}
		//plays the next element in the chain if it is chaining
		if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
			this.play(commandNumber + 3, objectNumber, spriteNumber, cutsceenNumber + 1, eventNumber,slowEventNumber,soundNumber,textNumber);
		}
	}
	public void runAniamtionCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
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
				
				spritesToHandle.remove(spriteNumber);
				spritesToHandle.remove(spriteNumber);
				objectsToHandle.remove(objectNumber);
				comands.remove(commandNumber);
				comands.remove(commandNumber);
				if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
					comands.remove(commandNumber);
					this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
				} else {
					comands.remove(commandNumber);
				}
				return;
			}
		}
			//starts chaining if this is the start of a chain
			if (Boolean.parseBoolean(comands.get(commandNumber + 1))) {			
				chaining = true;
			}
			//plays the next element in the chain if it is chaining
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
				this.play(commandNumber + 3, objectNumber + 1, spriteNumber + 2, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
			}
	}
	public void runSpriteCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
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
		comands.remove(commandNumber);
		comands.remove(commandNumber);
		objectsToHandle.remove(objectNumber);
		spritesToHandle.remove(spriteNumber);
		if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
			comands.remove(commandNumber);
			this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
		} else {
			comands.remove(commandNumber);
		}
	}

	public void runBreakCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
		
		objectsToHandle.get(objectNumber).obj.setSprite(spritesToHandle.get(spriteNumber));
		BreakableObject working = (BreakableObject)objectsToHandle.get(objectNumber).obj;
		int workingIndex = spriteNumber;
		while (true) {
			if (this.spritesToHandle.get(workingIndex) == null) {
				break;
			}
			workingIndex = workingIndex + 1;
		}
		Sprite [] usableArray = new Sprite [workingIndex - spriteNumber];
		for (int i = 0; i < usableArray.length; i++) {
			usableArray[i] = spritesToHandle.get(spriteNumber + i);
		}
		working.Break(usableArray, Integer.parseInt(comands.get(commandNumber + 1)), Double.parseDouble(comands.get(commandNumber + 2)), Double.parseDouble(comands.get(commandNumber + 3)), Double.parseDouble(comands.get(commandNumber + 4)), Double.parseDouble(comands.get(commandNumber + 5)));
		//checks if it is the end of the chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 7))){
			//checks if it is the last event in the chain to conclude
			if (commandNumber == 0) {
				chaining = false;
			} else {
				comands.set(commandNumber - 1, "true");
			}
		}
		//starts chaining if this is the start of a chain
		if (Boolean.parseBoolean(comands.get(commandNumber + 6))) {
			chaining = true;
		}
		comands.remove(commandNumber);
		comands.remove(commandNumber);
		comands.remove(commandNumber);
		comands.remove(commandNumber);
		comands.remove(commandNumber);
		comands.remove(commandNumber);
		comands.remove(commandNumber);
		objectsToHandle.remove(objectNumber);
		for (int i = 0; i < workingIndex - spriteNumber; i++) {
		spritesToHandle.remove(spriteNumber);
		}
		if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
			comands.remove(commandNumber);
			this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
		} else {
			comands.remove(commandNumber);
		}
	}
	public void runCustomCode(int commandNumber,int objectNumber, int spriteNumber, int cutsceenNumber, int eventNumber, int slowEventNumber, int soundNumber,int textNumber) {
	
		if (this.customEvents.get(eventNumber).runEvent()) {
				//checks if it is the last event in the chain to conclude
				if (Boolean.parseBoolean(comands.get(commandNumber + 2))){
					if (commandNumber == 0) {
						chaining = false;
					} else {
						comands.set(commandNumber - 1, "true");
					}
				}
			customEvents.remove(eventNumber);
			comands.remove(commandNumber);
			comands.remove(commandNumber);
			if (chaining && !Boolean.parseBoolean(comands.get(commandNumber))) {
				comands.remove(commandNumber);
				this.play(commandNumber, objectNumber, spriteNumber, cutsceenNumber, eventNumber,slowEventNumber,soundNumber,textNumber);
			} else {
				comands.remove(commandNumber);
			}
			return;
		}
		//starts chaining if this is the start of a chain
				if (Boolean.parseBoolean(comands.get(commandNumber + 1))) {			
					chaining = true;
				}
				//plays the next element in the chain if it is chaining
				if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + 2))) {
					this.play(commandNumber + 3, objectNumber, spriteNumber, cutsceenNumber, eventNumber + 1,slowEventNumber,soundNumber,textNumber);
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
