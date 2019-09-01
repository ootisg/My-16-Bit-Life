package gameObjects;

import map.Room;
import projectiles.Button;
import resources.Sprite;

public class ClostridiumBowtielinea extends Enemy {
	
	public static final Sprite bowTie = new Sprite ("resources/sprites/config/Clostridium_bowtielinea_24x16.txt");
	
	private Button virus;
	private int cooldown;
	private boolean isStopped;
	private int timer;
public ClostridiumBowtielinea (){
	setSprite (bowTie);
	this.defence = 0;
	setHitboxAttributes(0, 0, 24, 16);
	getAnimationHandler().setFrameTime (166.7);
	this.health = 50;
	isStopped = false;
	timer = 0;
	}
	@Override
	public void enemyFrame (){
		boolean firstCollision = false;
		boolean secondCollison = false;
		boolean isChecked = false;
		boolean notGoingUp = false;
		boolean stopped = false;
		double y_basis = getY(); 
		while(getY() >= 0){
			setY(getY() - 1);
			if (Room.isColliding (this.hitbox()) && !isChecked){
				isChecked = true;
					if (firstCollision){
						secondCollison = true;
					} else{
						firstCollision = true;	
					}
			}
			if (isChecked && !(Room.isColliding(this.hitbox()))){
				isChecked = false;
			}
			if (this.getY() == 1 && !(Room.isColliding(this.hitbox())) && firstCollision){
				secondCollison = true;
			}
		}
		setY(y_basis);
		if (!secondCollison && !Room.isColliding(this.hitbox()) && !(getY() == 10)){
			setY(getY() - 1);
		} else {
			notGoingUp = true;
		}
		System.out.println(player.getX() - this.getX());
		if (!Room.isColliding(this.hitbox()) && !isStopped && (!(player.getX() - this.getX() < 3) || !(this.getX() - player.getX() < 3))){
		if (player.getX()> getX()){
			setX(getX() + 3);
			while (Room.isColliding(this.hitbox())){
				setX(getX() - 1);
				stopped = true;
			}
		} else {
			setX(getX() - 3);
			while (Room.isColliding(this.hitbox())){
				setX(getX() + 1);
				stopped = true;
			}
		}
	} 
		if (this.getX()- 24<=player.getX() && player.getX()<=getX() + 24 && cooldown >= 6){
			virus = new Button();
			virus.declare(getX() + 12, getY() + 16);
			cooldown = 0;
		}
		cooldown = cooldown + 1;
		if ((notGoingUp && stopped) || isStopped ){
			isStopped = true;
			if (player.getX()>= getX()){
				setX(getX() - 3);
			} else {
				setX(getX() + 3);
		}
	}		if (!notGoingUp){
		timer = timer + 1;
	}
			if (timer > 15){
				isStopped = false;
				timer = 0;
			}
}
}