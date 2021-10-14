package cutsceens;

import json.JSONObject;

public class WaitEvent extends CutsceneEvent {
		
		int time;
		long startime = -1;
		public WaitEvent(Cutsceen partOf, JSONObject info) {
			super(partOf, info);
			
			time = info.getInt("time") * 1000;
			
		}
		
		@Override
		public boolean runEvent () {
			
			if (startime == -1) {
				startime = System.currentTimeMillis();
			} else {
				if (System.currentTimeMillis() > startime + time) {
					return true;
				}
			}
			return false;
		}

	}
