package enemys;

import main.GameCode;
import resources.Sprite;
import statusEffect.Oneway;

public class MissleadingOneWay extends Enemy {
	Oneway finalWay;
	Sprite signSprite;
		public MissleadingOneWay () {
			signSprite = new Sprite ("resources/sprites/config/Missleading_oneway.txt");
			this.setSprite(signSprite);
			try{
				if (this.getVariantAttribute("Oriantation").equals("left")) {
				this.getAnimationHandler().setFlipHorizontal(true);
			}
		}catch (NullPointerException e) {
		}	
			this.health = 60;
			this.defence = 30;
			setHitboxAttributes (0,0,44,77);
	}
	@Override
		public void frameEvent () {
			if (GameCode.testJeffrey.getX() - this.getX() < 150 &&GameCode.testJeffrey.getX() - this.getX() >= -150 ) {
				finalWay = new Oneway (this.getAnimationHandler().flipHorizontal());
				finalWay.declare(0, 0);
			} else {
				GameCode.testJeffrey.status.statusAppliedOnJeffrey[1] = false;
				GameCode.testJeffrey.status.statusAppliedOnSam [1] = false;
				GameCode.testJeffrey.status.statusAppliedOnRyan[1] = false;
					GameCode.testJeffrey.bindLeft = false;	
					GameCode.testJeffrey.bindRight = false;
			}
		}
	@Override 
	public String checkName () {
		return "MISSLEADING ROAD SIGN (ONE WAY)";
	}
	@Override
	public String checkEntry () {
		return "YOU EVER WONDER WHO DESIGNED THE ROAD SIGNS BECAUSE THEY HAVE TO BE THE MOST FREQUENTLY PUBLISHED ARTIST OF ALL TIME AND THEY NEVER GET ANY RECOGNITION LIKE YOU THINK THEY CAN PUT THAT ON THERE RESUME. IF YOUR READING THIS ROAD SIGN GUY I KNOW A LOT OF PEOPLE DONT APPRECIATE YOUR WORK BUT AT LEAST I APRRECIATE IT";
	}
}
