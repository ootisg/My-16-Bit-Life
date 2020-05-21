package statusEffect;

import enemys.Enemy;
import main.GameCode;
import main.GameObject;
import players.Jeffrey;
import resources.Sprite;
import statusEffect.Status;
public class Poison extends GameObject{
	Enemy affectedPerson;
	Jeffrey affectedMAN;
	int timer;
	int effectTimer;
	int level;
	Sprite poisoned;
	Sprite poisonedWalk;
	int charictarAtStart;
	boolean firstRun;
	public Poison(Enemy affected, int tier) {
		affectedPerson = affected;
		timer = 0;
		effectTimer = 0;
		level = tier;
		firstRun = true;
	}
	public Poison (Jeffrey affected, int tier){
		affectedMAN = affected; 
		poisoned = new Sprite ("resources/sprites/config/jeffrey_idle_poisoned.txt");
		poisonedWalk = new Sprite ("resources/sprites/config/jeffrey_walking_poisoned.txt");
		timer = 0;
		charictarAtStart = GameCode.testJeffrey.witchCharictar;
		effectTimer = 0;
		level = tier;
		affectedMAN.walkSprite = poisonedWalk;
		affectedMAN.standSprite = poisoned;
		firstRun = true;
	}
	@Override
	public void frameEvent (){
		affectedMAN.walkSprite = poisonedWalk;
		affectedMAN.standSprite = poisoned;
		if (firstRun) {
			if (charictarAtStart == 0) {
			GameCode.testJeffrey.status.statusAppliedOnJeffrey [0] = true;
			}
			if (charictarAtStart == 1) {
				GameCode.testJeffrey.status.statusAppliedOnSam [0] = true;	
			}
			firstRun = false;
		}
		if ((!GameCode.testJeffrey.status.statusAppliedOnJeffrey [0] && charictarAtStart == 0) || (!GameCode.testJeffrey.status.statusAppliedOnSam [0] && charictarAtStart == 1)) {
			this.forget();
		}
		if (GameCode.testJeffrey.witchCharictar == charictarAtStart) {
		if (affectedMAN.getSprite().equals(poisoned)) {
		}
		if( !(affectedPerson == null) && ((timer == 150 && level == 1) || (timer == 120 && level == 2) || (timer == 90 && level == 3) || (timer == 60 && level == 4))){
			affectedPerson.health = affectedPerson.health - affectedPerson.health/50;
			timer = 0;
     		}
		if ( !(affectedMAN == null) && ((timer == 150 && level == 1) || (timer == 120 && level == 2) || (timer == 90 && level == 3) || (timer == 60 && level == 4))){
			if (charictarAtStart == 0) {
			affectedMAN.jeffreyHealth = affectedMAN.jeffreyHealth - affectedMAN.jeffreyHealth/50;
			}
			if (charictarAtStart == 1) {
				affectedMAN.samHealth = affectedMAN.samHealth - affectedMAN.samHealth/50;	
			}
			timer = 0;
		}
		timer = timer + 1;
		effectTimer = effectTimer + 1;
		if (effectTimer == 600){
			affectedMAN.walkSprite = new Sprite ("resources/sprites/config/jeffrey_walking");
			affectedMAN.standSprite = new Sprite ("resources/sprites/config/jeffrey_idle");
			if (charictarAtStart == 0) {
				GameCode.testJeffrey.status.statusAppliedOnJeffrey [0] = false;
				}
				if (charictarAtStart == 1) {
					GameCode.testJeffrey.status.statusAppliedOnSam [0] = false;	
				}
			this.forget();
			}
	}
	}
}
