package enemys;

import resources.Sprite;

public class LivingPeriod extends Enemy{
	public Sprite period;
	public LivingPeriod () {
	period = new Sprite ("resources/sprites/config/Period.txt");
	this.setSprite(period);
	this.setHitboxAttributes(0, 0, 5, 4);
	this.setHealth(20);
	this.setFalls(true);
	}
	@Override 
	public String checkName () {
		return "LIVING PERIOD";
	}
	@Override
	public String checkEntry () {
		return "ITS FRUSTATING THAT YOU CAN'T NAME TWO CLASSES THAT SAME THING BECAUSE THIS GAME ALREADY HAD A \" PERIOD.JAVA \" SO I HAD TO IMPROVISE";
	}
	@Override
	public void enemyFrame () {
		this.jumpyBoi(period, period, 3, 6, 2);
	}
	
}
