package cutsceens;

import java.util.ArrayList;
import java.util.HashMap;

import actions.MakeText;
import actions.Playsound;
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
import players.Jeffrey;
import resources.Sprite;

public class Cutsceen extends GameObject {
	ArrayList<String> comands = new ArrayList <String>();
	ArrayList <CutsceneObject> objectsInScene = new ArrayList <CutsceneObject>();
	ArrayList <CutsceneData> availableData = new ArrayList <CutsceneData> ();
	ListTbox box = null;
	private boolean played = false;
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
				GameObject [] passedObjects = (GameObject []) event.getJSONArray ("passedObjects").getContents().toArray();
				//do the thing
				playSceen (coolPath,passedObjects,startChain,endChain);
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
			case "giveItem":
				CutsceneObject wItem = searchByName (event.getString("item"));
				int amount;
				if (event.get("amount") != null) {
					amount = event.getInt("amount");
				} else {
					amount = 1;
				}
					
				giveItem(wItem,amount,startChain,endChain);
				break;
			case "removeItem":
				CutsceneObject workingItem = searchByName (event.getString("item"));
				int wowDUDE;
				if (event.get("amount") != null) {
					wowDUDE = event.getInt("amount");
				} else {
					wowDUDE = 1;
				}
				removeItem(workingItem, wowDUDE, startChain,endChain);
			case "removeMoney":
				int wowBRO;
				if (event.get("amount") != null) {
					wowBRO = event.getInt("amount");
				} else {
					wowBRO = 1;
				}
				removeMoney(wowBRO, startChain,endChain);
			case "giveMoney":
				int amountOfWEXP;
				if (event.get("amount") != null) {
					amountOfWEXP = event.getInt("amount");
				} else {
					amountOfWEXP= 1;
				}
				giveMoney(amountOfWEXP, startChain,endChain);
				break;
			case "removeWEXP":
				int wo;
				if (event.get("amount") != null) {
					wo = event.getInt("amount");
				} else {
					wo = 1;
				}
				removeWEXP(wo, startChain,endChain);
			case "giveWEXP":
				int amountOfWEXP2;
				if (event.get("amount") != null) {
					amountOfWEXP2 = event.getInt("amount");
				} else {
					amountOfWEXP2= 1;
				}
				giveWEXP(amountOfWEXP2, startChain,endChain);
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
			case "choice":
				Object [] choices =  event.getJSONArray ("choices").getContents().toArray();
				Object [] sceens = event.getJSONArray ("sceens").getContents().toArray();
				ArrayList [] objectsPerSceen = new ArrayList [choices.length];
				for (int bee = 0; bee < objectsPerSceen.length; bee++) {
					try {
					for (int k = 0; k < event.getJSONArray((String)choices[bee]).getContents().size(); k++) {
						String working = (String) event.getJSONArray((String)choices[bee]).getContents().get(k);
							objectsPerSceen[bee].add(this.searchByName(working));
						}
					} catch (NullPointerException e) {
					objectsPerSceen[bee] = null;
					}
					this.makeChoice(choices, sceens, objectsPerSceen, startChain, endChain);
				}
			case "hasItem":
				CutsceneObject itemToCheck = searchByName (event.getString("item"));
				String yesSceen = (String) event.getString("yesScene");
				ArrayList objectsForScene = new ArrayList ();
					try {
						objectsForScene = event.getJSONArray("yes").getContents();
					} catch (NullPointerException e) {
						objectsForScene = null;
					}
					this.checkItem(yesSceen, objectsForScene, itemToCheck, startChain, endChain);
				break;	
			case "custom":
				//yeet yeet
				customCode(CutsceneEvent.makeCutsceneEvent(event),startChain,endChain);
				break;
			}
			comands.add("dededededededededeedededeeeeeeeeeeeeeeed ah zombie indeed");
			availableData.add(new CutsceneData());
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
		availableData.add(new CutsceneData(new MoveSlowEvent (objectToMove,desX,desY,startVelocity,middleVelocity,endVelocity,startAcceleration,endAcceleration)));
		comands.add("moveSlow");
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	/*=*/}
	/**
	 * runs diffrent cutscenes based on wheather or not the player has a specific item
	 */
	public void checkItem (String yesScene, ArrayList pasedObjects, CutsceneObject itemToCheck, boolean startChain, boolean endChain) {
		comands.add("checkItem");
		if (pasedObjects == null) {
		comands.add("0");
		}
		comands.add(yesScene);
		availableData.add(new CutsceneData(itemToCheck));
		if (pasedObjects != null) {
			for (int i = 0; i < pasedObjects.size(); i++) {
				availableData.add(new CutsceneData(new CutsceneObject ((GameObject) pasedObjects.get(i))));
			}
		}
		}
	/**
	 * plays a sound effect
	 * @param soundPath the filepath to the sound effect
	 */
	public void playSound (String soundPath, boolean startChain, boolean endChain) {
		comands.add("sound");
		availableData.add(new CutsceneData (new Playsound ()));
		comands.add(soundPath);
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	public void giveItem (CutsceneObject Item, int amount, boolean startChain, boolean endChain) {
		comands.add("giveItem");
		comands.add(Integer.toString(amount));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
		availableData.add(new CutsceneData(Item));
	}
	public void giveMoney ( int amount, boolean startChain, boolean endChain) {
		comands.add("giveMoney");
		comands.add(Integer.toString(amount));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	public void removeMoney ( int amount, boolean startChain, boolean endChain) {
		comands.add("removeMoney");
		comands.add(Integer.toString(amount));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	public void giveWEXP ( int amount, boolean startChain, boolean endChain) {
		comands.add("giveWEXP");
		comands.add(Integer.toString(amount));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	public void removeWEXP ( int amount, boolean startChain, boolean endChain) {
		comands.add("removeWEXP");
		comands.add(Integer.toString(amount));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	public void removeItem (CutsceneObject Item, int amount, boolean startChain, boolean endChain) {
		comands.add("removeItem");
		comands.add(Integer.toString(amount));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
		availableData.add(new CutsceneData(Item));
	}
	public void makeChoice (Object[] choices, Object[] sceens, ArrayList <GameObject> [] pasedObjects, boolean startChain, boolean endChain) {
		comands.add("choice");
		comands.add(Integer.toString(choices.length));
		comands.add(Integer.toString(sceens.length));
		for (int i = 0; i < choices.length; i++) {
			comands.add((String)choices[i]);
		}
		for (int i = 0; i < sceens.length; i++) {
			if (passedObjects != null) {
				availableData.add(new CutsceneData(new Cutsceen((String)sceens[i],(GameObject []) pasedObjects[i].toArray())));
			} else {
				availableData.add(new CutsceneData (new Cutsceen((String)sceens[i],null)));	
			}
		}
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
		availableData.add(new CutsceneData (searchByGameObject (objectToBreak)));
		for (int i  = 0; i < posibleShards.size(); i++) {
			availableData.add(new CutsceneData (posibleShards.get(i)));
		}
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
		availableData.add(null);
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
		availableData.add(new CutsceneData(new MakeText()));
		comands.add(text);
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	/**
	 * plays a diffrent cutsceen
	 * @param cutsceen
	 */
	public void playSceen (String cutsceen, GameObject [] passedObjects, boolean startChain, boolean endChain) {
		comands.add("playScene");
		availableData.add(new CutsceneData (new Cutsceen (cutsceen,passedObjects)));
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
		availableData.add(new CutsceneData (onWhat.getSprite()));
		availableData.add(new CutsceneData (animaiton));
		availableData.add(new CutsceneData (searchByGameObject (onWhat)));
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
		availableData.add(new CutsceneData (newSprite));
		availableData.add(new CutsceneData (searchByGameObject (onWhat)));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	public void customCode (CutsceneEvent sceen, boolean startChain, boolean endChain) {
		comands.add("custom");
		availableData.add(new CutsceneData (sceen));
		comands.add(Boolean.toString(startChain));
		comands.add(Boolean.toString(endChain));
	}
	/**
	 * returns true if the cutsceen is still playing
	 * and plays the cutsceen
	 * @return wheather or not the cutsceen is done playing
	 */
	public boolean play () {
		return this.play(0,0);
	}
	private boolean play (int commandNumber,int dataNumber) {

			if (!comands.isEmpty()) {
				boolean keepGoing = false;
				switch (comands.get(commandNumber)) {
				case "moveSlow":
					keepGoing = this.runMoveSlowCode(commandNumber,dataNumber);
					break;
				case "sound":
					keepGoing = this.runSoundCode(commandNumber,dataNumber);
					break;
				case "music":
					keepGoing = this.runMusicCode(commandNumber);
					break;
				case "text":
					keepGoing = this.runTextCode(commandNumber,dataNumber);
					break;
				case "playScene":
					keepGoing = this.runCutsceenCode(commandNumber,dataNumber);
					break;
				case "animation":
					keepGoing = this.runAniamtionCode(commandNumber,dataNumber);
					break;
				case "sprite":
					keepGoing = this.runSpriteCode(commandNumber,dataNumber);
					break;
				case "custom":
					keepGoing = this.runCustomCode(commandNumber,dataNumber);
					break;
				case "changeMap":
					keepGoing = this.runChangeMapCode (commandNumber);
					break;
				case "choice":
					keepGoing = this.runChoiceCode (commandNumber,dataNumber);
					break;
				case "giveItem":
					keepGoing = this.runAddItemCode(commandNumber,dataNumber);
					break;
				case "removeItem":
					keepGoing = this.runRemoveItemCode(commandNumber,dataNumber);
					break;
				case "giveWEXP":
					keepGoing = this.runAddWEXPCode(commandNumber);
					break;
				case "giveMoney":
					keepGoing = this.runAddMoneyCode(commandNumber);
					break;
				case "removeWEXP":
					keepGoing = this.runRemoveWEXPCode(commandNumber);
					break;
				case "removeMoney":
					keepGoing = this.runRemoveMoneyCode(commandNumber);
					break;
				case "checkItem":
					keepGoing = this.runCheckItemCode(commandNumber,dataNumber);
					break;
				case "break":
					keepGoing = this.runBreakCode (commandNumber,dataNumber);
					break;
				}
					if (!keepGoing) {
						//checks if it is the end of the chain
						if (Boolean.parseBoolean(comands.get(commandNumber + this.getAmountOfComandsUsed(commandNumber)))){
							//checks if it is the last event in the chain to conclude
							if (commandNumber == 0) {
								chaining = false;
							} else {
								//sets the new last event in the chian to the end of the chain if this concludes before anther event
								comands.set(commandNumber - 2, "true");
							}
						}
						
						//plays the event chained to this one one last time
						if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + this.getAmountOfComandsUsed(commandNumber)))) {
							this.flushComands(commandNumber);
							this.flushData(commandNumber);
							this.play(commandNumber, dataNumber);
						} else {
							this.flushComands(commandNumber);
							this.flushData(commandNumber);
						}
						return true;
					}
					//starts chaining if this is the start of a chain
					if (Boolean.parseBoolean(comands.get(commandNumber + this.getAmountOfComandsUsed(commandNumber) - 1))) {
						chaining = true;
					}
					//plays the next element in the chain if it is chaining
					if (chaining && !Boolean.parseBoolean(comands.get(commandNumber + this.getAmountOfComandsUsed(commandNumber)))) {
						this.play(commandNumber + this.getAmountOfComandsUsed(commandNumber) + 2, dataNumber + this.getAmountOfDataUsed(dataNumber) + 2);
					}
				return true;
			} else {
				comands.clear();
				availableData.clear();
				this.passedObjects = this.linkedObjects;
				this.consturcterCode(reconsturctionPath);
				return false;
			}
	}
	private void flushComands (int startNum) {
		while (true) {
			if (comands.get(startNum).equals("dededededededededeedededeeeeeeeeeeeeeeed ah zombie indeed")) {
				comands.remove(startNum);
				break;
			} else {
				comands.remove(startNum);
			}
		}
	}
	private void flushData (int startNum) {
		while (true) {
			if (availableData.get(startNum).isIdentifyer()) {
				availableData.remove(startNum);
				break;
			} else {
				availableData.remove(startNum);
			}
		}
	}
	private int getAmountOfComandsUsed (int startNum) {
		int workingNum = 1;
		while (true) {
			if (comands.get(startNum + workingNum).equals("dededededededededeedededeeeeeeeeeeeeeeed ah zombie indeed")) {
				return workingNum -1;
			} else {
				workingNum = workingNum + 1;
			}
		}
		
	}
	private int getAmountOfDataUsed (int startNum) {
		int workingNum = 1;
		while (true) {
			if (availableData.get(startNum + workingNum).isIdentifyer()) {
				return workingNum -1;
			} else {
				workingNum = workingNum + 1;
			}
		}
	}
private boolean runCheckItemCode (int commandNumber,int dataNumber) {
	int objects = Integer.parseInt(comands.get(commandNumber + 1));
		if (Jeffrey.getInventory().checkItem((Item) availableData.get(dataNumber).getObj().obj)) {
				GameObject [] working = new GameObject [objects];
				for (int i = 0; i < objects; i++) {
					working [i] = availableData.get(dataNumber + 1 + i).getObj().obj;
				}
				String employeeOfTheMonth = comands.get(commandNumber + 2);
				comands.clear();
				availableData.clear();
				passedObjects = working;
				this.consturcterCode(employeeOfTheMonth);
				
			} 
		return false;
	}
	private boolean runChoiceCode (int commandNumber, int dataNumber) {
		
		if (box == null) {
			String [] options = new String [Integer.parseInt(comands.get(commandNumber + 1))];
			for (int i = 0; i<options.length; i++) {
				options[i] = comands.get(commandNumber + i + 3);
			}
			box = new ListTbox (100,100,options);
		}
		if (box.getSelected() != -1) {
			if (box.declared()) {
				box.close();
			}
			if (!availableData.get(dataNumber + box.getSelected()).getScene().play()) {
				box = null;
				comands.clear();
			}
		}
		return true;
	}
	public boolean runMoveSlowCode(int commandNumber,int dataNumber) {
		return !availableData.get(dataNumber).getMoveSlowEvent().runEvent();
	}
	public boolean runSoundCode(int commandNumber,int dataNumber) {
		return availableData.get(dataNumber).getSoundAction().playSound(6F, comands.get(commandNumber +1));
			
	}
	public boolean runRemoveItemCode(int commandNumber,int dataNumber) {
		for (int i = 0; i < Integer.parseInt(comands.get(commandNumber + 1)); i++){
			Jeffrey.getInventory().removeItem((Item)availableData.get(dataNumber).getObj().obj);
			}
		return false;
	}
	public boolean runAddItemCode(int commandNumber,int dataNumber) {
		for (int i = 0; i < Integer.parseInt(comands.get(commandNumber + 1)); i++){
		Jeffrey.getInventory().addItem((Item)availableData.get(dataNumber).getObj().obj);
		}
		return false;
	}
	public boolean runRemoveWEXPCode(int commandNumber) {
		Jeffrey.getInventory().subractWEXP(Integer.parseInt(comands.get(commandNumber + 1)));
		return false;
	}
	public boolean runAddWEXPCode(int commandNumber) {
		Jeffrey.getInventory().addWEXP(Integer.parseInt(comands.get(commandNumber + 1)));
		return false;
	}
	public boolean runRemoveMoneyCode(int commandNumber) {
		Jeffrey.getInventory().subractMoney(Integer.parseInt(comands.get(commandNumber + 1)));
		return false;
	}
	public boolean runAddMoneyCode(int commandNumber) {
		Jeffrey.getInventory().addMoney(Integer.parseInt(comands.get(commandNumber + 1)));
		return false;
	}
	public boolean runMusicCode(int commandNumber) {
		GameCode.player.play(comands.get(commandNumber + 1), 6F);
		return false;
	}
	public boolean runChangeMapCode(int commandNumber) {
		Room.loadRoom(comands.get(commandNumber + 1));
		return false;
	}
	public boolean runTextCode(int commandNumber,int dataNumber) {
		return !availableData.get(dataNumber).getTextAction().makeText(comands.get(commandNumber + 1));
	}
	public boolean runCutsceenCode(int commandNumber,int dataNumber) {
		return availableData.get(dataNumber).getScene().play(); 
	}
	public boolean runAniamtionCode(int commandNumber,int dataNumber) {
		if (!availableData.get(dataNumber + 2).getObj().obj.getSprite().equals(availableData.get(dataNumber + 1).getSprite())) {
			availableData.get(dataNumber + 2).getObj().obj.setSprite(availableData.get(dataNumber + 1).getSprite());
		} else {
			if (availableData.get(dataNumber + 2).getObj().obj.getAnimationHandler().getFrame() == availableData.get(dataNumber).getObj().obj.getAnimationHandler().getImage().getFrameCount() - 1) {
				availableData.get(dataNumber + 2).getObj().obj.setSprite(availableData.get(dataNumber).getSprite());
				return false;
			}
		}
		return true;
	}
	public boolean runSpriteCode(int commandNumber, int dataNumber) {
		availableData.get(dataNumber + 1).getObj().obj.setSprite(availableData.get(dataNumber).getSprite());
		return false;
	}

	public boolean runBreakCode(int commandNumber,int dataNumber) {
		availableData.get(dataNumber).getObj().obj.setSprite(availableData.get(dataNumber + 1).getSprite());
		BreakableObject working = (BreakableObject)availableData.get(dataNumber).getObj().obj;
		int workingIndex = dataNumber + 1;
		while (true) {
			if (this.availableData.get(workingIndex) == null) {
				break;
			}
			workingIndex = workingIndex + 1;
		}
		Shard [] usableArray = new Shard [workingIndex - (dataNumber+ 1)];
		for (int i = 0; i < usableArray.length; i++) {
			usableArray[i] = new Shard (availableData.get(dataNumber + 1 + i).getSprite());
		}
		working.Break(usableArray, Integer.parseInt(comands.get(commandNumber + 1)), Double.parseDouble(comands.get(commandNumber + 2)), Double.parseDouble(comands.get(commandNumber + 3)), Double.parseDouble(comands.get(commandNumber + 4)), Double.parseDouble(comands.get(commandNumber + 5)));
		return false;
	}
	public boolean runCustomCode(int commandNumber, int dataNumber) {
		return availableData.get(dataNumber).getEvent().runEvent();
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
		} else if (genMethod.equals ("hijack")) {
			obj = new CutsceneObject (ObjectHandler.getObjectsByName (objData.getString ("type")).get (0));
		} else if (genMethod.equals("pass")) {
			for (int i = 0; i <passedObjects.length; i++) {
				if (passedObjects[i].getClass().getSimpleName().equals(objData.getString("type"))){
					obj = new CutsceneObject(passedObjects[i]);
				}
			}
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
		public boolean isPersistant () {
			return persistent;
		}
	}
	
	private static class CutsceneData  {
		
			Object data;
			public CutsceneData (CutsceneObject obj) {
				data = obj;
			}
			public CutsceneData (Sprite sprite) {
				data = sprite;
			}
			public CutsceneData (CutsceneEvent event) {
				data = event;
			}
			public CutsceneData (MoveSlowEvent event) {
				data = event;
			}
			public CutsceneData (Playsound player) {
				data = player;
			}
			public CutsceneData (MakeText text) {
				data = text;
			}
			public CutsceneData (Cutsceen scene) {
				data = scene;
			}
			public CutsceneData () {
				data = true;
			}
			public boolean isIdentifyer () {
				try {
					return (Boolean) data;
				} catch (ClassCastException e) {
					return false;
				}
			}
			public Cutsceen getScene () {
				return (Cutsceen) data;
			}
			public CutsceneObject getObj () {
				return (CutsceneObject) data;
			}
			public Sprite getSprite () {
				return (Sprite) data;
			}
			public CutsceneEvent getEvent () {
				return (CutsceneEvent) data;
			}
			public MoveSlowEvent getMoveSlowEvent () {
				return (MoveSlowEvent) data;
			}
			public Playsound getSoundAction () {
				return (Playsound) data;
			}
			public MakeText getTextAction () {
				return (MakeText) data;
			}
	}
	
}
