package gameObjects;

import java.util.ArrayList;

import main.ObjectHandler;

public class BattleTrigger extends Trigger {
	ArrayList <Enemy> enemysToSpawn = new ArrayList <Enemy>();
	double [] xCoordinates;
	double [] yCoordinates;
	int timer;
	boolean enemysSpawned;
	 public BattleTrigger() {
		 xCoordinates = new double [200];
		 yCoordinates = new double [200];
		 timer = 0;
	}
	@Override
	public void frameEvent () {
		if (timer == 0) {
			int uppyThing = 0;
			while (uppyThing < ObjectHandler.getObjectsByName("HitboxRightBottomBound").size()) {
			if(ObjectHandler.getObjectsByName("HitboxRightBottomBound").get(uppyThing).getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner"))){
				this.setHitboxAttributes(0, 0, (int) (ObjectHandler.getObjectsByName("HitboxRightBottomBound").get(uppyThing).getX()- this.getX()), (int) (ObjectHandler.getObjectsByName("HitboxRightBottomBound").get(uppyThing).getY() + 1- this.getY()));
				break;
			}
			uppyThing = uppyThing + 1;
		}
			int counter = Enemy.enemyList.size();
			int x = 0;
			for (int i = 0; i <= counter -1; i++) {
				if (this.isColliding(Enemy.enemyList.get(i))) {
				xCoordinates [x] = Enemy.enemyList.get(i).getX();
				yCoordinates [x] = Enemy.enemyList.get(i).getY();
				enemysToSpawn.add(Enemy.enemyList.get(i));
				Enemy.enemyList.get(i).forget();
				x = x + 1;
				}
			}
			for (int i = 0; i <= enemysToSpawn.size() - 1; i++ ) {
				if (Enemy.enemyList.contains(enemysToSpawn.get(i))) {
					Enemy.enemyList.remove(enemysToSpawn.get(i));
				}
			}
		}
		timer = timer + 1;
	}
	public void triggerEvent () {
		if (!enemysSpawned) {
			for (int i = 0; i != enemysToSpawn.size(); i++) {
			Enemy.enemyList.add(enemysToSpawn.get(i));
			enemysToSpawn.get(i).declare(xCoordinates[i], yCoordinates[i]);
			}
			enemysSpawned = true;
		}
		for (int i = 0; i <= enemysToSpawn.size() - 1; i++) {
			if (!Enemy.enemyList.contains(enemysToSpawn.get(i))) {
				enemysToSpawn.remove(i);
			}	
		}
		if (enemysToSpawn.isEmpty()) {
			this.eventFinished = true;
		}
	}
}
