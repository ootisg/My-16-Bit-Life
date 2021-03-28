package triggers;

import cutsceens.Cutsceen;
import main.GameCode;
import main.GameObject;
import main.RenderLoop;

public class ZoomTrigger extends RessesiveTrigger {
	int finalResX = -1;
	int finalResY = -1;
	public static final int DEFALUT_SPEED = 5;
	public ZoomTrigger () {
		 this.setHitboxAttributes(0, 0, 16, 16);
		
	}
	@Override
	public void triggerEvent () {
	
	if (!this.Triggered) {
		this.Triggered = true;
		 if (this.getVariantAttribute("scale") != null && finalResX == -1 && finalResY == -1) {
			 finalResX = (int)(GameCode.targetZoomX * Double.parseDouble(this.getVariantAttribute("scale")));
			 finalResY = (int)(GameCode.targetZoomY * Double.parseDouble(this.getVariantAttribute("scale")));
			 if (this.getVariantAttribute("speed") != null) {
				 GameCode.zoomTo(finalResX, finalResY, Integer.parseInt(this.getVariantAttribute("speed")));
			 } else {
				 GameCode.zoomTo(finalResX, finalResY, DEFALUT_SPEED);
			 }
		   }
		}
	if (GameCode.targetZoomX != finalResX || GameCode.targetZoomY != finalResY || (RenderLoop.window.getResolution()[0] == finalResX && RenderLoop.window.getResolution()[1] == finalResY)) {
		eventFinished = true;
	}
	}
}
