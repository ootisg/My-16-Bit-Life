package triggers;

import cutsceens.Cutsceen;

public class CutsceenTrigger extends Trigger {
	
	public CutsceenTrigger () {
	}
	@Override
	public void frameEvent () {
		if (Triggered) {
			Cutsceen.play(Integer.parseInt(this.getVariantAttribute("cutsceen")));
			if (Cutsceen.playing = false) {
				this.forget();
			}
		}
	}
	
}
