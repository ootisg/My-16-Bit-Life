package cutsceens;

import cutsceens.Cutsceen.CutsceneObject;
import items.Item;
import json.JSONObject;
import main.GameCode;
import players.Player;

public class RemoveMoneyEvent extends CutsceneEvent {

	int amount;
	
	public RemoveMoneyEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		if (info.get("amount") != null) {
			amount = info.getInt("amount");
		} else {
			amount = 1;
		}	
	}
	
	@Override
	public boolean runEvent () {
		Player.getInventory().subractMoney(amount);
		return false;
	}

}
