package cutsceens;

import cutsceens.Cutsceen.CutsceneObject;
import items.Item;
import json.JSONObject;
import main.GameCode;
import players.Player;

public class GiveItemEvent extends CutsceneEvent {

	Item item = null;
	int amount;
	
	public GiveItemEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		CutsceneObject wItem = partOf.searchByName (info.getString("item"));
		
		if (info.get("amount") != null) {
			amount = info.getInt("amount");
		} else {
			amount = 1;
		}	
		item = (Item)wItem.obj;
		
	}
	
	@Override
	public boolean runEvent () {
		for (int i = 0; i < amount; i++){
			Player.getInventory().addItem(item);
			}
			return false;
	}

}
