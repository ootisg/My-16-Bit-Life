package enemys;

import main.GameCode;
import main.ObjectHandler;
import players.Player;
import resources.Sprite;
import switches.Activateable;

public class MissleadingStop extends Enemy implements Activateable {
	boolean activated = true;
	public MissleadingStop () {
		this.setDeath(false);
		this.setSprite(new Sprite ("resources/sprites/config/Missleading_stop.txt"));
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
			if (this.health <= 0) {
				if (!Player.getInventory().checkKill(this)) {
					Player.getInventory().addKill(this);
				}
				enemyList.remove(this);		
				this.forget();
			}
			if (Player.getActivePlayer().getX() - this.getX() < 150 &&Player.getActivePlayer().getX() - this.getX() >= -150 && this.declared()) {
				Player.getActivePlayer().getStatus().applyStatus("Slowness", 1);
			}
		}
	}
	@Override 
	public String checkName () {
		return "MISSLEADING ROAD SIGN (STOP)";
	}
	@Override
	public String checkEntry () {
		return "ITS AN OCTOGON";
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
