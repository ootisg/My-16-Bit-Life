package enemys;

import map.Room;
import players.Jeffrey;
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
public String checkName () {
	return "CLOSTRIDIUM BOWTIELINEA";
}
@Override
public String checkEntry () {
	return "SOME DUMB BACKSTORY ABOUT HORIBLE DESASES";
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
			if (Room.isColliding (this) && !isChecked){
				isChecked = true;
					if (firstCollision){
						secondCollison = true;
					} else{
						firstCollision = true;	
					}
			}
			if (isChecked && !(Room.isColliding(this))){
				isChecked = false;
			}
			if (this.getY() == 1 && !(Room.isColliding(this)) && firstCollision){
				secondCollison = true;
			}
		}
		setY(y_basis);
		if (!secondCollison && !Room.isColliding(this) && !(getY() == 10)){
			setY(getY() - 1);
		} else {
			notGoingUp = true;
		}
		if (!Room.isColliding(this) && !isStopped && (!(Jeffrey.getActiveJeffrey().getX() - this.getX() < 3) || !(this.getX() - Jeffrey.getActiveJeffrey().getX() < 3))){
		if (Jeffrey.getActiveJeffrey().getX()> getX()){
			setX(getX() + 3);
			while (Room.isColliding(this)){
				setX(getX() - 1);
				stopped = true;
			}
		} else {
			setX(getX() - 3);
			while (Room.isColliding(this)){
				setX(getX() + 1);
				stopped = true;
			}
		}
	} 
		if (this.getX()- 24<=Jeffrey.getActiveJeffrey().getX() && Jeffrey.getActiveJeffrey().getX()<=getX() + 24 && cooldown >= 6){
			virus = new Button();
			virus.declare(getX() + 12, getY() + 16);
			cooldown = 0;
		}
		cooldown = cooldown + 1;
		if ((notGoingUp && stopped) || isStopped ){
			isStopped = true;
			if (Jeffrey.getActiveJeffrey().getX()>= getX()){
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