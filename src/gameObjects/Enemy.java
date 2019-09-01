package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;

public abstract class Enemy extends GameObject {
	//Template for enemies
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	public static String[] enemyList = new String [0];
	public int health = 1;
	protected double baseDamage = 2.5;
	public int defence;
	DamageText text;
	public Enemy () {
	}
	@Override
	public void frameEvent () {
		if (health <= 0) {
			this.deathEvent ();
		}
		enemyFrame ();
		if (isColliding (player)) {
			attackEvent ();
		}
	}
	public void enemyFrame () {
		
	}
	public void attackEvent () {
		player.damage (this.baseDamage);
	}
	public void deathEvent () {
		this.forget ();
	}
	public void damage (int amount) {
		text = new DamageText (amount, this.getX(), this.getY());
		text.declare(this.getX(), this.getY());
		amount = amount - defence;
		if(amount <= 0){
			amount = 1;
		}
		this.health = health - amount;
	}
	public void setHealth (int health) {
		this.health = health;
	}
	public int getHealth () {
		return this.health;
	}
}