package cutsceens;


import json.JSONArray;
import json.JSONObject;
import main.GameCode;

public class CreatePartyEvent extends CutsceneEvent {

	
	boolean [] party1;
	
	public CreatePartyEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		JSONArray party1 = info.getJSONArray("party");
		
		this.party1[0] = (Boolean)party1.get(0);
		this.party1[1] = (Boolean)party1.get(1);
		this.party1[2] = (Boolean)party1.get(2);
		
	}
	
	@Override
	public boolean runEvent () {
		GameCode.getPartyManager().createNewParty(party1);
		return false;
	}

}
