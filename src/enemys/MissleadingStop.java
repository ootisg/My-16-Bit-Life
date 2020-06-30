package enemys;

import main.GameCode;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;
import switches.Activateable;

public class MissleadingStop extends Enemy implements Activateable {
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
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
				if (!Jeffrey.getInventory().checkKill(this)) {
					Jeffrey.getInventory().addKill(this);
				}
				enemyList.remove(this);		
				this.forget();
			}
			if (j.getX() - this.getX() < 150 &&j.getX() - this.getX() >= -150 && this.declared()) {
				Jeffrey.status.statusAppliedOnJeffrey[3] = true;
				Jeffrey.status.statusAppliedOnRyan[3]= true;
				Jeffrey.status.statusAppliedOnSam[3] = true;
			} else {
				Jeffrey.status.statusAppliedOnJeffrey[3] = false;
				Jeffrey.status.statusAppliedOnRyan[3]= false;
				Jeffrey.status.statusAppliedOnSam[3] = false;
			}
		}
	}
	@Override 
	public String checkName () {
		return "MISSLEADING ROAD SIGN (STOP)";
	}
	@Override
	public String checkEntry () {
		return "ITS AN OCTOGON WHITCH HAS 1 2 3 4 5 6 7 8 EPIC SIDES AND 1 2 3 4 5 6 7 8 AWESOME ANGLES";
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
