package enemys;

import main.GameCode;
import main.ObjectHandler;
import players.Player;
import resources.Sprite;
import statusEffect.OneWayLeft;
import switches.Activateable;

public class MissleadingOneWay extends Enemy implements Activateable{
	Sprite signSprite;
	boolean activated = true;
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
		if (activated) {
			if (Player.getActivePlayer().getX() - this.getX() < 150 &&Player.getActivePlayer().getX() - this.getX() >= -150 ) {
				
				if (this.getAnimationHandler().flipHorizontal()) {
					Player.getActivePlayer().status.applyStatus("OneWayLeft", 1);
				} else {
					Player.getActivePlayer().status.applyStatus("OneWayRight", 1);
				}
			} 
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
	@Override
	public void activate() {
		activated = true;
	}
	@Override
	public void deactivate() {
		activated = false;
	}
	@Override
	public boolean isActivated() {
		return activated;
	}
	@Override
	public void pair() {
	
	}
}
