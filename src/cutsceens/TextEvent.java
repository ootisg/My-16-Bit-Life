package cutsceens;


import gui.Textbox;
import json.JSONObject;
import main.GameCode;

public class TextEvent extends CutsceneEvent {

	
	Textbox box;
	
	int x = 100;
	int y = 100;
	
	public TextEvent(Cutsceen partOf, JSONObject info) {
		super(partOf, info);
		
		//get filepath
		String text = info.getString("text");
		//do the thing
		
		box = new Textbox (text);
		box.changeUnpause();
		
		
		if (info.getString("color") != null) {
			box.setBox(info.getString("color"));
		} else {
			box.setBox("Black");
		}
		if (info.getString("fontName") != null) {
			box.setFont(info.getString("fontName"));
		} else {
			box.setFont("normal");
		}
		if (info.getString("boxName") != null) {
			box.giveName(info.getString("boxName"));
		} 
		if (info.getString("time") != null) {
			box.setTime(info.getInt("time"));
		}
		
		if (info.getString("x") != null) {
			x = info.getInt("x");
		}
		
		if (info.getString("y") != null) {
			y = info.getInt("y");
		}
		
	}
	
	@Override
	public boolean runEvent () {
	
		if (!box.declared()) {
			box.declare(x, y);
		}
		if (box.isDone) {
			box.forget();
			box = null;
			return true;
		} else {
			return false;
		}
	}

}
