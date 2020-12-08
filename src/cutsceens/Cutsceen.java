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
				availableData.add(new CutsceneData(new MoveSlowEvent (objToMove,desX,desY,startVelocity,middleVelocity,endVelocity,startAcceleration,endAcceleration)));
				comands.add("moveSlow");
				break;
			case "sound":
				//get filepath
				String path = event.getString("path");
				
				//do the thing
				comands.add("sound");
				availableData.add(new CutsceneData (new Playsound ()));
				comands.add(path);
				break;
			case "music":
				//get filepath
				String file = event.getString("path");
				//do the thing
				comands.add("music");
				comands.add(file);
				break;
			case "text":
				//get filepath
				String text = event.getString("text");
				//do the thing
				comands.add("text");
				availableData.add(new CutsceneData(new MakeText()));
				comands.add(text);
				if (event.getString("color") != null) {
					comands.add(event.getString("color"));
				} else {
					comands.add("Black");
				}
				if (event.getString("fontName") != null) {
					comands.add(event.getString("fontName"));
				} else {
					comands.add("normal");
				}
				if (event.getString("name") != null) {
					comands.add(event.getString("name"));
				} else {
					comands.add("null");
				}
				if (event.getString("time") != null) {
					comands.add(event.getString("time"));
				} else {
					comands.add("-1");
				}
				break;
			case "playScene":
				//get filepath
				String coolPath = event.getString("path");
				GameObject [] passedObjects = (GameObject []) event.getJSONArray ("passedObjects").getContents().toArray();
				//do the thing
				comands.add("playScene");
				availableData.add(new CutsceneData (new Cutsceen (coolPath,passedObjects)));
				break;
			case "changeMap":
				//get filepath
				String neatPath = event.getString("path");
				//do the thing
				comands.add("changeMap");
				comands.add(neatPath);
				break;
			case "animation":
				//get filepath and craft sprite
				String spritePath = event.getString("path");
				Sprite workingSprite = new Sprite (spritePath);
				
				// get gameObject
				GameObject workingObject = searchByName (event.getString("name")).obj;
				//do the thing
				comands.add("animation");
				availableData.add(new CutsceneData (workingObject.getSprite()));
				availableData.add(new CutsceneData (workingSprite));
				availableData.add(new CutsceneData (searchByGameObject (workingObject)));
				break;
			case "sprite":
				//get filepath and craft sprite
				String sPath = event.getString("path");
				Sprite wSprite = new Sprite (sPath);
				
				// get gameObject
				GameObject wObject = searchByName (event.getString("name")).obj;
				//do the thing
				comands.add("sprite");
				availableData.add(new CutsceneData (wSprite));
				availableData.add(new CutsceneData (searchByGameObject (wObject)));
				break;
			
			case "giveItem":
				CutsceneObject wItem = searchByName (event.getString("item"));
				int amount;
				if (event.get("amount") != null) {
					amount = event.getInt("amount");
				} else {
					amount = 1;
				}	
				comands.add("giveItem");
				comands.add(Integer.toString(amount));
				availableData.add(new CutsceneData(wItem));
				break;
			case "removeItem":
				CutsceneObject workingItem = searchByName (event.getString("item"));
				int wowDUDE;
				if (event.get("amount") != null) {
					wowDUDE = event.getInt("amount");
				} else {
					wowDUDE = 1;
				}
				comands.add("removeItem");
				comands.add(Integer.toString(wowDUDE));
				availableData.add(new CutsceneData(workingItem));
				break;
			case "removeMoney":
				int wowBRO;
				if (event.get("amount") != null) {
					wowBRO = event.getInt("amount");
				} else {
					wowBRO = 1;
				}
				comands.add("removeMoney");
				comands.add(Integer.toString(wowBRO));
				break;
			case "giveMoney":
				int amountOfWEXP;
				if (event.get("amount") != null) {
					amountOfWEXP = event.getInt("amount");
				} else {
					amountOfWEXP= 1;
				}
				comands.add("giveMoney");
				comands.add(Integer.toString(amountOfWEXP));
				break;
			case "removeWEXP":
				int wo;
				if (event.get("amount") != null) {
					wo = event.getInt("amount");
				} else {
					wo = 1;
				}
				comands.add("removeWEXP");
				comands.add(Integer.toString(wo));
				break;
			case "giveWEXP":
				int amountOfWEXP2;
				if (event.get("amount") != null) {
					amountOfWEXP2 = event.getInt("amount");
				} else {
					amountOfWEXP2= 1;
				}
				comands.add("giveWEXP");
				comands.add(Integer.toString(amountOfWEXP2));
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
				comands.add("break");
				comands.add(Integer.toString(amountOfShards));
				comands.add(Double.toString(minSpeed));
				comands.add(Double.toString(maxSpeed));
				comands.add(Double.toString(minDirection));
				comands.add(Double.toString(maxDirection));
				availableData.add(new CutsceneData (searchByGameObject (BEEEObject)));
				for (int m  = 0; m < workingBEEEE.size(); m++) {
					availableData.add(new CutsceneData (workingBEEEE.get(m)));
				}
				availableData.add(null);
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
				}
				comands.add("choice");
				comands.add(Integer.toString(choices.length));
				comands.add(Integer.toString(sceens.length));
				for (int d = 0; d < choices.length; d++) {
					comands.add((String)choices[d]);
				}
				for (int n = 0; n < sceens.length; n++) {
					if (objectsPerSceen != null) {
						availableData.add(new CutsceneData(new Cutsceen((String)sceens[n],(GameObject []) objectsPerSceen[n].toArray())));
					} else {
						availableData.add(new CutsceneData (new Cutsceen((String)sceens[n],null)));	
					}
				}
			break;
			case "hasItem":
				CutsceneObject itemToCheck = searchByName (event.getString("item"));
				String yesSceen = (String) event.getString("yesScene");
				int amountRequired;
				if (event.getString("amount") != null) {
					amountRequired = event.getInt("amount");
				} else {
					amountRequired = 1;
				}
				ArrayList objectsForScene = new ArrayList ();
					try {
						objectsForScene = event.getJSONArray("yes").getContents();
					} catch (NullPointerException e) {
						objectsForScene = null;
					}
					comands.add("checkItem");
					if (objectsForScene == null) {
					comands.add("0");
					}
					comands.add(yesSceen);
					comands.add(Integer.toString(amountRequired));
					availableData.add(new CutsceneData(itemToCheck));
					if (objectsForScene != null) {
						for (int v = 0; v < objectsForScene.size(); v++) {
							availableData.add(new CutsceneData(new CutsceneObject ((GameObject) objectsForScene.get(i))));
						}
					}
				break;	
			case "Join":
				CutsceneObject jeffrey = searchByName (event.getString("Jeffrey"));
				JSONArray party = event.getJSONArray("party");
				comands.add("Join");
				for (int x = 0; x > 3; x++) {
					comands.add((String)party.get(x));	
				}
				availableData.add(new CutsceneData (jeffrey));
				break;
			case "Split":
				CutsceneObject theJeffster = searchByName (event.getString("Jeffrey"));
				JSONArray party1 = event.getJSONArray("party1");
				JSONArray party2 = event.getJSONArray("party2");
				comands.add("Split");
				for (int y = 0; y > 3; y++) {
					comands.add((String)party1.get(y));	
				}
				for (int z = 0; z > 3; z++) {
					comands.add((String)party2.get(z));	
				}
				availableData.add(new CutsceneData (theJeffster));
				break;
			case "changeParty":
				CutsceneObject jeff = searchByName (event.getString("Jeffrey"));
				JSONArray partae = event.getJSONArray("party");
				comands.add("changeParty");
				for (int g = 0; g > 3; g++) {
					comands.add((String)partae.get(g));	
				}
				availableData.add(new CutsceneData (jeff));
				break;	
			case "custom":
				//yeet yeet
				comands.add("custom");
				availableData.add(new CutsceneData (CutsceneEvent.makeCutsceneEvent(event)));
				break;
			}
			//may cause problems for make choice but it will probably be alright
			comands.add(Boolean.toString(startChain));
			comands.add(Boolean.toString(endChain));
			comands.add("dededededededededeedededeeeeeeeeeeeeeeed ah zombie indeed");
			availableData.add(new CutsceneData());
		}
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
				case "Join":
					keepGoing = this.runJoinCode(commandNumber,dataNumber);
					break;
				case "Split":
					keepGoing = this.runSplitCode(commandNumber,dataNumber);
					break;	
				case "changeParty":
					keepGoing = this.runChangePartyCode(commandNumber,dataNumber);
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
private boolean runJoinCode (int commandNumber, int dataNumber) {
	Jeffrey j = (Jeffrey)availableData.get(dataNumber).getObj().obj;
	boolean [] party = new boolean [] {Boolean.parseBoolean(comands.get(commandNumber + 1)),Boolean.parseBoolean(comands.get(commandNumber+ 2)),Boolean.parseBoolean(comands.get(commandNumber + 3))};
	j.joinPartys(party);
	return false;
}
private boolean runSplitCode (int commandNumber, int dataNumber) {
	Jeffrey j = (Jeffrey)availableData.get(dataNumber).getObj().obj;
	boolean [] party1 = new boolean [] {Boolean.parseBoolean(comands.get(commandNumber + 1)),Boolean.parseBoolean(comands.get(commandNumber+ 2)),Boolean.parseBoolean(comands.get(commandNumber + 3))};
	boolean [] party2 = new boolean [] {Boolean.parseBoolean(comands.get(commandNumber + 4)),Boolean.parseBoolean(comands.get(commandNumber+ 5)),Boolean.parseBoolean(comands.get(commandNumber + 6))};
	j.splitParty(party1, party2).declare(j.getX(), j.getY());
	return false;
}
private boolean runChangePartyCode (int commandNumber, int dataNumber) {
	Jeffrey j = (Jeffrey)availableData.get(dataNumber).getObj().obj;
	boolean [] party = new boolean [] {Boolean.parseBoolean(comands.get(commandNumber + 1)),Boolean.parseBoolean(comands.get(commandNumber+ 2)),Boolean.parseBoolean(comands.get(commandNumber + 3))};
	j.setParty(party);
	return false;
}

private boolean runCheckItemCode (int commandNumber,int dataNumber) {
	int objects = Integer.parseInt(comands.get(commandNumber + 1));
		if (Jeffrey.getInventory().checkItemAmount((Item) availableData.get(dataNumber).getObj().obj) >= Integer.parseInt(comands.get(commandNumber + 3))) {
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
		return !availableData.get(dataNumber).getTextAction().makeText(comands.get(commandNumber + 1),comands.get(commandNumber + 2), comands.get(commandNumber + 3),comands.get(commandNumber + 4),Integer.parseInt(comands.get(commandNumber + 5)));
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
		} 
		if (genMethod.equals ("hijack")) {
			if (!objData.getString ("type").equals ("Jeffrey")){
				obj = new CutsceneObject (ObjectHandler.getObjectsByName (objData.getString ("type")).get (0));
			} else {
				obj = new CutsceneObject (Jeffrey.getActiveJeffrey());
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
			obj = new CutsceneObject (Jeffrey.getJeffreyWithCharacter(0));
		} 
		if (genMethod.equals("getS")) {
			obj = new CutsceneObject (Jeffrey.getJeffreyWithCharacter(1));
		} 
		if (genMethod.equals("getR")){
			obj = new CutsceneObject (Jeffrey.getJeffreyWithCharacter(2));
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
			public CutsceneData (Object obj) {
				data = obj;
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
