package triggers;

import cutsceens.Cutsceen;
import cutsceens.CutsceenHandler;
import main.ObjectHandler;

public class CutsceenTrigger extends Trigger {
	Cutsceen associatedCutsceen;
	public CutsceenTrigger () {
		 this.setHitboxAttributes(0, 0, 16, 16);
		
	}
	@Override
	public void triggerEvent () {
	ObjectHandler.pause(true);
	this.Triggered = true;
	 if (this.getVariantAttribute("cutsceen") != null) {
			associatedCutsceen = new Cutsceen ( "resources/cutsceenConfig/" + this.getVariantAttribute("cutsceen") + ".txt");
		 } else {
			 associatedCutsceen = new Cutsceen ("resources/cutsceenConfig/misconfiguredSceen.txt"); 
		}
	}
	@Override 
	public void pausedEvent () {
		if (this.Triggered()) {
		if (!associatedCutsceen.play()) {
			ObjectHandler.pause(false);
			this.eventFinished = true;
			}
		}
	}
}
