package cutsceens;

import java.util.ArrayList;

import gui.ListTbox;
import json.JSONObject;
import main.GameObject;

public class ChoiceEvent extends CutsceneEvent{

	Object [] choices;
	Object [] sceens;
	Cutsceen chosenSceen = null;
	ListTbox box = null;
	
	public ChoiceEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		choices =  info.getJSONArray ("choices").getContents().toArray();
		sceens = info.getJSONArray ("sceens").getContents().toArray();
		ArrayList [] objectsPerSceen = new ArrayList [choices.length];
		for (int bee = 0; bee < objectsPerSceen.length; bee++) {
			try {
			for (int k = 0; k < info.getJSONArray((String)choices[bee]).getContents().size(); k++) {
				String working = (String) info.getJSONArray((String)choices[bee]).getContents().get(k);
					objectsPerSceen[bee].add(partOf.searchByName(working));
				}
			} catch (NullPointerException e) {
			objectsPerSceen[bee] = null;
			}
		}
	}
	
	@Override
	public boolean runEvent () {
		if (box == null) {
			String [] options = new String [choices.length];
			for (int i = 0; i<options.length; i++) {
				options[i] = (String)choices[i];
			}
			box = new ListTbox (100,100,options);
		}
		if (box.getSelected() != -1) {
			if (box.declared()) {
				box.close();
				chosenSceen = (Cutsceen) sceens[box.getSelected()];
			}
			if (!chosenSceen.play()) {
				box = null;
				return false;
			}
		}
		return true;
	}
	
	
}
