package enemys;

import resources.Sprite;

public class ZombeeTree extends Enemy {
int timer;
String info;
Zombee bee;
	public ZombeeTree (){
		this.setSprite(new Sprite ("resources/sprites/Zombee tree.png"));
		this.setHitboxAttributes(22, 11, 70, 80);
		try {
		if (this.getVariantAttribute("Type").equals("Normal")) {
			this.health = 400;
			this.defence = 60;
			info = "N";
			}
			if (this.getVariantAttribute("Type").equals("Wood")) {
				this.health = 700;
				this.defence = 140;
				info = "W";
			}
			if (this.getVariantAttribute("Type").equals("PetrifiedWood")) {
				this.health = 1000;
				this.defence = 250;
				info = "PW";
			}
	} catch (NullPointerException e) {
		info = "N";
	}
		
			timer = 0;
	}
	@Override 
	public String checkName () {
		return "ZOMBEE TREE";
	}
	@Override
	public String checkEntry () {
		return "ZOMBEES TOOK OVER A TREE VERY SIMPLE BACKSTORY (UNLIKE THE ORIGINAL ENTRY FOR THIS WITCH WAS DUMB)";
	}
	@Override
	public void enemyFrame() {
		timer = timer + 1;
		if (timer == 80) {
			timer = 0;
			bee = new Zombee (info);
			bee.declare(this.getX(), this.getY());
		 }
	}
}
