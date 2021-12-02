package fightingminigame;

import main.GameObject;

public class FightingObject extends GameObject {
	//TODO store complex data here used to deal with damage calcuations
	
	Fighter freindly; // the fighter that used the projectile
	double mass; //previously called "weight"
	public FightingObject (Fighter f) {
		this.setFreindly(f);
	}
	
	public void setFreindly (Fighter user) {
		this.freindly = user;
	}
	
	public Fighter getFreindly () {
		return freindly;
	}
	public void reAllowMovement(int move) {
		freindly.stopUsingMove(move);
	}
}
