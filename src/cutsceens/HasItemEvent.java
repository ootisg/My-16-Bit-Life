package cutsceens;

import java.util.ArrayList;

import cutsceens.Cutsceen.CutsceneObject;
import gui.ListTbox;
import items.Item;
import json.JSONObject;
import main.GameObject;
import players.Player;

public class HasItemEvent extends CutsceneEvent{
	
	Cutsceen yesSceen;
	Cutsceen noSceen;
	Item itemToCheck;
	int amountRequired = 1;
	
	public HasItemEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		itemToCheck = (Item)partOf.searchByName (info.getString("item")).obj;
		
		ArrayList objectsForYesScene = new ArrayList ();
		ArrayList objectsForNoScene = new ArrayList ();		
		
		if (info.getString("amount") != null) {
			amountRequired = info.getInt("amount");
		} 
		
		try {
			objectsForYesScene = info.getJSONArray("yes").getContents();
		} catch (NullPointerException e) {
			
		}
		GameObject [] working = new GameObject [objectsForYesScene.size()];
		for (int i = 0; i < objectsForYesScene.size(); i++) {
			working [i] = (GameObject)objectsForYesScene.get(i);
		}
		yesSceen = new Cutsceen ((String) info.getString("yesScene"),working);
		
		try {
			objectsForNoScene = info.getJSONArray("no").getContents();
		} catch (NullPointerException e) {
			
		}
		
		if (info.getString("noScene") != null) {
			GameObject [] workingNo = new GameObject [objectsForNoScene.size()];
			for (int i = 0; i < objectsForNoScene.size(); i++) {
				workingNo [i] = (GameObject)objectsForNoScene.get(i);
			}
			noSceen = new Cutsceen ((String) info.getString("noScene"),workingNo);
		}
		
	}
	
	@Override
	public boolean runEvent () {
		if (Player.getInventory().checkItemAmount(itemToCheck.getClass().getSimpleName()) >= amountRequired) {
			return yesSceen.play();	
		} else {
			if (noSceen != null) {
				return noSceen.play();
			} else {
				return false;
			}
		}
	}
	
	
}
