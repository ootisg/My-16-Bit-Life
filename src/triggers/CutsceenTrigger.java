package triggers;


import cutsceens.Cutsceen;
import main.GameObject;
import main.ObjectHandler;

public class CutsceenTrigger extends Trigger {
	Cutsceen associatedCutsceen;
	public CutsceenTrigger () {
		 this.setHitboxAttributes(0, 0, 16, 16);
		
	}
	@Override
	public void triggerEvent () {
	
	if (!this.Triggered) {
		this.Triggered = true;
		 if (this.getVariantAttribute("cutsceen") != null) {
			 	if (this.getVariantAttribute("Partner") != null) {
			 		GameObject [] working = new GameObject [this.getPairedObject().getPairedObjects().toArray().length];
			 		for (int i = 0; i < this.getPairedObject().getPairedObjects().toArray().length; i++) {
			 			working[i] = (GameObject)this.getPairedObject().getPairedPairedObjects().toArray()[i];
			 		}
			 		associatedCutsceen = new Cutsceen ( "resources/cutsceenConfig/" + this.getVariantAttribute("cutsceen") + ".txt",working);
			 	} else {
			 		associatedCutsceen = new Cutsceen ( "resources/cutsceenConfig/" + this.getVariantAttribute("cutsceen") + ".txt");
			 	}
			 } else {
				 associatedCutsceen = new Cutsceen ("resources/cutsceenConfig/misconfiguredSceen.txt"); 
			}
		} else {
			if (!associatedCutsceen.play()) {
				this.eventFinished = true;
			}
		}	
	}
}
